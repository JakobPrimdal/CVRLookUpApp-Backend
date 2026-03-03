package dk.cvr.backend.controller;

// Project imports
import dk.cvr.backend.bll.CvrManager;
import dk.cvr.backend.dto.CompanyResponseDTO;

// Spring imports
import dk.cvr.backend.exception.CompanyNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:5173/")
@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private CvrManager manager;

    public CompanyController(CvrManager manager) {
        this.manager = manager;
    }

    @GetMapping("/{cvr}")
    public CompanyResponseDTO getCompany(@PathVariable String cvr) {
        return manager.getCvrByNumber(cvr);
    }
}