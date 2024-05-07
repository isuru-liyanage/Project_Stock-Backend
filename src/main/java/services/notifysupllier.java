package services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class notifysupllier {
    public boolean notifySuppli(String supplierEmail, String productName, int quantity) throws IOException {


        String apiUrl = "https://api.elasticemail.com/v2/email/send";
        String apiKey = "31002F15EC8BDD202DD460E2A234049861A1467A2791552593B041F0A05AED5AD6410287273B6B0A911AF32FE5C7946E";
        String subject = "DOT.STOCK Platform - Order Request";
        String from = "enmoskill@mail.com";
        String to =supplierEmail;
        String fromName = "DOT.STOCK Platform";
        String template = "send order";
        String mergeproduct = productName;
        String mergequantity = String.valueOf(quantity);



        String requestBody = "apikey=" + apiKey +
                "&subject=" + subject +
                "&from=" + from +
                "&to=" + to +
                "&fromName=" + fromName +
                "&template=" + template +
                "&merge_product=" + mergeproduct+
                "&merge_count=" + mergequantity;


        URL urlObject = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        int responseCode = connection.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        String line;
        StringBuilder responses = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            responses.append(line);
        }
        reader.close();
        JsonObject jsonResponse = JsonParser.parseString(responses.toString()).getAsJsonObject();
        if (responseCode==200){

            System.out.println(jsonResponse);
            boolean success = jsonResponse.getAsJsonPrimitive("success").getAsBoolean();
            if (success) {
                System.out.println("Request successful");
                return true;

            } else {
                return false;
//                    System.out.println("Request failed");

            }}

        connection.disconnect();
        return false;
    }
}
