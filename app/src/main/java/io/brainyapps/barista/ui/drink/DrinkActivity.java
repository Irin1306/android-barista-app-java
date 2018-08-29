package io.brainyapps.barista.ui.drink;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import io.brainyapps.barista.MainActivity;
import io.brainyapps.barista.R;
import io.brainyapps.barista.data.entity.Drink;
import io.brainyapps.barista.util.ActivityUtils;

public class DrinkActivity extends AppCompatActivity  {

    private String mTAG = MainActivity.TAG;

    public static final String DRINK_ID = "drink_id";

    private void setActionbarTextColor(ActionBar actBar, int color) {
        String title = actBar.getTitle().toString();
        Spannable spannablerTitle = new SpannableString(title);
        spannablerTitle.setSpan(new ForegroundColorSpan(color),
                0,
                spannablerTitle.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        actBar.setTitle(spannablerTitle);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int id = getIntent().getIntExtra("drink_id", -1);

        if (id == -1) {
            return;
        }
       // Toast.makeText(this, "DrinkActivity id = " + id, Toast.LENGTH_SHORT).show();

        setContentView(R.layout.content_main);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           // getActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_brown_24dp);

            setActionbarTextColor(getSupportActionBar(), ContextCompat.getColor(this, R.color.accent));

           /* int actionBarTitleId = Resources.getSystem()
                    .getIdentifier("action_bar_title", "id", "android");
            if (actionBarTitleId > 0) {
                TextView title = (TextView) findViewById(actionBarTitleId);
                if (title != null) {
                    title.setTextColor(ContextCompat.getColor(this, R.color.accent));
                }
            }*/
        }

        DrinkFragment drinkFragment = new DrinkFragment();

        ActivityUtils.replaceFragmentInContainer(
                R.id.mainContainer,
                getSupportFragmentManager(),
                drinkFragment
        );
        Bundle bundle = new Bundle();
        bundle.putInt(DRINK_ID, id);
        drinkFragment.setArguments(bundle);

        new DrinkPresenter(drinkFragment, id, getApplicationContext());
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return  true;
        //return super.onSupportNavigateUp();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


}
