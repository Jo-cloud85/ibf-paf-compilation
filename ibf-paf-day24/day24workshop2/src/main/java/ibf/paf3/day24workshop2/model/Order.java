package ibf.paf3.day24workshop2.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Order {
    private Integer orderId;
    private Date orderDate;
    private String customerName;
    private String shipAddress;
    private String notes;
    private float tax;
    private List<OrderDetails> orderDetails = new LinkedList<>();

    public Order() {
        this.orderDate = new Date();
    }

    public Order(Integer orderId, Date orderDate, String customerName, String shipAddress, String notes, float tax,
            List<OrderDetails> orderDetails) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.shipAddress = shipAddress;
        this.notes = notes;
        this.tax = tax;
        this.orderDetails = orderDetails;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", customerName=" + customerName
                + ", shipAddress=" + shipAddress + ", notes=" + notes + ", tax=" + tax + ", orderDetails="
                + orderDetails + "]";
    }
}
