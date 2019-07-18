package win.grishanya.narsoe;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoListShort_Company {

    @SerializedName("Name")
    @Expose
    private List<String> name = null;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Url")
    @Expose
    private List<String> url = null;
    @SerializedName("Telephone")
    @Expose
    private String telephone;
    @SerializedName("Email")
    @Expose
    private List<String> email = null;

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getUrl() {
        return url;
    }

    public void setUrl(List<String> url) {
        this.url = url;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

}
