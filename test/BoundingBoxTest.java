import org.junit.Before;
import org.junit.Test;
import service.geo.*;

import static org.junit.Assert.assertFalse;

public class BoundingBoxTest {

    private final double birminghamLat = 52.4862;
    private final double birminghamLng = 1.8904;

    private final double parisLat = 48.8566;
    private final double parisLng = 2.3522;

    private final double sanFranciscoLat = 37.773972;
    private final double sanFranciscoLng = -122.431297;

    private final double quebecLat = 46.829853;
    private final double quebecLng = -71.254028;

    private BoundingBox ukBoundingBox;
    private BoundingBox euBoundingBox;
    private BoundingBox usCanadaBoundingBox;

    @Before
    public void setUp() {
        euBoundingBox = new EUBoundingBox();
        ukBoundingBox = new UKBoundingBox();
        usCanadaBoundingBox = new USCanadaBoundingBox();
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

    @Test
    public void usCanadaContainsQuebec() {
        assert(usCanadaBoundingBox.contains(quebecLat, quebecLng));
    }

    @Test
    public void usCanadaContainsSanFrancisco() {
        assert(usCanadaBoundingBox.contains(sanFranciscoLat, sanFranciscoLng));
    }

    @Test
    public void usCanadaContainsParis() {
        assertFalse(usCanadaBoundingBox.contains(parisLat, parisLng));
    }
}
