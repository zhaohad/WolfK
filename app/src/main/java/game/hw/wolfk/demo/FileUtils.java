package game.hw.wolfk.demo;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by sunziying on 06/05/2018.
 */

public class FileUtils {
    public static final int SAVEFINISH = 1;
    public static final int LOADFINISH = 2;
    private static final String TAG = "Mydemo FileUtils";
    public static void savePerson(Person person, File file) {
                try {
                    Log.e(TAG, "savePerson started");
                    ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file));
                    oout.writeObject(person);
                    oout.close();
                    Log.e(TAG, "savePerson finish");
                } catch (IOException e) {
                    Log.e(TAG, "savePerson " + e.toString());
                    e.printStackTrace();
                }
    }

    public void loadPerson(File file) {
        try {
            Log.e(TAG, "loadPerson started");
            ObjectInputStream oin = new ObjectInputStream(new FileInputStream(file));
            Person newPerson = null; // 没有强制转换到Person类型
            newPerson = (Person)oin.readObject();
            oin.close();
            Log.e(TAG, "loadPerson finish");
        } catch (IOException e) {
            Log.e(TAG, "savePerson " + e.toString());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.e(TAG, "savePerson " + e.toString());
            e.printStackTrace();
        }
    }
}
