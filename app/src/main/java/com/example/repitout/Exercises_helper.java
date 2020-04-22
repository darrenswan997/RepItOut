package com.example.repitout;

import java.util.Map;

public class Exercises_helper {
    public String name;
    public String reps;
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

    public Exercises_helper(String name, String reps, String sets, Map<String, String> exercises) {
        this.name = name;
        this.reps = reps;
        this.sets = sets;
        this.exerciseMap = exercises;
    }

    public Exercises_helper(String eName, Map<String, String> exerciseMap) {
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
