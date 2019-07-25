package win.grishanya.narsoe.activity;

import android.content.Intent;
import android.net.NetworkRequest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import win.grishanya.narsoe.NetworkRequests;
import win.grishanya.narsoe.R;
import win.grishanya.narsoe.ResponseDataHandler;
import win.grishanya.narsoe.network.PhoneNumberHandler;

public class NumberInfoActivity extends AppCompatActivity {

    TextView phoneNumberTextView;
    TextView informationTextView;
    ProgressBar downloadProgressBar;
    String phoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_info);

        PhoneNumberHandler phoneNumberHandler = new PhoneNumberHandler();

        phoneNumberTextView = (TextView) findViewById(R.id.numberInfoPhoneNumberTextView);
        informationTextView = (TextView) findViewById(R.id.numberInfoFullINformationTextView);
        downloadProgressBar = (ProgressBar) findViewById(R.id.numberInfoProgressBar);

        informationTextView.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();
        phoneNumber = phoneNumberHandler.prettifyPhoneNumber(intent.getStringExtra("phoneNumber"));
        phoneNumberTextView.setText(phoneNumber);
        ShowNumberInfo(phoneNumber);
    }

    protected void ShowNumberInfo(String phoneNumber){
        ResponseDataHandler responseDataHandler = new ResponseDataHandler();
        responseDataHandler.getFullNumberInfo(phoneNumber, new ResponseDataHandler.NumberInfoCallbacks() {
            @Override
            public void onGetNumberInfo(String result) {
                informationTextView.setText(result);
                downloadProgressBar.setVisibility(View.INVISIBLE);
                informationTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onGetNumberInfoFailed(Throwable error) {
                informationTextView.setText(R.string.bad_request);
            }
        });
    }
}

