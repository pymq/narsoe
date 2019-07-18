package win.grishanya.narsoe;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import win.grishanya.narsoe.activity.MainActivity;
import win.grishanya.narsoe.activity.SettingsActivity;
import win.grishanya.narsoe.network.GetShortInformation;
import win.grishanya.narsoe.network.RetrofitInstance;

public class CallReceiver extends BroadcastReceiver {
    private static Boolean incomingCall = false;
    private static WindowManager windowManager;
    private static ViewGroup windowLayout;

    @Override
    public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action !=null) {
                if (action.equals("android.intent.action.PHONE_STATE")) {
                    String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                    if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        Log.i("info", "EXTRA_STATE_RINGING");
                        //Трубка не поднята, телефон звонит
                        String phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        incomingCall = true;
                        Log.d("info", "Show window: " + phoneNumber);
                        SharedPreferences myPreferences
                                = PreferenceManager.getDefaultSharedPreferences(context);
                        int modalWindowPosition = myPreferences.getInt("modalWindowPosition", 0);
                        showWindow(context, phoneNumber, modalWindowPosition);

                    } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                        //Телефон находится в режиме звонка (набор номера при исходящем звонке / разговор)
                        Log.i("info", "EXTRA_STATE_OFFHOOK");
//                        if (incomingCall) {
//                            Log.d("info", "Close window.");
//                            incomingCall = false;
//                            closeWindow();
//                        }
                    } else if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                        //Телефон находится в ждущем режиме - это событие наступает по окончанию разговора
                        //или в ситуации "отказался поднимать трубку и сбросил звонок".
                        Log.i("info", "EXTRA_STATE_IDLE");
                        if (incomingCall) {
                            Log.d("info", "Close window.");
                            incomingCall = false;
                            closeWindow();
                        }
                    }
                }
            }
        }

    public void showWindow(final Context context, String phone, int modalWindowPosition) {
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //ToDO добавить TYPE_SYSTEM_ALERT для api 23
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        //Если добавить  | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY | WindowManager.LayoutParams. TYPE_APPLICATION_OVERLAY отображаетс на весь экран

        //Верстка
        params.gravity = Gravity.TOP;
        params.y = modalWindowPosition;
        windowLayout = (ViewGroup) layoutInflater.inflate(R.layout.call_info, null);
        windowLayout.setBackgroundResource(R.color.colorBackGroundInfo);
        windowLayout.setId(View.generateViewId());
        TextView textViewNumber=(TextView) windowLayout.findViewById(R.id.textViewNumber);
        Button buttonClose=(Button) windowLayout.findViewById(R.id.buttonClose);
        textViewNumber.setText(phone);
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindow();
            }
        });
        showNumberInfo(phone,windowLayout);

        windowManager.addView(windowLayout, params);
    }

    public void closeWindow() {
        if (windowLayout != null){
            windowManager.removeView(windowLayout);
            windowLayout = null;
        }
    }

    public void showNumberInfo(String number,ViewGroup windowLayout) {
        final TextView informationTextView = (TextView) windowLayout.findViewById(R.id.informationTextView);
        String result;
        GetShortInformation service = RetrofitInstance.getRetrofitInstance().create(GetShortInformation.class);

        /*Call the method with parameter in the interface to get the employee data*/
        Call<InfoListShort> call = service.getData(number);


        call.enqueue(new Callback<InfoListShort>() {
            @Override
            public void onResponse(Call<InfoListShort> call, Response<InfoListShort> response) {
                Log.i("Request","GoodRequest"+response.code());
                String result ="";
                if(response.body().getCompany() != null) {
                    result +=
                            "Company name " + response.body().getCompany().getName() + "\n"+
                            "Company descr " + response.body().getCompany().getDescription() + "\n";
                }
                result +=
                        "Name " + response.body().getName()  + "\n"+
                        "Badge " + response.body().getBadge()  + "\n"+
                        "Rating " + response.body().getRating() + "\n"+
                        "Type " + response.body().getType();
                if(!response.body().getComments().isEmpty()){
                    result += "\n" + "Comment " + response.body().getComments().get(0);
                }
                informationTextView.setText(result);
        }

            @Override
            public void onFailure(Call<InfoListShort> call, Throwable t) {

                informationTextView.setText(R.string.bad_request);

            }
        });
    }

}

/*
КОроче,  опишу два решения, чтобы не забыть.
1) Регаем ресивер в самом окне, если получится. Пусть сам ловит и закрывается BroadcastReceiver components are not allowed to register to receive intents
2) Сохраняем в ресурсы ID вьюхи
*/