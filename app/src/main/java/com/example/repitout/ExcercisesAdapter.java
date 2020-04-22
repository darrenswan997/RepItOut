package com.example.repitout;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExcercisesAdapter extends RecyclerView.Adapter<ExcercisesAdapter.ExercisesViewHolder>{

    private Context mCtx;
    private List<Exercises_helper> exercisesList;
    public ItemClickListener itemClickListener;


    String name;//should be equal to exercise name
    int numberOfSets = 1;
    int numberOfReps = 0;

    public ExcercisesAdapter(Context mCtx, List<Exercises_helper> exercisesList) {
        this.mCtx = mCtx;
        this.exercisesList = exercisesList;
    }

    @NonNull
    @Override
    public ExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recyclerexercises,null);
        return new ExercisesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExercisesViewHolder holder, int position) {
        Exercises_helper exercises_helper = exercisesList.get(position);

        holder.exercise_name.setText(exercises_helper.getName());
        holder.numOfSets.setText(String.valueOf(numberOfSets));
        holder.numOfReps.setText(String.valueOf(numberOfReps));
        holder.countRepsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, RepHandler.class);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    public class ExercisesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView exercise_name, setsTV, numOfSets, repsTV, numOfReps;
        Button countRepsBtn;

        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);

            exercise_name = itemView.findViewById(R.id.excTV);
            setsTV = itemView.findViewById(R.id.setSetsTV);
            numOfSets = itemView.findViewById(R.id.TVsetsNumber);
            repsTV = itemView.findViewById(R.id.setRepsTV);
            numOfReps = itemView.findViewById(R.id.TVRepsNumber);
            countRepsBtn = itemView.findViewById(R.id.countReps_Btn);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
