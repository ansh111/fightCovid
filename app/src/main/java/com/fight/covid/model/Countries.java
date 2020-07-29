
package com.fight.covid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Countries {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("reports")
    @Expose
    private Integer reports;
    @SerializedName("cases")
    @Expose
    private Integer cases;
    @SerializedName("deaths")
    @Expose
    private Integer deaths;
    @SerializedName("recovered")
    @Expose
    private Integer recovered;
    @SerializedName("lat")
    @Expose
    private Integer lat;
    @SerializedName("lng")
    @Expose
    private Integer lng;
    @SerializedName("deltaCases")
    @Expose
    private Integer deltaCases;
    @SerializedName("deltaDeaths")
    @Expose
    private Integer deltaDeaths;



    @SerializedName("timeseries")
    @Expose
    private Timeseries timeseries;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getReports() {
        return reports;
    }

    public void setReports(Integer reports) {
        this.reports = reports;
    }

    public Integer getCases() {
        return cases;
    }

    public void setCases(Integer cases) {
        this.cases = cases;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(Integer deaths) {
        this.deaths = deaths;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }

    public Integer getLat() {
        return lat;
    }

    public void setLat(Integer lat) {
        this.lat = lat;
    }

    public Integer getLng() {
        return lng;
    }

    public void setLng(Integer lng) {
        this.lng = lng;
    }

    public Integer getDeltaCases() {
        return deltaCases;
    }

    public void setDeltaCases(Integer deltaCases) {
        this.deltaCases = deltaCases;
    }

    public Integer getDeltaDeaths() {
        return deltaDeaths;
    }

    public void setDeltaDeaths(Integer deltaDeaths) {
        this.deltaDeaths = deltaDeaths;
    }

    public Timeseries getTimeseries() {
        return timeseries;
    }

    public void setTimeseries(Timeseries timeseries) {
        this.timeseries = timeseries;
    }

    @Override
    public String toString() {
        return "Countries{" +
                "name='" + name + '\'' +
                ", flag='" + flag + '\'' +
                ", reports=" + reports +
                ", cases=" + cases +
                ", deaths=" + deaths +
                ", recovered=" + recovered +
                ", lat=" + lat +
                ", lng=" + lng +
                ", deltaCases=" + deltaCases +
                ", deltaDeaths=" + deltaDeaths +
                ", timeseries=" + timeseries +
                '}';
    }
}
