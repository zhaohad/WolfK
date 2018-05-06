package game.hw.wolfk.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Person implements Serializable {
    public int id;
    public String name;
    byte[] img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg(Bitmap bitmap) {
        this.img = getBytes(bitmap);
    }

    public Bitmap getImg() {
        return getBitmap(img);
    }


    public static byte[] getBytes(Bitmap bitmap){
        //实例化字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos);//压缩位图
        return baos.toByteArray();//创建分配字节数组
    }

    public static Bitmap getBitmap(byte[] bitmapdata) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
        return bitmap;
    }

    public void save(File file, Person person) {
        try {
            ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file));
            oout.writeObject(person);
            oout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}