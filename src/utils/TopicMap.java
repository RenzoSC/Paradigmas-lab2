package utils;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import namedEntities.NamedEntity;
import namedEntities.subEntities.Location;;

public class TopicMap {
    Map<String, Map<String, NamedEntity>> topmap = new HashMap<>();

    public TopicMap(){}

    public void createTopic(String topic){
        if(!topmap.keySet().contains(topic)){
            topmap.put(topic, new HashMap<>());
        }
    }
    public Boolean existsLabel(String topic, String label){
        createTopic(topic);
        return topmap.get(topic).containsKey(label);
        
    }

    public NamedEntity getEntity(String topic, String label){
        //asume que ya está la entidad
        return topmap.get(topic).get(label);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void addEntity(List<String>topics, String label, String cat){
        
        //como cada entidad con un label tiene los mismos topicos, si 
        //ya existe ese label con un topico, va a existir ese label
        //para todos los demás topicos, asi que solo me fijo en el primer topic
        if (existsLabel(topics.get(0), label)) {
            for(String top: topics){
                NamedEntity existingEntity = getEntity(top, label);
                existingEntity.addCount();
            }
        }else{
            //si no existe creo la entidad y lo agrego a todos sus respectivos topicos
            String capitalized = cat.substring(0,1).toUpperCase() + cat.substring(1).toLowerCase();
            NamedEntity newEntity = null;
            if(cat.equals("LOCATION")){
                Location loc = new Location(label, cat, "0", "0");    
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

                } catch (Exception e) {
                    System.err.println(e);
                }
            }
            for(String top: topics){
                createTopic(top);
                newEntity.addTopic(top);
                topmap.get(top).put(label, newEntity);
            }
        }
    }

    public void print(){
        for(Map.Entry<String,Map<String, NamedEntity>> entry: topmap.entrySet() ){
            String keyString = entry.getKey();
            Map <String, NamedEntity> values = entry.getValue();
            System.out.println("Topico: "+keyString);
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
