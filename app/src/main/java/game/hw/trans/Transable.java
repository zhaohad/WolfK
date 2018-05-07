package game.hw.trans;

import java.io.InputStream;
import java.io.OutputStream;

public interface Transable {
    String getClassName();
    void onWriteData(OutputStream os);
    void onReadData(InputStream is);
}
