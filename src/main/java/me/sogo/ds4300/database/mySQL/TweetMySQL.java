package me.sogo.ds4300.database.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import me.sogo.ds4300.database.TweetDatabaseAPI;
import me.sogo.ds4300.model.Tweet;

public class TweetMySQL extends MySQLAPI implements TweetDatabaseAPI {

  @Override
  public void initTables() {
    String sql_dropTable = "DROP TABLE IF EXISTS tweet;";
    String sql_createTweetTable = "CREATE TABLE IF NOT EXISTS tweet (" +
        "tweet_id INT PRIMARY KEY AUTO_INCREMENT," +
        "user_id INT NOT NULL," +
        "tweet_ts DATETIME NOT NULL," +
        "tweet_text VARCHAR(140) NOT NULL" +
        ");";

    try {
      Connection con = dbu.getConnection();
      Statement stmt = con.createStatement();
      stmt.execute(sql_dropTable);
      stmt.execute(sql_createTweetTable);
      stmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void insertTweet(Tweet tweet) {
    String sql = "INSERT INTO tweet (tweet_id,user_id,tweet_ts,tweet_text) VALUES (?,?,?,?)";
    try {
      Connection con = dbu.getConnection();
      PreparedStatement pstmt = con.prepareStatement(sql);

      pstmt.setInt(1, tweet.getTweetId());
      pstmt.setInt(2, tweet.getUserId());
      pstmt.setString(3, tweet.getTweetTimestamp());
      pstmt.setString(4, tweet.getTweetText());
      pstmt.execute();

      pstmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void insertTweets(List<Tweet> tweets) {
    String sql = "INSERT INTO tweet (tweet_id,user_id,tweet_ts,tweet_text) VALUES (?,?,?,?)";
    try {
      Connection con = dbu.getConnection();
      PreparedStatement pstmt = con.prepareStatement(sql);

      for (Tweet t : tweets) {
        pstmt.setInt(1, t.getTweetId());
        pstmt.setInt(2, t.getUserId());
        pstmt.setString(3, t.getTweetTimestamp());
        pstmt.setString(4, t.getTweetText());
        pstmt.execute();
      }
      pstmt.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public List<Tweet> getTweets(int userId) {
    return List.of();
  }
}
