package dk.cvr.backend.dal;

// Project imports
import dk.cvr.backend.be.Cvr;

public interface ICvrDAO {

    Cvr getCvrByNumber(String cvrNumber) throws Exception;

}
