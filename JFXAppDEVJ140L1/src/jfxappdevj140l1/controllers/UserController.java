package jfxappdevj140l1.controllers;

import jfxappdevj140l1.AppDemoException;

/**
 *
 * @author Olga
 */
public class UserController {
    
    private static final String USER_LOGIN = "admin";
    private static final String USER_PASSWORD = "admin";
    
    public static void checkUserData (String login, String password)
                                                    throws AppDemoException {
        
        if ( password.isEmpty() )
            throw new AppDemoException("Password field cannot be empty");
        
        if ( (!login.equals(USER_LOGIN)) || (!password.equals(USER_PASSWORD)) )
            throw new AppDemoException("User authorization error");
    }
}
