package me.sogo.ds4300.database.mySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
      throw new RuntimeException(e);
    }
  }

  @Override
  public void postTweet(Tweet tweet) {
    String sql = "INSERT INTO tweet (user_id,tweet_ts,tweet_text) VALUES (?,?,?)";
    try {
      Connection con = dbu.getConnection();
      PreparedStatement pstmt = con.prepareStatement(sql);

      pstmt.setInt(1, tweet.getUserId());
      pstmt.setString(2, tweet.getTweetTimestamp());
      pstmt.setString(3, tweet.getTweetText());
      pstmt.execute();

      pstmt.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void postTweets(List<Tweet> tweets) {
    String sql = "INSERT INTO tweet (user_id,tweet_ts,tweet_text) VALUES (?,?,?)";
    try {
      Connection con = dbu.getConnection();
      PreparedStatement pstmt = con.prepareStatement(sql);

      for (Tweet t : tweets) {
        pstmt.setInt(1, t.getUserId());
        pstmt.setString(2, t.getTweetTimestamp());
        pstmt.setString(3, t.getTweetText());
        pstmt.execute();
      }
      pstmt.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Tweet> getTweets(int userId) {
    return List.of();
  }

  @Override
  public List<Tweet> getTweets(List<Integer> userIds, int postCount) {
    if (userIds.isEmpty()) {
      return List.of();
    }

    StringBuilder uidPlaceholders = new StringBuilder();
    for (int i = 0; i < userIds.size(); i++) {
      uidPlaceholders.append("?");
      if (i < userIds.size() - 1) {
        uidPlaceholders.append(",");
      }
    }

    List<Tweet> tweets = new ArrayList<>();
    String sql = "SELECT tweet_id, user_id, tweet_ts, tweet_text " +
        "FROM tweet " +
        "WHERE user_id IN (" + uidPlaceholders.toString() + ") " +
        "ORDER BY tweet_ts DESC " +
        "LIMIT ?";

    try {
      Connection con = dbu.getConnection();
      PreparedStatement pstmt = con.prepareStatement(sql);

      for (int i = 0; i < userIds.size(); i++) {
        pstmt.setInt(i + 1, userIds.get(i));
      }
      pstmt.setInt(userIds.size() + 1, postCount);

      ResultSet rs = pstmt.executeQuery();
      while (rs.next()) {
        tweets.add(new Tweet(
            rs.getInt("tweet_id"),
            rs.getInt("user_id"),
            rs.getString("tweet_ts"),
            rs.getString("tweet_text")
        ));
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return tweets;
  }
}
