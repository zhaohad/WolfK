package game.hw.trans;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class TcpClient {
    private static final String TAG = "HWGAME:TcpClient";
    private String mHost;
    private int mPort;
    private Socket mSocket;
    private OutputStream mOutputStream;

    public TcpClient(String host, int port) {
        mHost = host;
        mPort = port;
    }

    public void init() {
        new Thread() {
            @Override
            public void run() {
                if (mSocket != null) {
                    Log.e(TAG, "socket already inited");
                    return;
                }

                try {
                    mSocket = new Socket(mHost, mPort);
                    mOutputStream = mSocket.getOutputStream();
                    // mSocket.setSoTimeout(5000);
                } catch (IOException e) {
                    Log.e(TAG, e.toString(), e);
                }

                // TODO notify client ready
            }
        }.start();
    }

    public void sendData(final Object o) {
        new Thread() {
            @Override
            public void run() {
                if (mSocket == null) {
                    Log.e(TAG, "socket not ready");
                    return;
                }

                OutputStream os = mOutputStream;
                if (os != null) {
                    try {
                        os.write(((String) o).getBytes());
                        os.flush();
                    } catch (IOException e) {
                        Log.e(TAG, e.toString(), e);
                    }
                } else {
                    Log.e(TAG, "output stream null");
                }
            }
        }.start();
    }

    public void release() {
        if (mOutputStream != null) {
            try {
                mOutputStream.close();
            } catch (IOException e) {
                Log.e(TAG, e.toString(), e);
            }
        }

        if (mSocket != null) {
            try {
                mSocket.close();
            } catch (IOException e) {
                Log.e(TAG, e.toString(), e);
            }
        }
    }
}
