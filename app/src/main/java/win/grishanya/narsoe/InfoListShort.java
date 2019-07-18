package win.grishanya.narsoe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoListShort {

    @SerializedName("Rating")
    @Expose
    private String rating;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("Badge")
    @Expose
    private String badge;
    @SerializedName("Comments")
    @Expose
    private List<String> comments = null;
    @SerializedName("Company")
    @Expose
    private InfoListShort_Company company;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public InfoListShort_Company getCompany() {
        return company;
    }

    public void setCompany(InfoListShort_Company company) {
        this.company = company;
    }
}
