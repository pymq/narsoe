package win.grishanya.narsoe.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ru.tinkoff.decoro.FormattedTextChangeListener;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;
import win.grishanya.narsoe.NetworkRequests;
import win.grishanya.narsoe.R;

public class MainActivity extends AppCompatActivity {

    private Button searchButton;
    private EditText phoneNumberEditText;
    private BottomNavigationView navigation;

    //Navigation view

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                        Intent intent = new Intent(MainActivity.this, RecentCallsActivity.class);
                        startActivity(intent);
                    return true;
            }
            return false;
        }
    };

    //Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_settings:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //ActivityMethods
    @Override
    protected void onPause() {
        super.onPause();
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        phoneNumberEditText = (EditText) findViewById(R.id.phoneNumberEditText);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);

        MaskImpl mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER);
        mask.setShowingEmptySlots(true);
        final FormatWatcher formatWatcher = new MaskFormatWatcher(mask);
        formatWatcher.installOn(phoneNumberEditText);

        formatWatcher.setCallback(new FormattedTextChangeListener() {
            @Override
            public boolean beforeFormatting(String oldValue, String newValue) {
                return false;
            }

            @Override
            public void onTextFormatted(FormatWatcher formatter, String newFormattedText) {
                String unformatedPhoneNumber = formatWatcher.getMask().toUnformattedString().replace("_","");

                //ToDO вынести в отдельный метод
                if(unformatedPhoneNumber.length()>11) {
                    searchButton.setEnabled(true);
                }else{
                    searchButton.setEnabled(false);
                }
            }
        });

        //Search Button Handler
        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NumberInfoActivity.class);
                intent.putExtra("phoneNumber",formatWatcher.getMask().toUnformattedString().replace("_",""));
                startActivity(intent);
            }
        });

        checkPermissions();

    }

    public String getIsValidPhonNumber(){
        //ToDO дописать :3
        boolean result = false;

        String userPhoneNumber = phoneNumberEditText.getText().toString();

        return userPhoneNumber;
    }

    public void checkPermissions(){

        if (checkSelfPermission(android.Manifest.permission.READ_CALL_LOG)
        == PackageManager.PERMISSION_GRANTED) {

        } else {
            //запрашиваем разрешение
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
        }

        if (checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED) {
                //ToDO Добавить какое-нибудь сообщение
        } else {
            //запрашиваем разрешение
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
            //ToDO Возможно для этого не надо. Чекнуть!
        if (checkSelfPermission(android.Manifest.permission.SYSTEM_ALERT_WINDOW)
                == PackageManager.PERMISSION_GRANTED) {
            //ToDO Добавить какое-нибудь сообщение
        } else {
            //запрашиваем разрешение
            checkDrawOverlayPermission();
        }
    }

    public void checkDrawOverlayPermission() {
        /** check if we already  have permission to draw over other apps */
        //Only for Api 23 or Higher
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (!Settings.canDrawOverlays(this)) {
                /** if not construct intent to request permission */
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                /** request permission via start activity for result */
                startActivity(intent);
            }
        }
    }
}

