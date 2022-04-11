package uk.ac.caledonian.dbowma201_s1909583_roadworks;
//Bowman Dylan s1909583
public class Roadwork {
    private String startDate;
    private String endDate;
    private String title;
    private String description;
    private String delay;
    private String link;
    private String geoRss;
    private String pubDate;

    public Roadwork(){

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getDelay() { return delay; }

    public String getGeoRss() {
        return geoRss;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getStartDate() { return startDate; }

    public String getEndDate() { return endDate; }

    public void setTitle(String title) {
        this.title = title;
    }

    //upon reading the description calls methods to handle formatting
    public void setDescription(String description) {
        this.description = description;
        formatDescription(description);
    }

    public void setDelay(String delay) { this.delay = delay.substring(18); }

    public void setLink(String link) {
        this.link = link;
    }

    public void setGeoRss(String geoRss) {
        this.geoRss = geoRss;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    //uses string manipulation to breakdown wonky datetime string into useful units
    public void setStartDate(String startDate) {
        startDate = startDate.substring(12);

        String[] dateArray = startDate.split(" ");
        String day = dateArray[0].substring(0 ,dateArray[0]. length() -1);
        String date = dateArray[1];
        String month = dateArray[2];
        String year = dateArray[3];
        this.startDate = date + " " + month + " " + year; }

    //same thing
    public void setEndDate(String endDate) {
        endDate = endDate.substring(12);

        String[] dateArray = endDate.split(" ");
        String day = dateArray[0].substring(0 ,dateArray[0]. length() -1);
        String date = dateArray[1];
        String month = dateArray[2];
        String year = dateArray[3];
        this.endDate = date + " " + month + " " + year; }

    //parents method for setting dates and delays
    public void formatDescription(String description){
        String[] descriptionArray = description.split("<br />");
        setStartDate(descriptionArray[0]);
        if(descriptionArray.length > 1)
            setEndDate(descriptionArray[1]);
        if(descriptionArray.length > 2)
            setDelay(descriptionArray[2]);


    }

    //its a toString
    @Override
    public String toString() {
        return "Roadwork{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", delay='" + delay + '\'' +
                ", link='" + link + '\'' +
                ", geoRss='" + geoRss + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}
