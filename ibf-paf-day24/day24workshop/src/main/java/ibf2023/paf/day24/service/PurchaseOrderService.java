package ibf2023.paf.day24.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import ibf2023.paf.day24.exception.PurchaseOrderException;
import ibf2023.paf.day24.model.PurchaseOrder;
import ibf2023.paf.day24.repo.LineItemsRepository;
import ibf2023.paf.day24.repo.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {

   @Autowired
   private PurchaseOrderRepository poRepo;

   @Autowired
   private LineItemsRepository liRepo;

   // 'throws PurchaseOrderException' is the rollback
   @Transactional(rollbackFor = PurchaseOrderException.class, isolation=Isolation.REPEATABLE_READ)
   public void insertPurchaseOrder(PurchaseOrder poWithId) throws PurchaseOrderException {

        // start transaction

        try {
            // String poId = UUID.randomUUID().toString().substring(0, 8);
            poRepo.createPurchaseOrder(poWithId);
            liRepo.createLineItems(poWithId.getPoId(), poWithId.getItems());
        } catch (Exception ex) {
            throw new PurchaseOrderException(ex.getMessage());
        }
        
        // commit
    }

}