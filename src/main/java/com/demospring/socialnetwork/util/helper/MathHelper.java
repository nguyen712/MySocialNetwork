package com.demospring.socialnetwork.util.helper;

public class MathHelper {
    /**
     * Calculate Distance between two poin in earth
     * @param lat1 latitude of point 1
     * @param lon1 longitude of point 1
     * @param lat2 latitude of point 2
     * @param lon2 longitude of point 2
     * @return double distance of two point
     * */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double r = 6371;
        return r * c;
    }

}
