package ibf.paf3.day24.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ibf.paf3.day24.exception.AccountsException;
import ibf.paf3.day24.repo.AccountsRepository;

// rollback if its an unchecked exception
// no rollback if its a checked exception

@Service
public class AccountsService {

    @Autowired
    private AccountsRepository accountsRepo;

    // @Autowired
    // private PlatformTransactionManager txMgr;

    // public void fundsTransfer2(String fromAcct, String toAcct, float amount) {
    //     TransactionStatus txStatus = txMgr.getTransaction(TransactionDefinition.withDefaults());
    //     try {
    //         accountsRepo.updateBalanceById(fromAcct, -amount);
    //         accountsRepo.updateBalanceById(toAcct, amount);
    //         txMgr.commit(txStatus);
    //     } catch (Exception ex) {
    //         txMgr.rollback(txStatus);
    //     }
    // }

    /* If there isn't any AccountException, the 'default' exception that will be thrown is RuntimeException 
    // which is unchecked exception. Transactions will rollback when an unchecked exception is thrown.

    When we add our own AccountsException because we want to customize our own exception, this AccountException
    is a checked exception, if we nvr rollbackFor, it will go through and any error related to account will not 
    get caught i.e. it does not get blocked by the AccountsException that we create. */
    @Transactional (rollbackFor = AccountsException.class)
    public void fundsTransfer(String fromAcct, String toAcct, float amount) throws AccountsException {

        // start transaction

        if (!accountsRepo.updateBalanceById(fromAcct, -amount)) {
            throw new AccountsException("Cannot perform transfer");
        }

        if (!accountsRepo.updateBalanceById(toAcct, +amount)){
            throw new AccountsException("Cannot perform transfer");
        }

        //commit
        
    }
}
