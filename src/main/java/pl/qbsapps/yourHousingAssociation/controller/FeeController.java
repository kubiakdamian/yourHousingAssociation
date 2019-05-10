package pl.qbsapps.yourHousingAssociation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.qbsapps.yourHousingAssociation.model.Fee;
import pl.qbsapps.yourHousingAssociation.model.response.FeeStatusResponse;
import pl.qbsapps.yourHousingAssociation.service.FeeService;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/fee")
public class FeeController {

    private final FeeService feeService;

    @Autowired
    public FeeController(FeeService feeService) {
        this.feeService = feeService;
    }

    @PostMapping("/{gas}/{coldWater}/{hotWater}/{sewage}/{heating}/{repairFund}")
    public ResponseEntity addFee(Principal user, @PathVariable float gas, @PathVariable float coldWater,
                                 @PathVariable float hotWater, @PathVariable float sewage, @PathVariable float heating,
                                 @PathVariable int repairFund) {
        feeService.addFees(user.getName(), gas, coldWater, hotWater, sewage, heating, repairFund);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/isAdded")
    public ResponseEntity<?> isFeeAlreadyAdded(Principal user) {
        boolean isAdded = feeService.isFeeFulfilled(user.getName());

        return ResponseEntity.ok(isAdded);
    }

    @GetMapping("/notAccepted")
    public ResponseEntity<?> getNotAcceptedFeesManagedByManager(Principal user) {
        ArrayList<Fee> fees = feeService.getNotAcceptedManagedFees(user.getName());

        return ResponseEntity.ok(fees);
    }

    @PostMapping("/accept/{feeId}")
    public ResponseEntity acceptManagedFee(Principal user, @PathVariable Long feeId) {
        feeService.acceptManagedFee(user.getName(), feeId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/decline/{feeId}")
    public ResponseEntity declineManagedFee(Principal user, @PathVariable Long feeId) {
        feeService.declineManagedFee(user.getName(), feeId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getFeeStatus(Principal user) {
        FeeStatusResponse feeStatusResponse = feeService.getFeeStatus(user.getName());

        return ResponseEntity.ok(feeStatusResponse);
    }
}
