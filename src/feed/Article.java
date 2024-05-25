package feed;


public class Article {
    // Atributes
    private String title="";
    private String description="";
    private String link="";
    private String pubDate="";

    // Constructor
    public Article(String title, String description, String link, String pubDate) { 
        this.title = title;
        this.description = description;
        this.link = link;
        this.pubDate = pubDate;
    }

    // Methods
    public void print() {
        System.out.println("Title: " + (title == null ? "":title));
        System.out.println("Description: " + (description == null ? "":description));
        System.out.println("Publication Date: " + (pubDate == null ? "":pubDate));
        System.out.println("Link: " + (link == null ? "":link));
        System.out.println("********************************************************************************");
    }

    //accessors
    public String getTitle(){
        return this.title;
    }

    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String newD){
        this.description = newD;
    }

    public String getLink(){
        return this.link;
    }
    
    public void setLink(String newLink){
        this.link = newLink;
    }
    
    public String getPubDate(){
        return this.pubDate;
    }
    
    public void setPubDate(String newPubDate){
        this.pubDate = newPubDate;
    }
}
