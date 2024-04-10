package ibf.paf3.day21workshop.model;

import java.time.LocalDateTime;

public class Orders extends Customer {
    private double taxRate;
    private LocalDateTime orderDate;

    public Orders() {}

    public Orders(double taxRate, LocalDateTime orderDate) {
        this.taxRate = taxRate;
        this.orderDate = orderDate;
    }

    public Orders(Integer id, String firstName, String lastName, double taxRate, LocalDateTime orderDate) {
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
