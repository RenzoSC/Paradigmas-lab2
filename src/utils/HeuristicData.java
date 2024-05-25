package utils;

public class HeuristicData {
    private String name;
    private String description;

    public HeuristicData(String name, String description){
        this.name = name;
        this. description = description;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public void print(){
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
    }
}
