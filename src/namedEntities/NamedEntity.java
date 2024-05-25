package namedEntities;

import java.util.ArrayList;
import java.util.List;

public class NamedEntity {
	private String label;
	private String category;
	private Integer counter = 0;
	private List<String> topic = new ArrayList<>();
	public NamedEntity(String label, String cat){
		this.label = label;
		this.category =cat;
		counter +=1;
	}

	public void addTopic(String top){
		topic.add(top);
	}

	public String getLabel(){
		return label;
	}

	public String getCategory(){
		return category;
	}

	public List<String> getTopic(){
		return topic;
	}

	public void addCount(){
        this.counter++;
    }

    public Integer getCount(){
        return counter;
    }
}