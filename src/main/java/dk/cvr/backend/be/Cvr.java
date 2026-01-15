package dk.cvr.backend.be;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Cvr {
    private String vat;                    // CVR-nummer
    private String status;                 // Status (Aktiv, ophørt, under konkurs)
    private String name;                   // Virksomhedens navn
    private String address;                // Virksomhedens adresse
    private Integer zipcode;               // Postnummer
    private String city;                   // By
    private Boolean protection;            // Reklamebeskyttelse (protected in API)
    private String phone;                  // Telefonnummer
    private String website;                // Hjemmeside
    private String email;                  // Email
    private String fax;                    // Faxnummer
    private String startDate;              // Etableret dato (YYYY-MM-DD)
    private String endDate;                // Lukket dato (YYYY-MM-DD)
    private Integer employees;             // Antal medarbejdere
    private Integer industryCode;          // Branchekode nummer
    private String industryDesc;           // Branchekode beskrivelse
    private String companyType;            // Virksomhedstype (kort)
    private String companyDesc;            // Virksomhedstype (fuld)
    private String[] owners;               // Ejere (array)
    private FinancialSummary[] financialSummary;  // Regnskabsdata (array)
    private String lastUpdated;

    // Financial summary
    public static class FinancialSummary {
        private String year;
        private Double revenue;
        private Double profit;
        private Double equity;

        public FinancialSummary() {}

        public String getYear() { return year; }
        public void setYear(String year) {
            if (year != null)
                this.year = year;
        }

        public Double getRevenue() { return revenue; }
        public void setRevenue(Double revenue) {
            this.revenue = revenue;
        }

        public Double getProfit() { return profit; }
        public void setProfit(Double profit) {
            this.profit = profit;
        }

        public Double getEquity() { return equity; }
        public void setEquity(Double equity) {
            this.equity = equity;
        }
    }


    // Getters and Setters
    public String getVat() { return vat; }
    public void setVat(String vat) {
        if (vat != null)
            this.vat = vat;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) {
        if (status != null)
            this.status = status;
    }

    public String getName() { return name; }
    public void setName(String name) {
        if (name != null)
            this.name = name;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) {
        if (address != null)
            this.address = address;
    }

    public Integer getZipcode() { return zipcode; }
    public void setZipcode(Integer zipcode) {
        if (zipcode != null)
            this.zipcode = zipcode;
    }

    public String getCity() { return city; }
    public void setCity(String city) {
        if (city != null)
            this.city = city;
    }

    public Boolean getProtection() { return protection; }
    public void setProtection(Boolean protection) {
        this.protection = protection;
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) {
        if (phone != null)
            this.phone = phone;
    }

    public String getWebsite() { return website; }
    public void setWebsite(String website) {
        if (website != null)
            this.website = website;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email != null)
            this.email = email;
    }

    public String getFax() { return fax; }
    public void setFax(String fax) {
        if (fax != null)
            this.fax = fax;
    }

    public String getStartdate() { return startDate; }
    public void setStartdate(LocalDate startDate) {
        if (startDate != null)
            this.startDate = String.valueOf(startDate);
    }

    public String getEnddate() { return endDate; }
    public void setEnddate(LocalDate endDate) {
        if (endDate != null)
            this.endDate = String.valueOf(endDate);
    }

    public Integer getEmployees() { return employees; }
    public void setEmployees(Integer employees) {
        if (employees != null)
            this.employees = employees;
    }

    public Integer getIndustrycode() { return industryCode; }
    public void setIndustrycode(Integer industrycode) {
        if (industrycode != null)
            this.industryCode = industrycode;
    }

    public String getIndustrydesc() { return industryDesc; }
    public void setIndustrydesc(String industrydesc) {
        if (industrydesc != null)
            this.industryDesc = industrydesc;
    }

    public String getCompanytype() { return companyType; }
    public void setCompanytype(String companytype) {
        if (companytype != null)
            this.companyType = companytype;
    }

    public String getCompanydesc() { return companyDesc; }
    public void setCompanydesc(String companydesc) {
        if (companydesc != null)
            this.companyDesc = companydesc;
    }

    public String[] getOwners() { return owners; }
    public void setOwners(String[] owners) {
        if (owners != null)
            this.owners = owners;
    }

    public FinancialSummary[] getFinancialSummary() { return financialSummary; }
    public void setFinancialSummary(FinancialSummary[] financialSummary) {
        if (financialSummary != null)
            this.financialSummary = financialSummary;
    }

    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(LocalDateTime lastUpdated) {
        if (lastUpdated != null)
            this.lastUpdated = lastUpdated.toString();
    }

    @Override
    public String toString() {
        return getName();
    }
}

