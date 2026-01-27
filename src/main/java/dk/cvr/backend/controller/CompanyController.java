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

    public CompanyController() throws Exception {
        try {
            manager = new CvrManager();
        } catch (Exception e) {
            throw new Exception("Failed to instantiate manager class in backend: " + e.getMessage());
        }
    }

    @GetMapping("/{cvr}")
    public Cvr getCompany(@PathVariable String cvr) throws Exception {
        try {
            return manager.getCvrByNumber(cvr);
        } catch (Exception e) {
            throw new Exception("Failed to retrieve company data from backend: " + e.getMessage());
        }
    }
}