import com.google.maps.model.LatLng;
import org.junit.Before;
import org.junit.Test;
import service.LocationTranslator;
import twitter4j.GeoLocation;

import static org.junit.Assert.assertNull;

/**
 * Precision isn't too important for location inference,
 * so long as coordinates are within a reasonable range...
 *
 * ...therefore the focus of the tests are to ensure the LocationTranslator class
 * is interacting with the API and returning ~appropriate results for valid addresses
 * and is returning null for bogus addresses
 */
public class LocationTranslatorTest {

    // address that clearly doesn't exist
    private String bogusAddress;
    // address used to test the translator class against
    private String sampleAddress;
    // lat/ln coordinates based on the sample address (found manually using Google Maps)
    private LatLng sampleCoordinates;
    private double marginForError;

    @Before
    public void setup() {
        bogusAddress = "i don't exist haha";
        sampleAddress = "B4 7UP";
        sampleCoordinates = new LatLng(52.485499,-1.890116);

        // Reasonable error margin for granular addresses
        // If the coordinates are within a reasonable range for a granular address, they should be for broad ones
        marginForError = 0.0001;
    }

    @Test
    public void returnsReasonablyAccurateCoordinates() {
        LatLng coordinates = LocationTranslator.addressToCoordinates(sampleAddress);

        double latDifference = Math.abs(coordinates.lat - sampleCoordinates.lat);
        double lngDifference = Math.abs(coordinates.lng - sampleCoordinates.lng);

        assert(latDifference < marginForError);
        assert(lngDifference < marginForError);
    }

    @Test
    public void returnsNullForBogusAddress() {
        assertNull(LocationTranslator.addressToCoordinates(bogusAddress));
    }

}
