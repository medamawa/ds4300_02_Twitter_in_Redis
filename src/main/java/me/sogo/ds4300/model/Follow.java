package me.sogo.ds4300.model;

public class Follow {
  private final int followerId;
  private final int followeeId;

  public Follow(int followerId, int followeeId) {
    this.followerId = followerId;
    this.followeeId = followeeId;
  }

  public int getFollowerId() {
    return followerId;
  }

  public int getFolloweeId() {
    return followeeId;
  }
}
