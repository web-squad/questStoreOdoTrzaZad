package server.mentorJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import controllers.dao.LoginAccesDAO;
import controllers.dao.MentorDAO;
import models.Artifact;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;

public class MentorAddArtifact implements HttpHandler {
    private static final String SESSION_COOKIE_NAME = "sessionId";
    private MentorDAO mentorDAO;
    private LoginAccesDAO loginAccesDAO;
    private CookieHelper cookieHelper;

    public MentorAddArtifact(MentorDAO mentorDAO, LoginAccesDAO loginAccesDAO) {
        this.mentorDAO = mentorDAO;
        this.cookieHelper = new CookieHelper();
        this.loginAccesDAO = loginAccesDAO;
    }


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        Optional<HttpCookie> httpCookie = getSessionIdCookie(httpExchange);
        String sessionId = httpCookie.get().getValue().replace("\"", "");
        System.out.println(sessionId);

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorAddArtifact.twig");

        JtwigModel model = JtwigModel.newModel();
        // render a template to a string
        String response = template.render(model);

        // send the results to a the client
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();


        if (method.equals("POST")) {
            Map inputs = getData(httpExchange);
            String providedArtifactName = inputs.get("name").toString();
            String providedArtifactDescription = inputs.get("description").toString();
            String providedArtifactPrice = inputs.get("price").toString();
            Artifact newArtifact = new Artifact(providedArtifactName, providedArtifactDescription, Integer.parseInt(providedArtifactPrice));
            mentorDAO.addArtifactToStore(newArtifact);
        }
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

    private static Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = URLDecoder.decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}