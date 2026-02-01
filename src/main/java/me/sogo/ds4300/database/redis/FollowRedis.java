package me.sogo.ds4300.database.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import me.sogo.ds4300.database.FollowDatabaseAPI;
import me.sogo.ds4300.model.Follow;

public class FollowRedis extends RedisAPI implements FollowDatabaseAPI {
  public FollowRedis(String host, int port) {
    super(host, port);
  }

  @Override
  public void insertFollows(List<Follow> followList) {
    for (Follow follow : followList) {
      String key = "user:" + follow.getFolloweeId() + ":followers";
      String value = String.valueOf(follow.getFollowerId());
      jedis.sadd(key, value);
    }
  }

  @Override
  public List<Integer> getFollowers(int userId) {
    String followerKey = "user:" + userId + ":followers";

    Set<String> followerSet = jedis.smembers(followerKey);
    List<Integer> followers = new ArrayList<>();
    for (String s : followerSet) {
      followers.add(Integer.parseInt(s));
    }
    return followers;
  }

  @Override
  public List<Integer> getFollows(int userId) {
    return List.of();
  }
}
