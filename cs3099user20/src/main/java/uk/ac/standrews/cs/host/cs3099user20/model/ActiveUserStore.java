package uk.ac.standrews.cs.host.cs3099user20.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

public class ActiveUserStore {

    public static Map<String, Timer> users = new HashMap<>();


    public ActiveUserStore() {
        addAdmin();
    }

    public Set<String> getUsers() {
        return users.keySet();
    }

    public void addUser(String userID) {
        if (userActive(userID)) {
            updateUserActivity(userID); // user already logged in, so just update their activity
        } else {

            Timer timer = new Timer();
            timer.schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            removeUser(userID);
                        }
                    },
                    3600000 // A user is deleted after an hour of logging in.
            );
            users.put(userID, timer);
        }
    }

    public void addAdmin() {
        Timer timer = new Timer();
        users.put("admin", timer);
        users.put("query", timer);
    }


    public void removeUser(String userID) {
        users.remove(userID);
    }

    public boolean userActive(String userID) {
        return users.keySet().contains(userID);
    }

    public void updateUserActivity(String userID) {
        users.get(userID).cancel(); // stops the timer
        removeUser(userID); // removes the user from the store
        addUser(userID); // then reallocated them to the store with a new scheduled task to remove them after an hour
    }

}
