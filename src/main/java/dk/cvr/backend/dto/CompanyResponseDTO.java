package dk.cvr.backend.dto;

// Java imports
import java.util.List;

public class CompanyResponseDTO {

    private String vat;
    private String name;
    private String status;

    private String address;
    private Integer zipcode;
    private String city;

    private Boolean protection;

    private String phone;
    private String email;
    private String website;
    private String fax;

    private String startdate;
    private String enddate;

    private Integer employees;

    private String industrycode;
    private String industrydesc;
    private String companytype;
    private String companydesc;

    private List<String> owners;


    public CompanyResponseDTO(
            String vat,
            String name,
            String status,
            String address,
            Integer zipcode,
            String city,
            Boolean protection,
            String phone,
            String email,
            String fax,
            String startdate,
            String enddate,
            String website,
            Integer employees,
            String industrycode,
            String industrydesc,
            String companytype,
            String companydesc,
            List<String> owners,
            String lastUpdated
    ) {
        this.vat = vat;
        this.name = name;
        this.status = status;
        this.address = address;
        this.zipcode = zipcode;
        this.city = city;
        this.protection = protection;
        this.phone = phone;
        this.email = email;
        this.fax = fax;
        this.startdate = startdate;
        this.enddate = enddate;
        this.website = website;
        this.employees = employees;
        this.industrycode = industrycode;
        this.industrydesc = industrydesc;
        this.companytype = companytype;
        this.companydesc = companydesc;
        this.owners = owners;
    }

    public String getVat() { return vat; }
    public String getName() { return name; }
    public String getStatus() { return status; }
    public String getAddress() { return address; }
    public Integer getZipcode() { return zipcode; }
    public String getCity() { return city; }
    public Boolean getProtection() { return protection; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getFax() { return fax; }
    public String getStartdate() { return startdate; }
    public String getEnddate() { return enddate; }
    public String getWebsite() { return website; }
    public String getIndustrycode() { return industrycode; }
    public Integer getEmployees() { return employees; }
    public String getIndustrydesc() { return industrydesc; }
    public String getCompanytype() { return companytype; }
    public String getCompanydesc() { return companydesc; }
    public List<String> getOwners() { return owners; }
}
