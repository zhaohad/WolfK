package game.hw.wolfk.data;

import android.graphics.Bitmap;

import java.io.InputStream;
import java.io.OutputStream;

import game.hw.trans.StreamUtils;
import game.hw.trans.Transable;

public abstract class BaseData implements Transable {
    private static final String TAG = "HWGAME:BaseData";

    @Override
    public String getClassName() {
        return this.getClass().getName();
    }

    protected void writeString(OutputStream os, String v) {
        StreamUtils.writeString(os, v);
    }

    protected String readString(InputStream is) {
        return StreamUtils.readString(is);
    }

    protected void writeBitmap(OutputStream os, Bitmap bmp) {
        StreamUtils.writeBitmap(os, bmp);
    }

    protected Bitmap readBitmap(InputStream is) {
        return StreamUtils.readBitmap(is);
    }
}
