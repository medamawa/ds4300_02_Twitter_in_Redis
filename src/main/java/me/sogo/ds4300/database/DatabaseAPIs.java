package me.sogo.ds4300.database;

import java.util.List;
import me.sogo.ds4300.model.Tweet;

public record DatabaseAPIs(
    TweetDatabaseAPI tweetApi,
    FollowDatabaseAPI followApi
) {

  /**
   * Retrieve the user timeline with two steps.
   *
   * 1. get all followees.
   * 2. get all tweets of those followees.
   *
   * @param userId
   * @param postCount
   * @return
   */
  public List<Tweet> getTimeline(final int userId, final int postCount) {
    List<Integer> follows = followApi.getFollows(userId);

//    System.out.println(follows);

    return tweetApi.getTweets(follows, postCount);
  }

  /**
   * Retrieve the user timeline at one time.
   *
   * @param userId
   * @param postCount
   * @return
   */
  public List<Tweet> getTimeline2(final int userId, final int postCount) {
    return tweetApi.getTimeline(userId, postCount);
  }
}
