package dk.cvr.backend.dal.db;

// Java imports
import java.sql.Connection;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
@Profile("db") /** Remove this @Profile annotation if DB is ever needed again **/
public class DBConnector {

    private final DataSource dataSource;

    public DBConnector(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

}
