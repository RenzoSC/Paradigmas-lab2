import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import feed.Article;
import feed.FeedParser;
import utils.CategoryMap;
import utils.Config;
import utils.FeedsData;
import utils.HeuristicData;
import utils.JSONDict;
import utils.JSONParser;
import utils.TopicMap;
import utils.UserInterface;

import namedEntities.HeuristicMap;
import namedEntities.Heuristic;
public class App {

    public static void main(String[] args) {

        List<FeedsData> feedsDataArray = new ArrayList<>();
        try {
            feedsDataArray = JSONParser.parseJsonFeedsData("src/data/feeds.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        List<HeuristicData> heuristicDataArray = new ArrayList<>();
        try {
            heuristicDataArray = JSONParser.parseJsonHeuristicData("src/data/heuristics.json");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        UserInterface ui = new UserInterface();
        Config config = ui.handleInput(args);

        run(config, feedsDataArray, heuristicDataArray);
    }

    private static void run(Config config, List<FeedsData> feedsDataArray, List<HeuristicData> heuristicDataArray) {
        if (feedsDataArray == null || feedsDataArray.size() == 0) {
            System.out.println("No feeds data found");
            return;
        }

        if (heuristicDataArray == null || heuristicDataArray.size() == 0) {
            System.out.println("No heuristics found");
            return;
        }

        List<Article> allArticles = new ArrayList<>();
        
        if (!(config.getStatsFormat().equals("cat") || config.getStatsFormat().equals("topic"))) {
            System.out.println("Format not founded!");
            System.out.println("Available formats are: ");
            System.out.println("   - cat: Category-wise stats");
            System.out.println("   - topic: Topic-wise stats");
            return;
        }

        if (!HeuristicMap.isHeuristic(config.getHeuristicKey())) {
            System.out.println("Heuristic not founded!");
            System.out.println("Available heuristic names are: ");
            for(HeuristicData heuristicData:heuristicDataArray){
                System.out.println("   <"+heuristicData.getName()+">: <"+heuristicData.getDescription()+">");
            }
            return;
        }

        if (config.getPrintHelp()) {
            printHelp(feedsDataArray, heuristicDataArray);
            return;
        }

        if (config.getFeedKey()!=null) {              //en caso de que se especifique el feed procesa
            String key = config.getFeedKey();
            List<Article>currenArticles = new ArrayList<>();
            boolean founded = false;
            for (FeedsData feedData : feedsDataArray) {
                if (feedData.getLabel().equals(key)) {
                    try {
                        currenArticles = FeedParser.parseXML(FeedParser.fetchFeed(feedData.getUrl()));
                        allArticles.addAll(currenArticles);
                    } catch (Exception e) {
                        System.err.println(e);
                    }
                    founded =true;
                }
            }
            if (!founded) {
                System.out.println("Feed key not founded!");
                System.out.println("Available feed keys are: ");
                for (FeedsData feedData : feedsDataArray) {
                    System.out.println("  - " + feedData.getLabel());
                }
                return;
            }
        }else{                                 //si no se especifica feed procesa todos
            List<Article>currenArticles = new ArrayList<>();
            for (FeedsData feedData : feedsDataArray) {
                try {
                    currenArticles = FeedParser.parseXML(FeedParser.fetchFeed(feedData.getUrl()));
                    allArticles.addAll(currenArticles);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        }

        if (config.getPrintFeed()) {
            System.out.println("Printing feed(s) ");
            for(Article art: allArticles){
                art.print();
            }
        }

        if (config.getComputeNamedEntities()) {
            String heuristic = config.getHeuristicKey();
            System.out.println("Computing named entities using " + heuristic);

            Heuristic heuristicFunc = HeuristicMap.getHeuristic(heuristic);
            List<String> candidates= new ArrayList<>();
            for(Article art : allArticles){
                String artTitle = art.getTitle();
                String artDesc = art.getDescription();
                candidates.addAll(heuristicFunc.extract(artTitle));
                candidates.addAll(heuristicFunc.extract(artDesc));
            }
            
            JSONDict dict = new JSONDict("src/data/dictionary.json");
            CategoryMap categoryMap = new CategoryMap();
            TopicMap topicMap = new TopicMap();

            for(String cand :candidates){
                for (int i = 0; i < dict.getLength(); i++) {
                    JSONArray keywords = dict.getKeywords(i);
                    List<Object> keywordslist = keywords.toList();
                    if(keywordslist.contains(cand)){
                        String cat = dict.getCategory(i);
                        String label = dict.getLabel(i);
                        JSONArray topics = dict.getTopic(i);
                        List<String>topicsList = new ArrayList<>();
                        for (int j = 0; j < topics.length(); j++) {
                            topicsList.add(topics.getString(j));
                        }
                        categoryMap.addEntity(topicsList, cat, label);
                        topicMap.addEntity(topicsList, label, cat);
                    }
                }
            }
            
            String statsFormat = config.getStatsFormat(); //obtenemos el tipo de stats que tenemos que printear
            System.out.println("\nStats: "+statsFormat);
            System.out.println("-".repeat(80));
            if (statsFormat.equals("cat")) {
                categoryMap.print();
            }else{
                topicMap.print();
            }
        }else{
            //En el caso de que no se haya especificado la heuristic
            //en ese caso se considera -pf como activo (si es que no estaba activo ya)
            if (!config.getPrintFeed()) {    
                System.out.println("Printing feed(s) ");
                for(Article art: allArticles){
                    art.print();
                }   
            }
        }
    }

    private static void printHelp(List<FeedsData> feedsDataArray, List<HeuristicData> heuristicDataArray) {
        System.out.println("Usage: make run ARGS=\"[OPTION]\"");
        System.out.println("Options:");
        System.out.println("  -h, --help: Show this help message and exit");
        System.out.println("  -f, --feed <feedKey>:                Fetch and process the feed with");
        System.out.println("                                       the specified key");
        System.out.println("                                       Available feed keys are: ");
        for (FeedsData feedData : feedsDataArray) {
            System.out.println("                                       " + feedData.getLabel());
        }
        System.out.println("  -ne, --named-entity <heuristicName>: Use the specified heuristic to extract");
        System.out.println("                                       named entities");
        System.out.println("                                       Available heuristic names are: ");

        for(HeuristicData heuristicData:heuristicDataArray){
            System.out.println("                                     <"+heuristicData.getName()+">: <"+heuristicData.getDescription()+">");
        }
    
        System.out.println("  -pf, --print-feed:                   Print the fetched feed");
        System.out.println("  -sf, --stats-format <format>:        Print the stats in the specified format");
        System.out.println("                                       Available formats are: ");
        System.out.println("                                       cat: Category-wise stats");
        System.out.println("                                       topic: Topic-wise stats");
    }

}
