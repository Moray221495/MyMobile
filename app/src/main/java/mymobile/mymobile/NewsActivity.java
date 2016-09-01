// package
package mymobile.mymobile;

// import android classes
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
            Toast.makeText(getApplicationContext(), "Error code 2: Internet fehler!", Toast.LENGTH_SHORT).show();
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
        if (id == R.id.action_news) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
