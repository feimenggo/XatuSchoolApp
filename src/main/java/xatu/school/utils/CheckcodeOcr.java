package xatu.school.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xatu.school.R;


/**
 * 验证码识别
 */
public class CheckcodeOcr {
    public static int yuzhi = 333;

    public static String getOcr(Context context, Bitmap file) throws Exception {
        // 1、图像的预处理
        Bitmap img = removeBackground(file);
        // 2、分割
        List<Bitmap> listImg = splitImage(img);
        // 3、训练
        Map<Bitmap, String> map = loadTrainData(context);
        // 4、识别
        String result = "";
        for (Bitmap bi : listImg) {
            result += getSingleCharOcr(bi, map);
        }
        return result;
    }

    private static Bitmap removeBackground(Bitmap img) throws Exception {

//        Bitmap img = BitmapFactory.decodeStream(context.getResources().openRawResource(R.raw.checkcode6));
        img = img.copy(Bitmap.Config.ARGB_8888, true);
        int width = img.getWidth();
        int height = img.getHeight();
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                if (isWhite(img.getPixel(x, y)) == 1) {
                    img.setPixel(x, y, Color.WHITE);
                } else {
                    img.setPixel(x, y, Color.BLACK);
                }
            }
        }
        return img;
    }

    private static int isWhite(int colorInt) {
        int red = Color.red(colorInt);
        int green = Color.green(colorInt);
        int blue = Color.blue(colorInt);
        if (red + green + blue > yuzhi) {
            return 1;
        }
        return 0;
    }

    private static List<Bitmap> splitImage(Bitmap img) throws Exception {
        List<Bitmap> subImgs = new ArrayList<>();
        int wid = 10;
        for (int poi = 0; poi < 4; poi++) {
            subImgs.add(Bitmap.createBitmap(img, wid * poi, 0, wid, 10));
        }
        return subImgs;
    }

    private static Map<Bitmap, String> loadTrainData(Context context) throws Exception {
        Map<Bitmap, String> map = new HashMap<>();
        Resources res = context.getResources();
        map.put(BitmapFactory.decodeStream(res.openRawResource(R.raw.s0)), "0");
        map.put(BitmapFactory.decodeStream(res.openRawResource(R.raw.s1)), "1");
        map.put(BitmapFactory.decodeStream(res.openRawResource(R.raw.s2)), "2");
        map.put(BitmapFactory.decodeStream(res.openRawResource(R.raw.s3)), "3");
        map.put(BitmapFactory.decodeStream(res.openRawResource(R.raw.s4)), "4");
        map.put(BitmapFactory.decodeStream(res.openRawResource(R.raw.s5)), "5");
        map.put(BitmapFactory.decodeStream(res.openRawResource(R.raw.s6)), "6");
        map.put(BitmapFactory.decodeStream(res.openRawResource(R.raw.s7)), "7");
        map.put(BitmapFactory.decodeStream(res.openRawResource(R.raw.s8)), "8");
        map.put(BitmapFactory.decodeStream(res.openRawResource(R.raw.s9)), "9");
        return map;
    }

    private static String getSingleCharOcr(Bitmap img, Map<Bitmap, String> map) {
        String result = "";
        int width = img.getWidth();
        int height = img.getHeight();
        int min = width * height;
        for (Bitmap bi : map.keySet()) {
            int count = 0;
            Label1:
            for (int x = 0; x < width; ++x) {
                for (int y = 0; y < height; ++y) {
                    if (isWhite(img.getPixel(x, y)) != isWhite(bi.getPixel(x, y))) {
                        count++;
                        if (count >= min)
                            break Label1;
                    }
                }
            }
            if (count < min) {
                min = count;
                result = map.get(bi);
            }
        }
        return result;
    }
}
