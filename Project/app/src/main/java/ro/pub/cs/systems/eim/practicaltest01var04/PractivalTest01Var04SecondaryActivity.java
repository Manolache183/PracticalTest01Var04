package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PractivalTest01Var04SecondaryActivity extends AppCompatActivity {

    Button cancelButton, okButton;
    TextView studentNameTextView, groupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practival_test01_var04_secondary);

        cancelButton = findViewById(R.id.cancel_button);
        okButton = findViewById(R.id.ok_button);
        studentNameTextView = findViewById(R.id.second_name_text_view);
        groupTextView = findViewById(R.id.second_group_text_view);

        Intent intentFromParent = getIntent();
        if (intentFromParent != null && intentFromParent.getExtras().containsKey(Constants.STUDENT_NAME)) {
            studentNameTextView.setText(intentFromParent.getStringExtra(Constants.STUDENT_NAME));
        }
        if (intentFromParent != null && intentFromParent.getExtras().containsKey(Constants.GROUP)) {
            groupTextView.setText(intentFromParent.getStringExtra(Constants.GROUP));
        }

        okButton.setOnClickListener(view -> {
            Intent intentToParent = new Intent();
            setResult(RESULT_OK, intentToParent);
            finish();
        });

        cancelButton.setOnClickListener(view -> {
            Intent intentToParent = new Intent();
            setResult(RESULT_CANCELED, intentToParent);
            finish();
        });
    }
}