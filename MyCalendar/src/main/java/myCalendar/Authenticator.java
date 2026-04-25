package myCalendar;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Authenticator {

    private Optional<EventOwner> currentUser = Optional.empty();
    private final Map<EventOwner, String> accounts = new HashMap<>();

    public Authenticator() {
        accounts.put(new EventOwner("Roger"), "Chat");
        accounts.put(new EventOwner("Pierre"), "KiRouhl");
    }

    public boolean createAccount(String name, String password) {
        EventOwner owner = new EventOwner(name);
        return Optional.of(owner)
                .filter(o -> !accounts.containsKey(o))
                .map(o -> {
                    accounts.put(o, password);
                    return true;
                })
                .orElse(false);
    }

    public boolean login(String name, String password) {
        EventOwner owner = new EventOwner(name);
        return Optional.of(owner)
                .filter(accounts::containsKey)
                .filter(o -> accounts.get(o).equals(password))
                .map(o -> {
                    currentUser = Optional.of(o);
                    return true;
                })
                .orElse(false);
    }

    public void disconnect() {
        currentUser = Optional.empty();
    }

    public boolean isAuthenticated() {
        return currentUser.isPresent();
    }

    public EventOwner getCurrentUser() {
        return currentUser.orElseThrow(() -> new IllegalStateException("Aucun utilisateur connecté."));
    }
}
