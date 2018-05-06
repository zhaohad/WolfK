package game.hw.trans;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UdpServer {
    private static final String TAG = "HWGAME:UdpServer";
    private DatagramSocket mSocket;
    private DatagramPacket mPacket;
    private DataThread mDataThread;
    private int mPort;

    public UdpServer(int port) {
        mPort = port;
    }

    public void startServer() {
        if (mSocket != null) {
            Log.e(TAG, "udp server already started");
            return;
        }

        try {
            mSocket = new DatagramSocket(mPort);
            byte[] buf = new byte[1024];
            mPacket = new DatagramPacket(buf, buf.length);
            mDataThread = new DataThread();
            mDataThread.start();
        } catch (SocketException e) {
            Log.e(TAG, e.toString(), e);
        }
    }

    private class DataThread extends Thread {
        private boolean mIsContinue = true;
        @Override
        public void run() {
            if (mSocket == null) {
                Log.e(TAG, "socket null");
                return;
            }

            if (mPacket == null) {
                Log.e(TAG, "packet null");
                return;
            }

            while (mIsContinue) {
                try {
                    mSocket.receive(mPacket);
                    Log.e(TAG, "length " + mPacket.getLength());
                    Log.e(TAG, "receive " + new String(mPacket.getData(), 0, mPacket.getLength()));
                } catch (IOException e) {
                    Log.e(TAG, e.toString(), e);
                }
            }
        }
    }
}
