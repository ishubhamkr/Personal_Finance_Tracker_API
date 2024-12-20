package personal.finance.tracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.finance.tracker.model.BankDetails;
import personal.finance.tracker.request.BankRegisterRequest;
import personal.finance.tracker.service.BankInfoService;

import java.util.List;

@RestController
@RequestMapping("/bank")
public class BankInfoController {

    @Autowired
    private BankInfoService bankInfoService;

    @PostMapping("/add")
    public ResponseEntity<Object> addBankInfo(@RequestBody BankRegisterRequest bankInfo) {
        try {
            BankDetails savedBankInfo = bankInfoService.addBankInfo(bankInfo);
            return ResponseEntity.ok(savedBankInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getBankInfoByUserId(@PathVariable String userId) {
        try {
            List<BankDetails> bankInfo = bankInfoService.getBankInfoByUserId(userId);
            return ResponseEntity.ok(bankInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
