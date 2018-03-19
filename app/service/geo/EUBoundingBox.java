package service.geo;

public class EUBoundingBox extends BoundingBox {

    public EUBoundingBox() {
        // this bounding box also covers the UK
        super(34.5, -31.5, 82.2, 74.4);
    }
}
