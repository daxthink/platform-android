package com.ushahidi.android.data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Ushahidi Team <team@ushahidi.com>
 */
public class TestHelper {

    public static String getResource(String resourceName) throws IOException {
        InputStream inputStream = TestHelper.class.getResourceAsStream("/" + resourceName);

        assert (inputStream != null);
        int n;
        byte[] buffer = new byte[81992];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((n = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, n);
        }

        return new String(bos.toByteArray());
    }
}
