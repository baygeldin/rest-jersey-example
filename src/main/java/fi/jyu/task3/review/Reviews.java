package fi.jyu.task3.review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Reviews {

    @XmlElement(name="reviews")
    private List<Review> reviewList;

    private static Map<String, Reviews> map = new HashMap<>();
    private static Reviews instance;


    public synchronized static Reviews getInstance(String name){
        instance = map.get(name);
        if(instance == null) {
            instance = new Reviews();
            map.put(name, instance);
            return instance;
        }
        return instance;
    }



    public Reviews() {
        this.reviewList = new ArrayList<Review>();
    }

    public synchronized List<Review> getReviewList() {
        return new ArrayList<Review>(reviewList);
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public synchronized void  add(Review c){
    	reviewList.add(c);
    }

}
