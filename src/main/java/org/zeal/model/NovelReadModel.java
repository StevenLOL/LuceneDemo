package org.zeal.model;

/**
 * Created by wangjw on 2015/10/10.
 */
public class NovelReadModel extends NovelChapterModel {
    private String title;
    private int wordCount;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
