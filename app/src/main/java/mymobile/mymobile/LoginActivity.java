// package
package mymobile.mymobile;

// import android classes
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jan Genz on 01.09.2016.
 */

// declare LoginActivity class
public class LoginActivity extends AppCompatActivity {
    // declare variables
    private Button login_staff_button, login_guest_button;
    private  EditText idBox,passwordBox;
    private TextView statusLabel;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // link layout

        // initialize UI
        login_staff_button = (Button) findViewById(R.id.login_staff_button);
        login_guest_button = (Button) findViewById(R.id.login_guest_button);
        idBox = (EditText) findViewById(R.id.id_box);
        passwordBox = (EditText) findViewById(R.id.password_box);
        statusLabel = (TextView)findViewById(R.id.statusLabel);

        // add hints to idBox, passwordBox
        idBox.setHint(R.string.txtbox_id);
        passwordBox.setHint(R.string.txtbox_password);

        // on staff-login
        login_staff_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // start login-task
                startAsyncLoginTask(v);
            }
        });

        // on member-login
        login_guest_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // start NewsActivity
                Intent myIntent = new Intent(LoginActivity.this, NewsActivity.class);
                myIntent.putExtra("permission_get", 1); // optional parameter (permission)
                LoginActivity.this.startActivity(myIntent);
            }
        });
    }

    // login-task pre-setup
    public void startAsyncLoginTask(View view) {
        // check for correct input
        if (idBox.getText().toString().isEmpty()) {
            // empty ID
            Toast.makeText(getApplicationContext(), "Error code 3: ID fehlt!", Toast.LENGTH_SHORT).show();
        } else if (passwordBox.getText().toString().isEmpty()) {
            // empty password
            Toast.makeText(getApplicationContext(), "Error code 4: Passwort fehlt!", Toast.LENGTH_SHORT).show();
        } else {
            // split id in forename and surename
            String s = idBox.getText().toString();
            String parts[] = s.split("_");

            // check for valid ID
            if (parts.length == 2) {
                // get surename, forename form ID (valid ID)
                String surename = parts[1];
                String forename = parts[0];

                // get password
                String password = passwordBox.getText().toString();

                // start AsyncLoginActivity
                new AsyncLoginActivity(this, statusLabel).execute(surename, forename, password);
            } else {
                // invalid ID
                Toast.makeText(getApplicationContext(), "Error code 5: Ungültige ID!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
