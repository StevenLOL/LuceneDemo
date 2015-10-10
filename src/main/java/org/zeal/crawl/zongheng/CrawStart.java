package org.zeal.crawl.zongheng;

import org.zeal.db.ZonghengDb;
import org.zeal.model.CrawlListInfo;

import java.util.List;

/**
 * Created by wangjw on 2015/10/10.
 */
public class CrawStart {
    private static boolean booleanCrawlList = false;
    private static boolean booleanCrawlIntro = false;
    //简介页采集线程数目
    private static int crawlIntroThreadNum = 2;
    private static boolean booleanCrawlRead = false;
    //阅读页采集线程数目
    private static int crawlReadThreadNum = 10;

    /**
     * @Author:wangjw
     * @Description: 更新列表页采集
     */
    public void startCrawlList(){
        if (booleanCrawlList) {
            return;
        }
        booleanCrawlList = true;
        ZonghengDb db = new ZonghengDb();
        List<CrawlListInfo> infos = db.getCrawlListInfos();
        if (infos == null) {
            return;
        }
        for (CrawlListInfo info : infos) {
            if (info.getUrl() == null || "".equals(info.getUrl())) {
                continue;
            }
            UpdateListThread thread = new UpdateListThread(info.getInfo(), info.getUrl(), info.getFrequency());
            thread.start();
        }
    }

    /**
     * @Author:wangjw
     * @Description: 小说简介页和章节列表页
     */
    public void startCrawlIntro() {
        if (booleanCrawlIntro) {
            return;
        }
        booleanCrawlIntro = true;
        for (int i = 0; i < crawlIntroThreadNum; i++) {
            IntroPageThread thread = new IntroPageThread("novel info thread" + i);
            thread.start();
        }
    }

    /**
     * @Author:wangjw
     * @Description: 小说阅读页
     */
    public void startCrawlRead() {
        if (booleanCrawlRead) {
            return;
        }
        booleanCrawlRead = true;
        for (int i = 0; i < crawlReadThreadNum; i++) {
            ReadPageThread thread = new ReadPageThread("novel read page" + i);
            thread.start();
        }
    }

    public static void main(String[] args) {
        CrawStart start = new CrawStart();
        start.startCrawlList();
        start.startCrawlIntro();
        start.startCrawlRead();
    }

}
