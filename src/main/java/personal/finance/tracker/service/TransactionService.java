package personal.finance.tracker.service;

import personal.finance.tracker.entity.Transaction;
import personal.finance.tracker.model.BankDetails;
import personal.finance.tracker.model.TransactionResponseModel;
import personal.finance.tracker.request.TransactionRequest;


public interface TransactionService {
    BankDetails addTransaction(Transaction transaction);

    TransactionResponseModel getTransactionForUser(TransactionRequest request);
}
