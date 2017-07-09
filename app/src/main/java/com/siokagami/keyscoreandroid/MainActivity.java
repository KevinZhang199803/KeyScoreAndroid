package com.siokagami.keyscoreandroid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.siokagami.keyscoreandroid.bluetooth.BlunoLibrary;
import com.siokagami.keyscoreandroid.utils.DisplayUtil;
import com.siokagami.keyscoreandroid.utils.FrescoUtil;

public class MainActivity extends BlunoLibrary implements View.OnClickListener {
    private HorizontalScrollView svScoreContainer;
    private LinearLayout llNodeContainer;
    private TextView tvBluetoothText;
    private Button btnStart;
    private Button btnPair;
    private String bluetoothResponseText = "";

    private String[] musicTrack = {"20", "20", "20", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "20", "20", "20", "20", "20"};
    private String[] tagList = {"000001"};

    private final Handler mHandler = new Handler();


    private Runnable ScrollRunnable = new Runnable() {
        @Override
        public void run() {
            int off = llNodeContainer.getMeasuredWidth() - svScoreContainer.getWidth();//判断高度
            if (off > 0) {
                svScoreContainer.scrollBy(1, 0);
                if (svScoreContainer.getScrollY() == off) {
                    Thread.currentThread().interrupt();
                } else {
                    mHandler.postDelayed(this, 1);
                }
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateProcess();
        initView();
        serialBegin(115200);
        initTrack();
    }

    private void initView() {
        svScoreContainer = (HorizontalScrollView) findViewById(R.id.sv_score_container);
        llNodeContainer = (LinearLayout) findViewById(R.id.ll_node_container);
        tvBluetoothText = (TextView) findViewById(R.id.tv_bluetooth_text);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnPair = (Button) findViewById(R.id.btn_pair);
        btnStart.setOnClickListener(this);
        btnPair.setOnClickListener(this);
    }

    @Override
    public void onConectionStateChange(connectionStateEnum theconnectionStateEnum) {
        switch (theconnectionStateEnum) {                                            //Four connection state
            case isConnected:
                btnPair.setText("Connected");
                break;
            case isConnecting:
                btnPair.setText("Connecting");
                break;
            case isToScan:
                btnPair.setText("Scan");
                break;
            case isScanning:
                btnPair.setText("Scanning");
                break;
            case isDisconnecting:
                btnPair.setText("isDisconnecting");
                break;
            default:
                break;
        }
    }

    @Override
    public void onSerialReceived(String theString) {
        bluetoothResponseText = theString;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mHandler.postDelayed(ScrollRunnable, 100);
                break;
            case R.id.btn_pair:
                buttonScanOnClickProcess();                                        //Alert Dialog for selecting the BLE device
                break;
        }
    }

    protected void onResume() {
        super.onResume();
        System.out.println("BlUNOActivity onResume");
        onResumeProcess();                                                        //onResume Process by BlunoLibrary
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        onActivityResultProcess(requestCode, resultCode, data);                    //onActivityResult Process by BlunoLibrary
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        onPauseProcess();                                                        //onPause Process by BlunoLibrary
    }

    protected void onStop() {
        super.onStop();
        onStopProcess();                                                        //onStop Process by BlunoLibrary
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onDestroyProcess();                                                        //onDestroy Process by BlunoLibrary
    }

    private void initTrack() {
        for (int i = 0; i < musicTrack.length; i++) {
            final ImageView imageView = new ImageView(MainActivity.this);
            imageView.setMinimumWidth(DisplayUtil.dp2px(MainActivity.this, 80));
            imageView.setMinimumHeight(DisplayUtil.dp2px(MainActivity.this, 320));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setTag(musicTrack[i]);
            imageView.setEnabled(false);
            imageView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    int[] location = new int[2];
                    imageView.getLocationInWindow(location);
                    if (location[0] > 930 && location[0] < 990) {
                        if (imageView.getTag() == null || !imageView.getTag().equals(bluetoothResponseText)) {
                            imageView.setEnabled(true);
                            imageView.setSelected(false);
                        } else {
                            imageView.setEnabled(true);
                            imageView.setSelected(true);

                        }
                    }
                }
            });
            imageView.setImageDrawable(getNodeDrawable(Integer.parseInt(musicTrack[i])));
            llNodeContainer.addView(imageView);
        }
    }

    private Drawable getNodeDrawable(int node) {
        switch (node) {
            case 0:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_start);
            case 1:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_1_selector);
            case 2:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_2_selector);
            case 3:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_3_selector);
            case 4:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_4_selector);
            case 5:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_5_selector);
            case 6:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_6_selector);
            case 7:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_7_selector);
            case 8:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_8_selector);
            case 9:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_9_selector);
            case 10:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_10_selector);
            case 11:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_11_selector);
            case 12:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_12_selector);
            case 13:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_13_selector);
            case 14:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_14_selector);
            case 15:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_15_selector);
            case 16:
                return DisplayUtil.getDrawable(MainActivity.this, R.drawable.icon_track_16_selector);
            default:
                return null;

        }
    }
}
