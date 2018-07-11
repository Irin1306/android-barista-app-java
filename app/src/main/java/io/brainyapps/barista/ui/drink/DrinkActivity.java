package io.brainyapps.barista.ui.drink;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import io.brainyapps.barista.R;
import io.brainyapps.barista.ui.hits.HitsFragment;
import io.brainyapps.barista.util.ActivityUtils;

public class DrinkActivity extends AppCompatActivity {

    private int mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getIntent().getIntExtra("drink_id", -1);

        if (id == -1) {
            return;
        }
        Toast.makeText(this, "id = " + id, Toast.LENGTH_SHORT).show();

        ActivityUtils.replaceFragmentInContainer(R.id.mainContainer,
                getSupportFragmentManager(),
                new DrinkFragment());


    }

    public int getMId(){
        return mId;
    }
}
