import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;

/**
 * Created by GiangNT on 2/20/2018.
 */
public class CrossRefHelper {
    public static void main(String[] args) {
        try {

            // demo query https://search.crossref.org/?q=How+do+I+organize+my+holiday+snaps%3F
            String q1 = "How do I organize my holiday snaps?";
            String url1 = "https://search.crossref.org/?q=" + URLEncoder.encode(q1, "UTF-8");

            System.out.println("Query on search.crossref.org");
            System.out.println(url1);

//            System.out.println("List of doi returned");
//
//
//            // get DOI List from search query on search.crossref.org
//            Document searrchCrossRefDoc = Jsoup.connect(url1).get();
//            Elements itemLinksOuter = searrchCrossRefDoc.getElementsByClass("item-links-outer");
//            for (Element el1 : itemLinksOuter){
//                Elements itemLinks = el1.getElementsByClass("item-links");
//                for (Element el2 : itemLinks){
//                    Elements href = el2.select("a[href]");
//                    for (Element el3 : href){
//                        if (el3.text().contains("https://doi.org")){
//                            System.out.println(el3.text());
//
//                            System.out.println("///////////////////////////////////////////");
//
//                        }
//                    }
//                }
//
//            }

            // now get bibtext from doi with https://www.doi2bib.org/bib/
            // demo url https://www.doi2bib.org/bib/https%3A%2F%2Fdoi.org%2F10.12968%2Fnrec.1999.1.3.7889
            String q2 = "https://doi.org/10.12968/nrec.1999.1.3.7889";
            String url2 = "https://www.doi2bib.org/bib/" + URLEncoder.encode(q2, "UTF-8");

            System.out.println("Query on search.crossref.org");
            System.out.println(url2);
            Document doi2BibDoc = Jsoup.connect("https://www.doi2bib.org/bib/https%3A%2F%2Fdoi.org%2F10.12968%2Fnrec.1999.1.3.7889")
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .referrer("http://www.google.com")
                    .timeout(1000*5).get();
            System.out.println(doi2BibDoc.text());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
