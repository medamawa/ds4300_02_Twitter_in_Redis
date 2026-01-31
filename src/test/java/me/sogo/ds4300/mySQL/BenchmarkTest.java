package me.sogo.ds4300.mySQL;

import java.util.List;
import java.util.Random;
import me.sogo.ds4300.database.DatabaseAPIs;
import me.sogo.ds4300.database.mySQL.FollowMySQL;
import me.sogo.ds4300.database.mySQL.MySQLUtils;
import me.sogo.ds4300.database.mySQL.TweetMySQL;
import me.sogo.ds4300.model.Tweet;
import me.sogo.ds4300.utility.CsvLoader;
import org.junit.Test;

public class BenchmarkTest {

  MySQLUtils dbu;
  DatabaseAPIs dbApis;

  final String schema = System.getenv("HW1_MYSQL_DATABASE");
  final String user = System.getenv("HW1_MYSQL_USER");
  final String password = System.getenv("HW1_MYSQL_PW");
  final String url = "jdbc:mysql://localhost:3306/" + schema;

  final String tweetsData = "src/main/resources/data/tweets.csv";
  final String followsData = "src/main/resources/data/follows.csv";

  private void setupDatabaseApis() throws RuntimeException {

    if (schema == null || user == null || password == null) {
      throw new RuntimeException("MySQL env vars are not set.");
    }

    System.out.println("Setup database connection by user: " + user);

    dbu = new MySQLUtils(url, user, password);
    dbApis = new DatabaseAPIs(new TweetMySQL(), new FollowMySQL());
    dbApis.tweetApi().authenticate(dbu);
    dbApis.followApi().authenticate(dbu);
  }

  private void cleanUpDatabase() {
    if (dbu == null) {
      throw new RuntimeException("dbu is null.");
    }

    System.out.println("Clean up database");
    dbu.initDatabase(schema);
    dbApis.tweetApi().initTables();
    dbApis.followApi().initTables();
  }

  private void closeDatabase() {
    if (dbApis != null) {
      dbApis.tweetApi().closeConnection();
      dbApis.followApi().closeConnection();
    }
  }

  @Test
  public void testTweetPostingSpeed() throws Exception {
    setupDatabaseApis();
    cleanUpDatabase();

    List<Tweet> tweets = CsvLoader.loadTweets(tweetsData);

    System.out.printf("Start inserting %d tweets...%n", tweets.size());
    long start = System.nanoTime();

    dbApis.tweetApi().postTweets(tweets);

    long end = System.nanoTime();
    double seconds = (end - start) / 1e9;
    System.out.printf("Inserted %d tweets in %.3f seconds (%.2f tweets/sec)%n",
        tweets.size(), seconds, tweets.size() / seconds);

    closeDatabase();
  }

  @Test
  public void testTimelineRetrievingSpeed() throws Exception {
    setupDatabaseApis();

    System.out.println("Please check that tweet data is already setup before running this test.");

    /**
    List<Follow> follows = CsvLoader.loadFollows(followsData);
    System.out.printf("Start inserting %d follows...%n", follows.size());
    dbApis.followApi().insertFollows(follows);
    System.out.printf("Inserted %d follows%n", follows.size());
    */

    Random random = new Random();
    int runs = 1000;
    double totalTime = 0.0;

    for (int i = 0; i < runs; i++) {
      int userId = random.nextInt(10000);

//      System.out.printf("Start retrieving %d's timeline...%n", userId);
      long start = System.nanoTime();

      dbApis.getTimeline2(userId, 10);

      long end = System.nanoTime();
      double seconds = (end - start) / 1e9;
//      System.out.printf("Retrieved %d's timeline in %.3f seconds (%.2f timelines/sec)%n",
//          userId, seconds, 1 / seconds);
      totalTime += seconds;
    }

    double avgTime = totalTime / runs;

    System.out.printf("Average over %d runs: %.6f seconds per run (%.2f timelines/sec)%n",
        runs, avgTime, 1 / avgTime);

    closeDatabase();
  }

  @Test
  public void testCleanup() throws Exception {
    setupDatabaseApis();
    cleanUpDatabase();
  }
}
