import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by GiangNT on 2/10/2018.
 */
public class XmlHandler extends DefaultHandler {
//    <article key="journals/cacm/Gentry10" mdate="2010-04-26">
//    <author>Craig Gentry</author>
//    <title>Computing arbitrary functions of encrypted data.</title>
//<pages>97-105</pages>
//<year>2010</year>
//<volume>53</volume>
//<journal>Commun. ACM</journal>
//<number>3</number>
//<ee>http://doi.acm.org/10.1145/1666420.1666444</ee>
//<url>db/journals/cacm/cacm53.html#Gentry10</url>
//</article>

    TransportAddress address;
    Client client;
    static ArrayList<String> mainTagList = new ArrayList<String>(Arrays
                                            .asList("article", "inproceedings", "proceedings","book", "incollection", "phdthesis",
                                                    "mastersthesis", "www"));
    static int countTitle = 0;
    boolean bTitle = false;
    boolean bAuthor;
    boolean bPages;
    boolean bYear;
    boolean bVolume;
    boolean bJournal;
    boolean bNumber;
    boolean bDoi; // ee tag
    boolean bUrl;
    boolean bCrossref;
    boolean bBookTitle;
    Record record;
    int recordCount = 0;
    long hitsCount = 0;
    ElasticHandler elasticHandler = new ElasticHandler();

    ArrayList<String> authorList = new ArrayList<String>();
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
        if (isMainTag(qName)){
            recordCount++;
            System.out.println("id: " + recordCount + "     " + qName);
        }
//        if (isMainTag(qName)){
//            recordCount++;
//            if (recordCount <= hitsCount){
//                System.out.println("id: " + recordCount);
//                return;
//            }
//            record = new Record();
//            record.setId(recordCount);
//        } else if (qName.equalsIgnoreCase("title")){
//            bTitle = true;
//        } else if (qName.equalsIgnoreCase("author")){
//            bAuthor = true;
//        } else if (qName.equalsIgnoreCase("pages")){
//            bPages = true;
//        } else if (qName.equalsIgnoreCase("year")){
//            bYear = true;
//        } else if (qName.equalsIgnoreCase("volume")){
//            bVolume = true;
//        } else if (qName.equalsIgnoreCase("journal")){
//            bJournal = true;
//        } else if (qName.equalsIgnoreCase("number")){
//            bNumber = true;
//        } else if (qName.equalsIgnoreCase("ee")){
//            bDoi = true;
//        } else if (qName.equalsIgnoreCase("url")){
//            bUrl = true;
//        } else if (qName.equalsIgnoreCase("crossref")){
//            bCrossref = true;
//        } else if (qName.equalsIgnoreCase("booktitle")){
//            bBookTitle = true;
//        }
    }

    private boolean isMainTag(String qName) {
        for (String tag : mainTagList ){
            if (qName.equalsIgnoreCase(tag)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException{
        if (isMainTag(qName)){
            partailUpdateRecordToElasticSearch(qName,recordCount);
        }
//        if (isMainTag(qName) && recordCount > hitsCount){
//            System.out.println("id: " + recordCount);
//            indexRecordToElasticSearch();
//        }
    }

    private void partailUpdateRecordToElasticSearch(String qName, int recordCount) {
        elasticHandler.partialUpdateRecord(client,qName,recordCount);
    }

    private void indexRecordToElasticSearch() {

        elasticHandler.indexRecord(client, record);

//        System.out.println(record.getTitle());
//        for (String author : record.getAuthorList()){
//            System.out.println(author);
//        }
//        System.out.println(record.getPages());
//        System.out.println(record.getDoi());
//        System.out.println(record.getYear());
//        System.out.println(record.getVolume());
//        System.out.println(record.getJournal());
//        System.out.println(record.getNumber());
//        System.out.println(record.getUrl());
//        System.out.println(record.getCrossref());
//        System.out.println(record.getBookTitle());


    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException{
        if (recordCount <= hitsCount){
            return;
        }
        if (bTitle){
            record.setTitle(new String(ch, start, length));
            bTitle = false;
        } else if (bAuthor){
            record.addAuthor(new String(ch, start, length));
            bAuthor = false;
        } else if (bPages){
            record.setPages(new String(ch, start, length));
            bPages = false;
        } else if (bYear){
            record.setYear(new String(ch, start, length));
            bYear = false;
        } else if (bVolume){
            record.setVolume(new String(ch, start, length));
            bVolume = false;
        } else if (bJournal){
            record.setJournal(new String(ch,start,length));
            bJournal = false;
        } else if (bNumber){
            record.setNumber(new String(ch, start, length));
            bNumber = false;
        } else if (bDoi){
            record.setDoi(new String(ch, start, length));
            bDoi = false;
        } else if (bUrl){
            record.setUrl(new String(ch, start, length));
            bUrl = false;
        } else if (bCrossref){
            record.setCrossref(new String(ch, start, length));
            bCrossref = false;
        } else if (bBookTitle){
            record.setBookTitle(new String(ch, start, length));
            bBookTitle = false;
        }
    }

    public Record getRecord(){
        return record;
    }

    public void initClient() {
        TransportAddress address = null;
        try {
            address = new TransportAddress(InetAddress.getByName("localhost"), 9300);
            client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(address);
            System.out.println("client initiated");
            SearchResponse response = client.prepareSearch("record").get();
            SearchHits hits = response.getHits();
            hitsCount = hits.getTotalHits();
            System.out.println("Number of record indexed: " + hitsCount);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
