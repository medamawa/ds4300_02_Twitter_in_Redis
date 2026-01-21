package me.sogo.ds4300.database.mySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;
import me.sogo.ds4300.database.DatabaseUtils;
import me.sogo.ds4300.model.Follow;

public class MySQLUtils implements DatabaseUtils {
  private final String url;
  private final String user;
  private final String password;
  private Connection connection;

  public MySQLUtils(String url, String user, String password) {
    this.url = url;
    this.user = user;
    this.password = password;
    this.connection = null;
  }

  @Override
  public void initDatabase(final String schema) {
    String sql_dropSchema = "DROP SCHEMA IF EXISTS " + schema + ";";
    String sql_createSchema = "CREATE SCHEMA IF NOT EXISTS " + schema + " DEFAULT CHARACTER SET utf8;";
    String sql_useSchema = "USE " + schema + ";";

    try {
      Connection con = getConnection();
      Statement stmt = con.createStatement();
      stmt.execute(sql_dropSchema);
      stmt.execute(sql_createSchema);
      stmt.execute(sql_useSchema);
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public Connection getConnection() {
    if (connection == null) {
      try {
        connection = DriverManager.getConnection(url, user, password);
        return connection;
      } catch (SQLException e) {
        System.err.println(e.getMessage());
        System.exit(1);
      }
    }

    return connection;
  }

  @Override
  public void closeConnection() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
      }
    }
  }
}
