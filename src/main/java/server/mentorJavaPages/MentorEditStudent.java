package server.mentorJavaPages;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import controllers.dao.MentorDAO;
import models.CodecoolerModel;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;
import server.helpers.CookieHelper;
import server.helpers.FormDataParser;
import controllers.dao.LoginAccesDAO;

        public class MentorEditStudent implements HttpHandler {
            private Optional<HttpCookie> cookie;
            private CookieHelper cookieHelper;
            private FormDataParser formDataParser;
            private LoginAccesDAO loginAccesDAO;
            private MentorDAO mentorDAO;

            public MentorEditStudent(Connection connection){
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
                            if (inputs.containsKey("search")){
                                List<String> searchResults = mentorDAO.searchForStudent(inputs.get("codecoolerToFind").toString());
                                System.out.println(searchResults);
                                response = generatePage(searchResults);
                            }
                            if (inputs.containsKey("submitCodecooler")){
                                try {
                                    int providedId = Integer.valueOf(inputs.get("id").toString());
                                    String providedName = inputs.get("firstName").toString();
                                    String providedSurname = inputs.get("lastName").toString();
                                    String providedNickname = inputs.get("nickname").toString();
                                    String providedEmail = inputs.get("email").toString();
                                    String providedPassword = inputs.get("password").toString();
                                    CodecoolerModel codecoolerModel = new CodecoolerModel(providedName, providedSurname, providedEmail, providedNickname, providedPassword);
                                    mentorDAO.editCodecooler(providedId, codecoolerModel);

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

                JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorEditStudent.twig");

                JtwigModel model = JtwigModel.newModel();

                return template.render(model);
            }

            private String generatePage(List<String> list){

                JtwigTemplate template = JtwigTemplate.classpathTemplate("HTML/mentorPages/mentorEditStudent.twig");

                JtwigModel model = JtwigModel.newModel().with("list", list);

                return template.render(model);
            }
        }