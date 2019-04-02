package server.codecoolerJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.dao.CodecoolerDAO;
import controllers.dao.LoginAccesDAO;
import models.CodecoolerModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
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

public class Store implements HttpHandler {
    private static final String SESSION_COOKIE_NAME = "sessionId";
    private CodecoolerDAO codecoolerDAO;
    private LoginAccesDAO loginAccesDAO;
    private CookieHelper cookieHelper;
    private FormDataParser formDataParser;

    public Store(Connection connection) {
        this.codecoolerDAO = new CodecoolerDAO(connection);
        this.cookieHelper = new CookieHelper();
        this.loginAccesDAO = new LoginAccesDAO(connection);
        this.formDataParser = new FormDataParser();
    }

//
//    public Store(CodecoolerDAO codecoolerDAO, CookieHelper cookieHelper, LoginAccesDAO loginAccesDAO, FormDataParser formDataParser) {
//        this.codecoolerDAO = codecoolerDAO;
//        this.cookieHelper = cookieHelper;
//        this.loginAccesDAO = loginAccesDAO;
//        this.formDataParser = formDataParser;
//    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        Optional<HttpCookie> httpCookie = getSessionIdCookie(httpExchange);

        int userId = 0;
        String coins = "";
        String coinsEverOwned = "";
        String level = "";
        String quest = "";
        String room = "";
        String team = "";
        String nickname = "";
        String name = "";
        String surname = "";
        String sessionId = httpCookie.get().getValue().replace("\"", "");
        ArrayList<String> artifacts = codecoolerDAO.readArtifacts();
        String table = createTable(artifacts);
        try{
            userId = Integer.parseInt(loginAccesDAO.getIdBySessionId(sessionId));
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
        if(method.equals("POST")) {
            handleBuyingItem(httpExchange, userId, codecoolerModel);

        }


        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/codecoolerPages/store.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();
        fillModelTwig(model, nickname, coins, coinsEverOwned, quest, room, team, name, surname, level, table);
        // render a template to a string
        String response = template.render(model);

        // send the results to a the client
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    public void handleBuyingItem(HttpExchange httpExchange, int userId, CodecoolerModel codecoolerModel) throws IOException {
        Map<String, String> formData = formDataParser.getData(httpExchange);
        int itemId = Integer.parseInt(formData.get("artifact-id"));
        int price = codecoolerDAO.getPriceOfArtifact(itemId);
        if (price != 0 && price <= codecoolerModel.getCoolcoins()){
            codecoolerDAO.addNewPossesion(userId, itemId);
            codecoolerDAO.subtractCodecoolersCoolcoins(userId, price);
        }
    }

    public void setFormDataParser(FormDataParser formDataParser) {
        this.formDataParser = formDataParser;
    }

    public void setCodecoolerDAO(CodecoolerDAO codecoolerDAO) {
        this.codecoolerDAO = codecoolerDAO;
    }

    private Optional<HttpCookie> getSessionIdCookie(HttpExchange httpExchange){
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        List<HttpCookie> cookies = cookieHelper.parseCookies(cookieStr);
        return cookieHelper.findCookieByName(SESSION_COOKIE_NAME, cookies);
    }

    private void fillModelTwig(JtwigModel model,  String nickname, String coins,  String coinsEverOwned, String quest, String room, String team, String name, String surname, String level, String table){
        model.with("nickname", nickname);
        model.with("coolcoins", coins);
        model.with("coolcoins_ever_owned", coinsEverOwned);
        model.with("quest", quest);
        model.with("room", room);
        model.with("team", team);
        model.with("name", name);
        model.with("surname", surname);
        model.with("level", level);
        model.with("table", table);
    }

    private String createTable(ArrayList<String> items) {
        String table = "<tr><th>Item ID</th><th>Item</th><th>Cost</th></tr>\n";
        for(String item : items) {
            String[] itemList = item.split(";");
            table += "<tr><td>"+ itemList[0] + "</td><td class = 'item'>" +itemList[1] + "</td><td class = 'price'>" + itemList[3] + "</td></tr>\n";
        }
        return table;
    }
}