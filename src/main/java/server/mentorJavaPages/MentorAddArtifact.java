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
import server.helpers.FormDataParser;

public class MentorAddArtifact implements HttpHandler {
    private static final String SESSION_COOKIE_NAME = "sessionId";
    private MentorDAO mentorDAO;
    private CookieHelper cookieHelper;
    FormDataParser formDataParser;

    public MentorAddArtifact(MentorDAO mentorDAO) {
        this.mentorDAO = mentorDAO;
        this.cookieHelper = new CookieHelper();
        formDataParser = new FormDataParser();
    }


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        Optional<HttpCookie> httpCookie = getSessionIdCookie(httpExchange);
        String sessionId = httpCookie.get().getValue().replace("\"", "");
        System.out.println(sessionId);
        String response = "";


        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/MentorAddArtifact.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // render a template to a string
        response = template.render(model);


        System.out.println("weszło do posta");
        if (method.equals("POST")) {

            Map inputs = formDataParser.getData(httpExchange);
            String providedArtifactName = inputs.get("name").toString();
            String providedArtifactDescription = inputs.get("description").toString();
            String providedArtifactPrice = inputs.get("price").toString();
            Artifact newArtifact = new Artifact(providedArtifactName, providedArtifactDescription, Integer.parseInt(providedArtifactPrice));
            mentorDAO.addArtifactToStore(newArtifact);

            httpExchange.getResponseHeaders().set("Location", "/MentorAddArtifact");
        }

        sendResponse(httpExchange, response);
    }

    private void sendResponse(HttpExchange httpExchange, String response) throws IOException {
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName(SESSION_COOKIE_NAME, cookies);
    }
}