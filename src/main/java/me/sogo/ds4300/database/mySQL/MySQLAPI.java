package me.sogo.ds4300.database.mySQL;

import me.sogo.ds4300.database.DatabaseAPI;
import me.sogo.ds4300.database.DatabaseUtils;

public abstract class MySQLAPI implements DatabaseAPI {
  protected MySQLUtils dbu;

  @Override
  public void authenticate(DatabaseUtils dbu) {
    if (dbu instanceof MySQLUtils) {
      this.dbu = (MySQLUtils) dbu;
    } else {
      throw new IllegalArgumentException("MySQLAPI requires MySQLUtils instance");
    }
  }

  @Override
  public void closeConnection() {
    dbu.closeConnection();
  }
}
