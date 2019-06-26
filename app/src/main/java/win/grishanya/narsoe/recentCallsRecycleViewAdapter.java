package win.grishanya.narsoe;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.provider.CallLog.Calls.INCOMING_TYPE;
import static android.provider.CallLog.Calls.MISSED_TYPE;
import static android.provider.CallLog.Calls.OUTGOING_TYPE;

public class recentCallsRecycleViewAdapter extends RecyclerView.Adapter<recentCallsRecycleViewAdapter.recentCallsRecycleViewHolder> {
    private ArrayList<Calls> listOfRecentCalls;

    //Здесь описывается вьюха
    public static class recentCallsRecycleViewHolder extends RecyclerView.ViewHolder{
        TextView callerName;
        TextView callerPhone;
        TextView callDate;
        ImageView callTypeIcon;

        public recentCallsRecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            callerName =  (TextView) itemView.findViewById(R.id.callerNameTextView);
            callerPhone = (TextView) itemView.findViewById(R.id.phoneNumbertextView);
            callDate = (TextView) itemView.findViewById(R.id.callDateTextView);
            callTypeIcon  =(ImageView) itemView.findViewById(R.id.callTypeImageView);
        }
    }

    //Конструктор
    public recentCallsRecycleViewAdapter (ArrayList<Calls> listOfRecentCalls){
        this.listOfRecentCalls = listOfRecentCalls;
    }

    //Назначили верстку
    @NonNull
    @Override
    public recentCallsRecycleViewAdapter.recentCallsRecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recent_calls_recycle_view_item,viewGroup,false);
        recentCallsRecycleViewHolder myViewHolder = new recentCallsRecycleViewHolder (v);
        return myViewHolder;
    }

    //Раскладываем данные по виджетам
    @Override
    public void onBindViewHolder(@NonNull recentCallsRecycleViewAdapter.recentCallsRecycleViewHolder recentCallsRecycleViewHolder, int i) {
        Calls calls = this.listOfRecentCalls.get(i);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss ");
        String date  = dateFormat.format( calls.date );
        recentCallsRecycleViewHolder.callDate.setText(date);
        recentCallsRecycleViewHolder.callerName.setText(calls.name.toString());
        recentCallsRecycleViewHolder.callerPhone.setText(calls.number.toString());
        switch (calls.type){
            case INCOMING_TYPE :{
                recentCallsRecycleViewHolder.callTypeIcon.setImageResource(android.R.drawable.sym_call_incoming);
                break;
            }
            case MISSED_TYPE :{
                recentCallsRecycleViewHolder.callTypeIcon.setImageResource(android.R.drawable.sym_call_missed);
                break;
            }
            case OUTGOING_TYPE :{
                recentCallsRecycleViewHolder.callTypeIcon.setImageResource(android.R.drawable.sym_call_outgoing);
                break;
            }
            default:{
                recentCallsRecycleViewHolder.callTypeIcon.setImageResource(android.R.drawable.sym_action_call);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return listOfRecentCalls.size();
    }
}
