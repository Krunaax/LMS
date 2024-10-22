package com.example.lms.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lms.Database.DatabaseHelper;
import com.example.lms.R;

public class IssueBookActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private EditText edtBookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_book);

        dbHelper = new DatabaseHelper(this);
        edtBookId = findViewById(R.id.edt_book_id);
        Button btnIssue = findViewById(R.id.btn_issue_book);
        Button btnReturn = findViewById(R.id.btn_return_book);

        btnIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bookId = Integer.parseInt(edtBookId.getText().toString());
                dbHelper.issueBook(bookId);
                Toast.makeText(IssueBookActivity.this, "Book Issued!", Toast.LENGTH_SHORT).show();
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int bookId = Integer.parseInt(edtBookId.getText().toString());
                dbHelper.returnBook(bookId);
                Toast.makeText(IssueBookActivity.this, "Book Returned!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
