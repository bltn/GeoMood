package models;

import com.google.maps.model.LatLng;
import service.LocationTranslator;
import service.NLP;
import twitter4j.GeoLocation;
import twitter4j.Status;

public class TweetFactory {

    /**
     * Creates a Tweet (model) Object from a Twitter4j Status Object,
     * if its location is either stated explicitly in its GeoLocation field
     * or can be inferred using its user profile location
     * @param status Twitter4j Status object
     * @return
     */
    public static Tweet createFromStatusIfHasLocation(Status status) {
        Tweet tweet = new Tweet();

        if (status.getGeoLocation() != null) {
            tweet.setText(status.getText());
            tweet.setGeoLocation(status.getGeoLocation());
            tweet.setUserLocation(status.getUser().getLocation());
            tweet.setSentimentValue(NLP.getSentiment(status.getText()));
            tweet.setTweetId(status.getId());

            return tweet;
        } else if (status.getUser().getLocation() != null) {
            LatLng coordinates = LocationTranslator.addressToCoordinates(status.getUser().getLocation());
            if (coordinates != null) {
                tweet.setText(status.getText());
                tweet.setGeoLocation(new GeoLocation(coordinates.lat, coordinates.lng));
                tweet.setUserLocation(status.getUser().getLocation());
                tweet.setSentimentValue(NLP.getSentiment(status.getText()));
                tweet.setTweetId(status.getId());

                return tweet;
            }
        }
        return null;
    }

}
