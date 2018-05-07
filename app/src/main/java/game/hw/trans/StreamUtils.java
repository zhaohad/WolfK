package game.hw.trans;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class StreamUtils {
    private static final String TAG = "HWGAME:StreamUtils";

    public static void writeString(OutputStream os, String v) {
        if (v == null) {
            writeInt(os, -1);
            return;
        }
        try {
            byte[] bytes = v.getBytes("utf-8");
            writeInt(os, bytes.length);
            os.write(bytes);
        } catch (IOException e) {
            Log.e(TAG, e.toString(), e);
        }
    }

    public static String readString(InputStream is) {
        byte[] buf = readNextDataBlock(is);
        if (buf == null) {
            return null;
        }

        try {
            return new String(buf, "utf-8");
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, e.toString(), e);
        }
        return null;
    }

    public static void writeBitmap(OutputStream os, Bitmap bmp) {
        if (bmp == null) {
            writeInt(os, -1);
            return;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        try {
            writeInt(os, baos.toByteArray().length);
            os.write(baos.toByteArray());
        } catch (IOException e) {
            Log.e(TAG, e.toString(), e);
        }
    }

    public static Bitmap readBitmap(InputStream is) {
        byte[] buf = readNextDataBlock(is);
        if (buf == null) {
            Log.e(TAG, "readBitmap buf, null");
            return null;
        }
        return BitmapFactory.decodeByteArray(buf, 0, buf.length);
    }

    public static byte[] readNextDataBlock(InputStream is) {
        int length = readInt(is);
        if (length < 0) {
            return null;
        }

        byte[] buf = new byte[length];
        boolean suc = readBlock(is, buf);
        if (!suc) {
            Log.e(TAG, "readNextDataBlock read failed");
        }
        return buf;
    }

    public static void writeInt(OutputStream os, int v) {
        int LOW_8 = 0xff;
        byte[] bytes = new byte[8];
        for (int i = 0; i < bytes.length; ++i) {
            byte low8 = (byte) (v & LOW_8);
            bytes[i] = low8;
            v = v >> 8;
        }
        try {
            os.write(bytes);
        } catch (IOException e) {
            Log.e(TAG, e.toString(), e);
        }
    }

    public static int readInt(InputStream is) {
        int v = 0;
        int LOW_8 = 0xff;
        byte[] bytes = new byte[8];
        boolean suc = readBlock(is, bytes);
        if (!suc) {
            Log.e(TAG, "readInt readFailed");
        }

        for (int i = 0; i < bytes.length; ++i) {
            v = (v << 8) + (bytes[bytes.length - i - 1] & LOW_8);
        }
        return v;
    }

    private static boolean readBlock(InputStream in, byte[] bytes) {
        if (bytes == null) {
            return false;
        }

        int left = bytes.length;
        int offset = 0;
        try {
            while (left > 0) {
                int read = in.read(bytes, offset, left);
                if (read < 0) {
                    break;
                }
                left = left - read;
                offset = offset + read;
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString(), e);
        }

        return left == 0;
    }
}
