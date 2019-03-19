package com.example.firsttry;

import android.os.Bundle;
import android.view.KeyEvent;

import com.theta360.pluginlibrary.activity.PluginActivity;
import com.theta360.pluginlibrary.callback.KeyCallback;
import com.theta360.pluginlibrary.receiver.KeyReceiver;
import com.theta360.pluginlibrary.values.LedColor;
import com.theta360.pluginlibrary.values.LedTarget;

import org.theta4j.webapi.Theta;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends PluginActivity {

    private Theta theta = Theta.createForPlugin();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private int pictNumber = 0;
    private int maxPicture = 10;

    private KeyCallback keyCallback = new KeyCallback() {
        @Override
        public void onKeyDown(int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyReceiver.KEYCODE_CAMERA){
                // notificationLed3Show(LedColor.MAGENTA);
                notificationLedBlink(LedTarget.LED3,LedColor.MAGENTA,500);

                executor.submit(() -> {
                    try {
                        while (pictNumber < maxPicture){
                            theta.takePicture();
                            Thread.sleep(4000);
                            pictNumber = pictNumber + 1;
                        }
                        notificationLedHide(LedTarget.LED3);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }

        }

        @Override
        public void onKeyUp(int keyCode, KeyEvent keyEvent) {

        }

        @Override
        public void onKeyLongPress(int keyCode, KeyEvent keyEvent) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAutoClose(true);
        // notificationLed3Show(LedColor.MAGENTA);
        // notificationLedBlink(LedTarget.LED4,LedColor.BLUE,1000);
        // notificationLedBlink(LedTarget.LED5,LedColor.BLUE,500);
        // notificationAudioShutter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setKeyCallback(keyCallback);
    }
}
