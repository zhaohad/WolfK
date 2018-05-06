package game.hw.trans;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UdpClient {
    private static final String TAG = "HWGAME:UdpClient";

    public static void sendData(final String host, final int port, final Object data) {
        new Thread() {
            @Override
            public void run() {
                DatagramSocket socket = null;
                try {
                    socket = new DatagramSocket();
                    byte[] buf = ((String) data).getBytes();
                    InetAddress addr = InetAddress.getByName(host);
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, addr, port);
                    socket.send(packet);
                } catch (SocketException e) {
                    Log.e(TAG, e.toString(), e);
                } catch (UnknownHostException e) {
                    Log.e(TAG, e.toString(), e);
                } catch (IOException e) {
                    Log.e(TAG, e.toString(), e);
                } finally {
                    if (socket != null) {
                        socket.close();
                    }
                }
            }
        }.start();
    }

}
