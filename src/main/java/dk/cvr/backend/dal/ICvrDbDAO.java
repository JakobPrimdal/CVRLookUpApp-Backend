package dk.cvr.backend.dal;

// Project imports
import dk.cvr.backend.be.Cvr;

public interface ICvrDbDAO {

    public Cvr getCvrByNumber(String cvrNumber) throws Exception;

    public Cvr createCvr(Cvr newCvr) throws Exception;

    public Cvr updateCvr(Cvr cvr) throws Exception;

    public void deleteCvr(String cvrNumber) throws Exception;

    public boolean exists(String cvrNumber) throws Exception;
}
