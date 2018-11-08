package server.codecoolerJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import controllers.dao.CodecoolerDAO;
import controllers.dao.LoginAccesDAO;
import models.CodecoolerModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;

public class CodecoolerMain implements HttpHandler {
    private static final String SESSION_COOKIE_NAME = "sessionId";
    private CodecoolerDAO codecoolerDAO;
    private LoginAccesDAO loginAccesDAO;
    private CookieHelper cookieHelper;
    public CodecoolerMain(CodecoolerDAO codecoolerDAO, LoginAccesDAO loginAccesDAO) {
        this.codecoolerDAO = codecoolerDAO;
        this.cookieHelper = new CookieHelper();
        this.loginAccesDAO = loginAccesDAO;
    }
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Optional<HttpCookie> httpCookie = getSessionIdCookie(httpExchange);
        int userId = 0;
        String coins = "30";
        String coinsEverOwned = "210";
        String level = "11";
        String quest = "Vanilla";
        String room = "Java";
        String team = "Siurki";
        String nickname = "Notarian";
        String name = "Karol";
        String surname = "Trzaska";
        String sessionId = httpCookie.get().getValue().replace("\"", "");
        System.out.println(sessionId);
        try{
            userId = Integer.parseInt(loginAccesDAO.getIdBySessionId(sessionId));
            System.out.println(userId);
        }catch(SQLException e){
            e.printStackTrace(); //temporary
        }
        CodecoolerModel codecoolerModel = codecoolerDAO.getCodecoolerModel(userId);
        if(userId != 0){
            coins = String.valueOf(codecoolerModel.getCoolcoins());
            coinsEverOwned = String.valueOf(codecoolerModel.getCoolcoinsEverEarned());
            quest = String.valueOf(codecoolerModel.getQuestInProgress());
            team = String.valueOf(codecoolerModel.getTeamID());
            nickname = codecoolerModel.getNickname();
            name = codecoolerModel.getFirstName();
            surname = codecoolerModel.getLastName();
            if(Integer.parseInt(coinsEverOwned) >= 10 && (Integer.parseInt(coinsEverOwned) % 10 == 0)) {
                level = String.valueOf(Integer.parseInt(coinsEverOwned) / 10);
            }else if (level.equals("0")){
                level = "1";
            }

        }

        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/codecoolerPages/codecoolerMain.twig");
        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();
        model.with("nickname", nickname);
        model.with("coolcoins", coins);
        model.with("coolcoins_ever_owned", coinsEverOwned);
        model.with("quest", quest);
        model.with("room", room);
        model.with("team", team);
        model.with("name", name);
        model.with("surname", surname);
        model.with("level", level);
        // render a template to a string
        String response = template.render(model);

        // send the results to a the client
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