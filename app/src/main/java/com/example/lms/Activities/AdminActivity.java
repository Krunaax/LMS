package com.example.lms.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lms.Database.DatabaseHelper;
import com.example.lms.R;

public class AdminActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private EditText edtTitle, edtAuthor;
    private Button btnAddBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        dbHelper = new DatabaseHelper(this);
        edtTitle = findViewById(R.id.edt_title);
        edtAuthor = findViewById(R.id.edt_author);
        btnAddBook = findViewById(R.id.btn_add_book);

        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String author = edtAuthor.getText().toString();
                dbHelper.addBook(title, author);
                edtTitle.setText("");
                edtAuthor.setText("");
            }
        });

        Button btnIssueBooks = findViewById(R.id.btn_issue_books);
        btnIssueBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, IssueBookActivity.class));
            }
        });

    }
}
