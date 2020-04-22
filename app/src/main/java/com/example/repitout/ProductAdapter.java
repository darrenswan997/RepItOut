package com.example.repitout;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private Context mCtx;
    public String dateOnBind;
    private List<routines_helper> routineList;
    public ItemClickListener itemClickListener;

    public ProductAdapter(Context mCtx, List<routines_helper> routineList) {
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
        holder.routine_name.setText("Routine Name : " + "\t"+routines_helper_obj.name);
        dateOnBind = ("" + (routines_helper_obj.getDay()) + "/" +(routines_helper_obj.getMonth()) + "/" +(routines_helper_obj.getYear()));
        holder.date.setText("Date Created : "+ dateOnBind);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                String name = routineList.get(pos).getName();
                String day = routineList.get(pos).getDay();
                String month = routineList.get(pos).getMonth();
                String year = routineList.get(pos).getYear();
                String date = ("" + day + "/" + month + "/" + year);

                Intent intent = new Intent(mCtx,RecordWorkout.class);
                intent.putExtra("Routine Name", name);
                intent.putExtra("date",date);
                mCtx.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routineList.size();
    }



    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView routine_name, date;
        public ItemClickListener itemClickListener;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            routine_name = itemView.findViewById(R.id.routineNameCardVTv);
            date = itemView.findViewById(R.id.displayDateTV);
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
