package me.vinitagrawal.bullets.view.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import me.vinitagrawal.bullets.R;
import me.vinitagrawal.bullets.Utility.Constants;
import me.vinitagrawal.bullets.data.ArticleContract;
import me.vinitagrawal.bullets.model.Article;
import me.vinitagrawal.bullets.view.activity.ArticleDetailActivity;
import me.vinitagrawal.bullets.view.activity.HomeActivity;

public class BulletsWidgetRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            Cursor cursor = null;
            @Override
            public void onCreate() {

            }

            @Override
            public void onDataSetChanged() {
                if(cursor!=null) {
                    cursor.close();
                }
                final long identityToken = Binder.clearCallingIdentity();

                cursor = getContentResolver().query(ArticleContract.ArticleEntry.CONTENT_URI,
                        HomeActivity.ARTICLE_COLUMNS,
                        ArticleContract.ArticleEntry.COLUMN_ARTICLE_CATEGORY + " = ?",
                        new String[]{Constants.CATEGORY_HOME},
                        null);

                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                if(cursor==null)
                    return 0;
                else
                    return cursor.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if(position == AdapterView.INVALID_POSITION || cursor == null || !cursor.moveToPosition(position))
                    return null;

                RemoteViews views = new RemoteViews(getPackageName(), R.layout.list_item_article);

                Article article = Article.fromCursor(cursor);
                views.setTextViewText(R.id.widget_article_title, article.getTitle());

                Bundle bundle = new Bundle();
                bundle.putParcelable(ArticleDetailActivity.ARTICLE_INTENT_KEY, article);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                views.setOnClickFillInIntent(R.id.widget_article_title, intent);

                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if(cursor.moveToPosition(position))
                    return cursor.getLong(cursor.getColumnIndex(ArticleContract.ArticleEntry.COLUMN_ARTICLE_ID));
                else
                    return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
