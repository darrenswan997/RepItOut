package com.example.repitout;

import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

public class Exercises_helper {
    public String name;
    public String reps;
    public HashMap<String, Object> timestamp;
    public Long creationDate;
    public Object time;

    public Long getCreationDate() {
        return creationDate;
    }

    public Exercises_helper(String name, String reps) {
        this.name = name;
        this.reps = reps;
        this.time = ServerValue.TIMESTAMP;
    }

    public String sets;
    public Map<String,String> exerciseMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getExercises() {
        return exerciseMap;
    }

    public void setExercises(Map<String, String> exerciseMap) {
        this.exerciseMap = exerciseMap;
    }

    public Exercises_helper(String name) {
        this.name = name;
    }

    public Exercises_helper(String name, String reps, HashMap<String, Object> timestamp) {
        this.name = name;
        this.reps = reps;
        this.timestamp = timestamp;
    }

    public Exercises_helper() {
    }



    public Exercises_helper(String name, String reps, String sets) {
        this.name = name;
        this.reps = reps;
        this.sets = sets;
    }



    public String getReps() {
        return reps;
    }

    public void setReps(String reps) {
        this.reps = reps;
    }

    public String getSets() {
        return sets;
    }

    public void setSets(String sets) {
        this.sets = sets;
    }
}
