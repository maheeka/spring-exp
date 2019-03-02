package com.springexp.correlation.handlers;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

import static com.springexp.correlation.Constants.X_CORRELATION_ID;

@Slf4j
public class CorrelationIDInterceptor implements ClientHttpRequestInterceptor {

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        addCorrelationID(request);
        log(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        addCorrelationID(response);
        log(response);
        return response;
    }

    private void log(HttpRequest request, byte[] body) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\n==== REQUEST BEGIN ======");
        stringBuilder.append("\nURI     : " + request.getURI().toString());
        stringBuilder.append("\nHeaders : " + request.getHeaders());
        stringBuilder.append("\nMethod  : " + request.getMethod());
        stringBuilder.append("\nBody    :" + body);
        stringBuilder.append("\n==== REQUEST END ======\n");

        log.info(stringBuilder.toString());
    }

    private void log(ClientHttpResponse response) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\n==== RESPONSE BEGIN ======");
        stringBuilder.append("\nStatus Code     : " + response.getStatusCode());
        stringBuilder.append("\nStatus Text     : " + response.getStatusText());
        stringBuilder.append("\nHeaders : " + response.getHeaders());
        stringBuilder.append("\nMethod  : " + response.getBody());
        stringBuilder.append("\n==== RESPONSE END ======\n");

        log.info(stringBuilder.toString());
    }

    private void addCorrelationID(ClientHttpResponse response) {
        HttpHeaders headers = response.getHeaders();
        headers.add(X_CORRELATION_ID, MDC.get(X_CORRELATION_ID));
    }

    private void addCorrelationID(HttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        headers.add(X_CORRELATION_ID, MDC.get(X_CORRELATION_ID));
    }
}
