package com.spnsolo.util;

public class CalculatorCoordinates {
    private static final int earthRadius = 6371;//Kilometres

    public static double distanceBetweenCoordinates(double lat1,
                                                    double lat2,
                                                    double lon1,
                                                    double lon2)
    {


        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double differenceLon = lon2 - lon1;
        double differenceLat = lat2 - lat1;
        double a = Math.pow(Math.sin(differenceLat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(differenceLon / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));

        return(c * earthRadius);
    }
}
