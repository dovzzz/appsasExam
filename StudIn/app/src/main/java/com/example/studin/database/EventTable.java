package com.example.studin.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class EventTable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "course_name")
    private String courseName;

    @NonNull
    @ColumnInfo(name = "exam_name")
    private String examName;

    @ColumnInfo(name = "exam_desc")
    private String examDesc;

    @NonNull
    @ColumnInfo(name = "exam_date")
    private String examDate;

    @ColumnInfo(name = "first_retake_date")
    private String firstRetakeDate;

    @NonNull
    @ColumnInfo(name = "exam_time")
    private String examTime;

    @ColumnInfo(name = "first_retake_time")
    private String firstRetakeTime;

    @ColumnInfo(name = "sources")
    private String sources;

    public EventTable() {
    }

    public EventTable(String cname, String ename, String examd, String date0, String date1,
                      String time0, String time1, String source) {
        this.courseName = cname;
        this.examName = ename;
        this.examDesc = examd;
        this.examDate = date0;
        this.firstRetakeDate = date1;
        this.examTime = time0;
        this.firstRetakeTime = time1;
        this.sources = source;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setCourseName(@NonNull String name) {
        this.courseName = name;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setExamName(@NonNull String name) {
        this.examName = name;
    }

    public String getExamName() {
        return this.examName;
    }

    public void setExamDesc(String description) {
        this.examDesc = description;
    }

    public String getExamDesc() {
        return this.examDesc;
    }

    public void setExamDate(@NonNull String date) {
        this.examDate = date;
    }

    public String getExamDate() {
        return this.examDate;
    }

    public void setFirstRetakeDate(String date) {
        this.firstRetakeDate = date;
    }

    public String getFirstRetakeDate() {
        return this.firstRetakeDate;
    }

    public void setExamTime(@NonNull String time) {
        this.examTime = time;
    }

    public String getExamTime() {
        return this.examTime;
    }

    public void setFirstRetakeTime(String time) {
        this.firstRetakeTime = time;
    }

    public String getFirstRetakeTime() {
        return this.firstRetakeTime;
    }

    public void setSources(String sources) {
        this.sources = sources;
    }

    public String getSources() {
        return this.sources;
    }

    public String getStringMain() {
        return this.courseName + " " + this.examName + " \n" +
                this.examDate + " " + this.examTime + " " + this.firstRetakeDate + " " +
                this.firstRetakeTime;
    }

    public String getStringAll() {
        return this.id + " " + this.courseName + " " + this.examName + " " + this.examDesc + " \n" +
                this.examDate + " " + this.examTime + " " + this.firstRetakeDate + " " +
                this.firstRetakeTime + " " + this.sources;
    }

}
