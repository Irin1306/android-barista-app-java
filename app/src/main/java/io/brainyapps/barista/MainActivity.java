package io.brainyapps.barista;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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

    private FirebaseAuth mAuth;

    private TextView nameTextView, emailTextView;
    private CircleImageView userImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

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

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nameTextView = navigationView.getHeaderView(0).findViewById(R.id.nameTextView);
        emailTextView = navigationView.getHeaderView(0).findViewById(R.id.emailTextView);
        userImage = navigationView.getHeaderView(0).findViewById(R.id.userPhotoCircleImageView);

        if (savedInstanceState == null) {
            MenuItem menuItem = navigationView.getMenu().getItem(0);

            onNavigationItemSelected(menuItem);

            menuItem.setChecked(true);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // sign in
        buildGoogleLastSignIn();
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
            } else {
                // TODO:
            }
        }

    }

    private void buildGoogleLastSignIn() {
        FirebaseUser user = mAuth.getCurrentUser();
        signInUiUpdate(user);
    }

    private void signInUiUpdate(FirebaseUser user) {
        // TODO:
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

    }

    private void signOutUiUpdate() {
        // TODO: стринга
        nameTextView.setText("not logged in");
        emailTextView.setText("");

        userImage.setImageResource(R.drawable.user_no_photo);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_in) {

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


            return true;
        }

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

        } else if (id == R.id.nav_drink_list) {

            ActivityUtils.replaceFragmentInContainer(R.id.mainContainer,
 
                    getSupportFragmentManager(),
                    new DrinksFragment());

        } else if (id == R.id.nav_history) {

            ActivityUtils.replaceFragmentInContainer(R.id.mainContainer,

                    getSupportFragmentManager(),
                    new HistoryFragment());

        } else if (id == R.id.nav_cart) {

            ActivityUtils.replaceFragmentInContainer(R.id.mainContainer,

                    getSupportFragmentManager(),
                    new CartFragment());

        } else if (id == R.id.nav_settings) {
            //

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
