package mymobile.mymobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by jange on 31.08.2016.
 */
public class ContactActivity extends AppCompatActivity {

    private Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        final Button login_staff_button = (Button) findViewById(R.id.send_contact_button);
        login_staff_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // action on send_event
                final EditText forenameBox = (EditText) findViewById(R.id.forenameBox);
                final EditText surenameBox = (EditText) findViewById(R.id.surenameBox);
                final EditText phoneBox = (EditText) findViewById(R.id.phoneBox);
                final EditText emailBox = (EditText) findViewById(R.id.emailBox);
                final EditText subjectBox = (EditText) findViewById(R.id.subjectBox);
                final EditText msgBox = (EditText) findViewById(R.id.msgBox);

                String forename = forenameBox.getText().toString();
                String surename = surenameBox.getText().toString();
                String phone = phoneBox.getText().toString();
                String email = emailBox.getText().toString();
                String subject = subjectBox.getText().toString();
                String msg = msgBox.getText().toString();

                try{
                    String link="http://http://dev.morayinteractivestudios.com/mymobile/mail_contact.php";
                    String data  = URLEncoder.encode("sender", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                    data += "&" + URLEncoder.encode("subject", "UTF-8") + "=" + URLEncoder.encode(subject, "UTF-8");
                    data += "&" + URLEncoder.encode("forename", "UTF-8") + "=" + URLEncoder.encode(forename, "UTF-8");
                    data += "&" + URLEncoder.encode("surename", "UTF-8") + "=" + URLEncoder.encode(surename, "UTF-8");
                    data += "&" + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
                    data += "&" + URLEncoder.encode("msg", "UTF-8") + "=" + URLEncoder.encode(msg, "UTF-8");

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
                        // sent
                        Toast.makeText(getApplicationContext(), "Erfolgreich gesendet!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Error code 2: Internet fehler!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact, menu);
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