package controllers.dao;

import com.sun.net.httpserver.HttpExchange;
import models.CodecoolerModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.codecoolerJavaPages.Store;
import server.helpers.FormDataParser;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;


class StoreTest {
    int itemId = 1;
    int userId = 1;
    int itemPrice = 10;
    HttpExchange httpExchange;
    FormDataParser formDataParser;
    CodecoolerDAO codecoolerDAO;
    CodecoolerModel codecoolerModel;
    Connection connection;
    Map<String, String> formData;

    @BeforeEach
    void setUp() {
        httpExchange = mock(HttpExchange.class);
        formDataParser = mock(FormDataParser.class);
        codecoolerDAO = mock(CodecoolerDAO.class);
        codecoolerModel = mock(CodecoolerModel.class);
        connection = mock(Connection.class);
        formData = new HashMap<>();
        formData.put("artifact-id", "1");
    }

    @Test
    void testIfHandleBuyingItemMethodCorrectlyChecksIfCodecoolerCanAffordItem() throws IOException {
        int codecoolerCoins = 15;
        when(formDataParser.getData(httpExchange)).thenReturn(formData);
        when(codecoolerDAO.getPriceOfArtifact(anyInt())).thenReturn(itemPrice);
        when(codecoolerModel.getCoolcoins()).thenReturn(codecoolerCoins);
        Store store = new Store(connection);
        store.setFormDataParser(formDataParser);
        store.setCodecoolerDAO(codecoolerDAO);
        store.handleBuyingItem(httpExchange, userId, codecoolerModel);
        verify(codecoolerDAO, times(1)).addNewPossesion(userId, itemId);
        verify(codecoolerDAO, times(1)).subtractCodecoolersCoolcoins(anyInt(), anyInt());
    }

    @Test
    void testIfHandleBuyingItemMethodCorrectlyChecksIfCodecoolerHasExactAmountOfCoinsRequiredToBuyItem() throws IOException {
        int codecoolerCoins = 10;
        when(formDataParser.getData(httpExchange)).thenReturn(formData);
        when(codecoolerDAO.getPriceOfArtifact(anyInt())).thenReturn(itemPrice);
        when(codecoolerModel.getCoolcoins()).thenReturn(codecoolerCoins);
        Store store = new Store(connection);
        store.setFormDataParser(formDataParser);
        store.setCodecoolerDAO(codecoolerDAO);
        store.handleBuyingItem(httpExchange, userId, codecoolerModel);
        verify(codecoolerDAO, times(1)).addNewPossesion(userId, itemId);
        verify(codecoolerDAO, times(1)).subtractCodecoolersCoolcoins(anyInt(), anyInt());
    }

    @Test
    void testIfHandleBuyingItemMethodCorrectlyChecksIfCodecoolerCannotAffordItem() throws IOException {
        int codecoolerCoins = 9;
        when(formDataParser.getData(httpExchange)).thenReturn(formData);
        when(codecoolerDAO.getPriceOfArtifact(anyInt())).thenReturn(itemPrice);
        when(codecoolerModel.getCoolcoins()).thenReturn(codecoolerCoins);
        Store store = new Store(connection);
        store.setFormDataParser(formDataParser);
        store.setCodecoolerDAO(codecoolerDAO);
        store.handleBuyingItem(httpExchange, userId, codecoolerModel);
        verify(codecoolerDAO, never()).addNewPossesion(userId, itemId);
        verify(codecoolerDAO, never()).subtractCodecoolersCoolcoins(anyInt(), anyInt());
    }

}