package server.adminJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import controllers.dao.CreepyGuyDAO;
import controllers.dao.LoginAccesDAO;
import models.CreepyGuyModel;
import models.MentorModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;
import server.helpers.FormDataParser;

public class MentorAdder implements HttpHandler {
    private CreepyGuyDAO creepyGuyDAO;
    private Optional<HttpCookie> cookie;
    private CookieHelper cookieHelper;
    private LoginAccesDAO loginAccesDAO;
    private FormDataParser formDataParser;
    private CreepyGuyModel creepyGuyModel;

    public MentorAdder(Connection connection){
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
                    response = generatePage(sessionId);
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

                    response = generatePage(sessionId);
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

    private String generatePage(String sessionId){
        creepyGuyModel = creepyGuyDAO.getAdminBySessionId(sessionId);
        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/adminPages/mentorAdder.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();

        model.with("nickname", creepyGuyModel.getNickName());
        // render a template to a string
        return template.render(model);
    }
}