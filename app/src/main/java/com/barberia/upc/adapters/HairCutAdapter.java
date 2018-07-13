package com.barberia.upc.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barberia.upc.barberupc.R;
import com.barberia.upc.fragments.HairCutFragment;
import com.barberia.upc.models.HairCut;
import com.bumptech.glide.Glide;

import java.util.List;

public class HairCutAdapter extends RecyclerView.Adapter<HairCutAdapter.ViewHolder> {

    List<HairCut> hairCutList;

    public HairCutAdapter(List<HairCut> hairCutList) {
        this.hairCutList = hairCutList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.hair_cut_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HairCut hairCut = hairCutList.get(position);
        holder.updateHairCut(hairCut);
    }

    @Override
    public int getItemCount() {
        return hairCutList.size();
    }

    public HairCutAdapter setHairCutList(List<HairCut> hairCutList) {
        this.hairCutList = hairCutList;
        return this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView hairCutPicture;
        TextView hairCutName;
        Context context;
        LinearLayout hairCutLayout;


        public ViewHolder(View itemView) {
            super(itemView);

            hairCutName = itemView.findViewById(R.id.hair_cut_name);
            hairCutPicture = itemView.findViewById(R.id.hair_cut_picture);
            hairCutLayout = itemView.findViewById(R.id.hair_cut_layout);

            context = itemView.getContext();
        }

        public void updateHairCut(HairCut hairCut) {
            hairCutName.setText(hairCut.getName());

            Glide.with(context)
                    .load(hairCut.getPicture())
                    .into(hairCutPicture);

            hairCutLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });

        }
    }
}
