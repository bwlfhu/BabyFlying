package com.allcam.babyflying.utils;


import android.util.Log;

import java.io.*;
import java.nio.charset.Charset;

public class IOUtil
{

    /**
     * 统一处理IO的关闭操作
     *
     * @param c
     */
    public static void closeIO(Closeable c)
    {
        if (null == c)
        {
            return;
        }

        try
        {
            c.close();
        }
        catch (IOException e)
        {
            Log.e("IOUtil", "closeIO fail", e);
        }
    }

    public static String readStringFormFile(String filePath)
    {
        byte[] bytes = readByteFormFile(filePath);
        return null == bytes ? "" : new String(bytes, Charset.forName("UTF-8"));
    }

    public static byte[] readByteFormFile(String filePath)
    {
        if (null == filePath)
        {
            return null;
        }

        ByteArrayOutputStream baos = null;
        FileInputStream fis = null;

        try
        {
            baos = new ByteArrayOutputStream();
            fis = new FileInputStream(filePath);
            transferStream(fis, baos);
            return baos.toByteArray();
        }
        catch (Exception e)
        {
            Log.e("IOUtil", "readByteFormFile fail", e);
            return null;
        }
        finally
        {
            closeIO(fis);
            closeIO(baos);
        }
    }

    public static void saveToFile(InputStream is, String filePath)
    {
        FileOutputStream fos = null;

        try
        {
            fos = new FileOutputStream(filePath);
            transferStream(is, fos);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeIO(is);
            closeIO(fos);
        }
    }

    public static void transferStream(InputStream is, OutputStream os) throws IOException
    {
        if (null == is || null == os)
        {
            Log.e("IOUtil", "stream is null");
            return;
        }

        int numread;
        byte[] buf = new byte[1024];

        while ((numread = is.read(buf)) != -1)
        {
            os.write(buf, 0, numread);
        }
        os.flush();
    }
}
