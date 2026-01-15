package dk.cvr.backend.controller;

// Project imports
import dk.cvr.backend.be.Cvr;
import dk.cvr.backend.bll.CvrManager;

// Spring imports
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private CvrManager manager;

    public CompanyController() {
        try {
            manager = new CvrManager();
        } catch (Exception e) {
            // Display error to user???
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{cvr}")
    public Cvr getCompany(@PathVariable String cvr) {
        try {
            return manager.getCvrByNumber(cvr);
        } catch (Exception e) {
            // Display error to user?????
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}