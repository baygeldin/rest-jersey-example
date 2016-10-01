package fi.jyu.task3.comment;

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
public class Comments {

    @XmlElement(name="comments")
    private List<Comment> commentList;

    private static Map<String, Comments> map = new HashMap<>();
    private static Comments instance;


    public synchronized static Comments getInstance(String name){
        instance = map.get(name);
        if(instance == null) {
            instance = new Comments();
            map.put(name, instance);
            return instance;
        }
        return instance;
    }



    public Comments() {
        this.commentList = new ArrayList<Comment>();
    }

    public synchronized List<Comment> getCommentList() {
        return new ArrayList<Comment>(commentList);
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public synchronized void  add(Comment c){
        commentList.add(c);
    }

}
