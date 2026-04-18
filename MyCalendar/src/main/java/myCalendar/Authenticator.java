package myCalendar;

import java.util.HashMap;
import java.util.Map;

public class Authenticator {

    private EventOwner currentUser;
    private final Map<EventOwner, String> accounts = new HashMap<>();

    public Authenticator() {
        accounts.put(new EventOwner("Roger"), "Chat");
        accounts.put(new EventOwner("Pierre"), "KiRouhl");
    }

    public boolean createAccount(String name, String password) {
        EventOwner owner = new EventOwner(name);
        if (accounts.containsKey(owner))
            return false;

        accounts.put(owner, password);
        return true;
    }

    public boolean login(String name, String password) {
        EventOwner owner = new EventOwner(name);
        if (!accounts.containsKey(owner))
            return false;

        if (!accounts.get(owner).equals(password))
            return false;

        currentUser = owner;
        return true;
    }

    public void disconnect() {
        currentUser = null;
    }

    public boolean isAuthenticated() {
        return currentUser != null;
    }

    public EventOwner getCurrentUser() {
        return currentUser;
    }
}
