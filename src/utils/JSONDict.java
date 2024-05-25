package utils;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;

public class JSONDict {
    private String DictPath = "";
    private JSONArray JDict = null;

    public JSONDict(String filePath){
        DictPath = filePath;
        try {
            JDict = new JSONArray(new String(Files.readAllBytes(Paths.get(filePath))));    
        } catch (Exception e) {
            JDict = null;
            System.err.println(e);
        }
    }

    public String getPath(){
        return DictPath;
    }

    // JSONObject getIndex(Integer index){
    //     return JDict.getJSONObject(index);
    // }

    public Integer getLength(){
        return JDict.length();
    }

    public JSONArray getTopic(Integer index){
        return JDict.getJSONObject(index).getJSONArray("Topics");
    }

    public JSONArray getKeywords(Integer index){
        return JDict.getJSONObject(index).getJSONArray("keywords");
    }

    public String getCategory(Integer index){
        return JDict.getJSONObject(index).getString("Category");
    }

    public String getLabel(Integer index){
        return JDict.getJSONObject(index).getString("label");
    }
}
