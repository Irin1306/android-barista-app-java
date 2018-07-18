package io.brainyapps.barista.ui.hits;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;

public class HitsAdapter extends RecyclerView.Adapter<HitsAdapter.ViewHolder> implements HitsContract.Adapter{
    private HitsContract.View mView;
    private HitsContract.Presenter mPresenter;
    private List<Drink> mDrinks;

    public HitsAdapter(HitsContract.View view, HitsContract.Presenter presenter, List<Drink> drinks){
        mDrinks = drinks;
        mView = view;
        mPresenter = presenter;

        mView.setAdapter(this);
        mPresenter.setAdapter(this);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.hits_list_row,
                        parent,
                        false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewName.setText(mDrinks.get(position).getName());



    }

    @Override
    public int getItemCount() {
        return mDrinks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewName;
        TextView textViewPrice;
        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
        }

        @Override
        public void onClick(View v) {

        }
    }
}