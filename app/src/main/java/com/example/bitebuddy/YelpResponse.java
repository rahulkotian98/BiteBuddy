package com.example.bitebuddy;

import java.util.List;

/**
 * Represents the response received from the Yelp API's business search endpoint.
 */
public class YelpResponse {

    /**
     * A list of Business objects representing the businesses found in the search.
     */
    public List<Business> businesses;  // Public for easy access from other classes

    /**
     * The total number of businesses matching the search criteria (may be larger than the number of businesses in the 'businesses' list).
     */

}
