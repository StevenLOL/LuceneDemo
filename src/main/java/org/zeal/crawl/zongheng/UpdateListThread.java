package org.zeal.crawl.zongheng;

import org.zeal.db.ZonghengDb;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by wangjw on 2015/10/10.
 */
public class UpdateListThread extends Thread{
    private boolean flag = false;
    private String url;//抓取的更新列表页URL
    private int frequency;//采集频率

    public UpdateListThread(String name, String url, int frequency){
        super(name);
        this.url = url;
        this.frequency = frequency;
    }

    @Override
    public void run() {
        flag = true;
        ZonghengDb db = new ZonghengDb();
        while (flag){
            try {
                UpdateList updateList = new UpdateList(url);
                List<String> urls = updateList.getPageUrls(true);
                db.saveInfoUrls(urls);
                TimeUnit.SECONDS.sleep(frequency);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        super.run();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        UpdateListThread thread = new UpdateListThread("llist", "http://book.zongheng.com/store/c0/c0/b9/u0/p1/v0/s9/t0/ALL.html", 60);
        thread.start();

    }

}
