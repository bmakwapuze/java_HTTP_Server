package com.simple.httpserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import sun.misc.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
public class MyHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        String requestParamValue = null;

        if("GET".equals(httpExchange.getRequestMethod())) {

            requestParamValue = handleGetRequest(httpExchange);

        }else if("POST".equals(httpExchange.getRequestMethod())) {

            requestParamValue = handlePostRequest(httpExchange);

        }

        handleResponse(httpExchange,requestParamValue);

    }

    private String handlePostRequest(HttpExchange httpExchange) throws IOException {
        InputStream inputStream= httpExchange.getRequestBody();

        byte[] result = IOUtils.readAllBytes(inputStream);
        String s = new String(result, StandardCharsets.UTF_8);
        System.out.println(s);
        return "this is post request";

    }
    private String handleGetRequest(HttpExchange httpExchange) {
//get users from db
        return "this is get request";
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
        OutputStream outputStream = httpExchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html>")
                .append("<body>")
                .append("<h1>")
                .append("Hello ")
                .append(requestParamValue)
                .append("</h1>")
                .append("</body>")
                .append("</html>");
        String htmlResponse = htmlBuilder.toString();
        httpExchange.sendResponseHeaders(200, 10000L);
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();

    }

}