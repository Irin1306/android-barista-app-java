package io.brainyapps.barista.ui.drink;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getIntent().getIntExtra("drink_id", -1);

        if (id == -1) {
            return;
        }

        Toast.makeText(this, "id = " + id, Toast.LENGTH_SHORT).show();
    }
}
