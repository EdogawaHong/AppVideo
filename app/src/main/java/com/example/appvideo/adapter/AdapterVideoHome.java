package com.example.appvideo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appvideo.ItemClick.ItemHomeClick;
import com.example.appvideo.PlayVideo;
import com.example.appvideo.R;
import com.example.appvideo.model.VideoInfo;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdapterVideoHome extends RecyclerView.Adapter<AdapterVideoHome.Viewhoder> implements Filterable {
    List<VideoInfo> videos;
    Context context;

    List<VideoInfo> videoInfoFull;

    public AdapterVideoHome(List<VideoInfo> videos, Context context) {
        this.videos = videos;
        this.context = context;
        videoInfoFull=new ArrayList<>(videos);
    }

    @NonNull
    @Override
    public Viewhoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_listvideo, parent, false);
        Viewhoder viewhoder = new Viewhoder(view);
        return viewhoder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewhoder holder, int position) {
        final VideoInfo videoInfo = videos.get(position);
        holder.tvTitle.setText(videoInfo.getTitle());
        holder.tvActor.setText(videoInfo.getActor());
        Picasso.with(holder.imgThumb.getContext()).load(videoInfo.getThumb()).into(holder.imgThumb);

        holder.setItemHomeClick(new ItemHomeClick() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent=new Intent(context,PlayVideo.class);
                intent.putExtra("video", (Serializable) videoInfo);
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return videos.size();
    }

    @Override
    public Filter getFilter() {
        return videoFilter;
    }

    private Filter videoFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<VideoInfo> filteredList=new ArrayList<>();
            if (charSequence==null||charSequence.length()==0){
                filteredList.addAll(videoInfoFull);
            }else {
                String filterPattern=charSequence.toString().toLowerCase().trim();
                for (VideoInfo info: videoInfoFull){
                    if (info.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(info);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            videos.clear();
            videos.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class Viewhoder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle, tvActor;
        ImageView imgThumb;
        ItemHomeClick itemHomeClick;

        public void setItemHomeClick(ItemHomeClick itemHomeClick) {
            this.itemHomeClick = itemHomeClick;
        }

        public Viewhoder(@NonNull final View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvItemTitle);
            tvActor = itemView.findViewById(R.id.tvItemActor);
            imgThumb = itemView.findViewById(R.id.imgItemThumb);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemHomeClick.onClick(view,getAdapterPosition(),false);
        }
    }
}
