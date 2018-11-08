package server.adminJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.dao.CreepyGuyDAO;
import controllers.dao.LoginAccesDAO;
import models.CreepyGuyModel;
import models.Level;
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

public class ExpLvlEditor implements HttpHandler {

    private CreepyGuyDAO creepyGuyDAO;
    private CookieHelper cookieHelper;
    private LoginAccesDAO loginAccesDAO;
    private FormDataParser formDataParser;
    private CreepyGuyModel creepyGuyModel;
    private String expLevelId;

    public ExpLvlEditor(Connection connection){
        creepyGuyDAO = new CreepyGuyDAO(connection);
        formDataParser = new FormDataParser();
        cookieHelper = new CookieHelper();
        loginAccesDAO = new LoginAccesDAO(connection);

    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "";
        Optional<HttpCookie> cookie = cookieHelper.getSessionIdCookie(httpExchange);
        String sessionId = cookie.get().getValue().substring(1, cookie.get().getValue().length() - 1);
        String method = httpExchange.getRequestMethod();
        creepyGuyModel = creepyGuyDAO.getAdminBySessionId(sessionId);

        if (cookie.isPresent()) {
            if (loginAccesDAO.checkSessionPresent(sessionId)){

                if (method.equals("GET")) {
                    response = generatePage();
                }

                if (method.equals("POST")){
                    Map inputs = formDataParser.getData(httpExchange);
                    if (inputs.containsKey("search")){
                        expLevelId = inputs.get("ID").toString();
                        response = fillPage(expLevelId);
                    }
                    if (inputs.containsKey("edit")){
                        creepyGuyDAO.editLevel(new Level(fillData(inputs)), expLevelId);
                        response = generatePage();
                    }
                    if (inputs.containsKey("delete")){
                        creepyGuyDAO.deleteLevel(expLevelId);
                        response = generatePage();
                    }
                    if (inputs.containsKey("add")){
                        inputs = formDataParser.getData(httpExchange);
                        creepyGuyDAO.addLevel(new Level(fillData(inputs)));
                        response = generatePage();
                    }

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

        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/adminPages/expLvlEditor.twig");


        JtwigModel model = JtwigModel.newModel();

        model.with("nickname", creepyGuyModel.getNickName());

        return template.render(model);
    }

    private String fillPage(String id){
        Level level = creepyGuyDAO.getLevelById(id);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/adminPages/expLvlEditor.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("nickname", creepyGuyModel.getNickName());
        if (!(level == null)) {
            model.with("description", level.getThreshold());
            model.with("name", level.getLevelName());
        }

        return template.render(model);
    }

    private Map<String, String> fillData(Map<String, String> inputs){
        Map <String, String> levelData = new HashMap<>();
        levelData.put("levelName", inputs.get("name").toString());
        levelData.put("threshold", inputs.get("description").toString());
        return levelData;
    }
}