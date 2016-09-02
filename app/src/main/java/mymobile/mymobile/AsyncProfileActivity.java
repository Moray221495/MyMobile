// package
package mymobile.mymobile;

// import android classes
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

// import java classes
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jan Genz on 02.09.2016.
 */

// declare AsyncProfileActivity class
public class AsyncProfileActivity extends AsyncTask<String,Void,String> {
    // declare variables
    private Context context;
    private  TextView forenameView, surenameView, locationView, phoneView;

    // constructor
    public AsyncProfileActivity(Context context, TextView forenameView, TextView surenameView, TextView locationView, TextView phoneView) {
        this.context = context;
        this.forenameView = forenameView;
        this.surenameView = surenameView;
        this.locationView = locationView;
        this.phoneView = phoneView;
    }

    // bg-task for web request
    @Override
    protected String doInBackground(String... arg0) {
        try {
            // get string data
            String id = (String)arg0[0];

            // create url
            String link="http://dev.morayinteractivestudios.com/mymobile/get_profile.php";
            String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

            // open connection
            URL url = new URL(link);
            URLConnection con = url.openConnection();

            // output-stream-writer
            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

            wr.write(data);
            wr.flush();

            // reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            // build response string
            StringBuilder sb = new StringBuilder();
            String line = null;

            // read server response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            // return result
            return sb.toString();

        } catch (Exception e) {
            // return exception
            return new String("Exception: " + e.getMessage());
        }
    }

    // read returned result
    @Override
    protected void onPostExecute(String result){
        // handle with result
        if (result.equals("false_0")) {
            // server error
            Toast.makeText(context.getApplicationContext(), R.string.error_0, Toast.LENGTH_SHORT).show();
        } else {
            // split result in forename, surename, phone, location
            String parts[] = result.split("_");

            // check for valid result
            if (parts.length == 4) {
                // valid result (show result via label)
                forenameView.setText(parts[0]);
                surenameView.setText(parts[1]);
                phoneView.setText(parts[2]);
                locationView.setText(parts[3]);
            } else {
                // internet error
                Toast.makeText(context.getApplicationContext(), R.string.error_2, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
