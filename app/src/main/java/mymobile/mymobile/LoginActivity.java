package mymobile.mymobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login_staff_button = (Button) findViewById(R.id.login_staff_button);
        login_staff_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // action on login_event
                final EditText idBox = (EditText) findViewById(R.id.id_box);
                final EditText passwordBox = (EditText) findViewById(R.id.password_box);

                String s = idBox.getText().toString();
                String parts[] = s.split("_");

                String password = passwordBox.getText().toString();

                String surename = parts[1];
                String forename = parts[0];

                try{
                    String link="http://http://dev.morayinteractivestudios.com/mymobile/login.php";
                    String data  = URLEncoder.encode("surename", "UTF-8") + "=" + URLEncoder.encode(surename, "UTF-8");
                    data += "&" + URLEncoder.encode("forename", "UTF-8") + "=" + URLEncoder.encode(forename, "UTF-8");
                    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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
                    } else if (result == "false_1") {
                        // invalid login
                        Toast.makeText(getApplicationContext(), "Error code 1: Benutzer unbekannt!", Toast.LENGTH_SHORT).show();
                    } else {
                        // valid login
                        int id = Integer.parseInt(result);

                        Intent myIntent = new Intent(LoginActivity.this, ProfileActivity.class);
                        myIntent.putExtra("id_get", id); //optional parameter
                        myIntent.putExtra("permission_get", 0); //optional parameter
                        LoginActivity.this.startActivity(myIntent);
                    }
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Error code 2: Internet fehler!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button login_guest_button = (Button) findViewById(R.id.login_guest_button);
        login_guest_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent myIntent = new Intent(LoginActivity.this, NewsActivity.class);
                myIntent.putExtra("permission_get", 1); //optional parameter
                LoginActivity.this.startActivity(myIntent);
            }
        });
    }
}
