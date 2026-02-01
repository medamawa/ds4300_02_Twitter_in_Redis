package me.sogo.ds4300.redis;

import java.util.List;
import java.util.Random;
import me.sogo.ds4300.database.DatabaseAPIs;
import me.sogo.ds4300.database.FollowDatabaseAPI;
import me.sogo.ds4300.database.TweetDatabaseAPI;
import me.sogo.ds4300.database.redis.FollowRedis;
import me.sogo.ds4300.database.redis.TweetRedis;
import me.sogo.ds4300.model.Follow;
import me.sogo.ds4300.model.Tweet;
import me.sogo.ds4300.utility.CsvLoader;
import org.junit.Test;

public class BenchmarkTest {
  DatabaseAPIs dbApis;

  final String tweetsData = "src/main/resources/data/tweets.csv";
  final String followsData = "src/main/resources/data/follows.csv";
  final String host = "localhost";
  final int port = 6379;

  private void setupDatabaseApis() {
    TweetDatabaseAPI tweetApi = new TweetRedis(host, port);
    FollowDatabaseAPI followApi = new FollowRedis(host, port);

    dbApis = new DatabaseAPIs(tweetApi, followApi);
  }

  private void cleanUpDatabase() {
    dbApis.tweetApi().initTables();
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

    List<Follow> follows = CsvLoader.loadFollows(followsData);
    System.out.printf("Start inserting %d follows...%n", follows.size());
    dbApis.followApi().insertFollows(follows);
    System.out.printf("Inserted %d follows%n", follows.size());

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
    System.out.println("Please confirm that tweet data is already setup before running this test.");

    Random random = new Random();
    int runs = 1000;
    double totalTime = 0.0;

    for (int i = 0; i < runs; i++) {
      int userId = random.nextInt(10000);

      System.out.printf("Start retrieving %d's timeline...%n", userId);
      long start = System.nanoTime();

      dbApis.getTimeline2(userId, 10);

      long end = System.nanoTime();
      double seconds = (end - start) / 1e9;
      System.out.printf("Retrieved %d's timeline in %.3f seconds (%.2f timelines/sec)%n",
          userId, seconds, 1 / seconds);
      totalTime += seconds;
    }

    double avgTime = totalTime / runs;

    System.out.printf("Average over %d runs: %.6f seconds per run (%.2f timelines/sec)%n",
        runs, avgTime, 1 / avgTime);

    closeDatabase();
  }
}
