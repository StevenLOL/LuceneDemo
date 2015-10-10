package org.zeal.model;

/**
 * Created by wangjw on 2015/10/10.
 */
public class CrawlListInfo {
    private String url;
    private String info;
    private int frequency;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
