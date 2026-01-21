package me.sogo.ds4300.database.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import me.sogo.ds4300.database.FollowDatabaseAPI;
import me.sogo.ds4300.model.Follow;

public class FollowMySQL extends MySQLAPI implements FollowDatabaseAPI {

  @Override
  public void initTables() {
    String sql_dropTable = "DROP TABLE IF EXISTS follow;";
    String sql_createFollowTable = "CREATE TABLE IF NOT EXISTS follow (" +
        "follower_id INT NOT NULL," +
        "followee_id INT NOT NULL" +
        ");";

    try {
      Connection con = dbu.getConnection();
      Statement stmt = con.createStatement();
      stmt.execute(sql_dropTable);
      stmt.execute(sql_createFollowTable);
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void insertFollows(List<Follow> followList) {
    String sql = "INSERT INTO follow (follower_id,followee_id) VALUES (?,?)";
    try {
      Connection con = dbu.getConnection();
      PreparedStatement pstmt = con.prepareStatement(sql);

      for (Follow f : followList) {
        pstmt.setInt(1, f.getFollowerId());
        pstmt.setInt(2, f.getFolloweeId());
        pstmt.execute();
      }
      pstmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Follow> getFollows(int userId) {
    return List.of();
  }
}
