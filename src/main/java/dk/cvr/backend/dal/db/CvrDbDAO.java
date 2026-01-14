package dk.cvr.backend.dal.db;

// Maven dependencies
import com.microsoft.sqlserver.jdbc.SQLServerException;

// Project imports
import dk.cvr.backend.be.Cvr;
import dk.cvr.backend.dal.db.DBConnector;

// Java imports
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CvrDbDAO {
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

    private Cvr mapRowToCvr(ResultSet rs) {
        try {
            Cvr company = new Cvr();

            company.setVat(rs.getString("Cvr"));
            company.setName(rs.getString("Name"));
            company.setAddress(rs.getString("Address"));
            company.setCity(rs.getString("City"));
            company.setZipcode(rs.getInt("Zipcode"));
            company.setCompanydesc(rs.getString("CompanyDesc"));
            company.setCompanytype(rs.getString("CompanyType"));
            company.setStatus(rs.getString("Status"));
            company.setIndustrydesc(rs.getString("IndustryDesc"));
            company.setIndustrycode(rs.getInt("IndustryCode"));
            company.setEmployees(rs.getInt("Employees"));
            company.setPhone(rs.getString("PhoneNum"));
            company.setFax(rs.getString("Fax"));
            company.setEmail(rs.getString("Email"));
            company.setWebsite(rs.getString("Website"));
            company.setStartdate(rs.getDate("StartDate").toLocalDate());
            company.setEnddate(rs.getDate("EndDate").toLocalDate());
            company.setProtection(rs.getInt("Protected") == 1);
            company.setOwners(rs.getString("Owners").replace("[", "").replace("]", "").split(", "));
            company.setLastUpdated(rs.getTimestamp("LastUpdated").toLocalDateTime());

            return company;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to map data from database" + e.getMessage());
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
            stmt.setInt(5, newCvr.getZipcode());
            stmt.setString(6, newCvr.getCompanydesc());
            stmt.setString(7, newCvr.getCompanytype());
            stmt.setString(8, newCvr.getStatus());
            stmt.setString(9, newCvr.getIndustrydesc());
            stmt.setInt(10, newCvr.getIndustrycode());
            stmt.setInt(11, newCvr.getEmployees());
            stmt.setString(12, newCvr.getPhone());
            stmt.setString(13, newCvr.getFax());
            stmt.setString(14, newCvr.getEmail());
            stmt.setString(15, newCvr.getWebsite());
            stmt.setDate(16, Date.valueOf(newCvr.getStartdate()));
            stmt.setDate(17, Date.valueOf(newCvr.getEnddate()));
            stmt.setInt(18, newCvr.getProtection() ? 1 : 0);
            stmt.setString(19, Arrays.toString(newCvr.getOwners()));
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

            stmt.setString(1, cvr.getName());
            stmt.setString(2, cvr.getAddress());
            stmt.setString(3, cvr.getCity());
            stmt.setInt(4, cvr.getZipcode());
            stmt.setString(5, cvr.getCompanydesc());
            stmt.setString(6, cvr.getCompanytype());
            stmt.setString(7, cvr.getStatus());
            stmt.setString(8, cvr.getIndustrydesc());
            stmt.setInt(9, cvr.getIndustrycode());
            stmt.setInt(10, cvr.getEmployees());
            stmt.setString(11, cvr.getPhone());
            stmt.setString(12, cvr.getFax());
            stmt.setString(13, cvr.getEmail());
            stmt.setString(14, cvr.getWebsite());
            stmt.setDate(15, Date.valueOf(cvr.getStartdate()));
            stmt.setDate(16, Date.valueOf(cvr.getEnddate()));
            stmt.setInt(17, cvr.getProtection() ? 1 : 0);
            stmt.setString(18, Arrays.toString(cvr.getOwners()));
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





}
