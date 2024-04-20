package ibf2023.paf.day24.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Propagation;
//import org.springframework.transaction.annotation.Transactional;

import ibf2023.paf.day24.model.LineItem;

@Repository
public class LineItemsRepository implements Queries {

   @Autowired
   private JdbcTemplate template;

   //@Transactional(propagation = Propagation.MANDATORY)
   public void createLineItems(String poId, List<LineItem> lineItems) {
      for (LineItem li: lineItems)
         template.update(SQL_LI_INSERT_LINEITEM, li.getItem(), li.getQuantity(), poId);
   }
   
}
