package server;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.Connector;
import controllers.dao.LoginAccesDAO;
import server.helpers.CookieHelper;
import views.View;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WelcomePage implements HttpHandler {
    String activeSession = "";

    private static final String SESSION_COOKIE_NAME = "sessionId";
    int counter = 0;
    CookieHelper cookieHelper = new CookieHelper();
    Connection connection;

    public WelcomePage() {
        String dbPass = "quest";
        String dbUser = "queststore";
        connection = new Connector().connect(dbUser, dbPass);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        counter ++;
        String response = "";
        String method = httpExchange.getRequestMethod();
        Optional<HttpCookie> cookie;

        if (method.equals("POST")) {
            InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String formData = br.readLine();
            Map inputs = parseFormData(formData);
            String providedMail = inputs.get("mail").toString();
            String providedPassword = inputs.get("password").toString();
            List<Integer> loginData = new LoginAccesDAO(connection).readLoginData(providedMail, providedPassword);
            if (loginData.get(0) != null) {
                String sessionId = String.valueOf(counter); // This isn't a good way to create sessionId. Find a better one!
                cookie = Optional.of(new HttpCookie(SESSION_COOKIE_NAME, sessionId));activeSession = sessionId;
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.get().toString());
                response = "<html><body>" +
                        "<h1>Hello " +
                        providedMail + " " + providedPassword +
                        "!</h1>" +
                        "<form method=\"GET\" action=\"login\">\n" +
                        "<input type=\"submit\" value=\"Logout\">\n" +
                        "</form> " +
                        "</body><html>";
            }
            else {
                httpExchange.getResponseHeaders().set("Location", "/login");
            }
        }
        sendResponse(httpExchange, response);

    }

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }

    private Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName(SESSION_COOKIE_NAME, cookies);
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
}
