package com.egco28.a13262;

/**
 * Created by Natamon Tangmo on 28-Oct-16.
 */

public class Fortune {
    private long id;
    private String results;
    private String datetime;
    private int picIndex;

    public Fortune(long id, String results, String datetime, int picIndex){
        this.id = id;
        this.results = results;
        this.datetime = datetime;
        this.picIndex = picIndex;

    }

    public long getId(){ return id; }
    public String getResults(){ return results; }
    public String getDateTime(){ return datetime; }
    public int getPicIndex(){ return picIndex; }



}
