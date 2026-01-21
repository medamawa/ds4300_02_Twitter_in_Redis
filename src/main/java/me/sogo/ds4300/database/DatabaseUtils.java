package me.sogo.ds4300.database;

import java.sql.Connection;

public interface DatabaseUtils {

  public void initDatabase(final String schema);

  public Connection getConnection();

  public void closeConnection();
}
