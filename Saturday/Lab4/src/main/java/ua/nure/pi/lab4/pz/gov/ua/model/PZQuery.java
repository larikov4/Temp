package ua.nure.pi.lab4.pz.gov.ua.model;

/**
 * Created by Yevhen_Larikov on 23-Dec-15.
 */
public class PZQuery {

    private String dispatchStation;
    private String arrivalStation;
    private String date;

    public PZQuery() {
    }

    public PZQuery(String dispatchStation, String arrivalStation, String date) {
        this.dispatchStation = dispatchStation;
        this.arrivalStation = arrivalStation;
        this.date = date;
    }

    public String getDispatchStation() {
        return dispatchStation;
    }

    public void setDispatchStation(String dispatchStation) {
        this.dispatchStation = dispatchStation;
    }

    public String getArrivalStation() {
        return arrivalStation;
    }

    public void setArrivalStation(String arrivalStation) {
        this.arrivalStation = arrivalStation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
