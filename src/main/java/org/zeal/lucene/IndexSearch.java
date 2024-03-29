package org.zeal.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;

/**
 * @desc 索引搜索
 * Created by Zeal on 2015/10/4.
 */
public class IndexSearch {
    public static void main(String[] args) {
        Directory directory = null;
        try {
            directory = FSDirectory.open(new File("D://index/test"));
            DirectoryReader directoryReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(directoryReader);
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
            QueryParser parser = new QueryParser(Version.LUCENE_43, "content", analyzer);
            Query query = parser.parse("Lucene");
            TopDocs topDocs = searcher.search(query, 10);
            if(topDocs != null){
                System.out.println("符合条件的文档总数为:" + topDocs.totalHits);
                for (int i = 0; i<topDocs.scoreDocs.length; i++){
                    Document document = searcher.doc(topDocs.scoreDocs[i].doc);
                    System.out.println("id = " + document.get("id"));
                    System.out.println("content = " + document.get("content"));
                    System.out.println("num = " + document.get("num"));
                }
            }
            directory.close();
            directoryReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
