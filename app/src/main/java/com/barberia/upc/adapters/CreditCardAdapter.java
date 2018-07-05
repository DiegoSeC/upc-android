package com.barberia.upc.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.barberia.upc.barberupc.MainActivity;
import com.barberia.upc.barberupc.R;
import com.barberia.upc.fragments.CreditCardFragment;
import com.barberia.upc.models.CreditCard;

import java.util.List;

public class CreditCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<CreditCard> creditCards;
    private Context context;

    public CreditCardAdapter(List<CreditCard> creditCards, Context context) {
        this.creditCards = creditCards;
        this.context = context;
    }

    public CreditCardAdapter setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
        return this;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            return new ViewHolder(
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.credit_card_item, parent, false)
            );
        }

        return new HeaderViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.credit_card_item_header, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof CreditCardAdapter.ViewHolder) {
            CreditCard creditCard = creditCards.get(position - 1);
            ViewHolder vh = (ViewHolder) holder;
            vh.updateView(creditCard);
        }

        if(holder instanceof CreditCardAdapter.HeaderViewHolder) {
            HeaderViewHolder vh = (HeaderViewHolder) holder;
            vh.changeState(creditCards.size());
        }
    }

    @Override
    public int getItemCount() {
        return creditCards.size() + 1;
    }

    private CreditCard getItem(int position) {
        return creditCards.get(position - 1);
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override
    public int getItemViewType(int position) {
        return isPositionHeader(position) ? TYPE_HEADER : TYPE_ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView creditType;
        private TextView creditName;


        public ViewHolder(View itemView) {
            super(itemView);

            creditType = itemView.findViewById(R.id.credit_card_type);
            creditName = itemView.findViewById(R.id.credit_card_name);
        }

        public void updateView(final CreditCard creditCard) {
            creditName.setText(creditCard.getName());
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView headerTitle;
        private Button addCreditCard;
        private TextView stateIndicator;
        private LinearLayout container;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            headerTitle = itemView.findViewById(R.id.credit_card_title);
            addCreditCard = itemView.findViewById(R.id.add_credit_card_button);
            stateIndicator = itemView.findViewById(R.id.loading_indicator);
            container = itemView.findViewById(R.id.loading_layout);

            goToCreditCard();
        }

        public void changeState(int state) {
            if(state == 0) {
                stateIndicator.setText(R.string.credit_card_empty);
            } else {
                container.setVisibility(View.GONE);
            }
        }

        private void goToCreditCard() {
            addCreditCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreditCardFragment creditCardFragment = new CreditCardFragment();
                    FragmentTransaction fragmentTransaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout_view, creditCardFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }
}
