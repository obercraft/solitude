package net.sachau.solitude;

import net.sachau.solitude.engine.Autowired;
import net.sachau.solitude.engine.Component;
import net.sachau.solitude.engine.View;
import org.reflections.Reflections;

import javax.annotation.PostConstruct;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ComponentManager {


    private Map<Class<?>, Object> beans = new ConcurrentHashMap<>();

    private static ComponentManager componentManager = new ComponentManager();

    public static ComponentManager getInstance() {
        return componentManager;
    }


    public void initComponents() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Reflections reflections = new Reflections();
        Set<Class<?>> dataBeanClasses = reflections.getTypesAnnotatedWith(Component.class);

        for (Class<?> dataBeanClass : dataBeanClasses) {

            Object dataBean = createBean(dataBeanClass);
            beans.put(dataBeanClass, dataBean);
            postConstruct(dataBean);

            beans.put(dataBeanClass, dataBean);

        }
    }

    public void initViews() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Reflections reflections = new Reflections();
        Set<Class<?>> dataBeanClasses = reflections.getTypesAnnotatedWith(View.class);

        for (Class<?> dataBeanClass : dataBeanClasses) {

            Object dataBean = createBean(dataBeanClass);
            beans.put(dataBeanClass, dataBean);
            postConstruct(dataBean);

            beans.put(dataBeanClass, dataBean);

        }
    }


    public <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }

    private Object createBean(Class beanClass) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Object bean;
        if ((bean = beans.get(beanClass)) != null) {
            // already created
            return bean;
        }
        Constructor<?>[] constructors = beanClass.getDeclaredConstructors();
        if (constructors != null && constructors.length > 0) {
            for (Constructor constructor : constructors) {
                Annotation annotation = constructor.getAnnotation(Autowired.class);
                if (annotation != null) {
                    Object[] parameters = new Object[constructor.getParameterTypes().length];
                    int i = 0;
                    for (Class parameterType : constructor.getParameterTypes()) {
                        Object existingBean = beans.get(parameterType);
                        if (existingBean != null) {
                            parameters[i] = existingBean;
                        } else {
                            Object newBean = createBean(parameterType);
                            parameters[i] = newBean;
                        }
                        i++;
                    }
                    Logger.debug("trying to create " + beanClass);
                    bean = constructor.newInstance(parameters);
                    beans.put(beanClass, bean);
                    Logger.debug("bean " + beanClass + " created");
                    return bean;
                }
            }
        }
        // default behaviour
        bean = beanClass.newInstance();
        beans.put(beanClass, bean);
        beans.put(beanClass, bean);
        return bean;

    }

    private void postConstruct(Object bean) {
        Method[] methods = bean.getClass()
                .getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation a : annotations) {
                if (a.annotationType()
                        .equals(PostConstruct.class)) {
                    try {
                        method.setAccessible(true);
                        Logger.debug(bean.getClass() + " invoking " + method.getName());
                        method.invoke(bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
