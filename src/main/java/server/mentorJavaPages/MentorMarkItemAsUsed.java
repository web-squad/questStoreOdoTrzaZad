package server.mentorJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.util.*;

import controllers.dao.MentorDAO;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;
import server.helpers.FormDataParser;
import controllers.dao.LoginAccesDAO;

public class MentorMarkItemAsUsed implements HttpHandler {
    private Optional<HttpCookie> cookie;
    private CookieHelper cookieHelper;
    private FormDataParser formDataParser;
    private LoginAccesDAO loginAccesDAO;
    private MentorDAO mentorDAO;

    public MentorMarkItemAsUsed(Connection connection){
        this.mentorDAO = new MentorDAO(connection);
        formDataParser = new FormDataParser();
        cookieHelper = new CookieHelper();
        loginAccesDAO = new LoginAccesDAO(connection);
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
                    if (inputs.containsKey("searchButton")){
                        try {
                            int providedId = Integer.valueOf(inputs.get("search").toString());
                            List<String> wallet = mentorDAO.possessedArtifacts(providedId);
                            response = generatePage(wallet);

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            response = generatePage();
                        }
                    }
                    if (inputs.containsKey("itemToMarkButton")){
                        try {
                            int providedId = Integer.valueOf(inputs.get("id").toString());
                            mentorDAO.markItemAsUsed(providedId);
                            response = generatePage();

                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            response = generatePage();
                        }
                    }
                }
            }
            else{
                httpExchange.getResponseHeaders().set("Location", "/login");
            }
        }
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String generatePage(){

        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorMarkItemAsUsed.twig");

        JtwigModel model = JtwigModel.newModel();

        return template.render(model);
    }

    private String generatePage(List<String> searchResult){

        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorMarkItemAsUsed.twig");

        JtwigModel model = JtwigModel.newModel().with("searchResult", searchResult);

        return template.render(model);
    }
}