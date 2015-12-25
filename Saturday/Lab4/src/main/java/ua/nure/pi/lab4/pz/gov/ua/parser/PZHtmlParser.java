package ua.nure.pi.lab4.pz.gov.ua.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import ua.nure.pi.lab4.pz.gov.ua.model.PZQuery;
import ua.nure.pi.lab4.pz.gov.ua.model.PZResponse;
import ua.nure.pi.lab4.pz.gov.ua.model.StationNotFoundException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yevhen_Larikov on 23-Dec-15.
 */
@Component
public class PZHtmlParser {

    private static final String PZ_URL = "http://www.pz.gov.ua/rezervGR/";
    private static final String PZ_STATION = "aj_stations.php?stan=";
    private static final String PZ_GET = "aj_g60.php";
    private static final String NOM = "nom";
    private static final String DISPATCH = "kstotpr";
    private static final String ARRIVAL = "kstprib";
    private static final String SDATE = "sdate";
    private static final String FORM_URLENCODED = "application/x-www-form-urlencoded";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String F_NAME = "f_name";

    public PZResponse getRoutes(PZQuery query) throws IOException, StationNotFoundException {
        Map<String, String> datamap = new HashMap<>();
        datamap.put(DISPATCH, getStationNumber(query.getDispatchStation()));
        datamap.put(ARRIVAL, getStationNumber(query.getArrivalStation()));
        datamap.put(SDATE, query.getDate());
        String body = Jsoup.connect(PZ_URL + PZ_GET)
                .header(CONTENT_TYPE, FORM_URLENCODED)
                .data(datamap).ignoreContentType(true).execute().body();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(body).getAsJsonObject();
        return new PZResponse(jsonObject);
    }

    public List<String> getStations(String city) throws IOException {
        List<String> stations = new ArrayList<>();
        JsonArray jsonArray = getStationsArray(city);
        for(int i = 0; i < jsonArray.size(); i++) {
            stations.add(jsonArray.get(i).getAsJsonObject().get(F_NAME).getAsString());
        }
        return stations;
    }

    private String getStationNumber(String station) throws IOException, StationNotFoundException {
        JsonArray jsonArray = getStationsArray(station);
        System.out.println();
        if(jsonArray.size() > 1) {
            throw new StationNotFoundException("that station does not found. Please go to the /trains/stations/{city} and view your station");
        }
        return jsonArray.get(0).getAsJsonObject().get(NOM).getAsString();
    }

    private JsonArray getStationsArray(String station) throws IOException {
        JsonParser jsonParser = new JsonParser();
        String str = PZ_URL + PZ_STATION + URLEncoder.encode(station, "UTF-8");
        String body = Jsoup.connect(str).userAgent("Mozilla")
                .header("Content-Type", "text/html; charset=utf-8")
                .execute().body();
        body = body.substring(body.indexOf("[{"));
        return jsonParser.parse(body).getAsJsonArray();
    }
}
