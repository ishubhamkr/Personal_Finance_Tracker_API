package personal.finance.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.finance.tracker.entity.BorrowerDetails;
import personal.finance.tracker.service.BorrowerDetailsService;

import java.util.List;

@RestController
@RequestMapping("/borrower")
public class BorrowerDetailsController {

    @Autowired
    private BorrowerDetailsService borrowerDetailsService;


    @PostMapping("/add")
    public ResponseEntity<Object> addBorrowerDetails(@RequestBody BorrowerDetails borrowerDetails) {
        try {
            BorrowerDetails savedBorrower = borrowerDetailsService.saveBorrowerDetails(borrowerDetails);
            return ResponseEntity.ok(savedBorrower);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/get")
    public ResponseEntity<Object> getBorrowerDetails(
            @RequestParam String userId, @RequestParam String bankId) {
        try {
            List<BorrowerDetails> borrowerDetails = borrowerDetailsService.getBorrowerDetailsByUserIdAndBankId(userId, bankId);
            return ResponseEntity.ok(borrowerDetails);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
