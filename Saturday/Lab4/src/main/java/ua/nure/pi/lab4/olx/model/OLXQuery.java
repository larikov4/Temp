package ua.nure.pi.lab4.olx.model;

/**
 * Created by Yevhen_Larikov on 23-Dec-15.
 */
public class OLXQuery {

    private String product;
    private String location;
    private int page;

    public OLXQuery() {
    }

    public OLXQuery(String product, String location) {
        this.product = product;
        this.location = location;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
