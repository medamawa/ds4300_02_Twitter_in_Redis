package me.sogo.ds4300.database.redis;

import me.sogo.ds4300.database.DatabaseAPI;
import me.sogo.ds4300.database.DatabaseUtils;
import redis.clients.jedis.Jedis;

public class RedisAPI implements DatabaseAPI {
  protected Jedis jedis;

  public RedisAPI(String host, int port) {
    jedis = new Jedis(host, port);
  }

  @Override
  public void authenticate(DatabaseUtils dbu) {
    // No need.
    return;
  }

  @Override
  public void initTables() {
    jedis.flushAll();
  }

  @Override
  public void closeConnection() {
    jedis.close();
  }
}
