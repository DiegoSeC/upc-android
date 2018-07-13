package com.barberia.upc.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barberia.upc.barberupc.MainActivity;
import com.barberia.upc.barberupc.R;
import com.barberia.upc.fragments.BarberFragment;
import com.barberia.upc.models.Barber;
import com.bumptech.glide.Glide;

import java.util.List;

public class BarberAdapter extends RecyclerView.Adapter<BarberAdapter.ViewHolder> {

    List<Barber> barbers;

    public BarberAdapter(List<Barber> barbers) {
        this.barbers = barbers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.baber_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Barber barber = barbers.get(position);
        holder.updateBarberItem(barber);
    }

    @Override
    public int getItemCount() {
        return barbers.size();
    }

    public BarberAdapter setBarbers(List<Barber> barbers) {
        this.barbers = barbers;
        return this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picBarber;
        TextView nameBarber;
        Context context;
        LinearLayout barberLayout;

        BarberFragment barberFragment;

        public ViewHolder(View itemView) {
            super(itemView);

            picBarber = itemView.findViewById(R.id.barber_pic_image_view);
            nameBarber = itemView.findViewById(R.id.barber_name_text_view);
            barberLayout = itemView.findViewById(R.id.barber_layout);

            context = itemView.getContext();
        }

        public void updateBarberItem(final Barber barber) {
            Glide.with(context)
                    .load(barber.getPicture())
                    .into(picBarber);

            nameBarber.setText(barber.getName());

            barberLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    barberFragment = BarberFragment.newInstance(barber);
                    FragmentTransaction fragmentTransaction = ((MainActivity) v.getContext()).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_view, barberFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }
}
