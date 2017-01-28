package me.vinitagrawal.bullets.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class ArticleProvider extends ContentProvider {

    private ArticleDbHelper articleDbHelper;

    @Override
    public boolean onCreate() {
        articleDbHelper = new ArticleDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor = articleDbHelper.getReadableDatabase().query(ArticleContract.ArticleEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return ArticleContract.ArticleEntry.CONTENT_TYPE;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = articleDbHelper.getWritableDatabase();
        Uri returnUri;
        long _id = 0;

        _id = db.insert(ArticleContract.ArticleEntry.TABLE_NAME, null, values);
        if(_id > 0)
            returnUri = ArticleContract.ArticleEntry.buildArticleUri(_id);
        else
            throw new SQLException("Failed to insert row into " + uri);

        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = articleDbHelper.getWritableDatabase();
        int rowsDeleted;

        if(selection == null)
            selection = "1";

        rowsDeleted = db.delete(ArticleContract.ArticleEntry.TABLE_NAME, selection, selectionArgs);

        if(rowsDeleted!=0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = articleDbHelper.getWritableDatabase();
        int rowsUpdated;

        rowsUpdated = db.update(ArticleContract.ArticleEntry.TABLE_NAME, values, selection, selectionArgs);
        if(rowsUpdated!=0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return 0;
    }
}
