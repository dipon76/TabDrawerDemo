package com.example.dipon.tabviewdemo.main.data;

import java.io.Serializable;

/**
 * Created by Dipon on 4/6/2017.
 */

public class ContactInfo implements Serializable {

    private String contactName;
    private String contactNumber;
    private String contactImage;
    private boolean isSelected;
    private String contactId;


    public ContactInfo() {
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactImage() {
        return contactImage;
    }

    public void setContactImage(String contactImage) {
        this.contactImage = contactImage;
    }
}
