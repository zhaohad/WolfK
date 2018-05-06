package game.hw.trans;

import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class TcpServer {
    private static final String TAG = "HWGAME:TcpServer";
    private ServerSocket mSocket;
    private int mPort;
    private AcceptThread mAcceptThread;
    private ArrayList<TcpPot> mTcpPots;

    public TcpServer(int port) {
        mPort = port;
    }

    public void startServer() {
        if (mSocket != null) {
            Log.e(TAG, "This server already started");
            return;
        }

        try {
            mSocket = new ServerSocket(mPort);
            mTcpPots = new ArrayList<>();
            mAcceptThread = new AcceptThread();
            mAcceptThread.start();
        } catch (IOException e) {
            Log.e(TAG, e.toString(), e);
        }
    }

    private class AcceptThread extends Thread {
        private boolean mIsContinue = true;
        @Override
        public void run() {
            if (mSocket == null) {
                Log.e(TAG, "Udp server socket null");
                return;
            }

            while (mIsContinue) {
                try {
                    Socket client = mSocket.accept();
                    if (client != null) {
                        TcpPot pot = new TcpPot(client);
                        mTcpPots.add(pot);
                        pot.startDataTrans();
                    }
                } catch (IOException e) {
                    Log.e(TAG, e.toString(), e);
                }
            }
        }
    }
}
