package kwiz.backend.api.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;

public class DBseeder {

    public static void main(String[] args) {

        String BASE_URL = "http://localhost:8081";
        
        System.out.println("Hello from DB seeder!");

        try{

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(BASE_URL + "/quiz/v2/api/modules/delete_all"))
                                    .DELETE()
                                    .build();
            
            client.send(request, HttpResponse.BodyHandlers.ofString());

            String requestBody = resolveFileFromResources("initial_module_load.json");
            System.out.println("Content = "+requestBody);

            request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/quiz/v2/api/modules/add"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());

        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    private static String resolveFileFromResources(String input) throws URISyntaxException, IOException{

        File inputFile = new File(Thread.currentThread().getContextClassLoader().getResource(input).toURI());
        return new String(Files.readAllBytes(inputFile.toPath()), "utf-8");
    }
}
