import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by GiangNT on 2/9/2018.
 */
public class App {
    public static void main(String[] args) {
//        try {
//
////            TransportAddress address = new TransportAddress(InetAddress.getByName("localhost"), 9300);
////            Client client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(address);
//
////            IndexResponse response = client.prepareIndex("production", "user", "john")
////                    .setSource(jsonBuilder()
////                            .startObject()
////                            .field("user", "John")
////                            .field("product", "mobile phone")
////                            .field("message", "Iphone X")
////                            .endObject()
////                    )
////                    .get();
////            client.prepareIndex("production", "user", "sue")
////                    .setSource(jsonBuilder()
////                            .startObject()
////                            .field("user", "sue")
////                            .field("product", "pen")
////                            .field("message", "saled pen")
////                            .endObject()
////                    )
////                    .get();
////            GetResponse response = client.prepareGet("production","user", "giang").get();
////            System.out.println(response.toString());
////        } catch (UnknownHostException e) {
////            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        }

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try{
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XmlHandler handler = new XmlHandler();
            handler.initClient();
            saxParser.parse(new File("C:\\Users\\GiangNT\\Desktop\\dblp\\test.xml"), handler);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
