
package com.astix.allanasosfa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TblActualVsTargetReport {

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
        
    }

    public Integer getTodayTarget() {
        return todayTarget;
    }

    public void setTodayTarget(Integer todayTarget) {
        this.todayTarget = todayTarget;
        
    }

    public Integer getTodayAchieved() {
        return todayAchieved;
    }

    public void setTodayAchieved(Integer todayAchieved) {
        this.todayAchieved = todayAchieved;
        
    }

    public Float getTodayBal() {
        return todayBal;
    }

    public void setTodayBal(Float todayBal) {
        this.todayBal = todayBal;
        
    }

    public Integer getTodayflg() {
        return todayflg;
    }

    public void setTodayflg(Integer todayflg) {
        this.todayflg = todayflg;
        
    }

    public Float getMonthTarget() {
        return monthTarget;
    }

    public void setMonthTarget(Float monthTarget) {
        this.monthTarget = monthTarget;
        
    }

    public Float getMonthAchieved() {
        return monthAchieved;
    }

    public void setMonthAchieved(Float monthAchieved) {
        this.monthAchieved = monthAchieved;
        
    }

    public Float getMonthBal() {
        return monthBal;
    }

    public void setMonthBal(Float monthBal) {
        this.monthBal = monthBal;
        
    }

    public Float getMonthflg() {
        return monthflg;
    }

    public void setMonthflg(Float monthflg) {
        this.monthflg = monthflg;
        
    }

    @SerializedName("Descr")
    @Expose
    private String descr;

    @SerializedName("TodayTarget")
    @Expose
    private Integer todayTarget;


    @SerializedName("TodayAchieved")
    @Expose
    private Integer todayAchieved;

    @SerializedName("TodayBal")
    @Expose
    private Float todayBal;

    @SerializedName("Todayflg")
    @Expose
    private Integer todayflg;

    @SerializedName("MonthTarget")
    @Expose
    private Float monthTarget;

    @SerializedName("MonthAchieved")
    @Expose
    private Float monthAchieved;

    @SerializedName("MonthBal")
    @Expose
    private Float monthBal;

    @SerializedName("Monthflg")
    @Expose
    private Float monthflg;

   
}
