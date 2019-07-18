package win.grishanya.narsoe.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import win.grishanya.narsoe.InfoListShort;

public interface GetShortInformation {
    @GET("api/search/all/")
    Call<InfoListShort> getData(@Query("phone") String phoneNumber);
}
