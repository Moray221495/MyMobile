package mymobile.mymobile;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by Mustafa on 26.08.2016.
 */
public class ProfileActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState, int id_get, int permission_get) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String id = String.valueOf(id_get);

        // action on create
        final TextView forenameV = (TextView) findViewById(R.id.forenameView);
        final TextView surenameV = (TextView) findViewById(R.id.surenameView);
        final TextView locationV = (TextView) findViewById(R.id.locationView);
        final TextView phoneV = (TextView) findViewById(R.id.phoneView);

        try{
            String link="http://dev.morayinteractivestudios.com/mymobile/get_profile.php";
            String data  = URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

            URL url = new URL(link);
            URLConnection con = url.openConnection();

            con.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());

            wr.write( data );
            wr.flush();

            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // read server response
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
                break;
            }
            String result = sb.toString();

            if (result == "false_0") {
                // server error
                Toast.makeText(getApplicationContext(), "Error code 0: Server fehler!", Toast.LENGTH_SHORT).show();
            } else {
                // valid data
                String parts[] = result.split("_");

                forenameV.setText(parts[0]);
                surenameV.setText(parts[1]);
                locationV.setText(parts[2]);
                phoneV.setText(parts[3]);
            }
        }
        catch(Exception e){
            Toast.makeText(getApplicationContext(), "Error code 2: Internet fehler!", Toast.LENGTH_SHORT).show();
        }
    }
}
