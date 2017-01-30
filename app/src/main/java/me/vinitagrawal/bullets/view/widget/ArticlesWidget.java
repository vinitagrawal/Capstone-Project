package me.vinitagrawal.bullets.view.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import me.vinitagrawal.bullets.R;
import me.vinitagrawal.bullets.view.activity.ArticleDetailActivity;
import me.vinitagrawal.bullets.view.activity.HomeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class ArticlesWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.app_name);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.articles_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        views.setRemoteAdapter(R.id.appwidget_list, new Intent(context, BulletsWidgetRemoteViewsService.class));

        Intent intent = new Intent(context, HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

        //open article detail for item selected through widget
        Intent articleDetailIntent = new Intent(context, ArticleDetailActivity.class);
        PendingIntent articleDetailPendingIntent = PendingIntent.getActivity(context, 0, articleDetailIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.appwidget_list, articleDetailPendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

