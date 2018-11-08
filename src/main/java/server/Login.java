package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.dao.LoginAccesDAO;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;
import server.helpers.FormDataParser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Login implements HttpHandler {
    private CookieHelper cookieHelper;
    private LoginAccesDAO loginAccesDAO;
    private FormDataParser formDataParser;
    private Optional<HttpCookie> cookie;

    public Login(Connection connection) {
        this.loginAccesDAO = new LoginAccesDAO(connection);
        formDataParser = new FormDataParser();
        cookieHelper = new CookieHelper();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        String method = httpExchange.getRequestMethod();


        if(method.equals("GET")) {
            cookie = cookieHelper.getSessionIdCookie(httpExchange);
            cookie.ifPresent(httpCookie -> loginAccesDAO.deleteSessionID(httpCookie.getValue()));
            response = generatePage();
        }

        if (method.equals("POST") ) {
            Map inputs = formDataParser.getData(httpExchange);
            String providedMail = inputs.get("email").toString();
            String providedPassword = inputs.get("pass").toString();

            List<Integer> loginData = loginAccesDAO.readLoginData(providedMail, providedPassword);
            String pageAdress = redirect(loginData);

            if (!pageAdress.equals(null)) {
                httpExchange.getResponseHeaders().set("Location", pageAdress);
                String sessionId = String.valueOf(hash(providedMail + providedPassword + LocalDateTime.now().toString()));
                loginAccesDAO.saveSessionId(sessionId, providedMail);
                cookie = Optional.of(new HttpCookie(CookieHelper.getSessionCookieName(), sessionId));
                httpExchange.getResponseHeaders().add("Set-Cookie", cookie.get().toString());
            } else {
                response = generatePage();
            }
        }

        sendResponse(httpExchange, response);
    }

    /**
     * Form data is sent as a urlencoded string. Thus we have to parse this string to get data that we want.
     * See: https://en.wikipedia.org/wiki/POST_(HTTP)
     */
    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(301, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }


    private long hash(String string) {
        long h = 1125899906842597L; // prime
        int len = string.length();

        for (int i = 0; i < len; i++) {
            h = 31*h + string.charAt(i);
        }
        return h;
    }

    private String generatePage(){

        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/login.twig");


        JtwigModel model = JtwigModel.newModel();


        return template.render(model);
    }

    private String redirect(List<Integer> loginData){
        if (!loginData.isEmpty()) {
            int accessLevel = loginData.get(0);
            if (accessLevel == 1){
                return "/codecoolerJavaPages/CodecoolerIndex";
            }
            if (accessLevel == 3){
                return "/adminJavaPages/GreetAdmin";
            }
            if (accessLevel == 2){
                return "/mentorJavaPages/MentorWelcomePage";
            }
        }
        return null;
    }
}
