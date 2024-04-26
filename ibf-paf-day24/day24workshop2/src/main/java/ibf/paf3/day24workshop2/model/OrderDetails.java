package ibf.paf3.day24workshop2.model;

public class OrderDetails {
    
    private Integer id;
    private String product;
    private float unitPrice;
    private float discount;
    private int quantity;

    public OrderDetails() {
    }

    public OrderDetails(Integer id, String product, float unitPrice, float discount, int quantity) {
        this.id = id;
        this.product = product;
        this.unitPrice = unitPrice;
        this.discount = discount;
        this.quantity = quantity;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetails [id=" + id + ", product=" + product + ", unitPrice=" + unitPrice
                + ", discount=" + discount + ", quantity=" + quantity + "]";
    }

    
}
