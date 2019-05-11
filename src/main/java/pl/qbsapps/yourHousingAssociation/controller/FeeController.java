package pl.qbsapps.yourHousingAssociation.controller;

import com.itextpdf.text.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
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

    @PostMapping("/{gas}/{coldWater}/{hotWater}/{sewage}")
    public ResponseEntity addFee(Principal user, @PathVariable double gas, @PathVariable double coldWater,
                                 @PathVariable double hotWater, @PathVariable double sewage) {
        feeService.addFees(user.getName(), gas, coldWater, hotWater, sewage);

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

    @PostMapping("/pay")
    public ResponseEntity payFee(Principal user) {
        feeService.payFee(user.getName());

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generatePdf(Principal user){
        ByteArrayInputStream document = feeService.generatePDF(user.getName());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=fee.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(document));
    }
}
