package win.grishanya.narsoe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfoListShort {

    @SerializedName("Rating")
    @Expose
    private String rating;
    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Company")
    @Expose
    private InfoListShort_Company company;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InfoListShort_Company getCompany() {
        return company;
    }

    public void setCompany(InfoListShort_Company company) {
        this.company = company;
    }

}
