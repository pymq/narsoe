package win.grishanya.narsoe.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

import win.grishanya.narsoe.R;

public class SettingsActivity extends AppCompatActivity {

    private Switch defineIncomingCalls;
    private SeekBar modalWindowPosition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SharedPreferences myPreferences
                = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        final SharedPreferences.Editor myEditor = myPreferences.edit();


        defineIncomingCalls = (Switch) findViewById(R.id.switchDefineIncomingCalls);
        defineIncomingCalls.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //ToDO Добавить обработку
                }
            }
        });

        modalWindowPosition = (SeekBar) findViewById(R.id.seekBarModalWindowVerticalPosition);
        modalWindowPosition.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("progress", progress+"");
                //ToDo незачем вызывать каждый раз
                myEditor.putInt("modalWindowPosition",getUserScreenHeight()/100*progress);
                myEditor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
    //ToDo добавить ханение настроек и вообще хоть что-то

    public int getUserScreenHeight (){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }


}
