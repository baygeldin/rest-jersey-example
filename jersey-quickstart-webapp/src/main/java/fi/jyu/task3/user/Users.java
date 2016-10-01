package fi.jyu.task3.user;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Users {

    @XmlElement(name="users")
    private List<User> userslist;
    private static Users instance;

    public Users() {
        userslist = new ArrayList<User>();
    }

    //singleton
    public synchronized static Users getInstance(){
        if(instance==null)
            instance = new Users();
        return instance;
    }

    public synchronized List<User> getUserslist() {
        return new ArrayList<User>(userslist);
    }

    public void setUserslist(List<User> userslist) {
        this.userslist = userslist;
    }

    public synchronized void  add(User u){
        userslist.add(u);
    }

    public User getByName(String name){

        List<User> usersCopy = getUserslist();

        for(User u: usersCopy)
            if(u.getName().toLowerCase().equals(name.toLowerCase()))
                return u;
        return null;
    }

}