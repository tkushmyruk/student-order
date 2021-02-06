package ua.taras.kushmyruk.dao;


import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SimpleRunner {
    public static void main(String[] args) {
        SimpleRunner simpleRunner = new SimpleRunner();
        simpleRunner.runTest();
    }

    private void runTest() {
        try {
          Class clazz =   Class.forName("ua.taras.kushmyruk.dao.DictionaryDaoImplTest");
           Constructor constructor =  clazz.getConstructor();
           Object instance = constructor.newInstance();
            Method[] methods = clazz.getMethods();
            for (Method m : methods){
               Test annot =  m.getAnnotation(Test.class);
               if (annot != null) {
                   m.invoke(instance);
               }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
