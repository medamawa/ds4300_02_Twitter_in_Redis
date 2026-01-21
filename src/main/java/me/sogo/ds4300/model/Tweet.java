package me.sogo.ds4300.model;

public class Tweet {
  private int tweetId;
  private int userId;
  private String tweetTimestamp;
  private String tweetText;

  public Tweet(int userId, String tweetTimestamp, String tweetText) {
    this.tweetId = -1;
    this.userId = userId;
    this.tweetTimestamp = tweetTimestamp;
    this.tweetText = tweetText;
  }

  public Tweet(int tweetId, int userId, String tweetTimestamp, String tweetText) {
    this.tweetId = tweetId;
    this.userId = userId;
    this.tweetTimestamp = tweetTimestamp;
    this.tweetText = tweetText;
  }

  public int getTweetId() {
    return tweetId;
  }

  public int getUserId() {
    return userId;
  }

  public String getTweetTimestamp() {
    return tweetTimestamp;
  }

  public String getTweetText() {
    return tweetText;
  }}
