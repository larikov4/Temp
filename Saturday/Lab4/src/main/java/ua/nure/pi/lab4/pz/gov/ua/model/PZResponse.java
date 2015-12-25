package ua.nure.pi.lab4.pz.gov.ua.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yevhen_Larikov on 23-Dec-15.
 */
public class PZResponse {

    private static final String DISPATCH = "nstotpr";
    private static final String ARRIVAL = "nstprib";
    private static final String TRAINS = "trains";
    private static final String DATE = "date";
    private static final String TRAIN = "train";
    private static final String FROM = "from";
    private static final String TO = "to";
    private static final String OTPR = "otpr";
    private static final String PRIB = "prib";
    private static final String VPUTI = "vputi";
    private static final String COUPE = "k";
    private static final String PLACKART = "p";

    private String dispatchStation;
    private String arrivalStation;
    private List<Train> trains;

    public PZResponse(JsonObject jsonObject) {
        this.dispatchStation = jsonObject.get(DISPATCH).getAsString();
        this.arrivalStation = jsonObject.get(ARRIVAL).getAsString();
        trains = getTrains(jsonObject.get(TRAINS).getAsJsonArray());
    }

    private List<Train> getTrains(JsonArray jsonArray) {
        List<Train> trains = new ArrayList<>();
        for(JsonElement element : jsonArray) {
            trains.add(getTrain((JsonObject) element));
        }
        return trains;
    }

    private Train getTrain(JsonObject element) {
        Train train = new Train();
        train.setDate(element.get(DATE).getAsString());
        train.setTrainNumber(element.get(TRAIN).getAsJsonObject().get("0").getAsString());
        train.setFromStation(element.get(FROM).getAsJsonObject().get("0").getAsString());
        train.setToStation(element.get(TO).getAsJsonObject().get("0").getAsString());
        train.setDispatchTime(element.get(OTPR).getAsString());
        train.setArrivalTime(element.get(PRIB).getAsString());
        train.setTimeInJourney(element.get(VPUTI).getAsString());
        train.setPlacesCoupe(element.get(COUPE).getAsInt());
        train.setPlacesPlackart(element.get(PLACKART).getAsInt());
        return train;
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

    public List<Train> getTrains() {
        return trains;
    }

    public void setTrains(List<Train> trains) {
        this.trains = trains;
    }
}
