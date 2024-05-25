package feed;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
public class FeedParser {

    public static List<Article> parseXML(String xmlData) {
        List<Article> articleList = new ArrayList<>();
        try {
            //parse string to a doc type xml
            Document xmldoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse( new InputSource( new StringReader(xmlData)));
            
            //get all item nodes
            NodeList articles = xmldoc.getElementsByTagName("item");
            for (int i = 0; i < articles.getLength(); i++) {
                String title = "";
                String description = "";
                String link = "";
                String date = "";

                Node elem = articles.item(i);

                //from all item nodes I get the childs
                NodeList articleChilds = elem.getChildNodes();
                for (int j = 0; j < articleChilds.getLength(); j++) {
                    Node childElem = articleChilds.item(j);
                    String tag = childElem.getNodeName();
                    if (tag.equals("title")) {
                        title = childElem.getTextContent();
                    }else if(tag.equals("description")){
                        description = childElem.getTextContent();
                    }else if(tag.equals("link")){
                        link = childElem.getTextContent();
                    }else if(tag.equals("pubDate")){
                        date = childElem.getTextContent();
                    }
                }

                //create Article with all information obtained
                Article art = new Article(title, description, link, date);
                articleList.add(art);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
                
        return articleList;
    }

    public static String fetchFeed(String feedURL) throws MalformedURLException, IOException, Exception {

        URL url = new URL(feedURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        
        connection.setRequestProperty("User-Agent", "Paganismo");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        int status = connection.getResponseCode();
        if (status != 200) {
            throw new Exception("HTTP error code: " + status);
        } else {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            connection.disconnect();
            return content.toString();
        }
    }
}
