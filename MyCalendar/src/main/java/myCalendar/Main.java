package myCalendar;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    static void main() {
        CalendarManager calendar = new CalendarManager();
        Scanner scanner = new Scanner(System.in);
        EventOwner utilisateur = null;
        boolean continuer = true;

        EventOwner[] utilisateurs = new EventOwner[99];
        String[] motsDePasses = new String[99];
        int nbUtilisateurs = 0;

        boolean fini = false;
        while (!fini) {

            if (utilisateur == null) {
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
                        Choix :\s""");

                switch (scanner.nextLine()) {
                    case "1":
                        System.out.print("Nom d'utilisateur: ");
                        utilisateur = new EventOwner(scanner.nextLine());

                        if (utilisateur.toString().equals("Roger")) {
                            String motDePasse = scanner.nextLine();
                            if (!motDePasse.equals("Chat")) {
                                utilisateur = null;
                            }
                        } else {
                            if (utilisateur.toString().equals("Pierre")) {
                                String motDePasse = scanner.nextLine();
                                if (!motDePasse.equals("KiRouhl")) {
                                    utilisateur = null;
                                }
                            } else {
                                System.out.print("Mot de passe: ");
                                String motDePasse = scanner.nextLine();

                                for (int i = 0; i < nbUtilisateurs; i = i + 1) {
                                    if (utilisateurs[i].toString().equals(utilisateur) && motsDePasses[i].equals(motDePasse)) {
                                        utilisateur = utilisateurs[i];
                                    }
                                }
                            }
                        }
                        break;

                    case "2":
                        System.out.print("Nom d'utilisateur: ");
                        utilisateur = new EventOwner(scanner.nextLine());
                        System.out.print("Mot de passe: ");
                        String motDePasse = scanner.nextLine();
                        System.out.print("Répéter mot de passe: ");
                        if (scanner.nextLine().equals(motDePasse)) {
                            utilisateurs[nbUtilisateurs] = utilisateur;
                            motsDePasses[nbUtilisateurs] = motDePasse;
                            nbUtilisateurs = nbUtilisateurs + 1;
                        } else {
                            System.out.println("Les mots de passes ne correspondent pas...");
                            utilisateur = null;
                        }
                        break;
                    default:
                        System.err.println("Choix invalide, veuillez réessayer.");
                }
            }

            while (continuer && utilisateur != null) {
                System.out.printf("""
                        Bonjour, %s
                        === Menu Gestionnaire d'Événements ===
                        1 - Voir les événements
                        2 - Ajouter un rendez-vous perso
                        3 - Ajouter une réunion
                        4 - Ajouter un évènement périodique
                        5 - Se déconnecter
                        Votre choix :\s""", utilisateur);

                String choix = scanner.nextLine();

                switch (choix) {
                    case "1":
                        System.out.println("""
                                === Menu de visualisation d'Événements ===
                                1 - Afficher TOUS les événements
                                2 - Afficher les événements d'un MOIS précis
                                3 - Afficher les événements d'une SEMAINE précise
                                4 - Afficher les événements d'un JOUR précis
                                5 - Retour
                                Votre choix :\s""");

                        choix = scanner.nextLine();

                        switch (choix) {
                            case "1":
                                calendar.afficherEvenements();
                                break;

                            case "2":
                                System.out.print("Entrez l'année (AAAA) : ");
                                int anneeMois = Integer.parseInt(scanner.nextLine());
                                System.out.print("Entrez le mois (1-12) : ");
                                int mois = Integer.parseInt(scanner.nextLine());

                                EventDateTime debutMois = new EventDateTime(LocalDateTime.of(anneeMois, mois, 1, 0, 0));
                                EventDateTime finMois = debutMois.plusMonths(1).minusSeconds(1);

                                afficherListe(calendar.eventsDansPeriode(debutMois, finMois));
                                break;

                            case "3":
                                System.out.print("Entrez l'année (AAAA) : ");
                                int anneeSemaine = Integer.parseInt(scanner.nextLine());
                                System.out.print("Entrez le numéro de semaine (1-52) : ");
                                int semaine = Integer.parseInt(scanner.nextLine());

                                EventDateTime debutSemaine = new EventDateTime(
                                        LocalDateTime.now()
                                                .withYear(anneeSemaine)
                                                .with(WeekFields.of(Locale.FRANCE).weekOfYear(), semaine)
                                                .with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1)
                                                .withHour(0).withMinute(0)
                                );
                                EventDateTime finSemaine = debutSemaine.plusDays(7).minusSeconds(1);

                                afficherListe(calendar.eventsDansPeriode(debutSemaine, finSemaine));
                                break;

                            case "4":
                                System.out.print("Entrez l'année (AAAA) : ");
                                int anneeJour = Integer.parseInt(scanner.nextLine());
                                System.out.print("Entrez le mois (1-12) : ");
                                int moisJour = Integer.parseInt(scanner.nextLine());
                                System.out.print("Entrez le jour (1-31) : ");
                                int jour = Integer.parseInt(scanner.nextLine());

                                EventDateTime debutJour = new EventDateTime(LocalDateTime.of(anneeJour, moisJour, jour, 0, 0));
                                EventDateTime finJour = debutJour.plusDays(1).minusSeconds(1);

                                afficherListe(calendar.eventsDansPeriode(debutJour, finJour));
                                break;
                        }
                        break;

                    case "2":
                        // Ajout simplifié d'un RDV personnel
                        System.out.print("Titre de l'événement : ");
                        EventTitle titre = new EventTitle(scanner.nextLine());
                        System.out.print("Année (AAAA) : ");
                        int annee = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mois (1-12) : ");
                        int moisRdv = Integer.parseInt(scanner.nextLine());
                        System.out.print("Jour (1-31) : ");
                        int jourRdv = Integer.parseInt(scanner.nextLine());
                        System.out.print("Heure début (0-23) : ");
                        int heure = Integer.parseInt(scanner.nextLine());
                        System.out.print("Minute début (0-59) : ");
                        int minute = Integer.parseInt(scanner.nextLine());
                        System.out.print("Durée (en minutes) : ");
                        EventDuration duree = new EventDuration(Integer.parseInt(scanner.nextLine()));

                        calendar.ajouterEvent(EventType.RDV_PERSONNEL, titre, utilisateur,
                                new EventDateTime(LocalDateTime.of(annee, moisRdv, jourRdv, heure, minute)), duree,
                                new EventLocation(""), "", new EventFrequency(0));

                        System.out.println("Événement ajouté.");
                        break;

                    case "3":
                        // Ajout simplifié d'une réunion
                        System.out.print("Titre de l'événement : ");
                        EventTitle titre2 = new EventTitle(scanner.nextLine());
                        System.out.print("Année (AAAA) : ");
                        int annee2 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mois (1-12) : ");
                        int moisRdv2 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Jour (1-31) : ");
                        int jourRdv2 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Heure début (0-23) : ");
                        int heure2 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Minute début (0-59) : ");
                        int minute2 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Durée (en minutes) : ");
                        EventDuration duree2 = new EventDuration(Integer.parseInt(scanner.nextLine()));
                        System.out.println("Lieu :");
                        EventLocation lieu = new EventLocation(scanner.nextLine());
                        
                        StringBuilder participants = new StringBuilder(utilisateur.toString());

                        System.out.println("Ajouter un participant ? (oui / non)");
                        while (scanner.nextLine().equals("oui"))
                        {
                            System.out.print("Participants : " + participants);
                            participants.append(", ").append(scanner.nextLine());
                        }

                        calendar.ajouterEvent(EventType.REUNION, titre2, utilisateur,
                                new EventDateTime(LocalDateTime.of(annee2, moisRdv2, jourRdv2, heure2, minute2)), duree2,
                                lieu, participants.toString(), new EventFrequency(0));

                        System.out.println("Événement ajouté.");
                        break;

                        case "4":
                        // Ajout simplifié d'une réunion
                        System.out.print("Titre de l'événement : ");
                        EventTitle titre3 = new EventTitle(scanner.nextLine());
                        System.out.print("Année (AAAA) : ");
                        int annee3 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mois (1-12) : ");
                        int moisRdv3 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Jour (1-31) : ");
                        int jourRdv3 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Heure début (0-23) : ");
                        int heure3 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Minute début (0-59) : ");
                        int minute3 = Integer.parseInt(scanner.nextLine());
                        System.out.print("Frequence (en jours) : ");
                        EventFrequency frequence = new EventFrequency(Integer.parseInt(scanner.nextLine()));

                        calendar.ajouterEvent(EventType.PERIODIQUE, titre3, utilisateur,
                                new EventDateTime(LocalDateTime.of(annee3, moisRdv3, jourRdv3, heure3, minute3)), new EventDuration(0),
                                new EventLocation(""), "", frequence);

                        System.out.println("Événement ajouté.");
                        break;

                    default:
                        System.out.println("Déconnexion ! Voulez-vous continuer ? (O/N)");
                        continuer = scanner.nextLine().trim().equalsIgnoreCase("oui");

                        utilisateur = null;
                }
            }
        }
    }

    private static void afficherListe(List<Event> evenements) {
        if (evenements.isEmpty()) {
            System.out.println("Aucun événement trouvé pour cette période.");
        } else {
            System.out.println("Événements trouvés : ");
            for (Event e : evenements) {
                System.out.println("- " + e.description());
            }
        }
    }
}
