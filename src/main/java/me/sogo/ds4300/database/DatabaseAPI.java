package me.sogo.ds4300.database;

public interface DatabaseAPI {
  /**
   * Set connection settings.
   */
  public void authenticate(DatabaseUtils dbu);

  /**
   * Initialize necessary tables.
   * If they exist, delete them and create new ones.
   */
  public void initTables();

  /**
   * Close the connection when application finishes
   */
  public void closeConnection();
}
