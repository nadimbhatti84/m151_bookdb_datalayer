package ch.bzz.book.data;

import ch.bzz.book.service.Config;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySqlDB {
    private static Connection connection;
    private static PreparedStatement prepStmt;
    private static ResultSet resultSet;

    public static Connection getConnection() {
        try {
            if(connection == null || !connection.isValid(2)) {
                String jdbcRessource = Config.getProperty("jdbcRessource");
                InitialContext initialContext = new InitialContext();
                DataSource dataSource = (DataSource) initialContext.lookup(jdbcRessource);
                connection = dataSource.getConnection();
            }
        } catch (NamingException nameEx) {
            nameEx.printStackTrace();
            throw new RuntimeException();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException();
        }
        return connection;
    }

    public static void setConnection(Connection connection) {
        MySqlDB.connection = connection;
    }

    public static PreparedStatement getPreparedStmt() {
        return preparedStmt;
    }

    public static void setPreparedStmt(PreparedStatement preparedStmt) {
        MySqlDB.preparedStmt = preparedStmt;
    }

    public static ResultSet getResultSet() {
        return resultSet;
    }

    public static void setResultSet(ResultSet resultSet) {
        MySqlDB.resultSet = resultSet;
    }

    private MySqlDB(){

    }

    public static void sqlClose(){
        try {
            if (resultSet != null)
                resultSet.close();
            if (prepStmt != null)
                prepStmt.close();
            if (connection != null)
                connection.close();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException();
        }
    }
}
