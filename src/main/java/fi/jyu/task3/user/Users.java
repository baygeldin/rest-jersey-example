package fi.jyu.task3.user;


import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import fi.jyu.task3.exception.ErrorMessage;

@XmlRootElement
@XmlAccessorType (XmlAccessType.FIELD)
public class Users {

    @XmlElement(name="users")
    private List<User> userslist;
    private static Users instance;
    private static int id;

    public Users() {
        userslist = new ArrayList<User>();
    }

    //singleton
    public synchronized static Users getInstance(){
        if(instance==null){
        	Users.setId(-1);
            instance = new Users();
            }
        return instance;
    }

    public synchronized List<User> getUserslist() {
    	System.out.println(userslist.isEmpty());
    	if (userslist.isEmpty()){
    		ErrorMessage errorMessage = new ErrorMessage("Not found...", 404, "http://myDocs.org"); 
    		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
    				   throw new NotFoundException(response);
    	}
        return new ArrayList<User>(userslist);
    }

    public void setUserslist(List<User> userslist) {
        this.userslist = userslist;
    }

    public synchronized User addUser(User u){
    	/*List <User> usersCopy = getUserslist();
    	for(User u2: usersCopy){
    		if(u2.getId() == u.getId()){
    			return;
    		}
    	}*/
    	u.setId(getIncrementedId());
        userslist.add(u);
        return u;
    }

    public User getById(int id){
        List<User> usersCopy = getUserslist();
        for(User u: usersCopy)
            if(u.getId() == id)
                return u;
        return null;
    }
    
    public void updateUser(User u){
    	List<User> usersCopy = getUserslist();
    	for(int i = 0; i < usersCopy.size(); i++)
            if(usersCopy.get(i).getId() == u.getId()){
            	usersCopy.remove(i);
            	usersCopy.add(u);
            	break;
		}
    	userslist = usersCopy;

    }
    
    public void removeByID(int id){
    	List<User> usersCopy = getUserslist();
    	for (int i = 0; i < usersCopy.size(); i++) {
			if (usersCopy.get(i).getId() == id){
				usersCopy.remove(i);
				break;
			}
		userslist = usersCopy;
		}
    }

	private static int getIncrementedId() {
		id = id + 1;
		return id;
	}

	private static void setId(int id) {
		Users.id = id;
	}
	
	public int getId(){
		return id;
	}

}