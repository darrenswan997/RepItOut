package com.example.repitout;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ProductViewHolder> {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    String userID = firebaseUser.getUid();
    CreateRoutines createRoutines = new CreateRoutines();
    String s, name;
    public CreateRoutines getCreateRoutines() {
        s = createRoutines.routine;
        return createRoutines;
    }
    public String routine;
    private Context mCtx;
    public String dateOnBind;
    private List<routines_helper> routineList;
    public ItemClickListener itemClickListener;


    public RoutineAdapter(Context mCtx, List<routines_helper> routineList) {
        this.mCtx = mCtx;
        this.routineList = routineList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.recyclerroutines, parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        routines_helper routines_helper_obj = routineList.get(position);
        holder.routine_name.setText(routines_helper_obj.name);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                 routine = routineList.get(pos).getName();
                 name=routine;
                /*Intent intent2 = new Intent(mCtx,Exercises_for_routines.class);
                intent2.putExtra("Day", routine);*/
                Intent intent = new Intent(mCtx,RecordWorkout.class);
                intent.putExtra("Routine Name ", routine);

                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routineList.size();
    }



    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView routine_name, date;
        public ItemClickListener itemClickListener;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            routine_name = itemView.findViewById(R.id.routineNameCardVTv);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v, getLayoutPosition());
        }
        public void setItemClickListener(ItemClickListener ic){
            this.itemClickListener = ic;
        }



    }


}
