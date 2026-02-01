package me.sogo.ds4300.database.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import me.sogo.ds4300.database.TweetDatabaseAPI;
import me.sogo.ds4300.model.Tweet;
import org.json.JSONObject;

public class TweetRedis extends RedisAPI implements TweetDatabaseAPI {
  public TweetRedis(String host, int port) {
    super(host, port);
  }

  @Override
  public void postTweet(Tweet tweet) {
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

  @Override
  public void postTweets(List<Tweet> tweets) {
    for (Tweet tweet : tweets) {
      postTweet(tweet);
    }
  }

  @Override
  public List<Tweet> getTweets(int userId) {
    return List.of();
  }

  @Override
  public List<Tweet> getTweets(List<Integer> userIds, int postCount) {
    return List.of();
  }

  @Override
  public List<Tweet> getTimeline(int userId, int postCount) {
    String timelineKey = "timeline:" + userId;
    List<String> tweetStrings = jedis.lrange(timelineKey, 0, postCount);

    List<Tweet> tweets = new ArrayList<>();
    for (String tweetString : tweetStrings) {
      JSONObject tweetJson = new JSONObject(tweetString);
      tweets.add(new Tweet(tweetJson));
    }
    return tweets;
  }
}
