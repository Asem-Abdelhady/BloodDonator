package com.example.bloodonation1.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bloodonation1.Models.LocalRequestModel;
import com.example.bloodonation1.R;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.NoteHolder> {
    private List<LocalRequestModel> requestes = new ArrayList<>();

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.request_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        LocalRequestModel currentRequest = requestes.get(position);
        holder.age.setText(currentRequest.getAge());
        holder.phone.setText(currentRequest.getPhone());
        holder.bloodType.setText(String.valueOf(currentRequest.getBloodType()));
        holder.name.setText(currentRequest.getName());
        holder.date.setText(currentRequest.getDate());
    }

    @Override
    public int getItemCount() {
        return requestes.size();
    }

    public void setRequestes(List<LocalRequestModel> requestes) {
        this.requestes = requestes;
        notifyDataSetChanged();
    }

    public LocalRequestModel getRequestAt(int position) {
        return requestes.get(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView age;
        private TextView phone;
        private TextView bloodType;
        private TextView name;
        private TextView date;

        public NoteHolder(View itemView) {
            super(itemView);
            age = itemView.findViewById(R.id.age);
            phone = itemView.findViewById(R.id.phone);
            bloodType = itemView.findViewById(R.id.bloodType);
            name =itemView.findViewById(R.id.name);
            date = itemView.findViewById(R.id.date);
        }
    }


}
