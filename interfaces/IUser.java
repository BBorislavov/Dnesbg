package interfaces;

import exceptions.UserException;
import model.User;

public interface IUser {
	
//	public AtomicInteger getUserId();

	public String getUsername();

	public String getPassword();

	public String getEmail();

	public boolean isAdmin();
	
	public void giveRights(String name) throws UserException;

}
