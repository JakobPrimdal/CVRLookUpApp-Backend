package dk.cvr.backend.bll;

// Project imports
import dk.cvr.backend.be.Cvr;
import dk.cvr.backend.dal.ICvrDbDAO;
import dk.cvr.backend.dal.api.CvrApiDAO;
import dk.cvr.backend.dal.ICvrApiDAO;
import dk.cvr.backend.dto.CompanyResponseDTO;

// Spring imports
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

    public CompanyResponseDTO getCvrByNumber(String cvrNumber) throws Exception {

        // DB-Cache Logic

        Cvr company;

        boolean exists = dbDao.exists(cvrNumber);

        if (exists) {
            Cvr db = dbDao.getCvrByNumber(cvrNumber);

            String lastUpdatedStr = db.getLastUpdated().replace(" ", "T");
            LocalDateTime lastUpdated = LocalDateTime.parse(lastUpdatedStr);
            LocalDateTime cutOffTime = LocalDateTime.now().minusHours(24);

            if (lastUpdated.isAfter(cutOffTime)) {
                // if cvr exists in DB && is less than 24 hours old - return cvr from DB
                company = db;
            } else {
                // if cvr exists in DB && isn't less than 24 hours old - update cvr in DB
                company = apiDao.getCvrByNumber(cvrNumber);
                dbDao.updateCvr(company);
            }

        } else {
            // if cvr !exists in DB then - get cvr from API and create new cvr in DB
            company = apiDao.getCvrByNumber(cvrNumber);
            dbDao.createCvr(company);
        }

        return mapToDTO(company);

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
                List.of(company.getOwners()),
                company.getLastUpdated()
        );

    }
}
