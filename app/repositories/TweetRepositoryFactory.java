package repositories;

import com.typesafe.config.ConfigFactory;

public class TweetRepositoryFactory {

    private static final String testDBName = ConfigFactory.load().getString("database.test.name");
    private static final String testCollectionName = ConfigFactory.load().getString("database.test.collection");
    private static final String devDBName = ConfigFactory.load().getString("database.development.name");
    private static final String devCollectionName = ConfigFactory.load().getString("database.development.collection");

    public static TweetRepository getTweetRepository(String env) {
        if (env.equals("test")) {
            return new TweetRepository(testDBName, testCollectionName);
        } else if (env.equals("dev")) {
            return new TweetRepository(devDBName, devCollectionName);
        } else {
            return null;
        }
    }
}
