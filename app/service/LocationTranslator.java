package service;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.typesafe.config.ConfigFactory;
import twitter4j.GeoLocation;

public class LocationTranslator {

    private static final String GOOGLE_API_KEY = ConfigFactory.load().getString("GOOGLE_MAPS_API_KEY");
    private static final GeoApiContext apiContext = buildGoogleAPIContext();

    public static void main(String[] args) {
        addressToCoordinates("diafoijff");
    }

    public static LatLng addressToCoordinates(String location) {
        LatLng coordinates = getCoordinatesFromAPI(location);
        return coordinates;
    }

    private static LatLng getCoordinatesFromAPI(String location) {
        try {
            GeocodingResult[] results = GeocodingApi.geocode(apiContext, location).await();

            // means address (according to the API) doesn't exist
            if (results.length == 0) return null;
            return results[0].geometry.location;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static GeoApiContext buildGoogleAPIContext() {
        GeoApiContext.Builder builder = new GeoApiContext.Builder();
        builder.apiKey(GOOGLE_API_KEY);
        return builder.build();
    }
}
