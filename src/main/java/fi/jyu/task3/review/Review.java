package fi.jyu.task3.review;


public class Review {

    private int id;
    private String author;
    private String text;
    private String date;

    public Review(){}

    public Review(String author, String text, String date) {
        this.id = 0;
        this.author = author;
        this.text = text;
        this.date = date;
    }

    public int getId() {
    	id++;
        return id;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
