package com.ushahidi.android.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.ushahidi.android.data.api.Date;
import com.ushahidi.android.data.api.DateDeserializer;
import com.ushahidi.android.data.api.ValueDeserializer;
import com.ushahidi.android.data.entity.PostValueEntity;

import org.junit.Before;

import java.lang.reflect.Field;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class BaseTestCase {

    public Gson gson;

    @Before
    public void setUp() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        builder.registerTypeAdapter(Date.class, new DateDeserializer());
        builder.registerTypeAdapter(PostValueEntity.class, new ValueDeserializer());
        gson = builder.create();

    }

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
