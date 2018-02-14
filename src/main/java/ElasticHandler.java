import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.*;

/**
 * Created by GiangNT on 2/14/2018.
 */
public class ElasticHandler {
    public void indexRecord(Client client, Record record){
        try {
            XContentBuilder builder = jsonBuilder()
                    .startObject();
//            private String title;
//            private ArrayList<String> authorList;
//            private String pages;
//            private String year;
//            private String volume;
//            private String journal;
//            private String number;
//            private String doi;
//            private String url;
//            private String crossref;
//            private String bookTitle;
            if (record.getTitle() != null){
                builder.field("title", record.getTitle());
            }
            if (record.getAuthorList().size() != 0){
                builder.field("author", record.getAuthorList());
            }
            if (record.getPages() != null){
                builder.field("page", record.getPages());
            }
            if (record.getYear() != null){
                builder.field("year", record.getYear());
            }
            if (record.getVolume() != null){
                builder.field("volume", record.getVolume());
            }
            if (record.getJournal() != null){
                builder.field("journal", record.getJournal());
            }
            if (record.getNumber() != null){
                builder.field("number", record.getNumber());
            }
            if (record.getDoi() != null){
                builder.field("doi", record.getDoi());
            }
            if (record.getUrl() != null){
                builder.field("url", record.getUrl());
            }
            if (record.getCrossref() != null){
                builder.field("crossref", record.getCrossref());
            }
            if (record.getBookTitle() != null){
                builder.field("booktitle", record.getBookTitle());
            }
            builder.endObject();
            IndexResponse response = client.prepareIndex("record", "id", String.valueOf(record.getId())).setSource(builder).get();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
