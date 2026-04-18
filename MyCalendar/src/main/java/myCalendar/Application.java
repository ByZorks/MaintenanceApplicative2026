package myCalendar;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Application {

    private final CalendarManager calendar = new CalendarManager();
    private final Authenticator auth =  new Authenticator();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            if (auth.isAuthenticated()) {
                authenticatedStartmenu();
            } else {
                startMenu();
            }
        }
    }

    private void startMenu() {
        System.out.println("""
                          _____         _                   _                __  __
                         / ____|       | |                 | |              |  \\/  |
                        | |       __ _ | |  ___  _ __    __| |  __ _  _ __  | \\  / |  __ _  _ __    __ _   __ _   ___  _ __
                        | |      / _` || | / _ \\| '_ \\  / _` | / _` || '__| | |\\/| | / _` || '_ \\  / _` | / _` | / _ \\| '__|
                        | |____ | (_| || ||  __/| | | || (_| || (_| || |    | |  | || (_| || | | || (_| || (_| ||  __/| |
                         \\_____| \\__,_||_| \\___||_| |_| \\__,_| \\__,_||_|    |_|  |_| \\__,_||_| |_| \\__,_| \\__, | \\___||_|
                                                                                                            __/ |
                                                                                                            |___/
                        
                        1 - Se connecter
                        2 - Créer un compte""");
        int choix = readInt("Choix :");
        switch (choix) {
            case 1 -> login();
            case 2 -> createAccount();
            default -> System.err.println("Choix invalide");
        }
    }

    private void createAccount() {
        String name = readString("Entrez votre nom :");
        String password = readString("Entrez votre mot de passe :");

        boolean accountCreated = auth.createAccount(name, password);

        if (!accountCreated)
            System.err.println("Le compte existe déjà.");
    }

    private void login() {
        String name = readString("Entrez votre nom :");
        String password = readString("Entrez votre mot de passe :");

        boolean loggedIn = auth.login(name, password);

        if (!loggedIn)
            System.err.println("Nom d'utilisateur ou mot de passe incorrect.");
    }

    private void authenticatedStartmenu() {
        System.out.printf("""
                Bonjour, %s
                === Menu Gestionnaire d'Événements ===
                1 - Voir les événements
                2 - Ajouter un rendez-vous perso
                3 - Ajouter une réunion
                4 - Ajouter un évènement périodique
                5 - Se déconnecter""", auth.getCurrentUser());
        int choix = readInt("Votre choix :");
        switch (choix) {
            case 1 -> listEvents();
            case 2 -> System.out.println("Fonctionnalité non implémentée.");
            case 3 -> System.out.println("Fonctionnalité non implémentée.");
            case 4 -> System.out.println("Fonctionnalité non implémentée.");
            case 5 -> {
                String disconnect = readString("Déconnexion ! Voulez-vous continuer ? (O/N)");
                switch (disconnect.toUpperCase()) {
                    case "O" -> auth.disconnect();
                    case "N" -> System.out.println("Déconnexion annulée.");
                    default -> System.err.println("Choix invalide, déconnexion annulée.");
                }
            }
        }
    }

    private void listEvents() {
        boolean quit = false;

        while (!quit) {
            System.out.println("""
                === Menu de visualisation d'Événements ===
                1 - Afficher TOUS les événements
                2 - Afficher les événements d'un MOIS précis
                3 - Afficher les événements d'une SEMAINE précise
                4 - Afficher les événements d'un JOUR précis
                5 - Retour""");
            int choix = readInt("Choix :");
            switch (choix) {
                case 1 -> calendar.afficherEvenements();
                case 2 -> {
                    int annee = readInt("Entrez l'année (AAAA) :");
                    int mois = readInt("Entrez le mois (1-12) :");

                    EventDateTime debut = new EventDateTime(LocalDateTime.of(annee, mois, 1, 0, 0));
                    EventDateTime fin = debut.plusMonths(1).minusSeconds(1);
                    afficherListe(calendar.eventsDansPeriode(debut, fin));
                }
                case 3 -> {
                    int annee = readInt("Entrez l'année (AAAA) :");
                    int semaine = readInt("Entrez la semaine (1-52) :");

                    EventDateTime debut = new EventDateTime(LocalDateTime.now()
                            .withYear(annee)
                            .with(WeekFields.of(Locale.FRANCE).weekOfYear(), semaine)
                            .with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1)
                            .withHour(0).withMinute(0));
                    EventDateTime fin = debut.plusDays(7).minusSeconds(1);
                    afficherListe(calendar.eventsDansPeriode(debut, fin));
                }
                case 4 -> {
                    int annee = readInt("Entrez l'année (AAAA) :");
                    int mois = readInt("Entrez le mois (1-12) :");
                    int jour = readInt("Entrez le jour (1-31) :");

                    EventDateTime debut = new EventDateTime(LocalDateTime.of(annee, mois, jour, 0, 0));
                    EventDateTime fin = debut.plusDays(1).minusSeconds(1);
                    afficherListe(calendar.eventsDansPeriode(debut, fin));
                }
                case 5 -> quit = true;
                default -> System.err.println("Choix invalide");
            }
        }
    }

    private static void afficherListe(List<Event> evenements) {
        if (evenements.isEmpty()) {
            System.err.println("Aucun événement trouvé pour cette période.");
        } else {
            System.out.println("Événements trouvés : ");
            for (Event e : evenements) {
                System.out.println("- " + e.description());
            }
        }
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.println(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException _) {
                System.err.println("Entrée invalide, veuillez entrer un nombre.");
            }
        }
    }

    private String readString(String prompt) {
        while (true) {
            System.out.println(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isBlank())
                return line;

            System.err.println("Entrée invalide, veuillez entrer une chaîne de caractères non vide.");
        }
    }
}
