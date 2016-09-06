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
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Jan Genz on 06.09.2016.
 */

// declare ViewActivity class
public class ViewActivity extends AppCompatActivity {
    // declare variables
    private Toolbar toolbar;
    private ListView listview;
    private ArrayAdapter<String> listadapter;
    private String code = "J8ap)b9N<N%(WpU?5Rb$y625";

    // on create
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view); // link layout

        // initialize actionbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // initialize UI
        listview = (ListView) findViewById(R.id.listView);



        // start AsyncViewActivity
        new AsyncViewActivity(this, listview).execute(code);
    }

    // link menu-layout with actionbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    // add items to menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // on item_click event (handle actions)
        if (id == R.id.action_contact) {
            // start ContactActivity
            Intent myIntent = new Intent(ViewActivity.this, ContactActivity.class);
            ViewActivity.this.startActivity(myIntent);

            return true;
        } else if (id == R.id.action_news) {
            // start NewsActivity
            Intent myIntent = new Intent(ViewActivity.this, NewsActivity.class);
            ViewActivity.this.startActivity(myIntent);

            return true;
        } else if (id == R.id.action_profile) {
            // get profile_ID
            SharedPreferences sharedPreferences = getSharedPreferences("settings", 0);
            String profile_id = sharedPreferences.getString("profile_id", "");

            // set search_ID
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("search_id", profile_id);
            editor.commit();

            // start ProfileActivity
            Intent myIntent = new Intent(ViewActivity.this, ProfileActivity.class);
            ViewActivity.this.startActivity(myIntent);

            return true;
        } else if (id == R.id.action_map) {
            // start MapActivity
            Intent myIntent = new Intent(ViewActivity.this, MapActivity.class);
            ViewActivity.this.startActivity(myIntent);

            return true;
        } else if (id == R.id.action_logout) {
            // set ID to invalid ID (0) - user is logged out (as guest)
            SharedPreferences sharedPreferences= getSharedPreferences("settings", 0);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("id", "0");
            editor.commit();

            // start LoginActivity
            Intent myIntent = new Intent(ViewActivity.this, LoginActivity.class);
            ViewActivity.this.startActivity(myIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
