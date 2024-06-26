package ibf2023.paf.day24.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class PurchaseOrder {
    private String poId;
    private String name;
    private String email;
    private Date deliveryDate;
    private boolean rush;
    private String comments;
    private Date lastUpdate;
    private List<LineItem> items = new LinkedList<>(); // name must match payload key
    
    public PurchaseOrder() {
    }

    public PurchaseOrder(String poId, String name, String email, Date deliveryDate, boolean rush, String comments,
            Date lastUpdate, List<LineItem> items) {
        this.poId = poId;
        this.name = name;
        this.email = email;
        this.deliveryDate = deliveryDate;
        this.rush = rush;
        this.comments = comments;
        this.lastUpdate = lastUpdate;
        this.items = items;
    }

    public String getPoId() {
        return poId;
    }

    public void setPoId(String poId) {
        this.poId = poId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public boolean isRush() {
        return rush;
    }

    public void setRush(boolean rush) {
        this.rush = rush;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public void setLineItems(List<LineItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "PurchaseOrder [poId=" + poId + 
                            ", name=" + name + 
                            ", email=" + email + 
                            ", deliveryDate=" + deliveryDate + 
                            ", rush=" + rush + 
                            ", comments=" + comments + 
                            ", lastUpdate=" + lastUpdate + 
                            ", items=" + items + "]";
    }    
}
