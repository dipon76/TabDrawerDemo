package com.example.dipon.tabviewdemo.main.data;

import android.net.Uri;

/**
 * @author Dipon
 *         on 5/18/2017.
 */

public class ImageItem {

    private String imageUri;
    private String imageTitle;

    public ImageItem(String imageUri, String imageTitle) {
        this.imageUri = imageUri;
        this.imageTitle = imageTitle;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }
}
