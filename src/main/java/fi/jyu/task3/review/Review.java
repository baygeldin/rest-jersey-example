package fi.jyu.task3.review;


public class Review {

<<<<<<< HEAD
    private int id;
=======
    private String id;
>>>>>>> c670216fe99f51b46273426de6dc8948a7da371f
    private String author;
    private String text;
    private String date;

    public Review(){}

<<<<<<< HEAD
    public Review(String author, String text, String date) {
        this.id = 0;
=======
    public Review(String id, String author, String text, String date) {
        this.id = id;
>>>>>>> c670216fe99f51b46273426de6dc8948a7da371f
        this.author = author;
        this.text = text;
        this.date = date;
    }

<<<<<<< HEAD
    public int getId() {
    	id++;
        return id;
    }

=======
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
>>>>>>> c670216fe99f51b46273426de6dc8948a7da371f

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
