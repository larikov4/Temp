package ua.nure.pi.lab4.olx.model;

import java.util.List;

/**
 * Created by Yevhen_Larikov on 23-Dec-15.
 */
public class OLXResponse {

    private int countOfPages;
    private int currentPage;
    private List<Product> products;

    public OLXResponse() {
    }

    public OLXResponse(int countOfPages, int currentPage, List<Product> products) {
        this.countOfPages = countOfPages;
        this.currentPage = currentPage;
        this.products = products;
    }

    public int getCountOfPages() {
        return countOfPages;
    }

    public void setCountOfPages(int countOfPages) {
        this.countOfPages = countOfPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
