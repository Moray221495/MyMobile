// package
package mymobile.mymobile;

// import android classes
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Jan Genz on 26.08.2016.
 */

// declare ProfileActivity class
public class ProfileActivity extends AppCompatActivity {
    // declare variables
    private  TextView forenameView, surenameView, locationView, phoneView;
    private Toolbar toolbar;

    // on create
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // link layout

        // get search_ID
        SharedPreferences sharedPreferences= getSharedPreferences("settings", 0);
        String id = sharedPreferences.getString("search_id", "");

        // initialize actionbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // initialize UI
        forenameView = (TextView) findViewById(R.id.forenameView);
        surenameView = (TextView) findViewById(R.id.surenameView);
        locationView = (TextView) findViewById(R.id.locationView);
        phoneView = (TextView) findViewById(R.id.phoneView);

        if (id.equals("0")) {
            // invalid ID
            forenameView.setText(R.string.label_no_profile);
            surenameView.setText(R.string.label_no_profile);
            locationView.setText(R.string.label_no_profile);
            phoneView.setText(R.string.label_no_profile);
        } else {
            // valid ID
            new AsyncProfileActivity(this, forenameView, surenameView, locationView, phoneView).execute(id);
        }
    }

    // link menu-layout with actionbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    // add items to menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // on item_click event (handle actions)
        if (id == R.id.action_contact) {
            // start ContactActivity
            Intent myIntent = new Intent(ProfileActivity.this, ContactActivity.class);
            ProfileActivity.this.startActivity(myIntent);

            return true;
        } else if (id == R.id.action_news) {
            // start NewsActivity
            Intent myIntent = new Intent(ProfileActivity.this, NewsActivity.class);
            ProfileActivity.this.startActivity(myIntent);

            return true;
        } else if (id == R.id.action_logout) {
            // set ID to invalid ID (0) - user is logged out (as guest)
            SharedPreferences sharedPreferences= getSharedPreferences("settings", 0);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("id", "0");
            editor.commit();

            // start LoginActivity
            Intent myIntent = new Intent(ProfileActivity.this, LoginActivity.class);
            ProfileActivity.this.startActivity(myIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
