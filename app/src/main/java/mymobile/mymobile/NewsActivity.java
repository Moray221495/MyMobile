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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Jan Genz on 01.09.2016.
 */

// declare NewsActivity class
public class NewsActivity extends AppCompatActivity {
    // declare variables
    private Toolbar toolbar;
    private  WebView webview;

    // on create
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news); // link layout

        // initialize actionbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // initialize UI
        webview = (WebView) findViewById(R.id.webView);

        try {
            // load news-feed (via redirect)
            webview.setWebViewClient(new WebViewClient());
            webview.getSettings().setJavaScriptEnabled(true);
            webview.loadUrl("http://dev.morayinteractivestudios.com/mymobile/redirect.php/");
        } catch (Exception e) {
            // return exception
            Toast.makeText(getApplicationContext(), R.string.error_2, Toast.LENGTH_SHORT).show();
        }
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
            Intent myIntent = new Intent(NewsActivity.this, ContactActivity.class);
            NewsActivity.this.startActivity(myIntent);

            return true;
        } else if (id == R.id.action_profile) {
            // get profile_ID
            SharedPreferences sharedPreferences= getSharedPreferences("settings", 0);
            String profile_id = sharedPreferences.getString("profile_id", "");

            // set search_ID
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("search_id", profile_id);
            editor.commit();

            // start ProfileActivity
            Intent myIntent = new Intent(NewsActivity.this, ProfileActivity.class);
            NewsActivity.this.startActivity(myIntent);

            return true;
        } else if (id == R.id.action_logout) {
            // set ID to invalid ID (0) - user is logged out (as guest)
            SharedPreferences sharedPreferences= getSharedPreferences("settings", 0);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("id", "0");
            editor.commit();

            // start LoginActivity
            Intent myIntent = new Intent(NewsActivity.this, LoginActivity.class);
            NewsActivity.this.startActivity(myIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
