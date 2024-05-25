package namedEntities.subEntities;

import namedEntities.NamedEntity;

public class Location extends NamedEntity{
    private String latitud;
    private String longitud;

    public Location(String label, String cat, String latitud, String longitud){
        super(label,cat);
        this.latitud = latitud;
        this.longitud = longitud;
    } 

    public String getLatitud(){
        return latitud;
    }

    public String getLongitud(){
        return longitud;
    }
}
