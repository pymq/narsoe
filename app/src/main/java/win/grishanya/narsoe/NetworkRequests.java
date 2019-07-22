package win.grishanya.narsoe;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import win.grishanya.narsoe.network.GetShortInformation;
import win.grishanya.narsoe.network.RetrofitInstance;

public class NetworkRequests {

    public interface MakeResponseCallbacks{
        void onGetResponse (Response<InfoListShort> response);
        void onGetFailed (Throwable error);
    }



    public static void MakeResponse(String number,final MakeResponseCallbacks makeResponseCallbacks){
        GetShortInformation service = RetrofitInstance.getRetrofitInstance().create(GetShortInformation.class);
        Call<InfoListShort> call = service.getData(number);

        call.enqueue(new Callback<InfoListShort>() {
            @Override
            public void onResponse(Call<InfoListShort> call, Response<InfoListShort> response) {
                Log.i("Request","GoodRequest"+response.code());
                makeResponseCallbacks.onGetResponse(response);
            }

            @Override
            public void onFailure(Call<InfoListShort> call, Throwable t) {
                makeResponseCallbacks.onGetFailed(t);
            }
        });
    }

}
