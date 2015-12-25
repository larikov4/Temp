package ua.nure.pi.lab4.olx.parser;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import ua.nure.pi.lab4.olx.model.OLXQuery;
import ua.nure.pi.lab4.olx.model.Product;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yevhen_Larikov on 23-Dec-15.
 */
@Component
public class OLXHtmlParser {

    private static final String OLX_DOMAIN = "http://olx.ua/";
    private static final String DELIMETER = "/";
    private static final String UTF_8 = "UTF-8";
    private static final String URL_SELECTOR = "a.marginright5.link.linkWithHash.detailsLink";
    private static final String WITHOUT_LOCATION = "list/";
    private static final String PAGE = "?page=";

    public List<Product> getGoods(OLXQuery query) throws IOException {
        String url = makeUrl(query);
        if(query.getPage() > 1) {
            url += PAGE + query.getPage();
        }
        Document document = Jsoup.connect(url).get();
        return parseDocument(document);
    }

    public int getCountOfPages(OLXQuery query) throws IOException {
        Document document = Jsoup.connect(makeUrl(query)).get();
        return getPageCount(document);
    }

    private String makeUrl(OLXQuery query) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        if (!StringUtils.isBlank(query.getLocation())) {
            sb.append(URLEncoder.encode(query.getLocation(), UTF_8));
            sb.append(DELIMETER);
        } else {
            sb.append(WITHOUT_LOCATION);
        }
        if (StringUtils.isBlank(query.getProduct())) {
            return "";
        } else {
            sb.append("q-").append(URLEncoder.encode(query.getProduct(), UTF_8));
            sb.append(DELIMETER);
        }
        return OLX_DOMAIN + sb.toString();
    }

    private List<Product> parseDocument(Document document) {
        List<Product> products = new ArrayList<>();
        Elements tds = document.select(".offer");
        Product product;
        for (Element element : tds) {
            product = getProduct(element);
            if (product != null) {
                products.add(product);
            }
        }
        return products;
    }

    private int getPageCount(Document document) {
        Element element = document.select("div.pager.rel.clr").first();
        if (element == null) {
            return 1;
        }
        String textPage = element.select("span.item.fleft").last().select("a > span").text();
        return Integer.valueOf(textPage);
    }

    private Product getProduct(Element element) {
        Product product = new Product();
        product.setTitle(getTitle(element));
        if (StringUtils.isBlank(product.getTitle())) {
            return null;
        }
        product.setPrice(getPrice(element));
        product.setUri(getURI(element));
        return product;
    }

    private String getTitle(Element element) {
        Element select = element.select(".detailsLink").select("strong").first();
        return select != null ? select.text() : "";
    }

    private String getURI(Element element) {
        Element first = element.select(URL_SELECTOR).first();
        return first != null ? first.attr("href") : "";
    }

    private String getPrice(Element element) {
        Element first = element.select(".price").select("strong").first();
        return first != null ? first.text() : "";
    }
}
