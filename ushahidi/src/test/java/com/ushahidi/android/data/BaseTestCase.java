package com.ushahidi.android;

import java.lang.reflect.Field;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class BaseTestCase {

    /**
     * Resets a Singleton class. Uses Reflection to find a private field called sInstance then
     * nullifies the field.
     *
     * @param clazz The class to reset.
     */
    protected void clearSingleton(Class clazz) {
        Field instance;
        try {
            instance = clazz.getDeclaredField("sInstance");
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
