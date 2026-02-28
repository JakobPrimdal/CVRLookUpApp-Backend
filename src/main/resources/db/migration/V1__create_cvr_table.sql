CREATE TABLE IF NOT EXISTS cvr (
    Cvr VARCHAR(8) PRIMARY KEY,

    Name VARCHAR(255),
    Address VARCHAR(255),
    City VARCHAR(100),
    Zipcode INTEGER,

    CompanyDesc TEXT,
    CompanyType VARCHAR(100),
    Status VARCHAR(100),

    IndustryDesc TEXT,
    IndustryCode INTEGER,

    Employees INTEGER,

    PhoneNum VARCHAR(50),
    Fax VARCHAR(50),
    Email VARCHAR(255),
    Website VARCHAR(255),

    StartDate DATE,
    EndDate DATE,

    Protected BOOLEAN,

    Owners TEXT,

    LastUpdated TIMESTAMP NOT NULL
);