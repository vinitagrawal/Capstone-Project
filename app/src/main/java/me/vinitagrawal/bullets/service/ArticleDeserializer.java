package me.vinitagrawal.bullets.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import me.vinitagrawal.bullets.model.Article;

public class ArticleDeserializer implements JsonDeserializer<Article> {

    @Override
    public Article deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        final JsonObject jsonObject = json.getAsJsonObject();
        Article article = new Article();

        try {
            article.setId(jsonObject.get("id").getAsInt());
            article.setTitle(jsonObject.get("title").getAsString());

            //deserialize date
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            article.setPublishedAt(simpleDateFormat.parse(jsonObject.get("published_at").getAsString()));

            //deserialize source
            JsonObject source = jsonObject.get("source").getAsJsonObject();
            if (source != null) {
                article.setSourceName(source.get("name").getAsString());
                article.setSourceLogoUrl(source.get("logo_url")!=null ? source.get("logo_url").getAsString() : "");
            }

            //deserialize author
            JsonObject author = jsonObject.get("author").getAsJsonObject();
            if (author != null) {
                article.setAuthor(author.get("name").getAsString());
            }

            //deserialize media
            JsonArray media = jsonObject.get("media").getAsJsonArray();
            if (media.size()>0) {
                article.setMedia(media.get(0).getAsJsonObject().get("url").getAsString());
            }

            //deserialize link
            JsonObject links = jsonObject.get("links").getAsJsonObject();
            if (links != null) {
                article.setPermalink(links.get("permalink").getAsString());
            }

            //deserialize sentences
            JsonArray summaryArray = jsonObject.get("summary").getAsJsonObject().getAsJsonArray("sentences");
            if(summaryArray.size()>0) {
                ArrayList<String> sentences = new ArrayList<>(summaryArray.size());
                for (int i = 0; i < summaryArray.size(); i++) {
                    sentences.add(summaryArray.get(i).getAsString().replaceAll("\n\n", ". ").replaceAll("\n", ". "));
                }
                article.setSentences(sentences);
            }

            return article;
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
