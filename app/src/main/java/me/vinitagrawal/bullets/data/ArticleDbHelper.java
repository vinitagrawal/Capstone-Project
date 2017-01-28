package me.vinitagrawal.bullets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import me.vinitagrawal.bullets.data.ArticleContract.ArticleEntry;

public class ArticleDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "articles.db";

    public ArticleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_ARTICLE_TABLE = "CREATE TABLE " + ArticleEntry.TABLE_NAME + " (" +
                ArticleEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +

                ArticleEntry.COLUMN_ARTICLE_ID + " INTEGER NOT NULL, " +
                ArticleEntry.COLUMN_ARTICLE_TITLE + " TEXT NOT NULL, " +
                ArticleEntry.COLUMN_ARTICLE_SENTENCES + " TEXT , " +
                ArticleEntry.COLUMN_ARTICLE_CATEGORY + " TEXT NOT NULL, " +
                ArticleEntry.COLUMN_ARTICLE_MEDIA + " TEXT , " +
                ArticleEntry.COLUMN_ARTICLE_SOURCE_NAME + " TEXT NOT NULL, " +
                ArticleEntry.COLUMN_ARTICLE_SOURCE_LOGO_URL + " TEXT , " +
                ArticleEntry.COLUMN_ARTICLE_AUTHOR + " TEXT , " +
                ArticleEntry.COLUMN_ARTICLE_PERMALINK + " TEXT , " +
                ArticleEntry.COLUMN_ARTICLE_PUBLISHED_AT + " TEXT NOT NULL " +
                " );";

        db.execSQL(CREATE_ARTICLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ ArticleEntry.TABLE_NAME);
        onCreate(db);
    }
}
