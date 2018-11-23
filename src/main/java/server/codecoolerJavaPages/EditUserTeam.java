package server.codecoolerJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.dao.CodecoolerDAO;
import controllers.dao.LoginAccesDAO;
import models.CodecoolerModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.ResponseSender;
import server.Team;
import server.helpers.CookieHelper;
import server.helpers.FormDataParser;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class EditUserTeam implements HttpHandler {
    private CodecoolerDAO codecoolerDAO;
    private LoginAccesDAO loginAccesDAO;
    private FormDataParser formDataParser;

    public EditUserTeam(Connection connection) {
        this.codecoolerDAO = new CodecoolerDAO(connection);
        this.loginAccesDAO = new LoginAccesDAO(connection);
        this.formDataParser = new FormDataParser();
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        ResponseSender responseSender = new ResponseSender();
        Optional<HttpCookie> httpCookie = responseSender.getSessionIdCookie(httpExchange);
        String sessionId = httpCookie.get().getValue().replace("\"", "");
        String method = httpExchange.getRequestMethod();
        List<Team> teamsList = codecoolerDAO.getAllTeams();
        int userId = 0;
        if(method.equals("GET")){
            try{
                userId = Integer.parseInt(loginAccesDAO.getIdBySessionId(sessionId));
            }catch(SQLException e){
                e.printStackTrace();
            }
            CodecoolerModel codecoolerModel = codecoolerDAO.getCodecoolerModel(userId);
            if(userId != 0){
                sendResponseIfUserExists(httpExchange, codecoolerModel, teamsList);

            }else{
                responseSender.sendResponseIfInvalidUser(httpExchange);
            }
        } else if(method.equals("POST")) {
            Map<String, String> formData = formDataParser.getData(httpExchange);
            String teamName = formData.get("team-name");
            codecoolerDAO.editCodecoolerTeam(userId, teamName);
        }


    }

    private void sendResponseIfUserExists(HttpExchange httpExchange, CodecoolerModel codecoolerModel, List<Team> teamsList) throws IOException {
        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/codecoolerPages/editUserTeam.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();
        fillModelTwig(model, codecoolerModel, teamsList);
        // render a template to a string
        String response = template.render(model);
        // send the results to a the client
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void fillModelTwig(JtwigModel model, CodecoolerModel codecoolerModel, List<Team> teamsList){
        int coinsEverOwned = codecoolerModel.getCoolcoinsEverEarned();
        String level = String.valueOf(codecoolerModel.getExpLevel());
        if(coinsEverOwned >= 10 && coinsEverOwned % 10 == 0) {
            level = String.valueOf(coinsEverOwned / 10);
        }else if (level.equals("0")){
            level = "1";
        }
        model.with("codecoolerModel", codecoolerModel);
        model.with("level", level);
        model.with("teamsList", teamsList);
    }

}