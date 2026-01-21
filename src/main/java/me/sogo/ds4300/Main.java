package me.sogo.ds4300;

import me.sogo.ds4300.database.DatabaseAPIs;
import me.sogo.ds4300.database.mySQL.MySQLUtils;
import me.sogo.ds4300.database.mySQL.TweetMySQL;
import me.sogo.ds4300.database.mySQL.FollowMySQL;
import me.sogo.ds4300.utility.CsvLoader;


public class Main {

  public static void main(String[] args) {

    final String tweetsData = "src/main/resources/sample_data/tweets.csv";
    final String followsData = "src/main/resources/sample_data/follows.csv";

    DatabaseAPIs dbApis = setupApis();

    try {
      dbApis.tweetApi().postTweets(CsvLoader.loadTweets(tweetsData));
      dbApis.followApi().insertFollows(CsvLoader.loadFollows(followsData));
    } catch (Exception e) {
      e.printStackTrace();
    }

    dbApis.tweetApi().closeConnection();
    dbApis.followApi().closeConnection();
  }

  private static DatabaseAPIs setupApis() throws RuntimeException {
    final String schema = System.getenv("HW1_MYSQL_DATABASE");
    final String user = System.getenv("HW1_MYSQL_USER");
    final String password = System.getenv("HW1_MYSQL_PW");
    final String url = "jdbc:mysql://localhost:3306/" + schema;

    if (schema == null || user == null || password == null) {
      throw new RuntimeException("MySQL env vars are not set.");
    }

    System.out.println("user: " + user);

    MySQLUtils dbu = new MySQLUtils(url, user, password);
    dbu.initDatabase(schema);

    DatabaseAPIs dbApis = new DatabaseAPIs(new TweetMySQL(), new FollowMySQL());

    dbApis.tweetApi().authenticate(dbu);
    dbApis.followApi().authenticate(dbu);

    dbApis.tweetApi().initTables();
    dbApis.followApi().initTables();

    return dbApis;
  }
}