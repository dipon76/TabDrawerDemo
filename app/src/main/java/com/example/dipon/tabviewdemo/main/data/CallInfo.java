package com.example.dipon.tabviewdemo.main.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Dipon on 5/16/2017.
 */

public class CallInfo implements Serializable{
    private int call_id;
    private String callerName;
    private String callerNumber;
    private String callDuration;
    private String callDate;
    private String callType;
    private String callerImage;


    public CallInfo() {
    }

    public CallInfo(int call_id, String callerName, String callerNumber, String callDuration, String callDate, String callerImage) {
        this.call_id = call_id;
        this.callerName = callerName;
        this.callerNumber = callerNumber;
        this.callDuration = callDuration;
        this.callDate = callDate;
        this.callerImage = callerImage;
    }

    public int getCall_id() {
        return call_id;
    }

    public void setCall_id(int call_id) {
        this.call_id = call_id;
    }

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public String getCallerNumber() {
        return callerNumber;
    }

    public void setCallerNumber(String callerNumber) {
        this.callerNumber = callerNumber;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getCallerImage() {
        return callerImage;
    }

    public void setCallerImage(String callerImage) {
        this.callerImage = callerImage;
    }
}
