package com.example.lms.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "library.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    private static final String TABLE_BOOKS = "books";
    private static final String TABLE_USERS = "users";

    // Books Table Columns
    private static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_AUTHOR = "author";
    private static final String COLUMN_IS_ISSUED = "isIssued";

    // Users Table Columns
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT," // Add this line
                + COLUMN_ID + " INTEGER,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_AUTHOR + " TEXT,"
                + COLUMN_IS_ISSUED + " INTEGER DEFAULT 0" + ")";
        db.execSQL(CREATE_BOOKS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // Method to add a book
    public void addBook(String title, String author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR, author);
        db.insert(TABLE_BOOKS, null, values);
        db.close();
    }

    // Method to get all books
    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_BOOKS, new String[]{"_id", COLUMN_ID, COLUMN_TITLE, COLUMN_AUTHOR, COLUMN_IS_ISSUED},
                null, null, null, null, null);
    }


    // Method to issue a book
    public void issueBook(int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_ISSUED, 1); // Set isIssued to 1
        db.update(TABLE_BOOKS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});
        db.close();
    }

    // Method to return a book
    public void returnBook(int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_IS_ISSUED, 0); // Set isIssued to 0
        db.update(TABLE_BOOKS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});
        db.close();
    }

    public Cursor searchBooks(String query) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_TITLE + " LIKE ? OR " + COLUMN_AUTHOR + " LIKE ?";
        String[] selectionArgs = {"%" + query + "%", "%" + query + "%"};
        return db.query(TABLE_BOOKS, null, selection, selectionArgs, null, null, null);
    }

    public Cursor getBookById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_BOOKS, null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
    }


}
