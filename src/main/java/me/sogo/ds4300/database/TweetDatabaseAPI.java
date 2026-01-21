package me.sogo.ds4300.database;

import java.util.List;
import me.sogo.ds4300.model.Tweet;

public interface TweetDatabaseAPI extends DatabaseAPI {
  public void insertTweet(Tweet tweet);

  public void insertTweets(List<Tweet> tweets);

  public List<Tweet> getTweets(final int userId);
}
