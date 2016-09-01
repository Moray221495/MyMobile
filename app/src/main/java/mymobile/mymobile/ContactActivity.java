// package
package mymobile.mymobile;

// import android classes
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        // TODO VALIDATION OF INPUT VIA ALGORYTHM

        // start AsyncContactActivity
        new AsyncContactActivity(this).execute(forename, surename, phone, email, subject, msg);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}