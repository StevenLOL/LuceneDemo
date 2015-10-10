package org.zeal.crawl.zongheng;

import org.zeal.db.ZonghengDb;
import org.zeal.model.NovelIntroModel;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangjw on 2015/10/10.
 */
public class IntroPageThread extends Thread{
    private boolean flag = false;

    public IntroPageThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        flag = true;
        try {
            ZonghengDb db = new ZonghengDb();
            while (flag) {
                //随机获取一个待采集的简介页url
                String url = db.getRandIntroPageUrl(1);
                if (url != null) {
                    IntroPage intro = new IntroPage(url);
                    NovelIntroModel bean =	intro.getNovelIntro();
                    //采集小说章节列表页信息
                    ChapterPage chapterPage = new ChapterPage(bean.getChapterlisturl());
                    List<String[]> chapters = chapterPage.getChaptersInfo();
                    bean.setChapterCount(chapters == null ? 0 : chapters.size());
                    //更新小说简介信息
                    db.updateInfo(bean);
                    //插入待采集的章节列表
                    db.saveChapters(chapters);
                    //如果本次有待采集的资源，睡眠一个时间，没有待采集的资源，睡眠另一个时间
                    TimeUnit.MILLISECONDS.sleep(500);
                }else {
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        IntroPageThread thread = new IntroPageThread("novelinfo");
        thread.start();
    }
}
