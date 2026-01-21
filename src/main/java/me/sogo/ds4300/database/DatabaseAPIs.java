package me.sogo.ds4300.database;

public record DatabaseAPIs(
    TweetDatabaseAPI tweetApi,
    FollowDatabaseAPI followApi
) {
}
