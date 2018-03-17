import org.junit.Before;
import org.junit.Test;
import service.BoundingBox;
import service.UKBoundingBox;

import static org.junit.Assert.assertFalse;

public class BoundingBoxTest {

    private final double birminghamLat = 52.4862;
    private final double birminghamLng = 1.8904;

    private final double parisLat = 48.8566;
    private final double parisLng = 2.3522;

    BoundingBox ukBoundingBox;

    @Before
    public void setUp() {
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
}
