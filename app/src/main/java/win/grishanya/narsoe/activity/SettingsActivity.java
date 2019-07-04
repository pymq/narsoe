package win.grishanya.narsoe;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private Switch defineIncomingCalls;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        defineIncomingCalls = (Switch) findViewById(R.id.switchDefineIncomingCalls);
        defineIncomingCalls.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //ToDO Добавить обработку
                }
            }
        });

    }
    //ToDo добавить ханение настроек и вообще хоть что-то
}
