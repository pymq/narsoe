package win.grishanya.narsoe.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import win.grishanya.narsoe.R;

public class SettingsActivity extends AppCompatActivity {

    private Switch defineIncomingCalls;
    private Switch closeModalWindowWhenCallApply;
    private SeekBar modalWindowPosition;
    private SharedPreferences myPreferences;
    private ViewGroup windowLayout = null;
    private WindowManager windowManager = null;
    private Boolean modalWidowCreated = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        defineIncomingCalls = (Switch) findViewById(R.id.switchDefineIncomingCalls);
        closeModalWindowWhenCallApply = (Switch) findViewById(R.id.settingsCloseModalWindowWhenCallApplySwitch);
        modalWindowPosition = (SeekBar) findViewById(R.id.seekBarModalWindowVerticalPosition);




        myPreferences
                = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        final SharedPreferences.Editor myEditor = myPreferences.edit();

        showSaveSettings();

        defineIncomingCalls.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myEditor.putBoolean("defineIncomingCalls",isChecked);
                myEditor.apply();
            }
        });

        closeModalWindowWhenCallApply.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                myEditor.putBoolean("closeModalWindowWhenCallApply",isChecked);
                myEditor.apply();
            }
        });


        modalWindowPosition.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int modalWindowPos = getUserScreenHeight()/100*progress;
                myEditor.putInt("modalWindowPosition",modalWindowPos);
                showModalWindowPosition(modalWindowPos);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                myEditor.apply();
                if(modalWidowCreated) {
                    windowManager.removeView(windowLayout);
                    modalWidowCreated = false;
                }
            }
        });

    }

    private int getUserScreenHeight (){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    private void showSaveSettings (){
        defineIncomingCalls.setChecked(myPreferences.getBoolean("defineIncomingCalls",true));
        closeModalWindowWhenCallApply.setChecked(myPreferences.getBoolean("closeModalWindowWhenCallApply",true));
        modalWindowPosition.setProgress((myPreferences.getInt("modalWindowPosition",0)*100)/getUserScreenHeight());
    }

    private void showModalWindowPosition(int position) {
        if (!modalWidowCreated) {
            windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);

            LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT);
            //Если добавить  | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY | WindowManager.LayoutParams. TYPE_APPLICATION_OVERLAY отображаетс на весь экран
            //Верстка
            params.alpha = 0.6f;
            params.gravity = Gravity.TOP;
            params.y = position;
            windowLayout = (ViewGroup) layoutInflater.inflate(R.layout.call_info, null);
            windowLayout.setBackgroundResource(R.color.colorBackGroundInfo);

            windowManager.addView(windowLayout, params);
            modalWidowCreated = true;
        }else {
            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT);
            params.alpha = 0.6f;
            params.gravity = Gravity.TOP;
            params.y = position;
            windowManager.updateViewLayout(windowLayout, params);
        }
    }

}
