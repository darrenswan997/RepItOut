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

public class ExcercisesAdapter extends RecyclerView.Adapter<ExcercisesAdapter.ExercisesViewHolder>{

    private Context mCtx;
    private List<Exercises_helper> exercisesList;
    private List<routines_helper> routinesList;
    public ItemClickListener itemClickListener;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    String userID = firebaseUser.getUid();
    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("workout");
    DatabaseReference dbr = db.child(userID).child("Routines");
    DatabaseReference dbRemove;
    String  r;



    String name;//should be equal to exercise name
    int numberOfSets = 0;
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
        String name = exercises_helper.getName();
        holder.exercise_name.setText(name);
        //holder.numOfSets.getText().toString();
        //holder.numOfReps.setText(String.valueOf(numberOfReps));
        holder.addRepsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String setsval = holder.numOfSets.getText().toString();
                int setv = Integer.parseInt(setsval);
                String repsval = holder.numOfReps.getText().toString();
                int repsv = Integer.parseInt(repsval);
                int total = (setv * repsv);
                String r = String.valueOf(total);
                holder.totalNumOfReps.setText(r);*/
                Intent intent = new Intent(mCtx, RepHandler.class);
                intent.putExtra("Exercise", name);
                mCtx.startActivity(intent);
            }
        });
        /*holder.countReps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send reps to watch
            }
        });*/

    }
    public void removeAt(int pos){
        exercisesList.remove(pos);
        notifyItemRemoved(pos);
        notifyItemRangeChanged(pos, exercisesList.size());
    }

    @Override
    public int getItemCount() {
        return exercisesList.size();
    }

    public class ExercisesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView exercise_name, setsTV, repsTV, totalreptv, totalNumOfReps, repsRec, repRecNum;
        EditText  numOfSets, numOfReps;
        Button addRepsBtn, countReps;

        public ExercisesViewHolder(@NonNull View itemView) {
            super(itemView);

            //totalreptv = itemView.findViewById(R.id.totalRepsTV);
            //totalNumOfReps = itemView.findViewById(R.id.totalReps);
            exercise_name = itemView.findViewById(R.id.excTV);
            //setsTV = itemView.findViewById(R.id.setSetsTV);
           // numOfSets = itemView.findViewById(R.id.etSetsNumber);
          //  repsTV = itemView.findViewById(R.id.setRepsTV);
           // numOfReps = itemView.findViewById(R.id.etRepsNumber);
            addRepsBtn = itemView.findViewById(R.id.countReps_Btn);
           // countReps = itemView.findViewById(R.id.remove_Btn);
           // repsRec = itemView.findViewById(R.id.RepsRecievedTV);
          //  repRecNum = itemView.findViewById(R.id.RepsRecievedNum);


        }

        @Override
        public void onClick(View v) {

        }
    }
}
