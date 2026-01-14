package dk.cvr.backend.bll;

// Project imports
import dk.cvr.backend.be.Cvr;
import dk.cvr.backend.dal.api.CvrApiDAO;
import dk.cvr.backend.dal.ICvrApiDAO;

public class CvrManager {
    private ICvrApiDAO dao = new CvrApiDAO();

    public Cvr getCvrByNumber(String cvrNumber) throws Exception {
        return dao.getCvrByNumber(cvrNumber);
    }
}
