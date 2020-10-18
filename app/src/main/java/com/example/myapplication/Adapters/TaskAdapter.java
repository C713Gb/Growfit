package com.example.myapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.Tasks;
import com.example.myapplication.Models.Task;
import com.example.myapplication.R;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    ArrayList<Task> taskArrayList;
    Context mContext;
    String statCheck = "";

    Tasks tasks;

    public TaskAdapter(ArrayList<Task> taskArrayList, Context mContext) {
        this.taskArrayList = taskArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.text.setText(taskArrayList.get(position).getName());

        String stat = taskArrayList.get(position).getDone();
        if (stat.equals("yes")) {
            holder.text2.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(true);
        } else {
            holder.text2.setVisibility(View.GONE);
            holder.checkBox.setChecked(false);
        }

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    holder.checkBox.setChecked(false);
                    statCheck = taskArrayList.get(position).getName()+" remove_update";
                } else {
                    holder.checkBox.setChecked(true);
                    statCheck = taskArrayList.get(position).getName();
                }
                tasks.updateLayout(statCheck);
            }
        });

        holder.Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    holder.checkBox.setChecked(false);
                    statCheck = taskArrayList.get(position).getName()+" remove_update";
                } else {
                    holder.checkBox.setChecked(true);
                    statCheck = taskArrayList.get(position).getName();
                }
                tasks.updateLayout(statCheck);
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    holder.checkBox.setChecked(true);
                    statCheck = taskArrayList.get(position).getName();
                } else {
                    holder.checkBox.setChecked(false);
                    statCheck = taskArrayList.get(position).getName()+" remove_update";
                }
                tasks.updateLayout(statCheck);
            }
        });



    }

    @Override
    public int getItemCount() {
        return taskArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView text, text2;
        CheckBox checkBox;
        RelativeLayout Layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tasks = (Tasks) mContext;
            text = itemView.findViewById(R.id.task_txt);
            text2 = itemView.findViewById(R.id.task_stat);
            checkBox = itemView.findViewById(R.id.check_task);
            Layout = itemView.findViewById(R.id.task_linear);


        }
    }
}
