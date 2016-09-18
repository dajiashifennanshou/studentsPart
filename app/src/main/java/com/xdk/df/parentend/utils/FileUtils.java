package com.xdk.df.parentend.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {
    /**
     * sd卡的根目录
     */
    private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();
    /**
     * 手机的缓存根目录
     */
    private static String mDataRootPath = null;
    /**
     * 保存Image的目录名
     */
    private final static String FOLDER_NAME = "/com.xdk.df.parentend.portrait";


    public FileUtils(Context context) {
        mDataRootPath = context.getCacheDir().getPath();
    }


    /**
     * 获取储存Image的目录
     *
     * @return
     */
    public String getStorageDirectory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                mSdRootPath + FOLDER_NAME : mDataRootPath + FOLDER_NAME;
    }

    /**
     * 保存Image的方法，有sd卡存储到sd卡，没有就存储到手机目录
     *
     * @param fileName
     * @param bitmap
     * @throws IOException
     */
    public void savaBitmap(String fileName, Bitmap bitmap) throws IOException {
        if (bitmap == null) {
            return;
        }
        String path = getStorageDirectory();
        File folderFile = new File(path);
        if (!folderFile.exists()) {
            folderFile.mkdir();
        }
        File file = new File(path + File.separator + fileName);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
    }

    /**
     * 从手机或者sd卡获取Bitmap
     *
     * @param fileName
     * @return
     */
    public Bitmap getBitmap(String fileName) {
        return getBitmapFromFile(new File(getStorageDirectory() + File.separator + fileName),500,500);
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @return
     */
    public boolean isFileExists(String fileName) {
        return new File(getStorageDirectory() + File.separator + fileName).exists();
    }

    public Bitmap getBitmapFromFile(File dst, int width, int height) {

        if (null != dst && dst.exists()) {

            BitmapFactory.Options opts = null;

            if (width > 0 && height > 0) {

                opts = new BitmapFactory.Options();        //设置inJustDecodeBounds为true后，decodeFile并不分配空间，此时计算原始图片的长度和宽度

                opts.inJustDecodeBounds = true;

                BitmapFactory.decodeFile(dst.getPath(), opts);

                // 计算图片缩放比例

                final int minSideLength = Math.min(width, height);

                opts.inSampleSize = computeSampleSize(opts, minSideLength,

                        width * height);         //这里一定要将其设置回false，因为之前我们将其设置成了true

                opts.inJustDecodeBounds = false;

                opts.inInputShareable = true;

                opts.inPurgeable = true;

            }

            try {

                return BitmapFactory.decodeFile(dst.getPath(), opts);

            } catch (OutOfMemoryError e) {

                e.printStackTrace();

            }

        }
        return null;
    }
    public static int computeSampleSize(BitmapFactory.Options options,

                                        int minSideLength, int maxNumOfPixels) {

        int initialSize = computeInitialSampleSize(options, minSideLength,

                maxNumOfPixels);


        int roundedSize;

        if (initialSize <= 8) {

            roundedSize = 1;

            while (roundedSize < initialSize) {

                roundedSize <<= 1;

            }

        } else {

            roundedSize = (initialSize + 7) / 8 * 8;

        }


        return roundedSize;

    }


    private static int computeInitialSampleSize(BitmapFactory.Options options,

                                                int minSideLength, int maxNumOfPixels) {

        double w = options.outWidth;

        double h = options.outHeight;


        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math

                .sqrt(w * h / maxNumOfPixels));

        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math

                .floor(w / minSideLength), Math.floor(h / minSideLength));


        if (upperBound < lowerBound) {

            // return the larger one when there is no overlapping zone.

            return lowerBound;

        }


        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {

            return 1;

        } else if (minSideLength == -1) {

            return lowerBound;

        } else {

            return upperBound;

        }

    }

    /**
     * 获取文件的大小
     *
     * @param fileName
     * @return
     */
    public long getFileSize(String fileName) {
        return new File(getStorageDirectory() + File.separator + fileName).length();
    }


    /**
     * 删除SD卡或者手机的缓存图片和目录
     */
    public void deleteFile() {
        File dirFile = new File(getStorageDirectory());
        if (!dirFile.exists()) {
            return;
        }
        if (dirFile.isDirectory()) {
            String[] children = dirFile.list();
            for (int i = 0; i < children.length; i++) {
                new File(dirFile, children[i]).delete();
            }
        }
        dirFile.delete();
    }
}