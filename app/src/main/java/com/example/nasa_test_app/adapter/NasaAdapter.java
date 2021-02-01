package com.example.nasa_test_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nasa_test_app.R;
import com.example.nasa_test_app.data.CollectionNasa;
import com.example.nasa_test_app.data.Datum;
import com.example.nasa_test_app.data.Link;


import java.util.ArrayList;
import java.util.List;

public class NasaAdapter extends RecyclerView.Adapter<NasaAdapter.NasaViewHolder> {

    List<Datum> datumList = new ArrayList<>();

    public void setDatumList(List<Datum> datumList) {
        this.datumList = datumList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NasaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nasa_item, parent, false);
        return new NasaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NasaViewHolder holder, int position) {
        holder.bind(datumList.get(position));
    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }


    class NasaViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public NasaViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
        void bind(Datum datum) {
            textView.setText(datum.getTitle());
        }
    }
}
