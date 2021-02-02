package com.example.nasa_test_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nasa_test_app.R;
import com.example.nasa_test_app.data.Link;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NasaAdapter extends RecyclerView.Adapter<NasaAdapter.NasaViewHolder> {

    private List<Link> linkList = new ArrayList<>();

    private OnImageClickListener onImageClickListener;

    public void setLinkList(List<Link> linkList) {
        this.linkList = linkList;
        notifyDataSetChanged();
    }

    public List<Link> getLinkList() {
        return linkList;
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    @NonNull
    @Override
    public NasaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nasa_item, parent, false);
        return new NasaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NasaViewHolder holder, int position) {
        Link link = linkList.get(position);
        Picasso.get().load(link.getHref()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    class NasaViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;

        public NasaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onImageClickListener != null) {
                        onImageClickListener.onImageClick(getAdapterPosition());
                    }
                }
            });
        }

    }
}
