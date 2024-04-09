package ibf.paf3.day21workshop.model;

public class Orders extends Customer {
    private double taxRate;
    private String orderDate;

    public Orders() {}

    public Orders(double taxRate, String orderDate) {
        this.taxRate = taxRate;
        this.orderDate = orderDate;
    }

    public Orders(Integer id, String firstName, String lastName, double taxRate, String orderDate) {
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
