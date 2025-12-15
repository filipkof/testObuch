package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;


public class StudentGenerator {
    String baseUri = "http://localhost:8080";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @SneakyThrows
    public void createStudent(String id, String name) {

        StudentRequest body = new StudentRequest(id, name, null);
        String json = MAPPER.writeValueAsString(body);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(baseUri + "/student");
        request.setHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        httpClient.execute(request);
    }

    @SneakyThrows
    public String getStudent(int id) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(baseUri + "/student/" + id);
        CloseableHttpResponse httpResponse = httpClient.execute(request);
        HttpEntity entity = httpResponse.getEntity();
        return EntityUtils.toString(entity);
    }

    @SneakyThrows
    public void createStudentWithMarks(String id, String name, int[] marks) {

        StudentRequest body = new StudentRequest(id, name, marks);
        String json = MAPPER.writeValueAsString(body);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost(baseUri + "/student");
        request.setHeader("Content-Type", "application/json");
        request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
        httpClient.execute(request);
    }
}


