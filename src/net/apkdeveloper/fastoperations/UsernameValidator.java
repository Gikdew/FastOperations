package net.apkdeveloper.fastoperations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator{
	 
	  private Pattern pattern;
	  private Matcher matcher;

	  private static final String USERNAME_PATTERN = "^[a-zA-Z0-9_-]{3,10}$";

	  public UsernameValidator(){
		  pattern = Pattern.compile(USERNAME_PATTERN);
	  }

	  public boolean validate(final String username){
		  matcher = pattern.matcher(username);
		  return matcher.matches();

	  }
}