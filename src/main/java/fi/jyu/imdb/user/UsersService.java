package fi.jyu.imdb.user;


import java.util.*;

public class UsersService {
    private ArrayList<User> usersList;
    private static UsersService instance;

    private UsersService() {
        this.usersList = new ArrayList<User>();
    }

    public synchronized static UsersService getInstance(){
		if(instance == null){
			instance = new UsersService();
			User admin = new User("admin", "admin", "admin@imdb.com");
			instance.addUser(admin);
			instance.verify(admin);
            instance.makeAdmin(admin);
	    }
		
	    return instance;
    }

	public ArrayList<User> getUsersList() {
		ArrayList<User> list = new ArrayList<User>();
		for (User user : usersList) {
			if (user != null) {
				list.add(user);
			}
		}
		
		return list;
	}
	
	public User getUser(int id) {
		return usersList.get(id);
	}

    public User getUserByLogin(String login) {
        for (User user : usersList) {
            if (user.getLogin().equals(login)) {
                return user;
           }
        }
        return null;
    }

    public void verify(User user) {
        user.getRole().add("verified");
    }

    public void makeAdmin(User user) {
        user.getRole().add("admin");
    }
	
	public synchronized User addUser(User user) {
        if (user.getRole().isEmpty()) {
            user.getRole().add("user");
        }
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