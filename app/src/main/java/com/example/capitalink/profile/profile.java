package com.example.capitalink.profile;

public class profile {

    private String ListItem;
    private int mImage=NO_IMAGE_PROVIDED;
    public static  final int NO_IMAGE_PROVIDED=-1;

    public profile(String listItem, int mImage) {
        ListItem = listItem;
        this.mImage = mImage;
    }

    public String getListItem() {
        return ListItem;
    }

    public int getmImage() {
        return mImage;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return mImage != NO_IMAGE_PROVIDED;
    }
}
