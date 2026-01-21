package me.sogo.ds4300.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import me.sogo.ds4300.model.Follow;
import me.sogo.ds4300.model.Tweet;

public class CsvLoader {

  public static List<Tweet> loadTweets(final String path) throws IOException {
    List<Tweet> tweets = new ArrayList<>();

    List<String> lines = Files.readAllLines(Paths.get(path));

    // Skip header line.
    for (int i = 1; i < lines.size(); i++) {
      String line = lines.get(i);

      // USER_ID,TWEET_TEXT
      String[] parts = line.split(",", 2);

      int userId = Integer.parseInt(parts[0]);
      String tweetText = parts[1]
          .replaceAll("^\"|\"$", "");   // Delete " at both ends.

      String timestamp = LocalDateTime.now().toString();

      tweets.add(new Tweet(userId, timestamp, tweetText));
    }

    return tweets;
  }

  public static List<Follow>  loadFollows(final String path) throws IOException {
    List<Follow> follows = new ArrayList<>();

    List<String> lines = Files.readAllLines(Paths.get(path));

    // Skip header line.
    for (int i = 1; i < lines.size(); i++) {
      String line = lines.get(i);

      String[] parts = line.split(",");

      int followeeId = Integer.parseInt(parts[0]);
      int followerId = Integer.parseInt(parts[1]);

      follows.add(new Follow(followerId, followeeId));
    }

    return follows;
  }
}
