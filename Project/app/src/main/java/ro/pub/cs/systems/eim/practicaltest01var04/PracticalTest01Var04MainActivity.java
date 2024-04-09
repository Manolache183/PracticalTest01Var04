package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    Button navigateToSecondaryActivityButton, displayInformationButton;
    EditText studentNameEditText, groupEditText;
    CheckBox studentNameCheckBox, groupCheckBox;
    TextView displayInformationTextView;

    ActivityResultLauncher<Intent> activityResultLauncher;

    private IntentFilter intentFilter = new IntentFilter();

    boolean startedService = false;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_RECEIVER_TAG, intent.getStringExtra(Constants.BROADCAST_RECEIVER_EXTRA));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        for (String actionType : Constants.actionTypes) {
            intentFilter.addAction(actionType);
        }
        navigateToSecondaryActivityButton = findViewById(R.id.navigate_to_secondary_activity_button);
        displayInformationButton = findViewById(R.id.display_information_button);
        studentNameEditText = findViewById(R.id.student_name_edit_text);
        groupEditText = findViewById(R.id.group_edit_text);
        studentNameCheckBox = findViewById(R.id.student_name_check_box);
        groupCheckBox = findViewById(R.id.group_check_box);
        displayInformationTextView = findViewById(R.id.display_information_text_view);

        displayInformationButton.setOnClickListener(view -> {
            String studentName = studentNameEditText.getText().toString();
            String group = groupEditText.getText().toString();
            String text = "";
            if (studentNameCheckBox.isChecked()) {
                if (studentName.isEmpty()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                } else {
                    text += studentName;
                }
            }
            if (groupCheckBox.isChecked()) {
                if (group.isEmpty()) {
                    Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
                } else {
                    text += " " + group;
                }
            }

            displayInformationTextView.setText(text);
            // start service if both checkboxes ar completed and the service is not already started and the text is not empty
            if (studentNameCheckBox.isChecked() && groupCheckBox.isChecked() && !startedService && !text.isEmpty()) {
                Intent intent = new Intent(this, PracticalTest01Var04Service.class);
                intent.putExtra(Constants.STUDENT_NAME, studentName);
                intent.putExtra(Constants.GROUP, group);
                intent.putExtra(Constants.DISPLAY_INFORMATION, text);
                startedService = true;
                getApplicationContext().startService(intent);
            }


        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "CANCELED", Toast.LENGTH_LONG).show();
            }
        });

        navigateToSecondaryActivityButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, PractivalTest01Var04SecondaryActivity.class);
            intent.putExtra(Constants.STUDENT_NAME, studentNameEditText.getText().toString());
            intent.putExtra(Constants.GROUP, groupEditText.getText().toString());
            activityResultLauncher.launch(intent);
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.STUDENT_NAME, studentNameEditText.getText().toString());
        savedInstanceState.putString(Constants.GROUP, groupEditText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(Constants.STUDENT_NAME)) {
            studentNameEditText.setText(savedInstanceState.getString(Constants.STUDENT_NAME));
        } else {
            studentNameEditText.setText("");
        }
        if (savedInstanceState.containsKey(Constants.GROUP)) {
            groupEditText.setText(savedInstanceState.getString(Constants.GROUP));
        } else {
            groupEditText.setText("");
        }
        if (savedInstanceState.containsKey(Constants.DISPLAY_INFORMATION)) {
            displayInformationTextView.setText(savedInstanceState.getString(Constants.DISPLAY_INFORMATION));
        } else {
            displayInformationTextView.setText("");
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Var04Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}