package mymobile.mymobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by jange on 28.08.2016.
 */
public class NewsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState, int permission_get) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        WebView webview = new WebView(this);
        setContentView(webview);

        webview.loadUrl("http://dev.morayinteractivestudios.com/mymobile/redirect.php/");
    }
}
