package win.grishanya.narsoe;

import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import win.grishanya.narsoe.network.GetShortInformation;
import win.grishanya.narsoe.network.RetrofitInstance;

public class NetworkRequests {

    public interface NumberInfoCallbacks{
        void onGetNumberInfo(String result);
        void onGetNumberInfoFailed(Throwable error);
    }

    private interface MakeResponseCallbacks{
        void onGetResponse (Response<InfoListShort> response);
        void onGetFailed (Throwable error);
    }

    public void getNumberInfo(String number,final NumberInfoCallbacks numberInfoCallbacks) {
        MakeResponseCallbacks makeResponseCallbacks = new MakeResponseCallbacks() {
            @Override
            public void onGetResponse(Response<InfoListShort> response) {
                String result = "";
                if(response.body().getCompany() != null) {
                    result = addStringIfNotEmpty(result,response.body().getCompany().getName(),"Company name ",true);
                    result = addStringIfNotEmpty(result,response.body().getCompany().getDescription(),"Company descr ",true);
                }

                result = addStringIfNotEmpty(result,response.body().getName(),"Name ",true);
                result = addStringIfNotEmpty(result,response.body().getBadge(),"Badge ",true);
                result = addStringIfNotEmpty(result,response.body().getRating(),"Rating ",true);
                result = addStringIfNotEmpty(result,response.body().getType(),"Type ",true);

                if(!response.body().getComments().isEmpty()){
                    result = addStringIfNotEmpty(result,response.body().getComments().get(0),"Comment ",false);
                }
                numberInfoCallbacks.onGetNumberInfo(result);
            }

            @Override
            public void onGetFailed(Throwable error) {
                numberInfoCallbacks.onGetNumberInfoFailed(error);
            }
        };
        makeResponse(number,makeResponseCallbacks);
    }

    private void makeResponse(String number,final MakeResponseCallbacks makeResponseCallbacks){
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

    private String addStringIfNotEmpty(String result,String varible, String varibleHeader, Boolean chageTheLine ){

        if (!varible.isEmpty()){
            result +=varibleHeader + " " + varible;
            if(chageTheLine){
                result += "\n";
            }
        }

        return result;
    }

    private String addStringIfNotEmpty(String result, List<String> varible, String varibleHeader, Boolean chageTheLine){

        if (!varible.isEmpty()){
            result +=varibleHeader + " " + varible.toString();
            if(chageTheLine){
                result += "\n";
            }
        }

        return result;
    }
}
