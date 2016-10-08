package fi.jyu.task3.security;

import java.util.ArrayList;
import java.util.List;

public final class PasswordAuthentication {

	private PasswordAuthentication() {}
	
	public static User Authentication(String user, String password){
		User userToReturn = new User();
		List <String> roleAdmin = new ArrayList<String>();
		List <String> roleMembers = new ArrayList<String>();

		

		if (user.equals("admin") && password.equals("admin")){
			userToReturn.setLogin("admin");
			userToReturn.setPassword("admin");
			roleAdmin.add("admin");
			userToReturn.setRole(roleAdmin);
			return userToReturn;
		}
		else 
		{
			userToReturn.setLogin("user1");
			userToReturn.setPassword("user1");
			roleMembers.add("member");
			userToReturn.setRole(roleMembers);
			return userToReturn;
		}
	}
	
	
	
}
