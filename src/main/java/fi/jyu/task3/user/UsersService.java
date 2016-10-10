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
		ArrayList<User> list = new ArrayList<User>();
		for (User user : usersList) {
			if (user != null) {
				list.add(user);
			}
		}
		
		return list;
	}

	private void setUsersList(ArrayList<User> usersList) {
		this.usersList = usersList;
	}
	
	public User getUser(int id) {
		return usersList.get(id);
	}

    public User getUserByName(String login) {
        for (User user : usersList) {
            if (user.getLogin().equals(login)) {
                return user;
           }
        }
        return null;
    }

    public User approveUser(String login) {
        List<String> roleMembers = new ArrayList<String>();

        for (User user : usersList) {
            if (user.getLogin().equals(login)) {
                roleMembers.add("registred");
                user.setRole(roleMembers);
                return user;
            }
        }
        return null;

    }
	
	public synchronized User addUser(User user, char flagReg) {
        List <String> roleMembers = new ArrayList<String>();

		if (flagReg == 'u'){
            roleMembers.add("unregistred");
			user.setRole(roleMembers);
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