package com.guardian.guardian;

/**
 * Creating a custom class called News.
 * {@link News} represents a single platform release.
 * It contains the variable that the user read.
 */
public class News {

    // Initialize strings.
    private String articleTitle;
    private String articleSection;
    private String articlePublicationDate;
    private String articleUrl;
    private String articleAuthor;

    /**
     * Default constructor {@link News} object.
     * @param articleSection is the sectionName name for news.
     * @param articleTitle is the webTitle of the news.
     * @param articlePublicationDate is the publication's webPublicationDate of the news.
     * @param articleUrl is the webUrl's name of the news.
     * @param articleAuthor is the author's name of the news.
     */
    public News(String articleTitle, String articleSection, String articlePublicationDate,
                String articleUrl, String articleAuthor) {

        this.articleTitle = articleTitle;
        this.articleSection = articleSection;
        this.articlePublicationDate = articlePublicationDate;
        this.articleUrl = articleUrl;
        this.articleAuthor = articleAuthor;
    }

    /**
     *
     * @return the string of the articleTitle's name.
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * @return the string of the section's name.
     */
    public String getArticleSection() {
        return articleSection;
    }

    /**
     * @return the string of the articlePublication's date
     */
    public String getArticlePublicationDate() {
        return articlePublicationDate;
    }

    /**
     * @return the string of the articleUrl's name.
     */
    public String getArticleUrl() {
        return articleUrl;
    }

    /**
     * @return the string of the author's name
     */
    public String getArticleAuthor() {
        return articleAuthor;
    }
}

