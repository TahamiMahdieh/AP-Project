package org.common;

import java.sql.Date;
import java.util.ArrayList;

public class Education {
    private String email;
    private String schoolName;
    private String fieldOfStudy;
    private Date startDate;
    private Date endDate;
    private double grade;
    private String activitiesAndSocieties;
    private String description;
    private ArrayList<String> skills;
    private boolean notifyNetwork;

    public Education(String email, String schoolName, String fieldOfStudy, Date startDate, Date endDate, double grade, String activitiesAndSocieties, String description, ArrayList<String> skills, boolean notifyNetwork) {
        this.email = email;
        this.schoolName = schoolName;
        this.fieldOfStudy = fieldOfStudy;
        this.startDate = startDate;
        this.endDate = endDate;
        this.grade = grade;
        this.activitiesAndSocieties = activitiesAndSocieties;
        this.description = description;
        this.skills = skills;
        this.notifyNetwork = notifyNetwork;
    }

    @Override
    public String toString() {
        return fieldOfStudy + " at " + schoolName;
    }

    public String getEmail() {
        return email;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getGrade() {
        return grade;
    }

    public String getActivitiesAndSocieties() {
        return activitiesAndSocieties;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getSkills() {
        return skills;
    }

    public boolean isNotifyNetwork() {
        return notifyNetwork;
    }
}
