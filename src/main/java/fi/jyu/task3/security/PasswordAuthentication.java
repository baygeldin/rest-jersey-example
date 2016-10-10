package fi.jyu.task3.security;

import fi.jyu.task3.user.User;
import fi.jyu.task3.user.UsersService;

import java.util.ArrayList;
import java.util.List;

public final class PasswordAuthentication {

	private PasswordAuthentication() {}

	public static User Authentication(String login, String password){
		User userToReturn = UsersService.getInstance().getUserByName(login);

		List <String> roleAdmin = new ArrayList<String>();
		List <String> roleMembers = new ArrayList<String>();



		if (login.equals("admin") && password.equals("admin")){
            userToReturn = new User();
			userToReturn.setLogin("admin");
			userToReturn.setPassword("admin");
			roleAdmin.add("admin");
			userToReturn.setRole(roleAdmin);
			return userToReturn;
		}

        if (userToReturn == null)
            return null;
        else
        {
            if (userToReturn.getLogin().equals(login) && userToReturn.getPassword().equals(password)){
                return userToReturn;
            }
            else return null;
        }
	}



}
