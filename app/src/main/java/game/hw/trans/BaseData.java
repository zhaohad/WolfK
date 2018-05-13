package game.hw.trans;

import android.graphics.Bitmap;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class BaseData implements Transable {
    private static final String TAG = "HWGAME:BaseData";

    @Override
    public final String getClassName() {
        return this.getClass().getName();
    }

    protected final void writeString(OutputStream os, String v) {
        StreamUtils.writeString(os, v);
    }

    protected final String readString(InputStream is) {
        return StreamUtils.readString(is);
    }

    protected final void writeBitmap(OutputStream os, Bitmap bmp) {
        StreamUtils.writeBitmap(os, bmp);
    }

    protected final Bitmap readBitmap(InputStream is) {
        return StreamUtils.readBitmap(is);
    }
}
