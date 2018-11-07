package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.Connector;
import controllers.dao.LoginAccesDAO;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Login implements HttpHandler {
    String activeSession;
    private static final String SESSION_COOKIE_NAME = "sessionId";
    int counter = 0;
    CookieHelper cookieHelper = new CookieHelper();
    Connection connection;

    public Login(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();
        counter ++;
        Optional<HttpCookie> cookie;

        if(method.equals("GET")) {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/login.twig");

            // create a model that will be passed to a template
            JtwigModel model = JtwigModel.newModel();

            // render a template to a string
            response = template.render(model);
        }

        if (method.equals("POST") ) {
            Map inputs = getData(httpExchange);
            String providedMail = inputs.get("email").toString();
            String providedPassword = inputs.get("pass").toString();
            List<Integer> loginData = new LoginAccesDAO(connection).readLoginData(providedMail, providedPassword);
            if (!loginData.isEmpty()) {
                String sessionId = String.valueOf(counter); // This isn't a good way to create sessionId. Find a better one!
                cookie = Optional.of(new HttpCookie(SESSION_COOKIE_NAME, sessionId));
                activeSession = sessionId;
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
                JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/login.twig");

                // create a model that will be passed to a template
                JtwigModel model = JtwigModel.newModel();

                // render a template to a string
                response = template.render(model);
            }
        }

        sendResponse(httpExchange, response);
    }

    /**
     * Form data is sent as a urlencoded string. Thus we have to parse this string to get data that we want.
     * See: https://en.wikipedia.org/wiki/POST_(HTTP)
     */
    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

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

    private Map<String, String> getData(HttpExchange httpExchange) throws IOException{
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        String formData = br.readLine();
        return parseFormData(formData);
    }
}
