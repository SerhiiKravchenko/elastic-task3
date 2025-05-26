package org.epam.learn.elastic;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;
import org.epam.learn.elastic.service.EmployeeService;

public class Main {


    public static void main(String[] args) throws IOException, ParseException {

        String emloyee = "{\n" +
                "  \"name\": \"Ana Brown\",\n" +
                "  \"dob\": \"1993-03-19\",\n" +
                "  \"address\": {\n" +
                "    \"country\": \"Belarus\",\n" +
                "    \"town\": \"Gomel\"\n" +
                "  },\n" +
                "  \"email\": \"anabrown9@gmail.com\",\n" +
                "  \"skills\": [\n" +
                "    \"Java\",\n" +
                "    \"AWS\"\n" +
                "  ],\n" +
                "  \"experience\": 10,\n" +
                "  \"rating\": 9.2,\n" +
                "  \"description\": \"confident, ambitious, highly motivated Java experience interview learning python\",\n" +
                "  \"verified\": true,\n" +
                "  \"salary\": 30000\n" +
                "}\n";

        EmployeeService employeeService = new EmployeeService();
        System.out.println(employeeService.getEmployeeById("CJqY8pYB0vzOhaIOiWJA"));
        System.out.println(employeeService.getAllEmployee());
        System.out.println(employeeService.createEmployee("35", emloyee));
        System.out.println(employeeService.deleteEmployee("35"));
        System.out.println(employeeService.searchEmployeeByFieldAndValue("skills","java"));
        System.out.println(employeeService.getAggregation("salary", "avg", "salary"));
        employeeService.closeRestClient();
    }
}
