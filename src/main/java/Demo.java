import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
            DeleteResponse response = client.prepareDelete("record", "id", "1").get();
            DeleteResponse response2 = client.prepareDelete("record", "id", "2").get();
//            SearchResponse response = client.prepareSearch().setQuery(QueryBuilders.matchQuery("author", "andrew")).setSize(1).get();
//            System.out.println(response.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
