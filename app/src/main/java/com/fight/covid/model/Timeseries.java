
package com.fight.covid.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timeseries {

    @SerializedName("confirmed")
    @Expose
    private List<Integer> confirmed = null;
    @SerializedName("deaths")
    @Expose
    private List<Integer> deaths = null;

    @Override
    public String toString() {
        return "Timeseries{" +
                "confirmed=" + confirmed +
                ", deaths=" + deaths +
                ", recovered=" + recovered +
                ", dates=" + dates +
                ", first='" + first + '\'' +
                '}';
    }

    @SerializedName("recovered")
    @Expose
    private List<Integer> recovered = null;
    @SerializedName("dates")
    @Expose
    private List<String> dates = null;
    @SerializedName("first")
    @Expose
    private String first;

    public List<Integer> getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(List<Integer> confirmed) {
        this.confirmed = confirmed;
    }

    public List<Integer> getDeaths() {
        return deaths;
    }

    public void setDeaths(List<Integer> deaths) {
        this.deaths = deaths;
    }

    public List<Integer> getRecovered() {
        return recovered;
    }

    public void setRecovered(List<Integer> recovered) {
        this.recovered = recovered;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

}
