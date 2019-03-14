package pl.qbsapps.yourHousingAssociation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/test")
    public String test() {
        return "TEST";
    }
}
