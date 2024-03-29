package models;

import org.bson.types.ObjectId;
import twitter4j.GeoLocation;

public class Tweet  {

    private ObjectId _id;
    private String text;
    private GeoLocation geoLocation;
    private String userLocation;

    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    private long tweetId;

    private int sentimentValue;

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public int getSentimentValue() {
        return sentimentValue;
    }

    public void setSentimentValue(int sentimentValue) {
        this.sentimentValue = sentimentValue;
    }

}