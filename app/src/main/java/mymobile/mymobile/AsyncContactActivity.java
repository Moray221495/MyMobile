// package
package mymobile.mymobile;

// import android classes
import android.content.Context;
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

// declare AsyncContactActivity class
public class AsyncContactActivity  extends AsyncTask<String,Void,String> {
    // declare variables
    private Context context;

    // constructor
    public AsyncContactActivity(Context context) {
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
            String forename = (String)arg0[0];
            String surename = (String)arg0[1];
            String phone = (String)arg0[2];
            String email = (String)arg0[3];
            String subject = (String)arg0[4];
            String msg= (String)arg0[5];

            // create url
            String link="http://dev.morayinteractivestudios.com/mymobile/mail_contact.php";
            String data  = URLEncoder.encode("forename", "UTF-8") + "=" + URLEncoder.encode(forename, "UTF-8");
            data += "&" + URLEncoder.encode("surename", "UTF-8") + "=" + URLEncoder.encode(surename, "UTF-8");
            data += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
            data += "&" + URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("subject", "UTF-8") + "=" + URLEncoder.encode(subject, "UTF-8");
            data += "&" + URLEncoder.encode("msg", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8");

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
        if (result.equals("true")) {
            // sent
            Toast.makeText(context.getApplicationContext(), R.string.notification_sent, Toast.LENGTH_SHORT).show();
        } else {
            // server error
            Toast.makeText(context.getApplicationContext(), R.string.error_2, Toast.LENGTH_SHORT).show();
        }
    }
}
