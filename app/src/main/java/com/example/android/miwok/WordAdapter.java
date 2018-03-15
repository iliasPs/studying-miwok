package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by vega on 28-Feb-18.
 * we created this class in order to have 2 textviews in the ArrayAdapter class
 */

public class WordAdapter extends ArrayAdapter<Word> { //we add the extend in order to inherit behavior from the arrayadapter. is is needed cause we will change the getView method
    private int mColorResourceId;



    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        // Check if the existing view is being reused, otherwise inflate a completely new one
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view, parent, false);
        }
        // Get the {@link Word} object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_view.xml layout with the ID text1
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.text1);

        //get the default word and set the text in the list_view.xml
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Find the TextView in the list_view.xml layout with the ID text2
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.text2);
        //get the translated word and set the text in the list_view.xml
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.basic);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        // Find the ImageView in the list_item.xml layout with the ID image.
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.list_view_icon);
        // Check if an image is provided for this word or not
        if (currentWord.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imageView.setImageResource(currentWord.getImageResourceId());
            // Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        } else {
            // Otherwise hide the ImageView (set visibility to GONE)
            imageView.setVisibility(View.GONE);
        }



        return listItemView;
    }
}
