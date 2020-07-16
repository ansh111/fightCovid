
package  com.fight.covid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Response {

    @SerializedName("timestamp")
    @Expose
    private String timestamp;


    @SerializedName("countries")
    @Expose
    private Map<String,Countries> countries;
    @SerializedName("max")
    @Expose
    private String max;
    @SerializedName("worldwide")
    @Expose
    private Worldwide worldwide;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public Worldwide getWorldwide() {
        return worldwide;
    }

    public void setWorldwide(Worldwide worldwide) {
        this.worldwide = worldwide;
    }

    public Map<String, Countries> getCountries() {
        return countries;
    }

    public void setCountries(Map<String, Countries> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "Response{" +
                "timestamp='" + timestamp + '\'' +
                ", countries=" + countries +
                ", max='" + max + '\'' +
                ", worldwide=" + worldwide +
                '}';
    }
}
