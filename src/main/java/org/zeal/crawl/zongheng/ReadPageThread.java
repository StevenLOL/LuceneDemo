package org.zeal.crawl.zongheng;

import org.zeal.db.ZonghengDb;
import org.zeal.model.NovelChapterModel;
import org.zeal.model.NovelReadModel;
import org.zeal.util.ParseMD5;

import java.util.concurrent.TimeUnit;

/**
 * Created by wangjw on 2015/10/10.
 */
public class ReadPageThread extends Thread{
    private boolean flag = false;
    public ReadPageThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        flag = true;
        ZonghengDb db = new ZonghengDb();
        while (flag) {
            try {
                //随机获取待采集的阅读页
                NovelChapterModel chapter = db.getRandReadPageUrl(1);
                if (chapter != null) {
                    ReadPage read = new ReadPage(chapter.getUrl());
                    NovelReadModel novel = read.getNovelRead();
                    if (novel == null) {
                        continue;
                    }
                    novel.setChapterId(chapter.getChapterId());
                    novel.setTime(chapter.getTime());
                    novel.setUrl(chapter.getUrl());
                    //保存阅读页信息
                    db.saveNovelRead(novel);
                    //将状态修改为不需要采集
                    db.updateChapterState(ParseMD5.parseStrToMd5L32(novel.getUrl()), 0);
                    //如果本次有待采集的资源，睡眠一个时间，没有待采集的资源，睡眠另一个时间
                    TimeUnit.MILLISECONDS.sleep(500);
                } else {
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ReadPageThread thread = new ReadPageThread("novel read page");
        thread.start();
    }

}
