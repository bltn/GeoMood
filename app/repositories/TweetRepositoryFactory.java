package repositories;

import com.typesafe.config.ConfigFactory;

public class TweetRepositoryFactory {

    private static final String testDBName = ConfigFactory.load().getString("database.test.name");
    private static final String testCollectionName = ConfigFactory.load().getString("database.test.collection");

    private static final String devDBName = ConfigFactory.load().getString("database.development.name");
    private static final String devCollectionName = ConfigFactory.load().getString("database.development.collection");

    private static final String productionDBName = ConfigFactory.load().getString("database.production.name");
    private static final String productionCollectionName = ConfigFactory.load().getString("database.production.collection");

    public static TweetRepository getTweetRepository(DBEnvironment env) {
        if (env == DBEnvironment.TEST) {
            return new TweetRepository(testDBName, testCollectionName);
        } else if (env == DBEnvironment.DEV) {
            return new TweetRepository(devDBName, devCollectionName);
        } else if (env == DBEnvironment.PRODUCTION) {
            return new TweetRepository(productionDBName, productionCollectionName);
        } else {
            return null;
        }
    }
}
