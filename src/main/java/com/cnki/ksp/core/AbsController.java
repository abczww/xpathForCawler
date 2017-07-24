package com.cnki.ksp.core;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.beans.CaptureRecord;
import com.cnki.ksp.dao.ArticleDao;
import com.cnki.ksp.dao.CaptureRecordDao;
import com.cnki.ksp.processor.AutohomeProcessor;

public abstract class AbsController implements CrawlerController, Runnable {

    @Autowired
    protected ArticleDao articleDao;
    @Autowired
    protected CaptureRecordDao crDao;
    protected Observer observer;
    /**
     * the forward urls.
     */
    protected List<String> articleUrls;
    protected Properties controllerProperties;
    protected Properties processorProperties;

    public void saveArticlesAndClean() throws InterruptedException {
        for (int i = 0; i < ArticlePool.getInstance().getFailUrls().size(); i++) {
            String url = ArticlePool.getInstance().getFailUrls().get(i);
            Processor processor = new AutohomeProcessor(url, processorProperties, observer);
            processor.execute();
            Thread.sleep(3000);
        }
        saveArticles();

    }

    private int saveArticles() {
        List<Article> arts = ArticlePool.getInstance().getAllArticles();
        List<Article> duplicatedArts = new ArrayList<Article>();
        for (Article art : arts) {
            if (!checkAndFindArticle(art)) {
                articleDao.saveOrUpdate(art);
            } else {
                System.out.println("Find duplicated records: " + art);
                duplicatedArts.add(art);
            }
        }

        observer.appendInfo("Save %d records in all", arts.size());
        observer.appendInfo("Found %d duplicated articles:", duplicatedArts.size());
        for (Article art : duplicatedArts) {
            observer.appendInfo(art.getTitle() + ": " + art.getUrl());
        }

        ArticlePool.getInstance().clear();
        return arts.size();

    }

    public boolean checkAndFindArticle(Article art) {
        List<Article> ids = articleDao.checkDuplicated(art);
        if (ids.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void run() {
        try {
            CaptureRecord cr = new CaptureRecord();
            cr = recrod(cr);
            execute();
            recrod(cr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CaptureRecord recrod(CaptureRecord cr) {
        if (cr.getId() == 0) {
            cr = new CaptureRecord();
            cr.setCreatedBy(System.getenv("UESRNAME"));
            cr.setKspId(Integer.parseInt(String.valueOf(controllerProperties.get("kspId"))));
            cr.setKspName(String.valueOf(controllerProperties.get("kspName")));
            cr.setUrl(String.valueOf(controllerProperties.get("entranceUrl")));
        } else {
            cr.setUpdatedBy(System.getenv("USERNAME"));
            cr.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        }
        crDao.saveOrUpdate(cr);
        return cr;
    }


    protected abstract void getAllUrlsByXPath(String url) throws Exception;

    public Properties getControllerProperties() {
        return controllerProperties;
    }

    public void setControllerProperties(Properties controllerProperties) {
        this.controllerProperties = controllerProperties;
    }

    public Properties getProcessorProperties() {
        return processorProperties;
    }

    public void setProcessorProperties(Properties processorProperties) {
        this.processorProperties = processorProperties;
    }

    protected void putProcessorToPool(Processor processor) {
        // ExecutorPool.put(processor);
    }

    public int getKspId() {
        Object value = controllerProperties.get("kspId");
        return null == value ? 0 : Integer.parseInt(String.valueOf(value));
    }

    public String getKspName() {
        Object value = controllerProperties.get("kspName");
        return null == value ? "" : String.valueOf(value);
    }
}
