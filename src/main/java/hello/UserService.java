package hello;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserService {

	public Map<String, Date> users = new HashMap<String, Date>();
	
	public void createNewUser(String username) {
		System.out.println("User " + username + " has just registered!");
		users.put(username, new Date());
	}
}
