package com.example.bitebuddy;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // UI elements
    private EditText searchEditText;     // Input for search term
    private Button searchButton;         // Triggers the Yelp search
    private RecyclerView recyclerView;   // Displays the search results
    private YelpApi yelpApi;            // Interface for Yelp API calls

    private BusinessAdapter businessAdapter; // Adapter for RecyclerView data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Set the layout

        // Initialize UI elements
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        recyclerView = findViewById(R.id.recyclerView); // Correct initialization of recyclerView

        // Setup RecyclerView
        businessAdapter = new BusinessAdapter(this);
        recyclerView.setAdapter(businessAdapter); // Now use the initialized recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrofit setup (once only)
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.yelp.com/v3/") // Base URL for Yelp API
                .addConverterFactory(GsonConverterFactory.create()) // Parse JSON responses
                .build();

        yelpApi = retrofit.create(YelpApi.class); // Create API interface instance

        // Search button click listener
        searchButton.setOnClickListener(view -> {
            String term = searchEditText.getText().toString();
            EditText locationEditText = findViewById(R.id.locationEditText);
            String location = locationEditText.getText().toString();

            // Retrieve API key from resources
            String apiKey = getString(R.string.yelp_api_key);
            String authorization = "Bearer " + apiKey;

            // Make Yelp API call
            Call<YelpResponse> call = yelpApi.searchBusinesses(authorization, term, location, 10);
            call.enqueue(new Callback<YelpResponse>() {
                @Override
                public void onResponse(Call<YelpResponse> call, Response<YelpResponse> response) {
                    if (response.isSuccessful()) { // Check for successful response
                        YelpResponse yelpResponse = response.body();
                        if (yelpResponse != null && yelpResponse.businesses != null) {
                            // Update RecyclerView with the received business data
                            businessAdapter.updateBusinesses(yelpResponse.businesses);
                        } else {
                            handleError("No businesses found."); // Handle empty response
                        }
                    } else {
                        // Handle API errors
                        handleError("API Error: " + response.code() + " - " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<YelpResponse> call, Throwable t) {
                    // Handle network errors
                    handleError("Network Error: " + t.getMessage());
                }
            });
        });
    }

    // Helper function to display error messages via Toast
    private void handleError(String errorMessage) {
        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
