package demo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hulimin on 2017/9/8.
 */

public class FileUtils {

    public static String byteToBitmapSaveImage(byte[] data){
        if(data.length>0){
            File f=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),   "/Camera");  //Camera"/gg_"+time+".jpg");      //
            if(f.isDirectory()){
                String[] name=f.list();
                MyLog.e("name --"+name.length+"\t"+name[0]);
             //   if(name.length==1){
                    String myName=name[0];
                    Bitmap bitmap= BitmapFactory.decodeByteArray(data, 0, data.length);
                    File newFile=new File(f, myName);
                    String filePath=newFile.getAbsolutePath();
                    MyLog.e("filePath:"+filePath);
                    saveBitmap(bitmap,filePath);
               // }
            }
        }
        return null;
    }





    public static String test(byte[] data){
        Bitmap bitmap= BitmapFactory.decodeByteArray(data, 0, data.length);
        String filePath=getFileStringNameByTime("gg");
        saveBitmap(bitmap,filePath);

        moveFile(filePath, "Camera");



            /*
            File f=new File(filePath);
            byte[] file_data=readFileByBytes(filePath);
            MyLog.e("Camera  ----  size: "+file_data.length);



            Bitmap bitmap1=BitmapFactory.decodeByteArray(file_data, 0, file_data.length);



     *//*       BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize = 8;
            InputStream  input = new ByteArrayInputStream(file_data);
            SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(input, null, options));
            bitmap = (Bitmap)softRef.get();;*//*
           // (new Rect(0, 0,20, 20),



            MyLog.e("Camera  ----  size: 111 "+file_data.length);

            String filePath1=getFileStringNameByTime("Camera");

            MyLog.e("Camera  ----  size: 2222 "+file_data.length);
            saveBitmap(bitmap1,filePath1);


            MyLog.e("Camera  ----            f.delete(); ");
            f.delete();*/
        return filePath;
    }




    //文件转移
    public static boolean moveFile(String srcFileName, String dirName) {
        MyLog.e("moveFile  ----   destDirName"+srcFileName);

        File srcFile = new File(srcFileName);
        if(!srcFile.exists() || !srcFile.isFile())
            return false;
        File f=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),   "/"+dirName);  //Camera"/gg_"+time+".jpg");      //
        String destDirName=f.getAbsolutePath();
        MyLog.e("moveFile  ----   destDirName"+destDirName);
        File destDir = new File(destDirName);
        if (!destDir.exists())
            destDir.mkdirs();
        return srcFile.renameTo(new File(destDirName + File.separator + srcFile.getName()));
    }


    public static byte[] readFileByBytes(String fileName){
        File file=new File(fileName);
        InputStream in=null;
        StringBuilder sb=sb=new StringBuilder();
        try {
            //一次读多个字节
            byte[] tempBytes=new byte[1024];
            in=new FileInputStream(file);
            int count=0;
            while ((count=in.read(tempBytes)) != -1) {
                sb.append(new String(tempBytes,0,count));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString().getBytes();
    }



    private static  void saveBitmap(Bitmap bm, String name) {
        MyLog.e("view--  saveBitmap:"+bm.toString());
       // File f = new File(Environment.getExternalStorageDirectory(), "/aaa/"+name+".png");
        File f=new File(name);
        MyLog.e("view--  name:"+f.getAbsolutePath());

        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            MyLog.e("view--  saveBitmap: succ  "+name);
        } catch (Exception e) {
            MyLog.e("view--  1111: file "+       e.getMessage());

        }

    }



    private static String getFileStringNameByTime(String dirName){
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String time=dateFormat.format(date);
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File f=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),   "/"+dirName+"/IMG_"+time+".jpg");  //Camera"/gg_"+time+".jpg");      //
        String fileName=f.getAbsolutePath();
        MyLog.e("fileNmae: "+fileName);
        return fileName;
    }



    private static String  writeDataToFile(byte[] data) {
        long l = System.currentTimeMillis();
        Date date = new Date(l);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String time=dateFormat.format(date);
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File f=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),   "/Camera/IMG_"+time+".jpg");  //"/gg_"+time+".jpg");      //
        String fileName=f.getAbsolutePath();
        MyLog.e("fileNmae: "+fileName);
        File file = fileIsExistAndCreatFile(fileName);
        try {
            FileOutputStream fstream = new FileOutputStream(file);
            BufferedOutputStream stream = new BufferedOutputStream(fstream);
            stream.write(data);// 调试到这里文件已经生成
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }



    private static File fileIsExistAndCreatFile(String fileName){
        File file=new File(fileName);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return file;
    }







}
