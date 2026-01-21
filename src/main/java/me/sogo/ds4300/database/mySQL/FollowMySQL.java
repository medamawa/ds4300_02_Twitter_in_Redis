package me.sogo.ds4300.database.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
      throw new RuntimeException(e);
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
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Integer> getFollowers(int userId) {
    List<Integer> followers = new ArrayList<>();
    String sql = "SELECT * FROM follow WHERE followee_id = " + userId;
    try {
      Connection con = dbu.getConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        followers.add(rs.getInt("follower_id"));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return followers;
  }

  @Override
  public List<Integer> getFollows(int userId) {
    List<Integer> follows = new ArrayList<>();
    String sql = "SELECT * FROM follow WHERE follower_id = " + userId;
    try {
      Connection con = dbu.getConnection();
      Statement stmt = con.createStatement();
      ResultSet rs = stmt.executeQuery(sql);
      while (rs.next()) {
        follows.add(rs.getInt("followee_id"));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return follows;
  }
}
