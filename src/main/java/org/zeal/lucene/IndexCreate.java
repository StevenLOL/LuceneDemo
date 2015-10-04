package org.zeal.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;

import static org.apache.lucene.index.IndexWriterConfig.OpenMode;

/**
 * @desc 索引创建
 * Created by Jiwei on 2015/10/3.
 */
public class IndexCreate {
    public static void main(String[] args) {

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_43, analyzer);
        indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
        Directory directory = null;
        IndexWriter indexWriter = null;
        try {
            directory = FSDirectory.open(new File("D://index/test"));
            if (indexWriter.isLocked(directory)) {
                indexWriter.unlock(directory);
            }
            indexWriter = new IndexWriter(directory, indexWriterConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document doc1 = new Document();
        doc1.add(new StringField("id", "abcde", Field.Store.YES));
        doc1.add(new TextField("content", "Lucene开发demo", Field.Store.YES));
        doc1.add(new IntField("num", 2, Field.Store.YES));
        try {
            indexWriter.addDocument(doc1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Document doc2 = new Document();
        doc2.add(new StringField("id", "abcde", Field.Store.YES));
        doc2.add(new TextField("content", "Lucene开发demo", Field.Store.YES));
        doc2.add(new IntField("num", 2, Field.Store.YES));
        try {
            indexWriter.addDocument(doc2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            indexWriter.commit();
            indexWriter.close();
            directory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
