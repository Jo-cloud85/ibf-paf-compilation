package ibf.paf3.day23workshop2.model;

import java.time.LocalDateTime;

public class Order {
    private int orderId;
    private LocalDateTime orderDate;
    private int customerId;
    private double totalPriceOfOrder;
    private double totalCostPrice;
    
    public Order() {
    }

    public Order(int orderId, LocalDateTime orderDate, int customerId, double totalPriceOfOrder, double totalCostPrice) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.totalPriceOfOrder = totalPriceOfOrder;
        this.totalCostPrice = totalCostPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getTotalPriceOfOrder() {
        return totalPriceOfOrder;
    }

    public void setTotalPriceOfOrder(double totalPriceOfOrder) {
        this.totalPriceOfOrder = totalPriceOfOrder;
    }

    public double getTotalCostPrice() {
        return totalCostPrice;
    }

    public void setTotalCostPrice(double totalCostPrice) {
        this.totalCostPrice = totalCostPrice;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", customerId=" + customerId
                + ", totalPriceOfOrder=" + totalPriceOfOrder + ", totalCostPrice=" + totalCostPrice + "]";
    }
}
