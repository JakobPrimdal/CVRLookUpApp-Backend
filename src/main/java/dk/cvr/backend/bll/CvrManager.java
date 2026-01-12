package dk.cvr.backend.bll;

// Project imports
import dk.cvr.backend.be.Cvr;
import dk.cvr.backend.dal.CvrDAO;
import dk.cvr.backend.dal.ICvrDAO;

public class CvrManager {
    private ICvrDAO dao = new CvrDAO();

    public Cvr getCvrByNumber(String cvrNumber) throws Exception {
        return dao.getCvrByNumber(cvrNumber);
    }
}
