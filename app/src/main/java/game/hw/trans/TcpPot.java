package game.hw.trans;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TcpPot {
    private static final String TAG = "HWGAME:TcpPot";
    private Socket mSocket;
    private DataTransThread mDataTransThread;

    public TcpPot(Socket socket) {
        mSocket = socket;
    }

    public void startDataTrans() {
        if (mSocket == null) {
            Log.e(TAG, "startDataTrans but socket is null");
        }

        if (mDataTransThread == null) {
            mDataTransThread = new DataTransThread();
            mDataTransThread.start();
        } else {
            Log.e(TAG, "data trans already started");
        }
    }

    private class DataTransThread extends Thread {
        private boolean mIsContinue = true;
        @Override
        public void run() {
            if (mSocket == null) {
                Log.e(TAG, "DataTransThread socket null");
                return;
            }
            InputStream in = null;
            try {
                in = mSocket.getInputStream();
                byte[] buf = new byte[1024];
                int cntRead;
                while ((cntRead = in.read(buf)) > 0) {
                    Log.e(TAG, "cntRead = " + cntRead);
                    Log.e(TAG, "received: " + new String(buf, 0, cntRead));
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString(), e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        Log.e(TAG, e.toString(), e);
                    }
                }
            }
        }
    }
}
