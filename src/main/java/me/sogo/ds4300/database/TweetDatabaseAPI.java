package me.sogo.ds4300.database;

import java.util.List;
import me.sogo.ds4300.model.Tweet;

public interface TweetDatabaseAPI extends DatabaseAPI {
  public void postTweet(Tweet tweet);

  public void postTweets(List<Tweet> tweets);

  public List<Tweet> getTweets(final int userId);

  public List<Tweet> getTweets(final List<Integer> userIds, final int postCount);
}
