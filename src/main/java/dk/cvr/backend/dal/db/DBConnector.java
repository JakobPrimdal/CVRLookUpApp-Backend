package dk.cvr.backend.dal.db;

// Java imports
import java.sql.Connection;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
public class DBConnector {

    private final DataSource dataSource;

    public DBConnector(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

}
