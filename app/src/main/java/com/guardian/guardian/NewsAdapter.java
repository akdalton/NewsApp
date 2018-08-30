package com.guardian.guardian;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_item, parent, false);
        }

        // Find news at the given position in the list
        final News currentNews = getItem(position);

        // Set title name of the article to display.
        TextView newsTitleTextView = listItemView.findViewById(R.id.title_text_view);
        final String title = currentNews.getArticleTitle();
        newsTitleTextView.setText(title);


        // Set news section of the article to display.
        TextView newsCategory = listItemView.findViewById(R.id.category_text_view);
        final String category = currentNews.getArticleSection();
        newsCategory.setText(category);

        // Set the publication date of article to display.
        TextView articlePublicationDate = listItemView.findViewById(R.id.date_text_view);
        final String publicationDate = currentNews.getArticlePublicationDate();
        final String newsDate = formatDate(publicationDate);
        articlePublicationDate.setText(newsDate);

        // set the author's name of the article to display.
        TextView articleAuthor = listItemView.findViewById(R.id.author_text_view);
        final String author = currentNews.getArticleAuthor();
        articleAuthor.setText(author);

        return listItemView;
    }

    /**
     * This method format the date into a specific pattern.
     * @param dateObj is the web publication date.
     * @return a date formatted's string.
     */
    private String formatDate(String dateObj) {
        String dateFormatted = "";
        SimpleDateFormat inputDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        SimpleDateFormat outputDate = new SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault());

        try {
            Date newDate = inputDate.parse(dateObj);
            return outputDate.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormatted;
    }
}

