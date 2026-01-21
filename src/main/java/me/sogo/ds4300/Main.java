package me.sogo.ds4300;

import java.util.ArrayList;
import java.util.List;
import me.sogo.ds4300.database.FollowDatabaseAPI;
import me.sogo.ds4300.database.mySQL.FollowMySQL;
import me.sogo.ds4300.database.TweetDatabaseAPI;
import me.sogo.ds4300.database.mySQL.MySQLUtils;
import me.sogo.ds4300.database.mySQL.TweetMySQL;
import me.sogo.ds4300.model.Tweet;
import me.sogo.ds4300.utility.CsvLoader;


public class Main {
  static final String DATABASE_NAME = System.getenv("HW1_MYSQL_DATABASE");
  static final String DB_USER = System.getenv("HW1_MYSQL_USER");
  static final String DB_PASSWORD = System.getenv("HW1_MYSQL_PW");
  static final String DB_URL = "jdbc:mysql://localhost:3306/" + DATABASE_NAME;

  static final String tweetsData = "src/main/resources/sample_data/tweets_sample.csv";
  static final String followsData = "src/main/resources/sample_data/follows_sample.csv";

  public static void main(String[] args) {

    if (DATABASE_NAME == null || DB_USER == null || DB_PASSWORD == null) {
      throw new RuntimeException("MySQL env vars are not set.");
    }
    System.out.println(DB_USER + ":" + DB_PASSWORD);

    MySQLUtils dbu = new MySQLUtils(DB_URL, DB_USER, DB_PASSWORD);
    dbu.initDatabase(DATABASE_NAME);

    TweetDatabaseAPI tweetApi = new TweetMySQL();
    FollowDatabaseAPI followApi = new FollowMySQL();

    tweetApi.authenticate(dbu);
    followApi.authenticate(dbu);

    tweetApi.initTables();
    followApi.initTables();

    try {
      tweetApi.insertTweets(CsvLoader.loadTweets(tweetsData));
      followApi.insertFollows(CsvLoader.loadFollows(followsData));
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }

    tweetApi.closeConnection();
    followApi.closeConnection();
  }
}