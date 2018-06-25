package io.brainyapps.barista.ui.drinks;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;

import android.support.transition.TransitionManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.util.ResizeAnimation;


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

            mainCardView.setOnClickListener(e-> mView.showToast(
                    mDrinks.get(getAdapterPosition())
            ));

            // ViewParent parent = imageViewBig.getParent();
            //Log.i("kkk", "getParent: " + imageViewBig.getParent());

            imageViewBig.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int height = imageViewBig.getMeasuredHeight();//175
            int width = imageViewBig.getMeasuredWidth();//365
            Log.i("kkk", "imageViewBig.getMeasuredHeight: " + height + "imageViewBig.getMeasuredWidth: " + width);
            //imageViewBig.setLayoutParams(new LinearLayout.LayoutParams(width, height));


            imageViewBig.setOnClickListener(v -> {

               ViewGroup.LayoutParams params =
                       imageViewBig.getLayoutParams();
               ViewGroup.MarginLayoutParams margParams =
                        (ViewGroup.MarginLayoutParams) imageViewBig.getLayoutParams();


               Log.i("kkk", "height: " + params.height + "width: " + params.width);
               Boolean expanded = false;//156, 276
               if (params.width > 276) {
                   expanded = true;
               };
                Log.i("kkk", " expanded: " + expanded);


                if (expanded){
                    params.width -= 30;
                    params.height -= 20;
                    margParams.setMargins(8,0,8,0);
                } else {
                    params.width += 30;
                    params.height += 20;
                    margParams.setMargins(-7,-10,-7,-10);
                }
                imageViewBig.setLayoutParams(params);


                /*imageView.setScaleType(expanded ? ImageView.ScaleType.CENTER_CROP :
                    ImageView.ScaleType.CENTER_CROP);

                */
                /*ResizeAnimation resizeAnimation = new ResizeAnimation(v, params.width);
                resizeAnimation.setDuration(600);
                v.startAnimation(resizeAnimation);*/


               /* ViewGroup.LayoutParams params = imageViewBig.getLayoutParams();
                int newWidth = params.width + 30;
                int newHeight = params.height + 30;
                params.height = newHeight;
                params.width = newWidth;
                imageViewBig.setLayoutParams(params);
                ViewGroup.MarginLayoutParams prs = (ViewGroup.MarginLayoutParams) imageViewBig.getLayoutParams();
                prs.setMargins(-15,-15,-15,-15);
*/
                //expanded ? ViewGroup.LayoutParams.MATCH_PARENT :
                        //ViewGroup.LayoutParams.WRAP_CONTENT;


               /* imageViewBig.setScaleType(expanded ? ImageView.ScaleType.CENTER_CROP :
                        ImageView.ScaleType.FIT_CENTER);*/
            });
        }


        @Override
        public void onClick(View v) {
            //
        }
    }
}
