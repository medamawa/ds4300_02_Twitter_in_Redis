-- Twitter RDB データベーススキーマ

-- ツイートテーブル
CREATE TABLE IF NOT EXISTS tweet (
    tweet_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    tweet_ts VARCHAR(50) NOT NULL,
    tweet_text VARCHAR(280) NOT NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_tweet_ts (tweet_ts)
);

-- フォロー関係テーブル
CREATE TABLE IF NOT EXISTS follows (
    follower_id INT NOT NULL,
    followee_id INT NOT NULL,
    PRIMARY KEY (follower_id, followee_id),
    INDEX idx_follower_id (follower_id),
    INDEX idx_followee_id (followee_id)
);
