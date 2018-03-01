import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by GiangNT on 2/14/2018.
 */
public class Demo {
    public static void main(String[] args) {
//
        TransportAddress address = null;
        try {
            address = new TransportAddress(InetAddress.getByName("localhost"), 9300);
            Client client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(address);

            DeleteIndexResponse deleteResponse = client.admin().indices().delete(new DeleteIndexRequest("record")).actionGet();

//            DeleteIndexResponse deleteResponse = client.admin().indices().delete(new DeleteIndexRequest("record")).actionGet();

//            DeleteResponse response = client.prepareDelete("record", "id", "1").get();
//            DeleteResponse response2 = client.prepareDelete("record", "id", "2").get();
//            SearchResponse response = client.prepareSearch().setQuery(QueryBuilders.matchQuery("author", "andrew")).setSize(1).get();
//            System.out.println(response.toString());
//            SearchResponse response = client.prepareSearch("record")
//                    .setSize(0) // Don't return any documents, we don't need them.
//                    .get();
//
//            SearchHits hits = response.getHits();
//            long hitsCount = hits.getTotalHits();
//            System.out.println(hitsCount);
//            IndexResponse response = client.prepareIndex("record", "id", "70033")
//                    .setSource(jsonBuilder()
//                            .startObject()
//                            .field("title", "test")
//                            .endObject()
//                    )
//                    .get();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
