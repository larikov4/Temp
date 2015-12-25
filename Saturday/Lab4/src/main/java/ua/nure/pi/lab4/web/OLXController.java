package ua.nure.pi.lab4.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.nure.pi.lab4.olx.model.OLXQuery;
import ua.nure.pi.lab4.olx.model.OLXResponse;
import ua.nure.pi.lab4.olx.parser.OLXHtmlParser;

import java.io.IOException;

/**
 * Created by Yevhen_Larikov on 23-Dec-15.
 */
@RequestMapping(value = "olx/")
@Controller
public class OLXController {

    @Autowired
    private OLXHtmlParser parser;

    @RequestMapping(value = {"/location/{location}/product/{product}"}, produces = "application/json")
    @ResponseBody
    public OLXResponse getProducts(@PathVariable String location, @PathVariable String product,
                                   @RequestParam(defaultValue = "1") int page) throws IOException {
        OLXQuery query = new OLXQuery(product, location);
        query.setPage(page);
        int countOfPages = parser.getCountOfPages(query);
        if(countOfPages < page) {
            page = countOfPages;
            query.setPage(page);
        }
        return new OLXResponse(countOfPages, page, parser.getGoods(query));
    }

    @RequestMapping(value = {"/product/{product}"}, produces = "application/json")
    @ResponseBody
    public OLXResponse getProductsWithouLocation(@PathVariable String product,
                                                 @RequestParam(defaultValue = "1") int page) throws IOException {
        OLXQuery query = new OLXQuery();
        query.setProduct(product);
        query.setPage(page);
        int countOfPages = parser.getCountOfPages(query);
        if(countOfPages < page) {
            page = countOfPages;
            query.setPage(page);
        }
        return new OLXResponse(countOfPages, page, parser.getGoods(query));
    }
}
