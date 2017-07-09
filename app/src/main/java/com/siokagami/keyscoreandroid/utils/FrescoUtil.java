package com.siokagami.keyscoreandroid.utils;

import android.content.Context;
import android.net.Uri;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;


public class FrescoUtil {


    private static int defaultBgGray;

    public static void init(Context context, int bgColor) {
        Fresco.initialize(context);
        defaultBgGray = bgColor;
    }

    public static void displayImage(SimpleDraweeView simpleDraweeView, String path, String imageSize) {
        if (StringUtils.isEmpty(path)) {
            displayImage(simpleDraweeView, defaultBgGray);
            return;
        }
        simpleDraweeView.setImageURI(Uri.parse(StringUtils.isEmpty(imageSize) ? path : path + imageSize));
    }



    public static void displayImage(SimpleDraweeView simpleDraweeView, int resId) {
        displayImage(simpleDraweeView, "res:///" + resId, null);
    }

    public static void displayImage(SimpleDraweeView simpleDraweeView, String url) {
        displayImage(simpleDraweeView, url, "");
    }

    public static final void clearMemory() {
        //释放Fresco占用的内存
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
//        imagePipeline.clearDiskCaches();
//        imagePipeline.clearCaches();
    }

}
