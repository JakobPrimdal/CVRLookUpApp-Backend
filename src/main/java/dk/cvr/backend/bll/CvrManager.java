package dk.cvr.backend.bll;

// Project imports
import dk.cvr.backend.be.Cvr;
import dk.cvr.backend.dal.ICvrDbDAO;
import dk.cvr.backend.dal.api.CvrApiDAO;
import dk.cvr.backend.dal.ICvrApiDAO;
import dk.cvr.backend.dal.db.CvrDbDAO;

// Java imports
import java.time.LocalDateTime;

public class CvrManager {
    private ICvrApiDAO apiDao = new CvrApiDAO();
    private ICvrDbDAO dbDao;

    public CvrManager() throws Exception {
        dbDao = new CvrDbDAO();
    }

    public Cvr getCvrByNumber(String cvrNumber) throws Exception {

        // DB-Cache Logic

        boolean exists = dbDao.exists(cvrNumber);

        if (exists) {
            Cvr db = dbDao.getCvrByNumber(cvrNumber);

            LocalDateTime lastUpdated = LocalDateTime.parse(db.getLastUpdated());
            LocalDateTime cutOffTime = LocalDateTime.now().minusHours(24);

            if (lastUpdated.isAfter(cutOffTime)) {
                // if cvr exists in DB && is less than 24 hours old - return cvr from DB
                return db;
            }
        }

        Cvr fresh = apiDao.getCvrByNumber(cvrNumber);

        if (exists) {
            // if cvr exists in DB && isn't less than 24 hours old - update cvr in DB
            dbDao.updateCvr(fresh);
        }
        else {
            // if cvr !exists in DB then - get cvr from API and create new cvr in DB
            dbDao.createCvr(fresh);
        }

        return fresh;

    }
}
