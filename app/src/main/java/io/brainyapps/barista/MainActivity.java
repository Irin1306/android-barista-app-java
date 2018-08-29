package io.brainyapps.barista;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.brainyapps.barista.data.source.DataRepository;
import io.brainyapps.barista.data.source.DataSource;
import io.brainyapps.barista.data.source.local.dao.DrinkDao;
import io.brainyapps.barista.ui.cart.CartFragment;
import io.brainyapps.barista.ui.drinks.DrinksFragment;
import io.brainyapps.barista.ui.history.HistoryFragment;
import io.brainyapps.barista.ui.hits.HitsFragment;
import io.brainyapps.barista.util.ActivityUtils;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final static String TAG = "MainActivity";

    private static final int RC_SIGN_IN = 123;

    public static final String ITEM_IND = "item_ind";

    private FirebaseAuth mAuth;

    private TextView nameTextView, emailTextView;
    private CircleImageView userImage;
    private ImageView arrowImage;
    private View navHeaderMain;
    private NavigationView navigationView;
    private AnimatedVectorDrawable mArrowUpDrawable, mArrowDownDrawable;
    //private Animation mAnimationDown, mAnimationUp;

    private Menu menu;

    private int itemInd = 0;

    private Boolean loggedIn = false;
    private Boolean menuLoggedIn = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);

        // firebase
        mAuth = FirebaseAuth.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menu = navigationView.getMenu();

        nameTextView = navigationView.getHeaderView(0).findViewById(R.id.nameTextView);
        emailTextView = navigationView.getHeaderView(0).findViewById(R.id.emailTextView);
        userImage = navigationView.getHeaderView(0).findViewById(R.id.userPhotoCircleImageView);
        arrowImage = navigationView.getHeaderView(0).findViewById(R.id.layoutParent).findViewById(R.id.arrowImageView);
        navHeaderMain = navigationView.getHeaderView(0);


        // mArrowDownDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_arrow_down_animatable);
        // mArrowUpDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.ic_arrow_up_animatable);
        mArrowDownDrawable = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.ic_arrow_down_animatable);
        mArrowUpDrawable = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.ic_arrow_up_animatable);

       // mAnimationDown = AnimationUtils.loadAnimation(navHeaderMain.getContext(), R.anim.fall_down_20);


        itemInd = getIntent().getIntExtra("item_ind", 0);
        //Log.i(TAG, "itemInd " + itemInd);
        if (savedInstanceState == null) {
            MenuItem menuItem = menu.getItem(itemInd);

            onNavigationItemSelected(menuItem);

            menuItem.setChecked(true);

        }

    }
        /*arrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation rotation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_icon);
                arrowImage.startAnimation(rotation);
                Toast.makeText(getApplicationContext(), "find is selected", Toast.LENGTH_SHORT).show();
            }
        });
*/


    @Override
    protected void onStart() {
        super.onStart();
        // sign in
        buildGoogleLastSignIn();

    }

    @Override
    protected void onResume() {
        super.onResume();
        // if(preferences.contains("user")) {
        // isUser = preferences.getBoolean("user", false);
        if (navHeaderMain != null) {
            navHeaderMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuLoggedIn = !menuLoggedIn;

                    prepareMenu(itemInd);

                }
            });
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                signInUiUpdate(user);
                // ...
                loggedIn = true;

            } else {
                // TODO:
            }
        }

    }

    private void buildGoogleLastSignIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        signInUiUpdate(user);

        loggedIn = user != null;

    }

    private void signInUiUpdate(FirebaseUser user) {

        if (user == null) {
            signOutUiUpdate();
            return;
        }

        nameTextView.setText(user.getDisplayName());
        emailTextView.setText(user.getEmail());

        Picasso.with(this)
                .load(user.getPhotoUrl())
                .noPlaceholder()
                .error(R.drawable.user_no_photo)
                .into(userImage);

        prepareMenu(itemInd);

    }

    private void signOutUiUpdate() {

        nameTextView.setText(R.string.userName);
        emailTextView.setText(R.string.userEmail);

        userImage.setImageResource(R.drawable.user_no_photo);

        prepareMenu(itemInd);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            menuLoggedIn = false;
            prepareMenu(itemInd);

        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }*/


    public void prepareMenu(int itemInd) {
        int action_sign_out = 101;
        int action_sign_in = 102;

        menu.clear();
        if (menuLoggedIn && !loggedIn) {

            menu.add(menu.NONE, action_sign_in, 1, "Login");

            //arrowImage.setImageResource(R.drawable.ic_arrow_drop_up_brown_24dp);
            arrowImage.setImageDrawable(mArrowDownDrawable);
            mArrowDownDrawable.start();

        } else if (menuLoggedIn && loggedIn) {

            menu.add(menu.NONE, action_sign_out, 1, "Logout");

            //arrowImage.setImageResource(R.drawable.ic_arrow_drop_up_brown_24dp);
            arrowImage.setImageDrawable(mArrowDownDrawable);
            mArrowDownDrawable.start();

        } else if (!menuLoggedIn) {

            navigationView.inflateMenu(R.menu.activity_main_drawer);
            MenuItem menuItem = menu.getItem(itemInd);
            menuItem.setChecked(true);

            //arrowImage.setImageResource(R.drawable.ic_arrow_drop_down_brown_24dp);
            arrowImage.setImageDrawable(mArrowUpDrawable);
            mArrowUpDrawable.start();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
      /*  if (id == R.id.action_sign_in) {

            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
                    //new AuthUI.IdpConfig.FacebookBuilder().build(),
                    //new AuthUI.IdpConfig.TwitterBuilder().build()
            );

            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);

            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {

            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            signOutUiUpdate();
                        }
                    });

            loggedIn = false;
            Toast.makeText(this, "action_sign_out" + loggedIn + "-isUser", Toast.LENGTH_LONG).show();

            return true;
        }
*/
        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_hits) {

            ActivityUtils.replaceFragmentInContainer(R.id.mainContainer,
                    getSupportFragmentManager(),
                    new HitsFragment());
            itemInd = 0;

        } else if (id == R.id.nav_drink_list) {

            ActivityUtils.replaceFragmentInContainer(R.id.mainContainer,

                    getSupportFragmentManager(),
                    new DrinksFragment());
            itemInd = 1;

        } else if (id == R.id.nav_history) {

            ActivityUtils.replaceFragmentInContainer(R.id.mainContainer,

                    getSupportFragmentManager(),
                    new HistoryFragment());
            itemInd = 2;

        } else if (id == R.id.nav_cart) {

            ActivityUtils.replaceFragmentInContainer(R.id.mainContainer,

                    getSupportFragmentManager(),
                    new CartFragment());
            itemInd = 3;

        } else if (id == R.id.nav_settings) {
            //

        } else if (id == 102) {

            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
                    //new AuthUI.IdpConfig.FacebookBuilder().build(),
                    //new AuthUI.IdpConfig.TwitterBuilder().build()
            );

            // Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);


        } else if (id == 101) {

            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            signOutUiUpdate();
                        }
                    });

            loggedIn = false;

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        menuLoggedIn = false;
        // Toast.makeText(this, "itemInd" + itemInd, Toast.LENGTH_LONG).show();


        return true;
    }


    /*public void showAlertDialog(int message, int positiveButton) {
        FragmentManager fm = getSupportFragmentManager();
        MyAlertDialogFragment alertDialog = MyAlertDialogFragment.newInstance(message, positiveButton);
        alertDialog.show(fm, "fragment_alert");
    }

    public void doPositiveClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Positive click!");
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }
*/
    @Override
    protected void onPause() {
        super.onPause();
        //if(preferences.contains("user")) {
        //isUser = preferences.getBoolean("user", false);

        // }
        if (navHeaderMain != null) {
            navHeaderMain.setOnClickListener(null);
        }
       // Toast.makeText(this, "Main Activity onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(this, "Main Activity onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "Main Activity onDestroy", Toast.LENGTH_SHORT).show();
    }
}