package utils;

public class Config {
    private boolean printFeed = false;
    private boolean computeNamedEntities = false;
    private boolean printHelp = false;
    private String feedKey = null;

    private String heuristicKey = null;
    private String statsFormat = "cat";
    
    public Config(boolean printFeed, boolean computeNamedEntities, String feedKey, String heuristicKey, String statsFormat) {
        this.printFeed = printFeed;
        this.computeNamedEntities = computeNamedEntities;
        this.feedKey = feedKey;
        this.heuristicKey = heuristicKey;
        if (statsFormat != null) {
            this.statsFormat = statsFormat;
        }
    }

    public boolean getPrintHelp() {
        return printHelp;
    }
    
    public boolean getPrintFeed() {
        return printFeed;
    }

    public boolean getComputeNamedEntities() {
        return computeNamedEntities;
    }

    public String getFeedKey() {
        return feedKey;
    }

    public String getHeuristicKey(){
        return heuristicKey;
    }

    public String getStatsFormat(){
        return statsFormat;
    }
}
