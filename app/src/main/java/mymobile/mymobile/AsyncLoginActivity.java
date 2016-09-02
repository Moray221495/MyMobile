// package
package mymobile.mymobile;

// import android classes
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

// import java classes
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Jan Genz on 01.09.2016.
 */

// declare AsyncLoginActivity class
public class AsyncLoginActivity  extends AsyncTask<String,Void,String>{
    // declare variables
    private Context context;

    // constructor
    public AsyncLoginActivity(Context context) {
        this.context = context;
    }

    // on pre-execute
    protected void onPreExecute(){
    }

    // bg-task for web request
    @Override
    protected String doInBackground(String... arg0) {
        try {
            // get string data
            String surename = (String)arg0[0];
            String forename = (String)arg0[1];
            String password = (String)arg0[2];

            // create url
            String link = "http://dev.morayinteractivestudios.com/mymobile/login.php";
            String data = URLEncoder.encode("surename", "UTF-8") + "=" + URLEncoder.encode(surename, "UTF-8");
            data += "&" + URLEncoder.encode("forename", "UTF-8") + "=" + URLEncoder.encode(forename, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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
        } else if (result.equals("false_1")) {
            // invalid login
            Toast.makeText(context.getApplicationContext(), R.string.error_1, Toast.LENGTH_SHORT).show();
        } else {
            if (!isNumeric(result)) {
                // internet error (no internet access)
                Toast.makeText(context.getApplicationContext(), R.string.error_2, Toast.LENGTH_SHORT).show();
            } else {
                // set IDs to result - user is logged in (as staff)
                SharedPreferences sharedPreferences= context.getSharedPreferences("settings", 0);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString("profile_id", result);
                editor.putString("search_id", result);
                editor.commit();

                // valid login (redirect to ProfileActivity)
                Intent myIntent = new Intent(context, ProfileActivity.class);
                context.startActivity(myIntent);
            }
        }
    }

    // check if string is number method
    public static boolean isNumeric(String s) {
        try {
            double d = Double.parseDouble(s);
        } catch(NumberFormatException nfe) {
            return false;
        }

        return true;
    }
}