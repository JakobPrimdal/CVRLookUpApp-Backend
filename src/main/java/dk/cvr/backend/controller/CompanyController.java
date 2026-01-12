package dk.cvr.backend.controller;

// Spring imports
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @GetMapping("/{cvr}")
    public String getCompany(@PathVariable String cvr) {
        return "CVR: " + cvr;
    }
}