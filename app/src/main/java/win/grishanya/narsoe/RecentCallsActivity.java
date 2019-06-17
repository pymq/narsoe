package win.grishanya.narsoe;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;

public class RecentCallsActivity extends AppCompatActivity {
    private BottomNavigationView navigation;
    public String [] listOfRecentCallsTables = new String []{
            CallLog.Calls._ID,
            CallLog.Calls.DATE,
            CallLog.Calls.NUMBER,
            CallLog.Calls.CACHED_NAME,
            CallLog.Calls.DURATION,
            CallLog.Calls.TYPE
    };
    private RecyclerView recentCalls;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dashboard:
//                    Intent intent = new Intent(RecentCallsActivity.this, MainActivity.class);
//                    startActivity(intent);
                    //mTextMessage.setText(R.string.title_recent_calls);
                    return true;
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_search);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onPause() {
        super.onPause();

        navigation.setSelectedItemId(R.id.navigation_dashboard);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_calls);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);

        recentCalls = (RecyclerView) findViewById(R.id.recentCallsRecyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recentCalls.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        RecyclerView.Adapter recentCallsRecycleViewAdapter = new recentCallsRecycleViewAdapter(getListOfRecentCalls());
        recentCalls.setAdapter(recentCallsRecycleViewAdapter);
        Log.d("STH",""+recentCallsRecycleViewAdapter.getItemCount());
    }

    public ArrayList<String[]> getListOfRecentCalls(){
        ArrayList<String[]> result = new ArrayList<String[]>();
        Cursor listOfRecentCalls = getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                listOfRecentCallsTables,
                null,
                null,
                CallLog.Calls.DATE + " ASC"
        );
        try {
            if (listOfRecentCalls.moveToFirst()) {
                do {
                    String _id = listOfRecentCalls.getString(listOfRecentCalls.getColumnIndex(CallLog.Calls._ID));
                    String number = listOfRecentCalls.getString(listOfRecentCalls.getColumnIndex(CallLog.Calls.NUMBER));
                    String date = listOfRecentCalls.getString(listOfRecentCalls.getColumnIndex(CallLog.Calls.DATE));
                    String name = listOfRecentCalls.getString(listOfRecentCalls.getColumnIndex(CallLog.Calls.CACHED_NAME));
                    String duration = listOfRecentCalls.getString(listOfRecentCalls.getColumnIndex(CallLog.Calls.DURATION));
                    String type = listOfRecentCalls.getString(listOfRecentCalls.getColumnIndex(CallLog.Calls.TYPE));
                    String [] data = new String[]{
                      _id,number,date,name,duration,type
                    };
                    result.add(data);
                } while (listOfRecentCalls.moveToNext());
            }
            if (!listOfRecentCalls.isClosed()) {
                listOfRecentCalls.close();
            }
        }catch (java.lang.NullPointerException e){
            Log.d("exception","NullPointerExceptionDB");
        }
        return result;
    }
}


