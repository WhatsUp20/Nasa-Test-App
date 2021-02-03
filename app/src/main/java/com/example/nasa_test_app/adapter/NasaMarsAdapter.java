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

public class NasaMarsAdapter extends RecyclerView.Adapter<NasaMarsAdapter.NasaVideoViewHolder> {
    private List<Link> linkList2 = new ArrayList<>();
    private List<Datum> datumList2 = new ArrayList<>();

    private NasaMarsAdapter.OnImageClickListener onImageClickListener;

    public void setLinkList2(List<Link> linkList2) {
        this.linkList2 = linkList2;
        notifyDataSetChanged();
    }

    public List<Datum> getDatumList2() {
        return datumList2;
    }

    public void setDatumList2(List<Datum> datumList2) {
        this.datumList2 = datumList2;
        notifyDataSetChanged();
    }

    public List<Link> getLinkList2() {
        return linkList2;
    }

    public void setOnImageClickListener2(NasaMarsAdapter.OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public interface OnImageClickListener {
        void onImageClick(int position);
    }

    @NonNull
    @Override
    public NasaMarsAdapter.NasaVideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nasa_mars_item, parent, false);
        return new NasaMarsAdapter.NasaVideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NasaMarsAdapter.NasaVideoViewHolder holder, int position) {
        Link link = linkList2.get(position);
        Datum datum = datumList2.get(position);
        holder.textViewTitle.setText(datum.getTitle());
        holder.textViewDescription.setText(datum.getDescription());
        Picasso.get().load(link.getHref()).into(holder.imageViewPlanet);
    }

    @Override
    public int getItemCount() {
        return linkList2.size();
    }

    class NasaVideoViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageViewPlanet;
        private TextView textViewTitle;
        private TextView textViewDescription;

        public NasaVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPlanet = itemView.findViewById(R.id.imageViewPlanet);
            textViewTitle = itemView.findViewById(R.id.titleToDetail2);
            textViewDescription = itemView.findViewById(R.id.descriptionToDetail2);

            itemView.setOnClickListener(v -> {
                if (onImageClickListener != null) {
                    onImageClickListener.onImageClick(getAdapterPosition());
                }
            });
        }

    }
}
