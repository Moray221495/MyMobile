// link package
package mymobile.mymobile;

// import android classes
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

// declare class
public class LoginActivity extends AppCompatActivity {
    // declare variables
    private Button login_staff_button, login_guest_button;
    private  EditText idBox,passwordBox;
    private TextView statusField;

    // on create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // link layout

        // initialize UI
        statusField = (TextView)findViewById(R.id.statusField);
        login_staff_button = (Button) findViewById(R.id.login_staff_button);
        login_guest_button = (Button) findViewById(R.id.login_guest_button);
        idBox = (EditText) findViewById(R.id.id_box);
        passwordBox = (EditText) findViewById(R.id.password_box);

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
                // load NewsActivity
                Intent myIntent = new Intent(LoginActivity.this, NewsActivity.class);
                myIntent.putExtra("permission_get", 1); // optional parameter (permission)
                LoginActivity.this.startActivity(myIntent);
            }
        });
    }

    public void startAsyncLoginTask(View view){
        // split id in forename and surename
        String s = idBox.getText().toString();
        String parts[] = s.split("_");

        String surename = parts[1];
        String forename = parts[0];

        // get password
        String password = passwordBox.getText().toString();

        // start AsyncLoginActivity
        new AsyncLoginActivity(this, statusField).execute(surename,forename,password);
    }
}
