package utils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namedEntities.NamedEntity;
import namedEntities.subEntities.Location;

public class CategoryMap {
    Map<String, Map<String, NamedEntity>> catmap = new HashMap<>();
    public CategoryMap(){
        catmap.put("PERSON", new HashMap<>());
        catmap.put("LOCATION", new HashMap<>());
        catmap.put("ORGANIZATION", new HashMap<>());
        catmap.put("EVENT", new HashMap<>());
        catmap.put("OTHER", new HashMap<>());
    }

    public Boolean existsLabel(String category, String label){
        return catmap.get(category).containsKey(label);
    }

    public NamedEntity getEntity(String category,String label){
        return catmap.get(category).get(label);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addEntity(List<String>topics, String cat, String label){
        if(existsLabel(cat, label)){
            NamedEntity existingEntity = getEntity(cat, label);
            existingEntity.addCount();
        }else{
            String capitalized = cat.substring(0,1).toUpperCase() + cat.substring(1).toLowerCase();
            NamedEntity newEntity = null;
            if(cat.equals("LOCATION")){
                Location loc = new Location(label, cat, "0", "0");    
                for(String top: topics){
                    loc.addTopic(top);
                }
                newEntity = loc;
            }else{
                String pathString = "namedEntities.subEntities.";
                pathString+= capitalized;
                try {
                    Class clazz = Class.forName(pathString);
                    Class[] parameters = new Class[] {String.class, String.class};
                    Constructor constructor = clazz.getConstructor(parameters);
                    Object o = constructor.newInstance(new Object[] {label, cat});
                    newEntity = (NamedEntity)o;
                    for(String top: topics){
                        newEntity.addTopic(top);
                    }

                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            catmap.get(cat).put(label, newEntity);
        }
    }

    public void print(){
        for(Map.Entry<String,Map<String, NamedEntity>> entry: catmap.entrySet() ){
            String keyString = entry.getKey();
            Map <String, NamedEntity> values = entry.getValue();
            if (!values.isEmpty()) {
                System.out.println("Category: "+keyString);
                for(Map.Entry<String,NamedEntity>subEntry:values.entrySet()){
                    Integer occur=0;
                    NamedEntity actual = subEntry.getValue();

                    occur = actual.getCount();
                    String actualLabel = subEntry.getKey();

                    System.out.println("     "+actualLabel+" ("+occur+")");
                }
            }
            
        }
    }
}
