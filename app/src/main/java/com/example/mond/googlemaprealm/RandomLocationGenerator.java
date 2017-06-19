package com.example.mond.googlemaprealm;

import com.example.mond.googlemaprealm.model.Marker;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RandomLocationGenerator {

    private Random mRandom;
    private ArrayList<Marker> mMarkers;
    private double mKmPerDegree;

    public List<Marker> generateRandomLocations(LatLng latLng, int radius, int count) {
        mKmPerDegree = getKmPerDegree(latLng);
        mMarkers = new ArrayList<>();
        for(int i = 0; i != count; i++ ){
            com.example.mond.googlemaprealm.model.Marker marker = new com.example.mond.googlemaprealm.model.Marker();
            marker.setIconType(1);
            marker.setTitle(String.valueOf(count));
            marker.setId(UUID.randomUUID().toString());
            LatLng randomLatLng = generateRandomLocation(latLng, radius);
            marker.setLatitude(randomLatLng.latitude);
            marker.setLongitude(randomLatLng.longitude);
            mMarkers.add(marker);
        }

        return mMarkers;
    }

    private LatLng generateRandomLocation(LatLng tapPosition, int radius){

        double radiusInDegrees = radius/mKmPerDegree;

        if(mRandom == null){
            mRandom = new Random();
        }

        double randomDegree = 360 * mRandom.nextDouble();
        double randomRadius = radiusInDegrees * mRandom.nextDouble();

        double lat = randomRadius * Math.cos(randomDegree);
        double lng = randomRadius * Math.sin(randomDegree);

        return new LatLng(tapPosition.latitude + lat,tapPosition.longitude + lng);
    }

    private double getKmPerDegree(LatLng currentPosition) {
        LatLng testPosition = new LatLng(currentPosition.latitude + 1, currentPosition.longitude + 1);
        return SphericalUtil.computeDistanceBetween(currentPosition, testPosition)/1000;
    }
}
