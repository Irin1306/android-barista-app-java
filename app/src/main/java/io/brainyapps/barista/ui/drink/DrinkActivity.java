package io.brainyapps.barista.ui.drink;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.data.source.DataRepository;

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
    }
}
