package me.vinitagrawal.bullets.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class ArticleContract {

    public static final String CONTENT_AUTHORITY = "me.vinitagrawal.bullets";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_ARTICLE = "article";

    public static final class ArticleEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_ARTICLE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ARTICLE;
        public static final String TABLE_NAME = "articles";

        public static final String COLUMN_ARTICLE_ID = "article_id";
        public static final String COLUMN_ARTICLE_TITLE = "title";
        public static final String COLUMN_ARTICLE_MEDIA = "media";
        public static final String COLUMN_ARTICLE_PERMALINK = "permalink";
        public static final String COLUMN_ARTICLE_SOURCE_NAME = "source_name";
        public static final String COLUMN_ARTICLE_SOURCE_LOGO_URL = "source_logo_url";
        public static final String COLUMN_ARTICLE_AUTHOR = "author";
        public static final String COLUMN_ARTICLE_CATEGORY = "category";
        public static final String COLUMN_ARTICLE_SENTENCES = "sentences";
        public static final String COLUMN_ARTICLE_PUBLISHED_AT = "published_at";

        public static Uri buildArticleUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
