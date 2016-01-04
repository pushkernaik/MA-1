package utils;

import java.nio.charset.Charset;

/**
 * Created by radhaprasadborkar on 17/12/15.
 */
public class Encoding {
    public static String getStringFromBytes(byte[] bytes )
    {
      return new String(bytes, Charset.forName("US-ASCII"));
    }
    public static byte[] getByteFromString(String string)
    {
        return string.getBytes(Charset.forName("US-ASCII"));
    }
}
