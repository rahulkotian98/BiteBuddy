package com.example.bitebuddy;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;


// This class represents a business entity, likely retrieved from the Yelp API
public class Business {
    private String name; // Name of the business
    @SerializedName("image_url") // Annotation to map this field to "image_url" in the Yelp API JSON
    private String imageUrl; // URL of the business's image
    private Location location; // Location object containing address details
    private double rating; // Rating of the business
    private String price; // Price level (e.g., "$$", "$$$")
    private List<Category> categories; // List of categories the business belongs to


    // ... (Getters for name, imageUrl, rating, categories)

    public char[] getRating() {
        return String.valueOf(rating).toCharArray();
    }

    public String getName() {
        return name;

    }

    public String getImageUrl() {
        return imageUrl;
    }


    // Formats the categories into a comma-separated string
    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    public String getCategoriesString() {
        if (categories == null || categories.isEmpty()) {
            return "N/A"; // Return "N/A" if no categories are available
        }
        return String.join(", ", categories.stream().map(Category::getTitle).toList());
    }

    // Inner class to represent a single category
    public static class Category {
        private String alias; // Alias (e.g., "pizza")
        private String title; // Title (e.g., "Pizza")

        // Getters (add more if needed from Yelp's API response)
        public String getTitle() {
            return title;
        }
    }

    // Inner class to represent the business location
    public static class Location {
        @SerializedName("address1") // Annotation to map "address1" in the API JSON
        private String address1; // Street address
        private String city; // City
        private String state; // State
        @SerializedName("zip_code") // Annotation for "zip_code" in the API JSON
        private String zipCode; // Zip code

        // Getters (REF: Yelp's API response)
        public String getAddress1() {
            return address1;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getZipCode() {
            return zipCode;
        }

    }

    // Helper method to get the full address in a formatted string
    public String getFormattedAddress() {
        if (location == null) return "N/A"; // Handle cases where the location is not available
        return String.format("%s, %s, %s %s",
                location.getAddress1(), location.getCity(), location.getState(), location.getZipCode());
    }
}
