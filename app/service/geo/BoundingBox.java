package service.geo;

public abstract class BoundingBox {

    private double minLat;
    private double minLong;

    private double maxLat;
    private double maxLong;

    public BoundingBox(double minLat, double minLong, double maxLat, double maxLong) {
        this.minLat = minLat;
        this.minLong = minLong;
        this.maxLat = maxLat;
        this.maxLong = maxLong;
    }

    public boolean contains(double lat, double lng) {
        boolean greaterThanMin = (lat >= minLat && lng >= minLong);
        boolean lessThanMax = (lat <= maxLat && lng <= maxLong);
        return greaterThanMin && lessThanMax;
    }
}