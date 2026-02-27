package dk.cvr.backend.bll;

// Project imports
import dk.cvr.backend.be.Cvr;
import dk.cvr.backend.dal.ICvrDbDAO;
import dk.cvr.backend.dal.api.CvrApiDAO;
import dk.cvr.backend.dal.ICvrApiDAO;
import dk.cvr.backend.dto.CompanyResponseDTO;

// Spring imports
import dk.cvr.backend.exception.CompanyNotFoundException;
import dk.cvr.backend.exception.CompanyServiceException;
import org.springframework.stereotype.Service;

// Java imports
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class CvrManager {
    private ICvrApiDAO apiDao = new CvrApiDAO();
    private ICvrDbDAO dbDao;

    public CvrManager(ICvrDbDAO dbDao) throws Exception {
        this.dbDao = dbDao;
    }

    public CompanyResponseDTO getCvrByNumber(String cvrNumber) {
        // DB-Cache Logic
        try {
            Cvr company = null;

            boolean exists = dbDao.exists(cvrNumber);

            if (exists) {
                Cvr db = dbDao.getCvrByNumber(cvrNumber);

                if (db == null)
                    throw new CompanyNotFoundException("Company with CVR " + cvrNumber + " not found");

                String lastUpdatedStr = db.getLastUpdated().replace(" ", "T");
                LocalDateTime lastUpdated = LocalDateTime.parse(lastUpdatedStr);
                LocalDateTime cutOffTime = LocalDateTime.now().minusHours(24);

                if (lastUpdated.isAfter(cutOffTime)) {
                    company = db;
                } else {
                    company = apiDao.getCvrByNumber(cvrNumber);
                    dbDao.updateCvr(company);
                }

            } else {
                company = apiDao.getCvrByNumber(cvrNumber);
                dbDao.createCvr(company);
            }

            if (company == null)
                throw new CompanyNotFoundException("Company with CVR " + cvrNumber + " not found");

            return mapToDTO(company);

        } catch (CompanyNotFoundException e) {
            throw e; // handled by GlobalExceptionHandler
        } catch (Exception e) {
            throw new CompanyServiceException("Database or API error while fetching CVR " + cvrNumber, e);
        }
    }

    private CompanyResponseDTO mapToDTO(Cvr company) {
        List<String> ownersList = null;

        if (company.getOwners() != null) {
            ownersList = Arrays.asList(company.getOwners());
        }

        return new CompanyResponseDTO(
                company.getVat(),
                company.getName(),
                company.getStatus(),
                company.getAddress(),
                company.getZipcode(),
                company.getCity(),
                company.getProtection(),
                company.getPhone(),
                company.getEmail(),
                company.getFax(),
                company.getStartdate(),
                company.getEnddate(),
                company.getWebsite(),
                company.getEmployees(),
                String.valueOf(company.getIndustrycode()),
                company.getIndustrydesc(),
                company.getCompanytype(),
                company.getCompanydesc(),
                company.getOwners()
        );

    }
}
