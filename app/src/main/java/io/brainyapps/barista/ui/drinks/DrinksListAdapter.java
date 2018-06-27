package io.brainyapps.barista.ui.drinks;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;


public class DrinksListAdapter extends
        RecyclerView.Adapter<DrinksListAdapter.ViewHolder>
        implements DrinksListContract.Adapter {

    private DrinksListContract.View mView;
    private DrinksListContract.Presenter mPresenter;

    private List<Drink> mDrinks;

    public DrinksListAdapter(DrinksListContract.View view,
                             DrinksListContract.Presenter presenter,
                             List<Drink> drinks) {
        mView = view;
        mPresenter = presenter;
        mDrinks = drinks;

        mView.setAdapter(this);
        mPresenter.setAdapter(this);
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

        holder.mainCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.showToast(
                        mDrinks.get(holder.getAdapterPosition())
                );
            }
        });

        ViewGroup.LayoutParams params =
                holder.imageViewBig.getLayoutParams();

        Toast.makeText(holder.imageViewBig.getContext(),
                "params = " + params.width,
                Toast.LENGTH_LONG).show();

        holder.imageViewBig.setOnClickListener(new View.OnClickListener() {

            //change size
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params =
                        holder.imageViewBig.getLayoutParams();
                ViewGroup.MarginLayoutParams margParams =
                        (ViewGroup.MarginLayoutParams) holder.imageViewBig.getLayoutParams();

                Boolean expanded = false;
                if (params.width > 276) {
                    expanded = true;
                }
                if (expanded) {
                    params.width -= 30;
                    params.height -= 20;
                    margParams.setMargins(8, 0, 8, 0);
                } else {
                    params.width += 30;
                    params.height += 20;
                    margParams.setMargins(-7, -10, -7, -10);
                }
                holder.imageViewBig.setLayoutParams(params);

            }
        });

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
        mDrinks.add(0, drink);
        notifyItemInserted(0);
    }

    // ViewHolder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView mainCardView;
        TextView idTextView;
        TextView nameTextView;
        ImageView imageViewBig;


        public ViewHolder(View itemView) {
            super(itemView);
            mainCardView = itemView.findViewById(R.id.mainCardView);
            idTextView = itemView.findViewById(R.id.idTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            imageViewBig = itemView.findViewById(R.id.appCompatImageViewBig);
        }


        @Override
        public void onClick(View v) {
            //
        }
    }
}
