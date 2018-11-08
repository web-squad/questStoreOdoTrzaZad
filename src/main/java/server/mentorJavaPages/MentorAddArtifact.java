package server.mentorJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.util.HashMap;
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
    private MentorDAO mentorDAO;
    private Optional<HttpCookie> cookie;
    private CookieHelper cookieHelper;
    private FormDataParser formDataParser;
    private LoginAccesDAO loginAccesDAO;

    public MentorAddArtifact(MentorDAO mentorDAO, LoginAccesDAO loginAccesDAO){
        formDataParser = new FormDataParser();
        cookieHelper = new CookieHelper();
        this.mentorDAO = mentorDAO;
        this.loginAccesDAO = loginAccesDAO;
    }


    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        cookie = cookieHelper.getSessionIdCookie(httpExchange);
        String sessionId = cookie.get().getValue().substring(1, cookie.get().getValue().length() - 1);
        String method = httpExchange.getRequestMethod();


        if (cookie.isPresent()) {
            if (loginAccesDAO.checkSessionPresent(sessionId)){

                if (method.equals("GET")) {
                    response = generatePage();
                }

                if (method.equals("POST")){

                    Map inputs = formDataParser.getData(httpExchange);
                    System.out.println(inputs);
                    String providedArtifactName = inputs.get("name").toString();
                    String providedArtifactDescription = inputs.get("description").toString();
                    String providedArtifactReward = inputs.get("price").toString();
                    Artifact newArtifact = new Artifact(providedArtifactName, providedArtifactDescription, Integer.parseInt(providedArtifactReward));
                    mentorDAO.addArtifactToStore(newArtifact);

                    response = generatePage();
                }
            }
            else{
                httpExchange.getResponseHeaders().set("Location", "/login");
            }
        }
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String generatePage(){
        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorAddArtifact.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        // render a template to a string
        return template.render(model);
    }
}