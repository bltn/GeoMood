package repositories;

import models.Tweet;
import org.jongo.MongoCursor;

public class TweetRepository {

    public static MongoCursor<Tweet> findByTopic(String topic) {
        return null;
    }
}

/**
 * DB db = new MongoClient().getDB("dbname");

 Jongo jongo = new Jongo(db);
 MongoCollection friends = jongo.getCollection("friends");

 MongoCursor<Friend> all = friends.find("{name: 'Joe'}").as(Friend.class);
 Friend one = friends.findOne("{name: 'Joe'}").as(Friend.class);
 MongoCursor<Friend> all = friends.find("{name: 'Joe'}").as(Friend.class);

 */
