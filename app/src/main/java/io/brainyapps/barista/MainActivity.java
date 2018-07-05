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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import io.brainyapps.barista.ui.cart.CartFragment;
import io.brainyapps.barista.ui.drinks.DrinksFragment;
import io.brainyapps.barista.ui.history.HistoryFragment;
import io.brainyapps.barista.ui.hits.HitsFragment;
import io.brainyapps.barista.util.ActivityUtils;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final static String TAG = "ygygvggh";

    private GoogleSignInClient mGoogleSignInClient;

    private TextView nameTextView, emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);

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
        // sign in
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);
            }
        }
    }

    private void buildGoogleLastSignIn() {
        mGoogleSignInClient = buildGoogleSignInClient();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        signInUiUpdate(account);
    }

    private GoogleSignInClient buildGoogleSignInClient() {
        GoogleSignInOptions gso =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .requestProfile()
                        .requestScopes(Drive.SCOPE_FILE)
                        .build();

        return GoogleSignIn.getClient(this, gso);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            signInUiUpdate(account);
        } catch (ApiException e) {
            e.printStackTrace();
            signInUiUpdate(null);
        }
    }

    private void signInUiUpdate(GoogleSignInAccount account) {
        // TODO:
        if (account == null) {
            signOutUiUpdate();
            return;
        }

        nameTextView.setText(account.getDisplayName());
        emailTextView.setText(account.getEmail());

    }

    private void signOutUiUpdate() {
        // TODO:
        nameTextView.setText(R.string.nav_header_title);
        emailTextView.setText(R.string.nav_header_subtitle);
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

            Intent intent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(intent, 1);

            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sign_out) {

            mGoogleSignInClient.revokeAccess().addOnCompleteListener(this, new OnCompleteListener<Void>() {
                @Override
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
