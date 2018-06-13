package io.brainyapps.barista.ui.drinks;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;

public class DrinksListAdapter extends
        RecyclerView.Adapter<DrinksListAdapter.ViewHolder>
        implements DrinksListContract.Adapter {

    private DrinksListContract.View mView;

    private List<Drink> mDrinks;

    public DrinksListAdapter(DrinksListContract.View view,
                             List<Drink> drinks) {
        mView = view;
        mDrinks = drinks;

        mView.setAdapter(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {

        View itemLayoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.drinks_list_row,
                        parent,
                        false);

        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {
        holder.idTextView.setText(
                String.valueOf(mDrinks.get(position).getId())
        );
        holder.nameTextView.setText(mDrinks.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDrinks.size();
    }

    @Override
    public void deleteLastElement() {

        if (mDrinks.size() == 0) {
            return;
        }

        mDrinks.remove(mDrinks.size() - 1);
        //notifyDataSetChanged();
        notifyItemRemoved(mDrinks.size());
    }

    @Override
    public void addFirstElement(Drink drink) {
        //mDrinks.add(0, drink);
        //notifyItemInserted(2);
        boolean fuckingCycle = true;
        long z = 0;

        for (int i = 0; i < 100; i++) {
            i = 0;
            z++;
            Log.i("tttttqweqweqwe", "z = " + z);
            Drink drink1 = new Drink();
            drink1.setName("qweqwe");
            mDrinks.add(2, drink1);
            notifyItemInserted(2);
        }
    }

    // ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView mainCardView;
        TextView idTextView;
        TextView nameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mainCardView = itemView.findViewById(R.id.mainCardView);
            idTextView = itemView.findViewById(R.id.idTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);

            mainCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mView.showToast(
                            mDrinks.get(getAdapterPosition())
                    );
                }
            });
        }

        @Override
        public void onClick(View v) {
            //
        }
    }
}
