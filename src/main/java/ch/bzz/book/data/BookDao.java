package ch.bzz.book.data;

import ch.bzz.book.service.Config;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * data access for book entity
 * <p>
 * M151: BookDB
 *
 * @author Marcel Suter
 * @version 1.0
 * @since 2019-10-13
 */
public class BookDao {

    /**
     * count all books in table Book
     *
     * @return number of books
     */
    public Integer count() {

        Connection connection;
        String jdbcRessource = Config.getProperty("jdbcRessource");
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup(jdbcRessource);
            connection = dataSource.getConnection();

        } catch (NamingException nameEx) {
            nameEx.printStackTrace();
            throw new RuntimeException();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            throw new RuntimeException();
        }

        PreparedStatement prepStmt = null;
        ResultSet resultSet = null;
        String sqlQuery;
        int bookCount = 0;
        try {
            sqlQuery = "SELECT COUNT(bookUUID)" +
                    "   FROM Book";
            prepStmt = connection.prepareStatement(sqlQuery);
            resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                bookCount = resultSet.getInt(1);
            }


        } catch (SQLException sqlEx) {

            sqlEx.printStackTrace();
            throw new RuntimeException();
        } finally {

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


            return bookCount;
        }
    }
}
