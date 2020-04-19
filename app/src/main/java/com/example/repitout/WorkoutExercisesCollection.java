package com.example.repitout;

import java.io.Serializable;
import java.util.ArrayList;

public class WorkoutExercisesCollection implements Serializable {

    private ArrayList<String> exercisess;

    public ArrayList<String> getExercisess() {
        return exercisess;
    }

    public void setExercisess(ArrayList<String> exercisess) {
        this.exercisess = exercisess;
    }
}
