package com.hrm.vishwesh.hrms;

/**
 * Created by vishwesh on 2/1/18.
 */

public class Employee {
    String firstName;
    String lastName;
    String remaining_sick;

    String remaining_casual;
    String getRemaining_privillages;

    public String getRemaining_casual() {
        return remaining_casual;
    }

    public void setRemaining_casual(String remaining_casual) {
        this.remaining_casual = remaining_casual;
    }

    public String getGetRemaining_privillages() {
        return getRemaining_privillages;
    }

    public void setGetRemaining_privillages(String getRemaining_privillages) {
        this.getRemaining_privillages = getRemaining_privillages;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRemaining_sick() {
        return remaining_sick;
    }

    public void setRemaining_sick(String remaining_sick) {
        this.remaining_sick = remaining_sick;
    }
}
