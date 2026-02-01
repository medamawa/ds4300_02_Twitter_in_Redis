package me.sogo.ds4300;

import java.util.List;
import java.util.Set;
import me.sogo.ds4300.model.Follow;
import me.sogo.ds4300.model.Tweet;
import me.sogo.ds4300.utility.CsvLoader;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

public class RedisMain {

  public static void main(String[] args) {
    final String tweetsData = "src/main/resources/sample_data/tweets.csv";
    final String followsData = "src/main/resources/sample_data/follows.csv";

    Jedis jedis = new Jedis("localhost", 6379);

    try {
      List<Tweet> tweets = CsvLoader.loadTweets(tweetsData);
      List<Follow> follows = CsvLoader.loadFollows(followsData);

      for (Follow follow : follows) {
        String key = "user:" + follow.getFolloweeId() + ":followers";
        String value = String.valueOf(follow.getFollowerId());
        jedis.sadd(key, value);
      }

      // postTweet
      for (Tweet tweet : tweets) {
        JSONObject tweetJson = new JSONObject()
            .put("user_id", String.valueOf(tweet.getUserId()))
            .put("timestamp", tweet.getTweetTimestamp())
            .put("text", tweet.getTweetText());

        // Store tweet.
        jedis.set("tweet:" + tweet.getTweetId(), tweetJson.toString());

        // Copy to each timeline.
        String followerKey = "user:" + tweet.getUserId() + ":followers";
        Set<String> followerIds = jedis.smembers(followerKey);
        for (String followerId : followerIds) {
          String timelineKey = "timeline:" + followerId;
          jedis.lpush(timelineKey, tweetJson.toString());
        }
      }


      // getTimeline
      for (int userId = 1; userId < 6; userId++) {
        String timelineKey = "timeline:" + userId;
        List<String> timeline = jedis.lrange(timelineKey, 0, 100);
        System.out.println(timeline);
      }

    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    jedis.close();
  }
}
