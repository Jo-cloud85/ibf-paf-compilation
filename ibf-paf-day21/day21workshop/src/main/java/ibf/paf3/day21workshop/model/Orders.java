package ibf.paf3.day21workshop.model;

import java.util.Date;

public class Orders extends Customer {
    private double taxRate;
    private Date orderDate;

    public Orders() {}

    public Orders(double taxRate, Date orderDate) {
        this.taxRate = taxRate;
        this.orderDate = orderDate;
    }

    public Orders(Integer id, String firstName, String lastName, double taxRate, Date orderDate) {
        super(id, firstName, lastName);
        this.taxRate = taxRate;
        this.orderDate = orderDate;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
