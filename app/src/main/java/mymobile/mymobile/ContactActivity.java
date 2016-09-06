// package
package mymobile.mymobile;

// import android classes
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jan Genz on 31.08.2016.
 */

// declare ContactActivity class
public class ContactActivity extends AppCompatActivity {
    // declare variables
    private Toolbar toolbar;
    private Button send_contact_button;
    private EditText forenameBox, surenameBox, phoneBox, emailBox, subjectBox, msgBox;

    // on create
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact); // link layout

        // initialize actionbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // initialize UI
        send_contact_button = (Button) findViewById(R.id.send_contact_button);
        forenameBox = (EditText) findViewById(R.id.forenameBox);
        surenameBox = (EditText) findViewById(R.id.surenameBox);
        phoneBox = (EditText) findViewById(R.id.phoneBox);
        emailBox = (EditText) findViewById(R.id.emailBox);
        subjectBox = (EditText) findViewById(R.id.subjectBox);
        msgBox = (EditText) findViewById(R.id.msgBox);

        // add hints to forenameBox, surenameBox, phoneBox, emailBox, subjectBox, msgBox
        forenameBox.setHint(R.string.txtbox_forename);
        surenameBox.setHint(R.string.txtbox_surename);
        phoneBox.setHint(R.string.txtbox_phone);
        emailBox.setHint(R.string.txtbox_email);
        subjectBox.setHint(R.string.txtbox_subject);
        msgBox.setHint(R.string.txtbox_msg);

        // on send
        send_contact_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // start send-task
                startAsyncSendTask(v);
            }
        });
    }

    // send pre-setup
    public void startAsyncSendTask(View view) {
        // get data
        String forename = forenameBox.getText().toString();
        String surename = surenameBox.getText().toString();
        String phone = phoneBox.getText().toString();
        String email = emailBox.getText().toString();
        String subject = subjectBox.getText().toString();
        String msg = msgBox.getText().toString();

        // validate input
        if (forenameBox.getText().toString().isEmpty()) {
            // forename empty
            Toast.makeText(getApplicationContext(), R.string.notification_forename_empty, Toast.LENGTH_SHORT).show();
        } else if (surenameBox.getText().toString().isEmpty()) {
            // surename empty
            Toast.makeText(getApplicationContext(), R.string.notification_surename_empty, Toast.LENGTH_SHORT).show();
        } else if (phoneBox.getText().toString().isEmpty()) {
            // phone empty
            Toast.makeText(getApplicationContext(), R.string.notification_phone_empty, Toast.LENGTH_SHORT).show();
        } else if (emailBox.getText().toString().isEmpty()) {
            // email empty
            Toast.makeText(getApplicationContext(), R.string.notification_email_empty, Toast.LENGTH_SHORT).show();
        } else if (subjectBox.getText().toString().isEmpty()) {
            // subject empty
            Toast.makeText(getApplicationContext(), R.string.notification_subject_empty, Toast.LENGTH_SHORT).show();
        } else if (msgBox.getText().toString().isEmpty()) {
            // msg empty
            Toast.makeText(getApplicationContext(), R.string.notification_msg_empty, Toast.LENGTH_SHORT).show();
        } else  if (forename.length() < 3 ^ forename.length() > 20) {
            // invalid forename
            Toast.makeText(getApplicationContext(), R.string.notification_forename_invalid, Toast.LENGTH_SHORT).show();
        } else if (surename.length() < 3 ^ surename.length() > 20) {
            // invalid surename
            Toast.makeText(getApplicationContext(), R.string.notification_surename_invalid, Toast.LENGTH_SHORT).show();
        } else if (phone.length() > 17) {
            // invalid phone
            Toast.makeText(getApplicationContext(), R.string.notification_phone_invalid, Toast.LENGTH_SHORT).show();
        } else if (subject.length() < 5 ^ subject.length() > 120) {
            // invalid subject
            Toast.makeText(getApplicationContext(), R.string.notification_subject_invalid, Toast.LENGTH_SHORT).show();
        } else if  (msg.length() < 20 || msg.length() > 2048) {
            // invalid msg
            Toast.makeText(getApplicationContext(), R.string.notification_msg_invalid, Toast.LENGTH_SHORT).show();
        } else {
            // validate email
            if (!isValidEmail(email)) {
                // invalid email
                Toast.makeText(getApplicationContext(), R.string.notification_email_invalid, Toast.LENGTH_SHORT).show();
            }   else {
                // start AsyncContactActivity
                new AsyncContactActivity(this).execute(forename, surename, phone, email, subject, msg);
            }
        }
    }

    // email validation method
    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            // return result
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    // link menu_layout with actionbar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contact, menu);
        return true;
    }

    // add items to menu
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // on item_click event (handle actions)
        if (id == R.id.action_news) {
            // start NewsActivity
            Intent myIntent = new Intent(ContactActivity.this, NewsActivity.class);
            ContactActivity.this.startActivity(myIntent);

            return true;
        } else if (id == R.id.action_profile) {
            // get profile_ID
            SharedPreferences sharedPreferences= getSharedPreferences("settings", 0);
            String profile_id = sharedPreferences.getString("profile_id", "");

            // set search_ID
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("search_id", profile_id);
            editor.commit();

            // start ProfileActivity
            Intent myIntent = new Intent(ContactActivity.this, ProfileActivity.class);
            ContactActivity.this.startActivity(myIntent);

            return true;
        } else if (id == R.id.action_map) {
            // start MapActivity
            Intent myIntent = new Intent(ContactActivity.this, MapActivity.class);
            ContactActivity.this.startActivity(myIntent);

            return true;
        } else if (id == R.id.action_view) {
            // start ViewActivity
            Intent myIntent = new Intent(ContactActivity.this, ViewActivity.class);
            ContactActivity.this.startActivity(myIntent);

            return true;
        } else if (id == R.id.action_logout) {
            // set ID to invalid ID (0) - user is logged out (as guest)
            SharedPreferences sharedPreferences= getSharedPreferences("settings", 0);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("id", "0");
            editor.commit();

            // start LoginActivity
            Intent myIntent = new Intent(ContactActivity.this, LoginActivity.class);
            ContactActivity.this.startActivity(myIntent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}