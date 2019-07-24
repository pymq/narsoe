package win.grishanya.narsoe;

import java.util.List;

import retrofit2.Response;

public class ResponseDataHandler {

    public interface NumberInfoCallbacks{
        void onGetNumberInfo(String result);
        void onGetNumberInfoFailed(Throwable error);
    }

    public void getNumberInfo(String number,final NumberInfoCallbacks numberInfoCallbacks) {
        NetworkRequests.MakeResponseCallbacks makeResponseCallbacks = new NetworkRequests.MakeResponseCallbacks() {
            @Override
            public void onGetResponse(Response<InfoListShort> response) {
                String result = "";
                if(response.body().getCompany() != null) {
                    result = addStringIfNotEmpty(result,response.body().getCompany().getName(),"Company name ",true);
                    result = addStringIfNotEmpty(result,response.body().getCompany().getDescription(),"Company descr ",true);
                }

                result = addStringIfNotEmpty(result,response.body().getName(),"Name ",true);
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
       NetworkRequests.MakeResponse(number,makeResponseCallbacks);
    }

    public void getFullNumberInfo (String number,final NumberInfoCallbacks numberInfoCallbacks){
        NetworkRequests.MakeResponseCallbacks makeResponseCallbacks = new NetworkRequests.MakeResponseCallbacks() {
            @Override
            public void onGetResponse(Response<InfoListShort> response) {
                String result = "";
                if(response.body().getCompany() != null) {
                    result = addStringIfNotEmpty(result,response.body().getCompany().getName(),"Company name ",true);
                    result = addStringIfNotEmpty(result,response.body().getCompany().getDescription(),"Company description ",true);
                    result = addStringIfNotEmpty(result,response.body().getCompany().getCity(),"Company city ",true);
                    result = addStringIfNotEmpty(result,response.body().getCompany().getUrl(),"Company URL ",true);
                    result = addStringIfNotEmpty(result,response.body().getCompany().getTelephone(),"Company Telephone ",true);
                    result = addStringIfNotEmpty(result,response.body().getCompany().getEmail(),"Company Email ",true);
                    result += "\n";
                }

                result = addStringIfNotEmpty(result,response.body().getName(),"Name ",true);
                result = addStringIfNotEmpty(result,response.body().getRating(),"Rating ",true);
                result = addStringIfNotEmpty(result,response.body().getType(),"Type ",true);
                result = addStringIfNotEmpty(result,response.body().getRegion(),"Region ",true);
                result += "\n";

                if(!response.body().getComments().isEmpty()){
                    result = addStringIfNotEmpty(result,response.body().getComments(),"Comment ",false);
                }
                numberInfoCallbacks.onGetNumberInfo(result);
            }

            @Override
            public void onGetFailed(Throwable error) {
                numberInfoCallbacks.onGetNumberInfoFailed(error);
            }
        };

        NetworkRequests.MakeResponse(number,makeResponseCallbacks);
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
