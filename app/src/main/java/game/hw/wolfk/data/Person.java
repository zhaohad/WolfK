package game.hw.wolfk.data;

import android.graphics.Bitmap;

import java.io.InputStream;
import java.io.OutputStream;

public class Person extends BaseData {
    public Bitmap avatar;
    public String name;

    @Override
    public void onWriteData(OutputStream os) {
        writeBitmap(os, avatar);
        writeString(os, name);
    }

    @Override
    public void onReadData(InputStream is) {
        avatar = readBitmap(is);
        name = readString(is);
    }
}
