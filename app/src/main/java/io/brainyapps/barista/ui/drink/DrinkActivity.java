package io.brainyapps.barista.ui.drink;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.DataSource;
import io.brainyapps.barista.util.ActivityUtils;

public class DrinkActivity extends AppCompatActivity {

    private List<Drink> mDrinks;

    private Drink mDrink;

    private DataRepository mData;

    private int mId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getIntent().getIntExtra("drink_id", -1);

        if (id == -1) {
            return;
        }
        Toast.makeText(this, "id = " + id, Toast.LENGTH_SHORT).show();

        setContentView(R.layout.content_main);

        ActivityUtils.replaceFragmentInContainer(R.id.mainContainer,
                getSupportFragmentManager(),
                new DrinkFragment());


    }

    @Override
    protected void onStart() {
        super.onStart();
        mData.getAllDrinks(new DataSource.GetDrinksCallback() {
                               @Override
                               public void onDrinksLoaded(List<Drink> drinks) {
                                   mDrink = findDrink(mId, drinks);
                               }
                           }

        );
    }

    Drink findDrink(int id, List<Drink> drinks) {
        for (Drink drink : drinks) {
            if ((id + "").equals(drink.getId() + "")) {
                return drink;
            }

        }
        return null;
    }
}
