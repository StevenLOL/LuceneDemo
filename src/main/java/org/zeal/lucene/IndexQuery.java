package org.zeal.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.util.Version;

/**
 * Created by Zeal on 2015/10/4.
 */
public class IndexQuery {
    public static void main(String[] args){
        String key = "Lucene";
        String field = "name";
        String [] fields = {"name","content"};
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
        Query query = null;
        QueryParser parser = new QueryParser(Version.LUCENE_43, field,analyzer);
        try {
            query = parser.parse(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(QueryParser.class + query.toString());
        MultiFieldQueryParser parser1 = new MultiFieldQueryParser(Version.LUCENE_43, fields,analyzer);
        try {
            query = parser1.Query(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(MultiFieldQueryParser.class + query.toString());

        query = new TermQuery(new Term(field, key));
        System.out.println(TermQuery.class + query.toString());

        query = new PrefixQuery(new Term(field, key));
        System.out.println(PrefixQuery.class + query.toString());

        PhraseQuery query1 = new PhraseQuery();
        query1.setSlop(2);
        query1.add(new Term(field, "Lucene"));
        query1.add(new Term(field, "Demo"));
        System.out.println(PhraseQuery.class + query1.toString());

        query = new WildcardQuery(new Term(field, "Lucene?"));
        System.out.println(WildcardQuery.class + query.toString());

        query = TermRangeQuery.newStringRange(field, "abc", "azz", false, false);
        System.out.println(TermRangeQuery.class + query.toString());

        BooleanQuery query2 = new BooleanQuery();
        query2.add(new TermQuery(new Term(field, "Lucene")), BooleanClause.Occur.SHOULD);
        query2.add(new TermQuery(new Term(field, "Demo")), BooleanClause.Occur.MUST);
        query2.add(new TermQuery(new Term(field, "开发")), BooleanClause.Occur.MUST_NOT);
        System.out.println(BooleanQuery.class + query2.toString());
    }
}
