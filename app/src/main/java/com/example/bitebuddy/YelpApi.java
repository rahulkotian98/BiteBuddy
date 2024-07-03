package com.example.bitebuddy;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface YelpApi {
//
//    /**
//    *
//     * Defines the API endpoint for searching Yelp businesses.
//     *
//     * @param authorization  The Yelp API authorization header (e.g., "Bearer YOUR_API_KEY").
//     * @param term           The search term (e.g., "restaurants", "pizza").
//     * @param location       The location to search around (e.g., "San Francisco").
//     * @param limit          The maximum number of results to return.
//     * @return A Call object that will asynchronously execute the request and return a `YelpResponse` object.
//     */

    @GET("businesses/search")  // Specifies the HTTP method (GET) and the relative path of the endpoint
    Call<YelpResponse> searchBusinesses(
            @Header("Authorization") String authorization, // Adds the Authorization header to the request
            @Query("term") String term,                     // Adds the 'term' query parameter to the request URL
            @Query("location") String location,             // Adds the 'location' query parameter
            @Query("limit") int limit                       // Adds the 'limit' query parameter
    );

}
