package com.example.lms.Activities;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lms.Database.DatabaseHelper;
import com.example.lms.R;

public class UserActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ListView listView;
    private EditText edtSearch;
    private SimpleCursorAdapter adapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        dbHelper = new DatabaseHelper(this);
        listView = findViewById(R.id.list_view);
        edtSearch = findViewById(R.id.edt_search);

        loadBooks();

        // Add a TextWatcher to filter results as the user types
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterBooks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadBooks() {
        cursor = dbHelper.getAllBooks();
        String[] from = {DatabaseHelper.COLUMN_TITLE, DatabaseHelper.COLUMN_AUTHOR};
        int[] to = {android.R.id.text1, android.R.id.text2};
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to, 0);
        listView.setAdapter(adapter);
    }

    private void filterBooks(String query) {
        Cursor filteredCursor = dbHelper.searchBooks(query);
        adapter.changeCursor(filteredCursor);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
    }


}
