package game.hw.trans;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;

import game.hw.wolfk.data.Person;

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
                while (mIsContinue) {
                    String className = StreamUtils.readString(in);
                    if (className == null) {
                        Log.e(TAG, "its null");
                    }
                    Log.e(TAG, "className = " + className);
                    Class clazz = Class.forName(className);
                    Constructor<Transable> constructor = clazz.getConstructor();
                    Transable transable = constructor.newInstance();
                    transable.onReadData(in);

                    // TODO hanwei just for debug, remove these below
                    if (transable instanceof Person) {
                        Log.e(TAG, "person.name = " + ((Person) transable).name);
                        Log.e(TAG, "person.avatar = " + ((Person) transable).avatar);
                        if (((Person) transable).avatar != null) {
                            Log.e(TAG, "asd: " + ((Person) transable).avatar.getByteCount());
                        }
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, e.toString(), e);
            } catch (ClassNotFoundException e) {
                Log.e(TAG, e.toString(), e);
            } catch (NoSuchMethodException e) {
                Log.e(TAG, e.toString(), e);
            } catch (IllegalAccessException e) {
                Log.e(TAG, e.toString(), e);
            } catch (InstantiationException e) {
                Log.e(TAG, e.toString(), e);
            } catch (InvocationTargetException e) {
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
