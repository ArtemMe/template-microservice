package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import wiremock.org.apache.http.HttpResponse;
import wiremock.org.apache.http.client.methods.HttpGet;
import wiremock.org.apache.http.impl.client.CloseableHttpClient;
import wiremock.org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockTest extends DemoApplicationTests{
    private WireMockServer wireMockServer = new WireMockServer(8080);
    private final PaymentFeignClient paymentFeignClient;
    private final ObjectMapper objectMapper;

    public WireMockTest(@Autowired PaymentFeignClient paymentFeignClient, @Autowired ObjectMapper objectMapper) {
        this.paymentFeignClient = paymentFeignClient;
        this.objectMapper = objectMapper;
    }

    @Test
    public void testW() throws IOException {
        wireMockServer.start();
        stubFor(get(urlEqualTo("/baeldung")).willReturn(aResponse().withBody("Welcome to Baeldung!")));
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080/baeldung");
        HttpResponse httpResponse = httpClient.execute(request);
        String responseString = convertResponseToString(httpResponse);
        System.out.println(responseString);
        wireMockServer.stop();
    }

    @Test
    public void testFeign() throws JsonProcessingException {
        wireMockServer.start();
        stubFor(
                get(urlEqualTo("/payments/"))
                        .willReturn(aResponse()
                                .withBody(objectMapper.writeValueAsString(List.of(new Payment("123", 321L))))
                                .withHeader("Content-Type", "application/json;charset=UTF-8")
                        ));
        List<Payment> paymentList = paymentFeignClient.getAllTransactions();
        Assertions.assertFalse(paymentList.isEmpty());
        Assertions.assertEquals("123", paymentList.get(0).getId());
        wireMockServer.stop();
    }

    private String convertResponseToString(HttpResponse response) throws IOException {
        InputStream responseStream = response.getEntity().getContent();
        Scanner scanner = new Scanner(responseStream, "UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }
}
