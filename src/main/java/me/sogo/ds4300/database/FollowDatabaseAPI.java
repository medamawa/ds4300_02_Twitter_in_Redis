package me.sogo.ds4300.database;

import java.util.List;
import me.sogo.ds4300.model.Follow;

public interface FollowDatabaseAPI extends DatabaseAPI {

  public void insertFollows(List<Follow> followList);

  public List<Integer> getFollowers(final int userId);

  public List<Integer> getFollows(final int userId);
}
