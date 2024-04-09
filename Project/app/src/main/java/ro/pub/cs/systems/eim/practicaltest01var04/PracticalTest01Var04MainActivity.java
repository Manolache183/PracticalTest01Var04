package ro.pub.cs.systems.eim.practicaltest01var04;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    Button navigateToSecondaryActivityButton, displayInformationButton;
    EditText studentNameEditText, groupEditText;
    CheckBox studentNameCheckBox, groupCheckBox;
    TextView displayInformationTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

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
        });
    }
}