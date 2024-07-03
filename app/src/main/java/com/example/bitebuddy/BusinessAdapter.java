package com.example.bitebuddy;

// Android UI components and utilities
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

// Glide for image loading (alternative to Picasso)
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

// RecyclerView components
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

// Other utilities
import java.util.ArrayList;
import java.util.List;

// import com.squareup.picasso.Picasso;

// Main Adapter class to manage business data in a RecyclerView
public class BusinessAdapter extends RecyclerView.Adapter<BusinessAdapter.ViewHolder> {

    // Member variables to hold the context and the list of businesses
    private Context context;
    private List<Business> businesses = new ArrayList<>();

    // Constructor: initializes the adapter with the context
    public BusinessAdapter(Context context) {
        this.context = context;
    }


    // Method to update the list of businesses and refresh the RecyclerView
    public void updateBusinesses(List<Business> newBusinesses) {
        businesses.clear(); // Clear the existing list
        businesses.addAll(newBusinesses); // Add the new businesses
        notifyDataSetChanged(); // Tell the RecyclerView to update itself
    }


    // Creates a ViewHolder for each business item in the RecyclerView
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.business_item, parent, false); // Inflate the layout for a single item
        return new ViewHolder(itemView); // Create and return a ViewHolder for this item
    }

    // Binds data (from the 'Business' object) to the views in the ViewHolder
    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Business business = businesses.get(position); // Get the business at the current position

        // Set text values for the business name, rating, and categories
        holder.businessNameTextView.setText(business.getName());
        holder.businessRatingTextView.setText("Rating: " + String.valueOf(business.getRating()));
        holder.businessCategoriesTextView.setText("Categories: " + business.getCategoriesString());

        // Logging for debugging (check the logcat for messages)
        Log.d("BusinessAdapter", " Image URL: " + business.getImageUrl());


        // Load image with Picasso
//        if (business.getImageUrl() != null) {
//            Picasso.get()
//                    .load(business.getImageUrl())
//                    .placeholder(R.drawable.ic_launcher_background) // Replace with a suitable placeholder
//                    .error(R.drawable.image_not_found)       // Replace with a suitable error image
//                    .into(holder.businessImageView);
//        } else {
//            // Set a default image if the URL is null
//            holder.businessImageView.setImageResource(R.drawable.image_not_found); // Replace with a suitable default image
//        }
        // Load image using Glide, handle errors, and set a placeholder
        Glide.with(context)
                .load(business.getImageUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.businessImageView.setImageResource(R.drawable.image_not_found);
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.businessImageView);
    }


    // Returns the total number of businesses in the list
    @Override
    public int getItemCount() {
        return businesses.size();
    }

    // Inner class that defines the ViewHolder for a single business item
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView businessImageView; // ImageView for the business image
        TextView businessNameTextView; // TextView for the business name
        TextView businessRatingTextView; // TextView for the business rating
        TextView businessCategoriesTextView; // TextView for the business categories
        TextView locationTextView; // TextView for the business location (commented out in your code)

        // (code to initialize the view references)
        ViewHolder(View itemView) {
            super(itemView);
            businessImageView = itemView.findViewById(R.id.businessImage);
            businessNameTextView = itemView.findViewById(R.id.businessName);
            businessRatingTextView = itemView.findViewById(R.id.businessRating);
            businessCategoriesTextView = itemView.findViewById(R.id.businessCategories);
            locationTextView = itemView.findViewById(R.id.location); // Binding locationTextView
        }
    }
}
