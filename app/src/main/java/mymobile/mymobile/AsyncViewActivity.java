// package
package mymobile.mymobile;

// import android classes
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

// import java classes
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jan Genz on 06.09.2016.
 */

// declare AsyncViewActivity class
public class AsyncViewActivity extends AsyncTask<String,Void,String> {
    // declare variables
    private Context context;
    private ListView listview;
    private ArrayAdapter<String> listadapter;

    // constructor
    public AsyncViewActivity(Context context, ListView listview) {
        this.context = context;
        this.listview = listview;
    }

    // on pre-execute
    protected void onPreExecute(){
    }

    // bg-task for web request
    @Override
    protected String doInBackground(String... arg0) {
        try {
            // get string data
            String code = (String)arg0[0];

            // create url
            String link="http://dev.morayinteractivestudios.com/mymobile/get_view.php";
            String data  = URLEncoder.encode("code", "UTF-8") + "=" + URLEncoder.encode(code, "UTF-8");

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
            // insert data into ViewActivity (get data as string-array)
            String parts[] = result.split("_");

            // build array-list from string-array
            ArrayList<String> partList = new ArrayList<String>();
            partList.addAll( Arrays.asList(parts));

            // adapt data from partList into row-layout
            listadapter = new ArrayAdapter<String>(context, R.layout.row, partList);

            // set adapted row-layout as actual layout for view
            listview.setAdapter(listadapter);

            // on click listener
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // get ID (via position)
                    int index =  position + 1;

                    // convert itemPosition to string
                    StringBuilder sb = new StringBuilder();
                    sb.append(index);

                    // set search_ID
                    SharedPreferences sharedPreferences= context.getSharedPreferences("settings", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("search_id", sb.toString());
                    editor.commit();

                    // start ProfileActivity
                    Intent myIntent = new Intent(context, ProfileActivity.class);
                    context.startActivity(myIntent);
                }
            });
        }
    }
}
