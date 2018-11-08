package server.adminJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import controllers.dao.CreepyGuyDAO;
import controllers.dao.LoginAccesDAO;
import models.MentorModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;
import server.helpers.FormDataParser;

public class MentorEditor implements HttpHandler {
    private CreepyGuyDAO creepyGuyDAO;
    private Optional<HttpCookie> cookie;
    private CookieHelper cookieHelper;
    private LoginAccesDAO loginAccesDAO;
    private FormDataParser formDataParser;

    public MentorEditor(Connection connection){
        creepyGuyDAO = new CreepyGuyDAO(connection);
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
                    // get a template file
                    JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/adminPages/mentorEditor.twig");

                    // create a model that will be passed to a template
                    JtwigModel model = JtwigModel.newModel();

                    // render a template to a string
                    response = template.render(model);
                }

                if (method.equals("POST")){
                    Map inputs = formDataParser.getData(httpExchange);
                    Map <String, String> mentorData = new HashMap<>();
                    mentorData.put("email", inputs.get("email").toString());
                    mentorData.put("password", inputs.get("pass").toString());
                    mentorData.put("firstName", inputs.get("name").toString());
                    mentorData.put("surname", inputs.get("surname").toString());
                    mentorData.put("room", inputs.get("class").toString());
                    mentorData.put("Nickname", inputs.get("nick").toString());
                    creepyGuyDAO.addMentor(new MentorModel(mentorData));
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
}