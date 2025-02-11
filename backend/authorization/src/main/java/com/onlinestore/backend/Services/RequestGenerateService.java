package com.onlinestore.backend.Services;

import com.onlinestore.backend.Converters.ConvertObjectToJson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@AllArgsConstructor
public class RequestGenerateService<T> {

    private final ConvertObjectToJson<T> convertObjectToJson;

    public String generateGetRequest(String uri, String contentType){
        try{
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-type", contentType)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());

            return response.body();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public String generatePostRequest(T obj, String uri, String contentType){
        try{
            String json = convertObjectToJson.convert(obj);
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-type", contentType)
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public void generatePatchRequest(){}
    public void generateDeleteRequest(){}

}
