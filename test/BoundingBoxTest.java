import org.junit.Before;
import org.junit.Test;
import service.geo.BoundingBox;
import service.geo.EUBoundingBox;
import service.geo.UKBoundingBox;

import static org.junit.Assert.assertFalse;

public class BoundingBoxTest {

    private final double birminghamLat = 52.4862;
    private final double birminghamLng = 1.8904;

    private final double parisLat = 48.8566;
    private final double parisLng = 2.3522;

    private final double sanFranciscoLat = 37.7749;
    private final double sanFranciscoLng = 122.4194;

    private BoundingBox ukBoundingBox;
    private BoundingBox euBoundingBox;


    @Before
    public void setUp() {
        euBoundingBox = new EUBoundingBox();
        ukBoundingBox = new UKBoundingBox();
    }

    @Test
    public void ukContainsBirmingham() {
        assert(ukBoundingBox.contains(birminghamLat, birminghamLng));
    }

    @Test
    public void ukContainsParis() {
        assertFalse(ukBoundingBox.contains(parisLat, parisLng));
    }

    @Test
    public void euContainsParis() {
        assert(euBoundingBox.contains(parisLat, parisLng));
    }

    @Test
    public void euContainsSanFrancisco() {
        assertFalse(euBoundingBox.contains(sanFranciscoLat, sanFranciscoLng));
    }
}
