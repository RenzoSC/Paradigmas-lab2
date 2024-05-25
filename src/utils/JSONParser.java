package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONParser {

    static public List<FeedsData> parseJsonFeedsData(String jsonFilePath) throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        List<FeedsData> feedsList = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(jsonData);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String label = jsonObject.getString("label");
            String url = jsonObject.getString("url");
            String type = jsonObject.getString("type");
            feedsList.add(new FeedsData(label, url, type));
        }
        return feedsList;
    }

    static public List<HeuristicData> parseJsonHeuristicData(String jsonFilePath) throws IOException{
        List<HeuristicData> heuristicList = new ArrayList<>();
        String jsonData = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        JSONArray jsonArray  = new JSONArray(jsonData);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String name = jsonObject.getString("name");
            String description = jsonObject.getString("description");
            heuristicList.add(new HeuristicData(name, description));
        }
        return heuristicList;
    }

    static public JSONArray parseJsonToMap(String jsonFilePath) throws IOException{
        String jsonData = new String(Files.readAllBytes(Paths.get(jsonFilePath)));
        JSONArray job = new JSONArray(jsonData);
        return job;
    }

    public static void main(String[] args) {
        try {
            JSONArray ar = JSONParser.parseJsonToMap("src/data/dictionary.json");
            System.out.println(ar.getJSONObject(0).getJSONArray("keywords").get(0));
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
