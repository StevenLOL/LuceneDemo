package org.zeal.model;

import org.apache.lucene.document.Document;

import java.util.List;

/**
 * Created by wangjw on 2015/10/10.
 */
public class SearchResultBean {
    private int count;
    private List<Document> datas;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Document> getDatas() {
        return datas;
    }

    public void setDatas(List<Document> datas) {
        this.datas = datas;
    }
}
