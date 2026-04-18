package myCalendar;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Application {

    private static final String MSG_CHOIX_INVALIDE = "Choix invalide";
    private static final String MSG_EVENEMENT_AJOUTE = "Événement ajouté.";
    private static final String PROMPT_TITRE_EVENEMENT = "Titre de l'événement : ";
    private static final String PROMPT_DUREE_MINUTES = "Durée (en minutes) : ";
    private static final String PROMPT_ANNEE = "Année (AAAA) :";
    private static final String PROMPT_MOIS = "Mois (1-12) : ";
    private static final String PROMPT_JOUR = "Jour (1-31) : ";
    private static final String PROMPT_NOM = "Nom :";
    private static final String PROMPT_MDP = "Mot de passe :";

    private final CalendarManager calendar = new CalendarManager();
    private final Authenticator auth =  new Authenticator();
    private final Scanner scanner = new Scanner(System.in);
    private boolean running = true;

    public void run() {
        while (running) {
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
                        2 - Créer un compte
                        3 - Quitter
                        """);
        int choix = readInt("Choix :");
        switch (choix) {
            case 1 -> login();
            case 2 -> createAccount();
            case 3 -> running = false;
            default -> System.err.println(MSG_CHOIX_INVALIDE);
        }
    }

    private void createAccount() {
        String name = readString(PROMPT_NOM);
        String password = readString(PROMPT_MDP);

        boolean accountCreated = auth.createAccount(name, password);

        if (!accountCreated)
            System.err.println("Le compte existe déjà.");
    }

    private void login() {
        String name = readString(PROMPT_NOM);
        String password = readString(PROMPT_MDP);

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
                5 - Se déconnecter
                """, auth.getCurrentUser());
        int choix = readInt("Votre choix :");
        switch (choix) {
            case 1 -> listEvents();
            case 2 -> ajouterRDVPersonnel();
            case 3 -> ajouterReunion();
            case 4 -> ajouterEventPeriodique();
            case 5 -> disconnect();
            default -> System.err.println(MSG_CHOIX_INVALIDE);
        }
    }

    private void disconnect() {
        String disconnect = readString("Déconnexion ! Confirmer la déconnexion ? (O/N)");
        switch (disconnect.toUpperCase()) {
            case "O" -> auth.disconnect();
            case "N" -> System.out.println("Déconnexion annulée.");
            default -> System.err.println("Choix invalide, déconnexion annulée.");
        }
    }

    private void ajouterEventPeriodique() {
        EventTitle title = new EventTitle(readString(PROMPT_TITRE_EVENEMENT));
        EventDateTime dateDebut = readDateTime();
        EventDuration duree = new EventDuration(readInt(PROMPT_DUREE_MINUTES));
        EventFrequency frequence = new EventFrequency(readInt("Frequence (en jours) : "));

        calendar.ajouterPeriodique(title, auth.getCurrentUser(), dateDebut, duree, frequence);

        System.out.println(MSG_EVENEMENT_AJOUTE);
    }

    private EventDateTime readDateTime() {
        while (true) {
            try {
                int annee = readInt(PROMPT_ANNEE);
                int mois = readInt(PROMPT_MOIS);
                int jour = readInt(PROMPT_JOUR);
                int heure = readInt("Heure début (0-23) : ");
                int minute = readInt("Minute début (0-59) : ");

                LocalDateTime dateTime = LocalDateTime.of(annee, mois, jour, heure, minute);
                return new EventDateTime(dateTime);
            } catch (DateTimeException _) {
                System.err.println("Date invalide.");
            }
        }
    }

    private void ajouterReunion() {
        EventTitle title = new EventTitle(readString(PROMPT_TITRE_EVENEMENT));
        EventDateTime dateDebut = readDateTime();
        EventDuration duree = new EventDuration(readInt(PROMPT_DUREE_MINUTES));
        EventLocation lieu = new EventLocation(readString("Lieu :"));

        List<EventOwner> participants = new ArrayList<>();
        participants.add(auth.getCurrentUser());
        while (readString("Ajouter un participant ? (oui / non)").equalsIgnoreCase("oui")) {
            System.out.println("Participants : " + participants);
            participants.add(new EventOwner(readString("Nom du participant : ")));
        }

        calendar.ajouterReunion(title, auth.getCurrentUser(), dateDebut, duree, lieu, new EventParticipants(participants));

        System.out.println(MSG_EVENEMENT_AJOUTE);
    }

    private void ajouterRDVPersonnel() {
        EventTitle title = new EventTitle(readString(PROMPT_TITRE_EVENEMENT));
        EventDateTime dateDebut = readDateTime();
        EventDuration duree = new EventDuration(readInt(PROMPT_DUREE_MINUTES));

        calendar.ajouterRdvPersonnel(title, auth.getCurrentUser(), dateDebut, duree);

        System.out.println(MSG_EVENEMENT_AJOUTE);
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
                    EventDateTime debut = readMonthStartDateTime();
                    EventDateTime fin = debut.plusMonths(1).minusSeconds(1);
                    afficherListe(calendar.eventsDansPeriode(debut, fin));
                }
                case 3 -> {
                    EventDateTime debut = readWeekStartDateTime();
                    EventDateTime fin = debut.plusDays(7).minusSeconds(1);
                    afficherListe(calendar.eventsDansPeriode(debut, fin));
                }
                case 4 -> {
                    EventDateTime debut = readDayStartDateTime();
                    EventDateTime fin = debut.plusDays(1).minusSeconds(1);
                    afficherListe(calendar.eventsDansPeriode(debut, fin));
                }
                case 5 -> quit = true;
                default -> System.err.println(MSG_CHOIX_INVALIDE);
            }
        }
    }

    private EventDateTime readMonthStartDateTime() {
        while (true) {
            try {
                int annee = readInt(PROMPT_ANNEE);
                int mois = readInt(PROMPT_MOIS);
                return new EventDateTime(LocalDateTime.of(annee, mois, 1, 0, 0));
            } catch (DateTimeException _) {
                System.err.println("Mois invalide.");
            }
        }
    }

    private EventDateTime readWeekStartDateTime() {
        while (true) {
            try {
                int annee = readInt(PROMPT_ANNEE);
                int semaine = readInt("Entrez la semaine (1-52) :");
                return new EventDateTime(LocalDateTime.now()
                        .withYear(annee)
                        .with(WeekFields.of(Locale.FRANCE).weekOfYear(), semaine)
                        .with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1)
                        .withHour(0).withMinute(0));
            } catch (DateTimeException _) {
                System.err.println("Semaine invalide.");
            }
        }
    }

    private EventDateTime readDayStartDateTime() {
        while (true) {
            try {
                int annee = readInt(PROMPT_ANNEE);
                int mois = readInt(PROMPT_MOIS);
                int jour = readInt(PROMPT_JOUR);
                return new EventDateTime(LocalDateTime.of(annee, mois, jour, 0, 0));
            } catch (DateTimeException _) {
                System.err.println("Date invalide.");
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
