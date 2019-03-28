package server.adminJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.dao.CreepyGuyDAO;
import controllers.dao.LoginAccesDAO;
import models.CreepyGuyModel;
import models.Room;
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

public class ClassEditor implements HttpHandler {

    private CreepyGuyDAO creepyGuyDAO;
    private CookieHelper cookieHelper;
    private LoginAccesDAO loginAccesDAO;
    private FormDataParser formDataParser;
    private CreepyGuyModel creepyGuyModel;
    private String classId;

    public ClassEditor(Connection connection){
        creepyGuyDAO = new CreepyGuyDAO(connection);
        formDataParser = new FormDataParser();
        cookieHelper = new CookieHelper();
        loginAccesDAO = new LoginAccesDAO(connection);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Optional<HttpCookie>  cookie = cookieHelper.getSessionIdCookie(httpExchange);
        if (cookie.isPresent()) {
            String sessionId = cookie.get().getValue().substring(1, cookie.get().getValue().length() - 1);
            if (loginAccesDAO.checkSessionPresent(sessionId)) {
                String response = "";
                String method = httpExchange.getRequestMethod();
                creepyGuyModel = creepyGuyDAO.getAdminBySessionId(sessionId);

                if (method.equals("GET")) {
                    response = generatePage();
                } else if (method.equals("POST")){
                    Map inputs = formDataParser.getData(httpExchange);
                    classId = inputs.get("ID").toString();
                    if (inputs.containsKey("search")){
                        response = fillPage(classId);
                    } else if (inputs.containsKey("edit")){
                        creepyGuyDAO.editRoom(new Room(fillData(inputs)), classId);
                        response = generatePage();
                    }else if (inputs.containsKey("delete")){
                        creepyGuyDAO.deleteRoom(classId);
                        response = generatePage();
                    }else if (inputs.containsKey("add")){
                        creepyGuyDAO.addRoom(new Room(fillData(inputs)));
                        response = generatePage();
                    }
                }
                httpExchange.sendResponseHeaders(301, response.getBytes().length);
                OutputStream os = httpExchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                httpExchange.getResponseHeaders().set("Location", "/login");
            }
        } else {
            httpExchange.getResponseHeaders().set("Location", "/login");
        }
    }

    private String generatePage(){
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/adminPages/classEditor.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("nickname", creepyGuyModel.getNickName());
        return template.render(model);
    }

    private String fillPage(String id){
        Room room = creepyGuyDAO.getRoomById(id);
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/adminPages/classEditor.twig");
        JtwigModel model = JtwigModel.newModel();
        model.with("nickname", creepyGuyModel.getNickName());
        if((room != null)) {
            model.with("description", room.getRoomDescription());
            model.with("name", room.getRoomName());
        }
        return template.render(model);
    }

    private Map<String, String> fillData(Map<String, String> inputs){
        Map <String, String> roomData = new HashMap<>();
        roomData.put("roomName", inputs.get("name").toString());
        roomData.put("roomDescription", inputs.get("description").toString());
        return roomData;
    }
}