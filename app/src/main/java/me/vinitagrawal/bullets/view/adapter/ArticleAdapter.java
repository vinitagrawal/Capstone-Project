package me.vinitagrawal.bullets.view.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.vinitagrawal.bullets.R;
import me.vinitagrawal.bullets.model.Article;

import static me.vinitagrawal.bullets.Utility.Utility.getRelativeDate;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private Context mContext;
    private List<Article> articleList;

    public interface ItemClickCallback {

        void onItemSelected(Article article);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView articleCardView;
        ImageView articleImageView;
        ImageView sourceImageView;
        TextView titleTextView;
        TextView sourceTextView;
        TextView timeTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            articleCardView = (CardView) itemView.findViewById(R.id.article_card_view);
            articleImageView = (ImageView) itemView.findViewById(R.id.article_image);
            sourceImageView = (ImageView) itemView.findViewById(R.id.source_image);
            titleTextView = (TextView) itemView.findViewById(R.id.article_title);
            sourceTextView = (TextView) itemView.findViewById(R.id.source_name);
            timeTextView = (TextView) itemView.findViewById(R.id.post_time);
        }
    }

    public ArticleAdapter(Context mContext, List<Article> articleList) {
        this.mContext = mContext;
        this.articleList = articleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_article, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Article article = articleList.get(position);

        Picasso.with(mContext).load(article.getMedia())
                .into(holder.articleImageView);

        if(!article.getSourceLogoUrl().isEmpty()) {
            Picasso.with(mContext).load(article.getSourceLogoUrl())
                    .placeholder(R.color.cardview_light_background)
                    .into(holder.sourceImageView);
        } else
            holder.sourceImageView.setVisibility(View.INVISIBLE);

        holder.titleTextView.setText(article.getTitle());
        holder.sourceTextView.setText(article.getSourceName());
        holder.timeTextView.setText(getRelativeDate(article.getPublishedAt()));

        holder.articleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ItemClickCallback) mContext).onItemSelected(article);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }
}
