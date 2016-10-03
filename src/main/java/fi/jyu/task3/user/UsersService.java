package fi.jyu.task3.user;


import java.util.*;

public class UsersService {
    private ArrayList<User> usersList;
    private static UsersService instance;

    private UsersService() {
        setUsersList(new ArrayList<User>());
    }

    public synchronized static UsersService getInstance(){
		if(instance == null){
			instance = new UsersService();
	    }
		
	    return instance;
    }

	public ArrayList<User> getUsersList() {
		return usersList;
	}

	private void setUsersList(ArrayList<User> usersList) {
		this.usersList = usersList;
	}
	
	public User getUser(int id) {
		return usersList.get(id);
	}
	
	public synchronized User addUser(User user) {
		user.setId(usersList.size());
		usersList.add(user);
		
		return user;
	}
	
	public synchronized void updateUser(int id, User user) {
		user.setId(id);
		usersList.set(id, user);
	}
	
	public synchronized void removeUser(int id) {
		usersList.set(id, null);
	}
}