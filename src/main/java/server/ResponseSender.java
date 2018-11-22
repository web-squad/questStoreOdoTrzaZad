package server;

import com.sun.net.httpserver.HttpExchange;
import models.CodecoolerModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseSender {


    public void sendResponseIfUserExists(HttpExchange httpExchange, CodecoolerModel codecoolerModel) throws IOException {
        // get a template file
        JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/codecoolerPages/codecoolerMain.twig");

        // create a model that will be passed to a template
        JtwigModel model = JtwigModel.newModel();
        fillModelTwig(model, codecoolerModel);
        // render a template to a string
        String response = template.render(model);
        // send the results to a the client
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void fillModelTwig(JtwigModel model, CodecoolerModel codecoolerModel){
        int coinsEverOwned = codecoolerModel.getCoolcoinsEverEarned();
        String level = String.valueOf(codecoolerModel.getExpLevel());
        if(coinsEverOwned >= 10 && coinsEverOwned % 10 == 0) {
            level = String.valueOf(coinsEverOwned / 10);
        }else if (level.equals("0")){
            level = "1";
        }
        model.with("codecoolerModel", codecoolerModel);
        model.with("level", level);
    }

    public void sendResponseIfInvalidUser(HttpExchange httpExchange) throws IOException {
        httpExchange.getResponseHeaders().add("Location", "/login");
        httpExchange.sendResponseHeaders(303, 0);
    }
}
