package ua.nure.pi.lab4.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nure.pi.lab4.pz.gov.ua.model.PZQuery;
import ua.nure.pi.lab4.pz.gov.ua.model.PZResponse;
import ua.nure.pi.lab4.pz.gov.ua.model.StationNotFoundException;
import ua.nure.pi.lab4.pz.gov.ua.parser.PZHtmlParser;

import java.io.IOException;
import java.util.List;

/**
 * Created by Yevhen_Larikov on 23-Dec-15.
 */
@RequestMapping(value = "trains/")
@Controller
public class PZController {

    @Autowired
    private PZHtmlParser parser;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public PZResponse getTrains(@RequestParam String from,
                                @RequestParam String to, @RequestParam String date) throws IOException, StationNotFoundException {
        PZQuery query = new PZQuery(from, to, date);
        return parser.getRoutes(query);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getTrainsForm() {
        return "trains-page";
    }

    @RequestMapping(value = "/stations", produces = "application/json", method = RequestMethod.POST)
    @ResponseBody
    public List<String> getStations(@RequestParam String city) throws IOException {
        return parser.getStations(city);
    }

    @RequestMapping(value = "/stations", method = RequestMethod.GET)
    public String getStationsForm() {
        return "stations-page";
    }
}
