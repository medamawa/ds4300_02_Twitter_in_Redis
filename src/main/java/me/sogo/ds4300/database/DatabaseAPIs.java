package me.sogo.ds4300.database;

import java.util.List;
import me.sogo.ds4300.model.Tweet;

public record DatabaseAPIs(
    TweetDatabaseAPI tweetApi,
    FollowDatabaseAPI followApi
) {

  public List<Tweet> getTimeline(final int userId, final int postCount) {
    List<Integer> follows = followApi.getFollows(userId);

    System.out.println(follows);

    return tweetApi.getTweets(follows, postCount);
  }
}
