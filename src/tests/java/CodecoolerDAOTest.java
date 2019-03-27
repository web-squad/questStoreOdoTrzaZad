import com.sun.net.httpserver.HttpExchange;
import controllers.dao.CodecoolerDAO;
import models.CodecoolerModel;
import org.junit.jupiter.api.Test;
import server.codecoolerJavaPages.Store;
import server.helpers.FormDataParser;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;


class CodecoolerDAOTest {

    @Test
    void testIfCheckingIfCodecoolerHasEnoughMoneyToBuyItemWorksCorrectly() throws IOException {
        HttpExchange httpExchange = mock(HttpExchange.class);
        FormDataParser formDataParser = mock(FormDataParser.class);
        CodecoolerDAO codecoolerDAO = mock(CodecoolerDAO.class);
        int itemId = 1;
        int userId = 1;
        CodecoolerModel codecoolerModel = mock(CodecoolerModel.class);
        Connection connection = mock(Connection.class);
        Map<String, String> formData = new HashMap<>();
        formData.put("artifact-id", "1");
        when(formDataParser.getData(httpExchange)).thenReturn(formData);
        when(codecoolerDAO.getPriceOfArtifact(anyInt())).thenReturn(10);
        when(codecoolerModel.getCoolcoins()).thenReturn(15);
        Store store = new Store(connection);
        store.setFormDataParser(formDataParser);
        store.setCodecoolerDAO(codecoolerDAO);
        store.handleBuyingItem(httpExchange, userId, codecoolerModel);
        verify(codecoolerDAO, times(1)).addNewPossesion(userId, itemId);
        verify(codecoolerDAO, times(1)).subtractCodecoolersCoolcoins(anyInt(), anyInt());
    }
}