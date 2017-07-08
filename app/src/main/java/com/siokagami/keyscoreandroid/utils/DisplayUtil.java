package com.siokagami.keyscoreandroid.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;


import com.siokagami.keyscoreandroid.base.ApplicationHolder;

import java.lang.reflect.Method;

@Deprecated
public class DisplayUtil {


    private static int screenWidth;

    private static int screenHeight;

    private static int physicalScreenHeight;

    private static int statusBarHeight;

    private static int bannerHeight;

    private static boolean hasLodadedSmartbar;

    private static boolean hasSmartbar;


    public static int dp2px(Context context,float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics));
    }


    public static int sp2px(float dipValue) {
        DisplayMetrics metrics = ApplicationHolder.instance.getResources().getDisplayMetrics();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dipValue, metrics));
    }

    public static int getScreenWidth(Context context) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            screenWidth = metrics.widthPixels;
        }
        return screenWidth;
    }

    public static Drawable getDrawable(Context context,int resId) {
        return ContextCompat.getDrawable(context, resId);
    }


//    public static int getStatusBarHeight(Activity activity) {
//        if (statusBarHeight == 0) {
//            Rect frame = new Rect();
//            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//            statusBarHeight = frame.top;
//        }
//        return statusBarHeight;
//    }

    public static int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            int resourceId = ApplicationHolder.instance.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                statusBarHeight = ApplicationHolder.instance.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return statusBarHeight;
    }

    public static int getNavigationBarHeight() {
        Resources resources = ApplicationHolder.instance.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    public static int getScreenHeight() {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) ApplicationHolder.instance.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            screenHeight = metrics.heightPixels;
        }
        return screenHeight;
    }

    /**
     * 获取屏幕的物理尺寸,与getScreenHeight相比,此方法不会受不同版本下navigationBar和statusBar的影响
     *
     * @return
     */
    public static int getPhysicalScreenHeight() {
        if (physicalScreenHeight == 0) {

            WindowManager wm = (WindowManager) ApplicationHolder.instance.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point point = getPhysicalSize(display);
            physicalScreenHeight = point.y;

        }
        return physicalScreenHeight;
    }

    public static int getRealScreenWidth() {
        WindowManager wm = (WindowManager) ApplicationHolder.instance.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    public static int getRealScreenHeight() {
        WindowManager wm = (WindowManager) ApplicationHolder.instance.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * 获取宽高比
     *
     * @return
     */
    public static float getRealScreenRatio() {
        WindowManager wm = (WindowManager) ApplicationHolder.instance.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels * 1.0f / metrics.heightPixels;
    }

    public static Point getRealSize(Display display) {
        Point outPoint = new Point();
        Method mGetRawH;
        try {
            mGetRawH = Display.class.getMethod("getRawHeight");
            Method mGetRawW = Display.class.getMethod("getRawWidth");
            outPoint.x = (Integer) mGetRawW.invoke(display);
            outPoint.y = (Integer) mGetRawH.invoke(display);
            return outPoint;
        } catch (Throwable e) {
            return null;
        }
    }

    public static Point getPhysicalSize(Display display) {
        if (Build.VERSION.SDK_INT >= 17) {
            Point outPoint = new Point();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getRealMetrics(metrics);
            outPoint.x = metrics.widthPixels;
            outPoint.y = metrics.heightPixels;
            return outPoint;
        }
        if (Build.VERSION.SDK_INT >= 14) {
            Point outPoint = getRealSize(display);
            if (outPoint != null)
                return outPoint;
        }
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 13) {
            display.getSize(outPoint);
        } else {
            outPoint.x = display.getWidth();
            outPoint.y = display.getHeight();
        }
        return outPoint;
    }


    public static int getScreenHeightWithoutStatusBar() {
        return getScreenHeight() - getStatusBarHeight();
    }

//    public static int getTextMeasureHeight(Context context, String text, float textSize, boolean singleLine) {
//        TextView textView = new TextView(context);
//        textView.setText(text);
//        textView.setSingleLine(singleLine);
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
//        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(getScreenWidth(), View.MeasureSpec.AT_MOST);
//        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        textView.measure(widthMeasureSpec, heightMeasureSpec);
//        int result = textView.getMeasuredHeight();
//        textView = null;
//        return result;
//    }


//    public static int getTextMeasureWidth(Context context, String text, float textSize, int padding) {
//        TextView textView = new TextView(context);
//        textView.setText(text);
//        textView.setPadding(padding, padding, padding, padding);
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
//        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(getScreenWidth(), View.MeasureSpec.AT_MOST);
//        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        textView.measure(widthMeasureSpec, heightMeasureSpec);
//        int result = textView.getMeasuredWidth();
//        textView = null;
//        return result;
//    }

    public static int getDimensionPixelSize(int resId) {
        return ApplicationHolder.instance.getResources().getDimensionPixelSize(resId);
    }
    /**
     * 是不是魅族的sb机器
     *
     * @return
     */
    public static boolean hasSmartBar() {
        if (hasLodadedSmartbar) {
            return hasSmartbar;
        }
        try {
            // 新型号可用反射调用Build.hasSmartBar()
            Method method = Class.forName("android.os.Build").getMethod("hasSmartBar");
            hasSmartbar = ((Boolean) method.invoke(null)).booleanValue();
        } catch (Exception e) {

        }
        // 反射不到Build.hasSmartBar(),则用Build.DEVICE判断
        if (Build.DEVICE.equals("mx2")) {
            hasSmartbar = true;
        } else if (Build.DEVICE.equals("mx") ||
                Build.DEVICE.equals("m9")) {
            hasSmartbar = false;
        }
        hasLodadedSmartbar = true;
        return hasSmartbar;
    }

    private static int contentViewHeight;

    public static int getContentViewHeight(Activity activity) {
        if (contentViewHeight == 0) {
            contentViewHeight = activity.getWindow().findViewById(android.R.id.content).getMeasuredHeight();
        }
        return contentViewHeight;
    }

    public static void scaleRect(RectF rect, float factor) {
        float diffHorizontal = (rect.right - rect.left) * (factor - 1f);
        float diffVertical = (rect.bottom - rect.top) * (factor - 1f);

        rect.top -= diffVertical / 2f;
        rect.bottom += diffVertical / 2f;

        rect.left -= diffHorizontal / 2f;
        rect.right += diffHorizontal / 2f;
    }

}
