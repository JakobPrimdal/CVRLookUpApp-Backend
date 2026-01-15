package dk.cvr.backend.dal.db;

// Maven dependencies
import com.microsoft.sqlserver.jdbc.SQLServerException;

// Project imports
import dk.cvr.backend.be.Cvr;
import dk.cvr.backend.dal.ICvrDbDAO;
import dk.cvr.backend.dal.db.DBConnector;

// Java imports
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CvrDbDAO implements ICvrDbDAO {
    private DBConnector databaseConnector;

    public CvrDbDAO() throws Exception {
        databaseConnector = new DBConnector();
    }

    public Cvr getCvrByNumber(String cvrNumber) throws Exception {

        String sql = "SELECT * FROM CVR WHERE Cvr = ?;";

        try (Connection conn = databaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cvrNumber);

            try (ResultSet rs = stmt.executeQuery()) {
                if(!rs.next()) {
                    throw new SQLException("CVR not found");
                }
                return mapRowToCvr(rs);
            }

        }
    }

    public Cvr createCvr(Cvr newCvr) throws Exception {
        String sql = """
                INSERT INTO CVR (Cvr, Name, Address, City, Zipcode,
                CompanyDesc, CompanyType, Status, IndustryDesc,
                IndustryCode, Employees, PhoneNum, Fax, Email,
                Website, StartDate, EndDate, Protected, Owners, LastUpdated)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);""";

        try (Connection conn = databaseConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, newCvr.getVat());
            stmt.setString(2, newCvr.getName());
            stmt.setString(3, newCvr.getAddress());
            stmt.setString(4, newCvr.getCity());

            Integer zipcode = newCvr.getZipcode();
            if (zipcode != null) {
                stmt.setInt(5, zipcode);
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setString(6, newCvr.getCompanydesc());
            stmt.setString(7, newCvr.getCompanytype());
            stmt.setString(8, newCvr.getStatus());
            stmt.setString(9, newCvr.getIndustrydesc());

            Integer industryCode = newCvr.getIndustrycode();
            if (industryCode != null) {
                stmt.setInt(10, industryCode);
            } else {
                stmt.setNull(10, Types.INTEGER);
            }

            Integer employees = newCvr.getEmployees();
            if (employees != null) {
                stmt.setInt(11, employees);
            } else {
                stmt.setNull(11, Types.INTEGER);
            }

            stmt.setString(12, newCvr.getPhone());
            stmt.setString(13, newCvr.getFax());
            stmt.setString(14, newCvr.getEmail());
            stmt.setString(15, newCvr.getWebsite());

            LocalDate startDate = null;
            if (newCvr.getStartdate() != null && !newCvr.getStartdate().isEmpty()) {
                startDate = LocalDate.parse(newCvr.getStartdate());
            }

            LocalDate endDate = null;
            if (newCvr.getEnddate() != null && !newCvr.getEnddate().isEmpty()) {
                endDate = LocalDate.parse(newCvr.getEnddate());
            }

            stmt.setDate(16, startDate != null ? Date.valueOf(startDate) : null);
            stmt.setDate(17, endDate != null ? Date.valueOf(endDate) : null);

            stmt.setInt(18, newCvr.getProtection() ? 1 : 0);

            String[] owners = newCvr.getOwners();
            stmt.setString(19, owners != null ? Arrays.toString(owners) : null);

            stmt.setTimestamp(20, Timestamp.valueOf(LocalDateTime.now()));

            stmt.executeUpdate();

            return getCvrByNumber(newCvr.getVat());
        }
    }

    public Cvr updateCvr(Cvr cvr) throws Exception {
        String sql = """
        UPDATE CVR
        SET Name = ?, Address = ?, City = ?, Zipcode = ?,
            CompanyDesc = ?, CompanyType = ?, Status = ?,
            IndustryDesc = ?, IndustryCode = ?, Employees = ?,
            PhoneNum = ?, Fax = ?, Email = ?, Website = ?,
            StartDate = ?, EndDate = ?, Protected = ?,
            Owners = ?, LastUpdated = ?
        WHERE Cvr = ?;
        """;

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cvr.getVat());
            stmt.setString(2, cvr.getName());
            stmt.setString(3, cvr.getAddress());
            stmt.setString(4, cvr.getCity());

            Integer zipcode = cvr.getZipcode();
            if (zipcode != null) {
                stmt.setInt(5, zipcode);
            } else {
                stmt.setNull(5, Types.INTEGER);
            }

            stmt.setString(6, cvr.getCompanydesc());
            stmt.setString(7, cvr.getCompanytype());
            stmt.setString(8, cvr.getStatus());
            stmt.setString(9, cvr.getIndustrydesc());

            Integer industryCode = cvr.getIndustrycode();
            if (industryCode != null) {
                stmt.setInt(10, industryCode);
            } else {
                stmt.setNull(10, Types.INTEGER);
            }

            Integer employees = cvr.getEmployees();
            if (employees != null) {
                stmt.setInt(11, employees);
            } else {
                stmt.setNull(11, Types.INTEGER);
            }

            stmt.setString(12, cvr.getPhone());
            stmt.setString(13, cvr.getFax());
            stmt.setString(14, cvr.getEmail());
            stmt.setString(15, cvr.getWebsite());
            LocalDate startDate = null;
            if (cvr.getStartdate() != null && !cvr.getStartdate().isEmpty()) {
                startDate = LocalDate.parse(cvr.getStartdate());
            }

            LocalDate endDate = null;
            if (cvr.getEnddate() != null && !cvr.getEnddate().isEmpty()) {
                endDate = LocalDate.parse(cvr.getEnddate());
            }

            stmt.setDate(16, startDate != null ? Date.valueOf(startDate) : null);
            stmt.setDate(17, endDate != null ? Date.valueOf(endDate) : null);

            stmt.setInt(17, cvr.getProtection() ? 1 : 0);

            String[] owners = cvr.getOwners();
            stmt.setString(18, owners != null ? Arrays.toString(owners) : null);

            stmt.setTimestamp(19, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(20, cvr.getVat());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("CVR not found – update failed");
            }

            return getCvrByNumber(cvr.getVat());
        }
    }

    public void deleteCvr(String cvrNumber) throws Exception {
        String sql = "DELETE FROM CVR WHERE Cvr = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cvrNumber);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("CVR not found – delete failed");
            }
        }
    }

    private Cvr mapRowToCvr(ResultSet rs) {
        try {
            Cvr company = new Cvr();

            company.setVat(rs.getString("Cvr"));
            company.setName(rs.getString("Name"));
            company.setAddress(rs.getString("Address"));
            company.setCity(rs.getString("City"));
            Integer zipcode = rs.getObject("Zipcode", Integer.class);
            company.setZipcode(zipcode != null ? zipcode : 0);

            company.setCompanydesc(rs.getString("CompanyDesc"));
            company.setCompanytype(rs.getString("CompanyType"));
            company.setStatus(rs.getString("Status"));
            company.setIndustrydesc(rs.getString("IndustryDesc"));

            Integer industryCode = rs.getObject("IndustryCode", Integer.class);
            company.setIndustrycode(industryCode != null ? industryCode : 0);

            Integer employees = rs.getObject("Employees", Integer.class);
            company.setEmployees(employees != null ? employees : 0);

            company.setPhone(rs.getString("PhoneNum"));
            company.setFax(rs.getString("Fax"));
            company.setEmail(rs.getString("Email"));
            company.setWebsite(rs.getString("Website"));

            Date start = rs.getDate("StartDate");
            company.setStartdate(start != null ? start.toLocalDate() : null);

            Date end = rs.getDate("EndDate");
            company.setEnddate(end != null ? end.toLocalDate() : null);

            Integer protectedValue = rs.getObject("Protected", Integer.class);
            company.setProtection(protectedValue != null && protectedValue == 1);

            String owners = rs.getString("Owners");
            company.setOwners(owners != null ? owners.replace("[", "").replace("]", "").split(", ") : new String[0]);

            Timestamp ts = rs.getTimestamp("LastUpdated");
            if (ts != null) {
                company.setLastUpdated(ts.toLocalDateTime());
            }

            return company;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map data from database" + e.getMessage());
        }

    }

    public boolean exists(String cvrNumber) throws Exception {
        String sql = """
                SELECT *
                FROM CVR
                WHERE Cvr = ?""";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cvrNumber);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                return true;
            } else {
                return false;
            }

        }
    }


}
