package models;

import com.google.maps.model.LatLng;
import service.geo.LocationTranslator;
import service.NLP;
import twitter4j.GeoLocation;
import twitter4j.Status;

public class TweetFactory {

    /**
     * Creates a Tweet (model) Object from a Twitter4j Status Object,
     * if the Status Object is 'valid'. See: statusHasValidAttributes() for definition of valid.
     *
     * @param status Twitter4j Status object
     * @return Tweet object created from the Status object, or null if the Status object
     * was invalid
     */
    public static Tweet createFromStatus(Status status) {
        boolean statusIsValid = statusHasValidAttributes(status);

        if (statusIsValid) {
            return initialiseTweetFromStatus(status);
        } else {
            return null;
        }
    }

    private static Tweet initialiseTweetFromStatus(Status status) {
        if (status.getGeoLocation() != null) {
            return buildTweetFields(status);
        } else if (status.getUser().getLocation() != null) {
            LatLng coordinates = LocationTranslator.addressToCoordinates(status.getUser().getLocation());
            if (coordinates != null) {
                return buildTweetFields(status, coordinates);
            }
        }
        // Tweet is useless without a GeoLocation tag so return null
        return null;
    }

    private static Tweet buildTweetFields(Status status, LatLng coordinates) {
        Tweet tweet = buildTweetFields(status);
        tweet.setGeoLocation(new GeoLocation(coordinates.lat, coordinates.lng));
        return tweet;
    }

    private static Tweet buildTweetFields(Status status) {
        Tweet tweet = new Tweet();

        tweet.setText(status.getText());
        tweet.setGeoLocation(status.getGeoLocation());
        tweet.setUserLocation(status.getUser().getLocation());
        tweet.setSentimentValue(NLP.getSentiment(status.getText()));
        tweet.setTweetId(status.getId());

        return tweet;
    }

    /**
     * For the purpose of Tweet creation, a Status object is the following are not null:
     * - status.getText()
     * - status.getId()
     * - status.getGeoLocation() and/or status.getUser().getLocation()
     *
     * @param status Twitter4j Status object to be validated
     * @return whether or not the object contains the required fields
     */
    private static boolean statusHasValidAttributes(Status status) {
        boolean hasValidAttributes = true;

        if (status.getText() == null) hasValidAttributes = false;
        if (status.getGeoLocation() == null && status.getUser().getLocation() == null) hasValidAttributes = false;
        if (status.getId() == 0) hasValidAttributes = false;

        return hasValidAttributes;
    }

}
