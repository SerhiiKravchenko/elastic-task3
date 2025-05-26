package org.epam.learn.elastic.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.epam.learn.elastic.RequestConfiguration;
import org.epam.learn.elastic.dto.Employee;

import co.elastic.clients.transport.rest5_client.low_level.Request;
import co.elastic.clients.transport.rest5_client.low_level.RequestOptions;
import co.elastic.clients.transport.rest5_client.low_level.Response;
import co.elastic.clients.transport.rest5_client.low_level.Rest5Client;

public class EmployeeService {

    private static final String EMPLOYEES_DOC = "/employees/_doc/";
    private static final String GET_METHOD = "GET";
    private static final String EMPLOYEE_SEARCH = "/employees/_search";
    private static final String SOURCE_PATH = "_source";
    private static final String POST_METHOD = "POST";
    private static final String DELETE_METHOD = "DELETE";
    private final RequestOptions requestOptions = RequestConfiguration.getRequestOptions();
    private final Rest5Client restClient = Rest5Client.builder(URI.create("http://localhost:9200")).build();

    public List<Employee> getAllEmployee() {
        Request request = new Request(
                GET_METHOD,
                EMPLOYEE_SEARCH);
        request.setOptions(requestOptions);

        String responseBody = getResponseBody(request);

        return getEmployeesFromResponseBody(responseBody);
    }

    public Employee getEmployeeById(String id) {
        Request request = new Request(
                GET_METHOD,
                EMPLOYEES_DOC + id);
        request.setOptions(requestOptions);

        String responseBody = getResponseBody(request);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JsonNode root = null;
        Employee employee = null;

        try {
            root = objectMapper.readTree(responseBody);
            JsonNode sourceNode = root.path(SOURCE_PATH);
            employee = objectMapper.treeToValue(sourceNode, Employee.class);
        } catch (JsonProcessingException e) {
            return new Employee();
        }
        return employee;
    }

    public String createEmployee(String employeeId, String employeeJson) {
        Request request = new Request(
                POST_METHOD,
                EMPLOYEES_DOC + employeeId);
        request.setOptions(requestOptions);
        request.setJsonEntity(employeeJson);

        String responseBody = getResponseBody(request);
        return responseBody.contains("created") ? "Created successfully!" : "Not created";
    }

    public String deleteEmployee(String employeeId) {
        Request request = new Request(
                DELETE_METHOD,
                EMPLOYEES_DOC + employeeId);
        request.setOptions(requestOptions);

        String responseBody = getResponseBody(request);
        return responseBody.contains("deleted") ? "Deleted successfully!" : "Not deleted";
    }

    public List<Employee> searchEmployeeByFieldAndValue(String field, String value) {
        Request request = new Request(
                GET_METHOD,
                EMPLOYEE_SEARCH);
        request.setOptions(requestOptions);
        String queryJson = "{\n" +
                "  \"query\": {\n" +
                "    \"match\": {\n" +
                "      \"" + field + "\": \"" + value + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        request.setJsonEntity(queryJson);

        String responseBody = getResponseBody(request);

        return getEmployeesFromResponseBody(responseBody);
    }

    public String getAggregation(String field, String metricType, String metricField) {
        Request request = new Request(
                GET_METHOD,
                EMPLOYEE_SEARCH);
        request.setOptions(requestOptions);
//        request.addParameter("pretty", "true");
        String queryJson = "{ " +
                "  \"size\": 0, " +
                "  \"aggs\": { " +
                "    \"" + field + "_agg\": { " +
                "      \"" + metricType + "\": { " +
                "        \"field\": \"" + metricField + "\" " +
                "      } " +
                "    } " +
                "  } " +
                "}";

        request.setJsonEntity(queryJson);

        return getResponseBody(request);
    }

    public void closeRestClient() throws IOException {
        restClient.close();
    }

    private List<Employee> getEmployeesFromResponseBody(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JsonNode root = null;

        try {
            root = objectMapper.readTree(responseBody);
            JsonNode sourceNode = root.path("hits").path("hits");
            return objectMapper.readValue(sourceNode.toString(), new TypeReference<List<Employee>>() {
            });

        } catch (JsonProcessingException e) {
            return Collections.emptyList();
        }
    }

    private String getResponseBody(Request request) {
        Response response = null;
        String responseBody = null;

        try {
            response = restClient.performRequest(request);
            responseBody = EntityUtils.toString(response.getEntity());
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }

        return responseBody;
    }
}
