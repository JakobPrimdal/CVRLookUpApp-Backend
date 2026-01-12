package dk.cvr.backend.dal;

// Maven dependencies
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

// Project imports
import dk.cvr.backend.be.Cvr;

// Java imports
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class CvrDAO implements ICvrDAO
{
    private static final String BASE_URL = "https://virkdata.dk/api/";
    private final HttpClient httpClient;
    private final Gson gson;

    public CvrDAO() {
        this.httpClient = HttpClient.newHttpClient();
        // Configure Gson to handle the field name mapping
        this.gson = new GsonBuilder()
                .setFieldNamingStrategy(field -> {
                    // Map "financialSummary" in Java to "financial_summary" in JSON
                    if (field.getName().equals("financialSummary")) {
                        return "financial_summary";
                    }
                    // Map "protection" in Java to "protected" in JSON
                    if (field.getName().equals("protection")) {
                        return "protected";
                    }
                    return field.getName();
                })
                .create();
    }


    public Cvr getCvrByNumber(String cvrNumber) throws Exception {
        if (cvrNumber == null || cvrNumber.trim().isEmpty()) {
            throw new Exception("CVR number cannot be null or empty");
        }

        try {
            String url = BASE_URL + "?search=" + cvrNumber.trim() + "&country=dk";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "CvrLookupApp/1.0")
                    .header("Authorization", getAPIKey())
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() == 200) {
                return parseCvrResponse(response.body());
            } else if (response.statusCode() == 404) {
                throw new Exception("CVR number not found: " + cvrNumber);
            } else {
                throw new Exception(
                        "Failed to fetch CVR data. Status code: " + response.statusCode()
                );
            }

        } catch (IOException e) {
            throw new Exception("Network error while accessing CVR API", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new Exception("Request was interrupted", e);
        }
    }

    private Cvr parseCvrResponse(String jsonResponse) throws Exception {
        try {
            Cvr cvr = gson.fromJson(jsonResponse, Cvr.class);

            if (cvr == null) {
                throw new Exception("Failed to parse CVR data");
            }

            return cvr;

        } catch (JsonSyntaxException e) {
            throw new Exception("Invalid JSON response from CVR API", e);
        }
    }

    private static String getAPIKey() {
        Properties accessKey = new Properties();
        try {
            accessKey.load(new FileInputStream(new File("config/API.key")));
        } catch (IOException e) {
            // Display error to user // Throw exception upwards instead
            throw new RuntimeException("Could not get API_Key");
        }
        return accessKey.getProperty("API_Key");
    }

    public static void main(String[] args) {
        CvrDAO dao = new CvrDAO();
        try {
            System.out.println("Starting CVR lookup...");
            Cvr test = dao.getCvrByNumber("43342878");
            System.out.println("Success! Company name: " + test.getName());
            System.out.println("Address: " + test.getAddress());
            System.out.println("City: " + test.getCity());
        } catch (Exception e) {
            System.out.println("Failed test method");
            System.out.println("Error message: " + e.getMessage());
            e.printStackTrace();
        }
    }

}