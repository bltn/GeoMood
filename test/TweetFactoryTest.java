import models.Tweet;
import models.TweetFactory;
import org.junit.Before;
import org.junit.Test;
import twitter4j.GeoLocation;
import twitter4j.Status;
import twitter4j.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

public class TweetFactoryTest {

    private Status status;
    private User user;

    private double bhamLat;
    private double bhamLng;


    @Before
    public void setUp() {
        // GIVEN a valid status object
        status = mock(Status.class);
        // AND a valid User object
        user = mock(User.class);
        // AND valid coordinates for Birmingham
        // coordinates were taken from the GeoCoding API when given 'Birmingham, UK' as a parameter
        bhamLat = 52.48624299999999;
        bhamLng = -1.890401;

        when(status.getUser()).thenReturn(user);
        when(user.getLocation()).thenReturn("Birmingham, UK");

        when(status.getText()).thenReturn("this is a tweet");
        when(status.getGeoLocation()).thenReturn(new GeoLocation(bhamLat, bhamLng));
        when(status.getId()).thenReturn((long)399);
    }

    @Test
    public void createFromValidStatus() {
        // WHEN a valid Status is passed as a parameter
        Tweet tweet = TweetFactory.createFromStatus(status);

        // THEN a valid tweet with equivalent fields should be returned
        assertEquals("this is a tweet", tweet.getText());
        assertEquals("Birmingham, UK", tweet.getUserLocation());
        assertEquals(new GeoLocation(bhamLat, bhamLng), tweet.getGeoLocation());
        assertEquals(399, tweet.getTweetId());
    }

    @Test
    public void createWithNullGeoLocation() {
        // GIVEN a status object with GeoLocation = null
        when(status.getGeoLocation()).thenReturn(null);

        // WHEN the status object is passed as a parameter
        Tweet tweet = TweetFactory.createFromStatus(status);

        // THEN a tweet with the correct GeoLocation inferred from the user location should be returned
        GeoLocation expected = new GeoLocation(bhamLat, bhamLng);
        assertEquals(expected, tweet.getGeoLocation());
    }

    @Test
    public void createWithNullGeoLocationAndInvalidUserLocation() {
        // GIVEN a status object with its user location set to an invalid address
        when(status.getGeoLocation()).thenReturn(null);
        when(user.getLocation()).thenReturn("blablablaidontexist");

        // WHEN the status object is passed as a param
        Tweet tweet = TweetFactory.createFromStatus(status);

        // THEN null should be returned
        assertNull(tweet);
    }

    @Test
    public void createWithNullText() {
        // GIVEN a status object with null text
        when(status.getText()).thenReturn(null);

        // WHEN the status object is passed as a parameter
        Tweet tweet = TweetFactory.createFromStatus(status);

        // THEN null should be returned
        assertNull(tweet);
    }
}
