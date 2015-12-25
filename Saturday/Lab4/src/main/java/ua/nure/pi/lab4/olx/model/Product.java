package ua.nure.pi.lab4.olx.model;

/**
 * Created by Yevhen_Larikov on 23-Dec-15.
 */
public class Product {

    private String title;
    private String price;
    private String uri;

    public Product() {
    }

    public Product(String title, String price, String uri) {
        this.title = title;
        this.price = price;
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "Product{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", uri='" + uri + '\'' +
                '}';
    }
}
