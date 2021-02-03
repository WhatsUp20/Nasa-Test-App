package com.example.nasa_test_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nasa_test_app.R;
import com.example.nasa_test_app.data.Datum;
import com.example.nasa_test_app.data.Link;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NasaSpaceAdapter extends RecyclerView.Adapter<NasaSpaceAdapter.NasaViewHolder> {

    private List<Link> linkList = new ArrayList<>();
    private List<Datum> datumList = new ArrayList<>();

    private OnImageClickListener onImageClickListener;

    public void setLinkList(List<Link> linkList) {
        this.linkList = linkList;
        notifyDataSetChanged();
    }

    public List<Datum> getDatumList() {
        return datumList;
    }

    public void setDatumList(List<Datum> datumList) {
        this.datumList = datumList;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nasa_space_item, parent, false);
        return new NasaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NasaViewHolder holder, int position) {
        Link link = linkList.get(position);
        Datum datum = datumList.get(position);
        holder.textViewTitle.setText(datum.getTitle());
        holder.textViewDescription.setText(datum.getDescription());
        Picasso.get().load(link.getHref()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return linkList.size();
    }

    class NasaViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView textViewTitle;
        private TextView textViewDescription;

        public NasaViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textViewTitle = itemView.findViewById(R.id.titleToDetail);
            textViewDescription = itemView.findViewById(R.id.descriptionToDetail);

            itemView.setOnClickListener(v -> {
                if (onImageClickListener != null) {
                    onImageClickListener.onImageClick(getAdapterPosition());
                }
            });
        }

    }
}
