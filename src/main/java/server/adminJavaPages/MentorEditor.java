package server.adminJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.dao.CreepyGuyDAO;
import controllers.dao.LoginAccesDAO;
import models.CreepyGuyModel;
import models.MentorModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;
import server.helpers.FormDataParser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MentorEditor implements HttpHandler {
    private CreepyGuyDAO creepyGuyDAO;
    private CookieHelper cookieHelper;
    private LoginAccesDAO loginAccesDAO;
    private FormDataParser formDataParser;
    private CreepyGuyModel creepyGuyModel;
    private String mentorId;

    public MentorEditor(Connection connection){
        creepyGuyDAO = new CreepyGuyDAO(connection);
        formDataParser = new FormDataParser();
        cookieHelper = new CookieHelper();
        loginAccesDAO = new LoginAccesDAO(connection);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        Optional<HttpCookie>  cookie = cookieHelper.getSessionIdCookie(httpExchange);
        String sessionId = cookie.get().getValue().substring(1, cookie.get().getValue().length() - 1);
        String method = httpExchange.getRequestMethod();

        if (cookie.isPresent()) {
            if (loginAccesDAO.checkSessionPresent(sessionId)){

                if (method.equals("GET")) {
                    response = generatePage(sessionId);
                }

                if (method.equals("POST")){
                    Map inputs = formDataParser.getData(httpExchange);
                    if (inputs.containsKey("search")){
                        mentorId = inputs.get("ID").toString();
                        response = fillPage(sessionId, mentorId);
                    }

                    if (inputs.containsKey("add")){
                        creepyGuyDAO.addMentor(new MentorModel(fillDataToMap(inputs)));
                        response = generatePage(sessionId);
                    }

                    if (inputs.containsKey("edit")){
                        creepyGuyDAO.editMentor(new MentorModel(fillDataToMap(inputs)), mentorId);
                        response = generatePage(sessionId);
                    }
                    if (inputs.containsKey("delete")){
                        creepyGuyDAO.deleteMentor(mentorId);
                        response = generatePage(sessionId);
                    }
                }

            }
            else{
                httpExchange.getResponseHeaders().set("Location", "/login");
            }
        }
        httpExchange.sendResponseHeaders(301, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private String generatePage(String sessionId){
        creepyGuyModel = creepyGuyDAO.getAdminBySessionId(sessionId);

        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/adminPages/mentorEditor.twig");


        JtwigModel model = JtwigModel.newModel();

        model.with("nickname", creepyGuyModel.getNickName());

        return template.render(model);
    }

    private String fillPage(String sessionId,  String id){
        creepyGuyModel = creepyGuyDAO.getAdminBySessionId(sessionId);
        MentorModel mentorModel = creepyGuyDAO.getMentorById(id);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/adminPages/mentorEditor.twig");

        JtwigModel model = JtwigModel.newModel();
        model.with("nickname", creepyGuyModel.getNickName());
        if (!(mentorModel == null)) {

            model.with("email", mentorModel.getEmail());
            model.with("nick", mentorModel.getNickName());
            model.with("name", mentorModel.getName());
            model.with("surname", mentorModel.getSurname());
            model.with("pass", mentorModel.getPassword());
            model.with("class", mentorModel.getRoom());
        }
        return template.render(model);
    }


    private Map<String, String> fillDataToMap(Map<String, String> inputs){
        Map <String, String> mentorData = new HashMap<>();
        mentorData.put("email", inputs.get("email").toString());
        mentorData.put("password", inputs.get("pass").toString());
        mentorData.put("firstName", inputs.get("name").toString());
        mentorData.put("surname", inputs.get("surname").toString());
        mentorData.put("room", inputs.get("class").toString());
        mentorData.put("Nickname", inputs.get("nick").toString());
        return mentorData;
    }
}