package ibf.paf3.day24workshop2.model;

public class OrderDetails {
    
    private Integer id;
    private String product;
    private float unitPrice;
    private float discount;
    private Integer quantity;
    private Integer orderId;

    public OrderDetails() {
    }

    public OrderDetails(Integer id, String product, float unitPrice, float discount, Integer quantity,
            Integer orderId) {
        this.id = id;
        this.product = product;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.quantity = quantity;
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderDetails [id=" + id + ", product=" + product + ", unitPrice=" + unitPrice + ", discount=" + discount
                + ", quantity=" + quantity + ", orderId=" + orderId + "]";
    }
}
