package controllers.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CodecoolerDAOTest {

    Statement statementMock;
    Connection connectionMock;
    ResultSet resultSetMock;

    @BeforeEach
    void setUp() {
        statementMock = mock(Statement.class);
        connectionMock = mock(Connection.class);
        resultSetMock = mock(ResultSet.class);
    }

    @Test
    void testIfGetAllTeamsCreateTeamListProperly() throws SQLException {
        //Given
        when(connectionMock.createStatement()).thenReturn(statementMock);
        CodecoolerDAO codecoolerDAO = new CodecoolerDAO(connectionMock);
        setMocksBehaviour(statementMock, resultSetMock);

        ArrayList<String> expected = new ArrayList<>();
        expected.add("1;bc");
        expected.add("2;ba");

        //When
        ArrayList<String> actual = codecoolerDAO.getAllTeams();

        //Then
        assertEquals(expected, actual);
    }

    private void setMocksBehaviour(Statement statementMock, ResultSet resultSetMock) throws SQLException {
        String query = "SELECT * FROM teams;";
        when(statementMock.executeQuery(query)).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(resultSetMock.getString("id")).thenReturn("1").thenReturn("2");
        when(resultSetMock.getString("team_name")).thenReturn("bc").thenReturn("ba");
    }
}