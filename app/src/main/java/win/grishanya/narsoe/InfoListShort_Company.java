package win.grishanya.narsoe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfoListShort_Company {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Website")
    @Expose
    private String website;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

}
