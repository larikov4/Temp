package ua.nure.pi.lab4.pz.gov.ua.model;

/**
 * Created by Yevhen_Larikov on 23-Dec-15.
 */
public class Train {
    private String date;
    private String trainNumber;
    private String fromStation;
    private String toStation;
    private String dispatchTime;
    private String arrivalTime;
    private String timeInJourney;
    private int placesCoupe;
    private int placesPlackart;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getFromStation() {
        return fromStation;
    }

    public void setFromStation(String fromStation) {
        this.fromStation = fromStation;
    }

    public String getToStation() {
        return toStation;
    }

    public void setToStation(String toStation) {
        this.toStation = toStation;
    }

    public String getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(String dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getTimeInJourney() {
        return timeInJourney;
    }

    public void setTimeInJourney(String timeInJourney) {
        this.timeInJourney = timeInJourney;
    }

    public int getPlacesCoupe() {
        return placesCoupe;
    }

    public void setPlacesCoupe(int placesCoupe) {
        this.placesCoupe = placesCoupe;
    }

    public int getPlacesPlackart() {
        return placesPlackart;
    }

    public void setPlacesPlackart(int placesPlackart) {
        this.placesPlackart = placesPlackart;
    }
}
