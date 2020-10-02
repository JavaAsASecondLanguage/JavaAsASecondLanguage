package io.github.javaasasecondlanguage.lecture03.practice4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


public class LocationService {
    private final List<Place> places;

    public LocationService(List<Place> places) {
        this.places = places;
    }

    public Optional<Place> findClosestByTag(Location userLocation, String tag) {
        try {
            Optional<Place> result;
            Place resultingPlace = places.stream()
                    .filter((x -> x.tags().contains(tag)))
                    .min(Comparator.comparing(x -> x.location().distanceTo(userLocation)))
                    .get();
            return Optional.ofNullable(resultingPlace);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    public String mostCommonTag() {
        var tagList = places.stream()
                .map(x -> x.tags())
                .collect(Collectors.toList());
        List<String> tagsRavel = new ArrayList<>();
        for (List<String> tags : tagList) {
            tagsRavel.addAll(tags);
        }
        return tagsRavel
            .stream()
            .max(Comparator.comparing(x -> Collections.frequency(tagsRavel, x)))
            .get();
    }
}

record Place(
        String name,
        Location location,
        List<String> tags
) {}

record Location(double lat, double lon) {
    // Thanks gosh we have internet!
    public double distanceTo(Location other) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(other.lat - lat);
        double lonDistance = Math.toRadians(other.lon - this.lon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.lat)) * Math.cos(Math.toRadians(other.lat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        return Math.abs(distance);
    }
}
