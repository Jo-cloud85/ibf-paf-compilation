package ibf2023.paf.day24.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import ibf2023.paf.day24.model.PurchaseOrder;

@Repository
public class PurchaseOrderRepository implements Queries {
    
    @Autowired
    private JdbcTemplate template;

    public void createPurchaseOrder(PurchaseOrder poWithId) {

        // Check if customer exists by the given poId
        boolean doesCustomerExist = isCustomerExist(poWithId.getEmail());

        if (!doesCustomerExist) {
            // If customer does not exist, add the new customer
            addNewCustomer(poWithId.getEmail(), poWithId.getName());
        }

        // insert into purchase_order(po_id, email, delivery_date, rush, comments) values
        // poId will be generated in PurchaseOrderService
        template.update(
            SQL_PO_INSERT_PURCHASEORDER, 
            poWithId.getPoId(),
            poWithId.getEmail(), 
            poWithId.getDeliveryDate(), 
            poWithId.isRush(), 
            poWithId.getComments(),
            poWithId.getLastUpdate());
   }

   public boolean isCustomerExist(String email) {
        boolean isExist = false;
        final SqlRowSet rs = template.queryForRowSet(SQL_DOES_CUSTOMER_EXIST, email);

        if(rs.next()){
            int count = rs.getInt(1);
            if(count > 0) {
                isExist = true;
            }
        }
        return isExist;
    }

    public void addNewCustomer(String email, String name) {
        boolean doesCustomerExist = isCustomerExist(email);
        if (!doesCustomerExist) {
            template.update(SQL_INSERT_CUSTOMER, email, name);
        }
    }
}
