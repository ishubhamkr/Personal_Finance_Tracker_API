package personal.finance.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.finance.tracker.entity.Transaction;
import personal.finance.tracker.model.BankDetails;
import personal.finance.tracker.model.TransactionResponseModel;
import personal.finance.tracker.request.TransactionRequest;
import personal.finance.tracker.service.TransactionService;


@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Endpoint to add a new transaction
    @PostMapping("/add")
    public ResponseEntity<Object> addTransaction(@RequestBody Transaction transaction) {
        try {
            BankDetails savedTransaction = transactionService.addTransaction(transaction);
            return ResponseEntity.ok(savedTransaction);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Endpoint to retrieve transactions for a user by bankId
    @GetMapping("/user/info")
    public ResponseEntity<Object> getTransactionsByUserId(@RequestBody TransactionRequest request) {
        try {
            TransactionResponseModel transactions = transactionService.getTransactionForUser(request);
            return ResponseEntity.ok(transactions);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
