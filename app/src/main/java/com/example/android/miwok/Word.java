package com.example.android.miwok;

/**
 * Created by vega on 28-Feb-18.
 */

public class Word {

    private String mDefaultTranslation; //private member variable of the class
    private String mMiwokTranslation; //private member variable of the class

    private int mSound;
    /** Image resource ID for the word */
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;

    public Word(String defaultTranslation, String miwokTranslation, int number) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mSound = number;
    }

    public Word(String defaultTranslation, String miwokTranslation, int number, int number2){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = number;
        mSound = number2;

    }
    //get the default translation of the word
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }
    //get the miwok translation of the word
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    public int getSound() {
        return mSound;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public int getImageResourceId() {
        return mImageResourceId;


    }
}
