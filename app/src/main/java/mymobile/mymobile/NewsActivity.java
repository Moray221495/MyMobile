package mymobile.mymobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by jange on 28.08.2016.
 */
public class NewsActivity extends AppCompatActivity {

    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState, int permission_get) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        try {

            WebView webview = new WebView(this);
            setContentView(webview);

            webview.loadUrl("http://dev.morayinteractivestudios.com/mymobile/redirect.php/");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error code 2: Internet fehler!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_news) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
