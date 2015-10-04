package org.zeal.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.StringReader;

/**
 * @desc 分词技术
 * Created by Zeal on 2015/10/4.
 */
public class AnalyzerDemo {
    private static String str = "我是一名程序员！";
    public static void print(Analyzer analyzer){
        StringReader stringReader = new StringReader(str);
        try {
            TokenStream tokenStream = analyzer.tokenStream("", stringReader);
            tokenStream.reset();
            CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println(analyzer.getClass() + "分词技术");
            while (tokenStream.incrementToken()){
                System.out.print(termAttribute.toString() + "|");
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Analyzer analyzer = null;
        analyzer = new StandardAnalyzer(Version.LUCENE_43);
        AnalyzerDemo.print(analyzer);

        analyzer = new WhitespaceAnalyzer(Version.LUCENE_43);
        AnalyzerDemo.print(analyzer);

        analyzer = new SimpleAnalyzer(Version.LUCENE_43);
        AnalyzerDemo.print(analyzer);

        analyzer = new CJKAnalyzer(Version.LUCENE_43);
        AnalyzerDemo.print(analyzer);

        analyzer = new KeywordAnalyzer();
        AnalyzerDemo.print(analyzer);

        analyzer = new StopAnalyzer(Version.LUCENE_43);
        AnalyzerDemo.print(analyzer);

        analyzer = new IKAnalyzer();
        AnalyzerDemo.print(analyzer);
    }
}
