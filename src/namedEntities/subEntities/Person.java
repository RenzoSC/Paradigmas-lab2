package namedEntities.subEntities;

import namedEntities.NamedEntity;

public class Person extends NamedEntity{
    private String name = null ;
    private String lastName = null;


    public Person(String label, String cat){
        super(label,cat);
    }

    public String getName(){
        return name;
    }

    public String getLastName(){
        return lastName;
    }

    
    public void setName(String name){
        this.name = name;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

}
