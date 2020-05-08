package com.example.repitout;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcercisesAdapter extends RecyclerView.Adapter<ExcercisesAdapter.ExercisesViewHolder>{

    private Context mCtx;
    private List<Exercises_helper> exercisesList;
    public ItemClickListener itemClickListener;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    String userID = firebaseUser.getUid();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("workout");
    DatabaseReference dbr = db.child(userID).child("Routines");
    DatabaseReference dbRemove;
    String  r;


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
        String name = exercises_helper.getName();
        String reps = exercises_helper.getReps();
        holder.exercise_name.setText(name);
        holder.prevReps.setText(reps);
        holder.addRepsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, RepHandler.class);
                intent.putExtra("Exercise", name);
                mCtx.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    public class ExercisesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView exercise_name , prevReps;
        Button addRepsBtn;

        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);

            prevReps = itemView.findViewById(R.id.PrevRepsVal);
            exercise_name = itemView.findViewById(R.id.excTV);
            addRepsBtn = itemView.findViewById(R.id.countReps_Btn);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
