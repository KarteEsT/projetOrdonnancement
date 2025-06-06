/*
 * Graphe.java                                           14 mai 2025
 * IUT de Rodez, Info1 2024-2025 TP2, pas de copyright
 */
package iut.info1.ordonnancement;

import java.util.ArrayList;
import java.util.List;

/**
 * Représenter et gère un graphe PERT 
 * (Program Evaluation Review Technique).
 * Elle permet d'ajouter des tâches, de définir leurs dépendances
 * et de calculer le chemin critique du projet.
 *
 * @author Le Goff Gabriel
 * @author Robache Gabriel
 * @author Roveri Esteban
 * @author Massicard MAel
 * @author Sauvaire Leo
 * @version 1.0
 */
public class Graphe {
    
    /** Titre du graphe */
    private String titre;
    
    /** Unité de temps du graphe */
    private String unite;
    
    /** Ensembles des différentes tâches du graphe */
    private ArrayList<Tache> taches;
    
    /** Ensemble des différents événement du graphe */
    private ArrayList<Evenement> evenements;
    
    /**
     * Crée un graphe PERT
     * @param titre du graphe
     * @param unite du graphe
     * @param taches composant le graphe
     * @param listeEvenements composant le graphe
     * @throws NullPointerException si le titre ou l'unité est null
     */
    public Graphe(String titre, String unite, ArrayList<Tache> taches) {
        if (titre == null || titre.isEmpty() || titre.isBlank()) {
            throw new NullPointerException("Le titre ne peut pas être null.");
        }

        if (unite == null) {
            throw new NullPointerException("L'unité ne peut pas être null.");
        }

        this.taches = new ArrayList<>();
        if (taches != null && !taches.isEmpty()) {
            for (Tache tache : taches) {
                ajouterTache(tache);
            }
        }
        
        this.evenements = new ArrayList<>();
        this.titre = titre;
        this.unite = unite;
    }

    /**
     * @return nouvelle valeur de titre
     */
    public String getTitre() {
        return titre;
    }

    /**
     * @return nouvelle valeur de unite
     */
    public String getUnite() {
        return unite;
    }

    /**
     * @return nouvelle valeur de taches
     */
    public ArrayList<Tache> getTaches() {
        return taches;
    }
    
    /**
     * Définit les tâches du graphe.
     * 
     * @param taches les tâches à définir
     */
    private void setTaches(ArrayList<Tache> taches) {
        this.taches = taches;
    }

    /**
     * @return nouvelle valeur de évènement
     */
    public ArrayList<Evenement> getEvenements() {
        return evenements;
    }
    
    /**
     * Ajoute une tâche au graphe et vérifie la validité 
     * des tâches requises.
     *
     * @param tache la tâche à ajouter
     * @throws NullPointerException si la tâche est null
     * @throws IllegalArgumentException si la tâche existe déjà dans le graphe
     */
    public void ajouterTache(Tache tache) {
        if (tache == null) {
            throw new NullPointerException("Une tâche ne peut pas être null.");
        }
        if (tache.getTachesRequises() != null ) {
            for (Tache requise : tache.getTachesRequises()) {
                if (!getTaches().contains(requise)) {
                    throw new IllegalArgumentException(
                            "La tâche requise " + requise.getLibelle() + 
                            " n'existe pas dans le graphe.");
                }
            }
        }
        
        if (getTaches().isEmpty() || getTaches() == null) {
            getTaches().add(tache);
        } else {
            if (getTaches().contains(tache)) {
                throw new IllegalArgumentException("La tâche " + 
                                                tache.getLibelle() +
                                                " existe déjà dans le graphe.");
            }

            getTaches().add(tache);
        }
        
    }
    
    /**
     * Ajoute plusieurs tâches au graphe. Cette méthode vérifie que les tâches
     * n'existent pas déjà
     * 
     * @param taches les tâches à ajouter
     * @throws NullPointerException     si une des tâches est null
     * @throws IllegalArgumentException si une des tâches 
     *         existe déjà dans le graphe
     */
    public void ajouterPlusieursTaches(Tache... taches) {
        for (Tache tache : taches) {
            ajouterTache(tache);
        }
    }

    /**
     * Ajoute un évènement au graphe.
     * Cette méthode vérifie que l'évènement n'existe pas déjà
     * @param evenement l'évènement à ajouter
     * @throws NullPointerException si la tâche est null
     * @throws IllegalArgumentException si l'évènement 
     *         existe déjà dans le graphe
     */
    private void ajouterEvenement(Evenement evenement) {
        if (evenement == null) {
            throw new NullPointerException("Un évènement ne " +
                                           "peut pas être null.");
        }
        if (!getEvenements().contains(evenement)) {
            getEvenements().add(evenement);
        }
        
    }
    
    /**
     * @return le nombre de tâches du graphe
     */
    public int getNombreTaches() {
        return getTaches().size();
    }
    
    /**
     * Trie la liste des tâches du graphe en fonction de leurs
     * taches requises (dépendances).
     * @throws IllegalStateException si le graphe contient un circuit
     */
    public void trierTaches() {
        if (existeCircuit()) {
            throw new IllegalStateException("Le graphe contient un circuit," + 
                                          " l'ordonnancement est impossible.");
        }
        /* Tri ArrayList */
        ArrayList<Tache> tachesTriees = new ArrayList<>(); 
        ArrayList<Tache> tachesNonTriees = new ArrayList<>(getTaches()); 
        ArrayList<Tache> tachesAEnlever = new ArrayList<>(); 
        
        while (!tachesNonTriees.isEmpty()) {
            for (Tache aTrier : tachesNonTriees) {
                if ( aTrier.getTachesRequises().isEmpty() || 
                     tachesTriees.containsAll(aTrier.getTachesRequises())) {
                    
                    tachesTriees.add(aTrier);
                    tachesAEnlever.add(aTrier);
                }
            }
            tachesNonTriees.removeAll(tachesAEnlever);
            tachesAEnlever.clear(); 
        }
        setTaches(tachesTriees);
    }
    
    /**
     * Méthode permettant de vérifier si il existe un 
     * circuit dans le graphe des tâches.
     * @return true si un circuit existe,
     *         false sinon.
     */
    public boolean existeCircuit() {
        // Modéliser le graphe par une matrice d'adjacence
        // M* = M
        /*
         * Algorithme de détection de circuit :
         * On recherche si il existe une ligne ou une colonne de 0 dans M*
         * ^  -si non, alors il existe un circuit
         * |  -si oui :
         * |    -on supprime la ligne et la colonne correspondantes
         * |    -M* devient la matrice résultante de cette opération
         * |    -on recommence jusqu'à ce que M* ne contienne que des 0 
         * |     (ou soit vide,). Alors il n'existe pas de circuit
         * |     |
         *  ------
         */
        
        /* Modélisation du graphe par une matrice d'adjacence */
        /* On compte le nombre de taches */
        int nombreTaches = getNombreTaches();
        
        /* On crée la matrice d'adjacence */
        boolean[][] matriceAdjacence = new boolean[nombreTaches][nombreTaches];
        
        /* On remplit la matrice d'adjacence */
        for (int i = 0; i < nombreTaches; i++) {
            Tache tache = getTaches().get(i);
            for (Tache requise : tache.getTachesRequises()) {
                int j = getTaches().indexOf(requise);
                if (j != -1) {
                    matriceAdjacence[i][j] = true;
                }
            }
        }
        
        /* Algorithme de détection de circuit */
        boolean matriceEnCoursDeManipulation = true;
        boolean[] supprime = new boolean[nombreTaches];
        do {
            boolean modification = false;

            for (int i = 0; i < nombreTaches; i++) {
                if (supprime[i]) {
                    continue; // Ignorer les lignes/colonnes déjà supprimées
                }

                boolean ligneVide = true;
                boolean colonneVide = true;

                for (int j = 0; j < nombreTaches; j++) {
                    if (!supprime[j]) {
                        ligneVide &= !matriceAdjacence[i][j];
                        colonneVide &= !matriceAdjacence[j][i];
                    }
                }

                if (ligneVide || colonneVide) {
                    supprime[i] = true; // ligne/colonne comme supprimée
                    modification = true;
                }
            }

            if (!modification) {
                for (int i = 0; i < nombreTaches; i++) {
                    if (!supprime[i]) {
                        for (int j = 0; j < nombreTaches; j++) {
                            if (!supprime[j] && matriceAdjacence[i][j]) {
                                return true; // Circuit détecté
                            }
                        }
                    }
                }
                matriceEnCoursDeManipulation = false; // Pas de circuit
            }
        } while (matriceEnCoursDeManipulation);

        return false;
    }
    
    /**
     * Initialise le graphe en créant les événements,
     * les tâches fictives et en calculant les dates.
     */
    public void initialiserGraphe() {
        // Étape 1 : Créer les événements
        creerEvenements();
        
        // Étape 2 : Calcul des dates au plus tôt
        Outils.calculerDatesAuPlusTot(this);
    
        // Étape 3 : Calcul des dates au plus tard
        Outils.calculerDatesAuPlusTard(this);
    }

    /**
     * Crée les événements du graphe en fonction des tâches.
     */
    public void creerEvenements() {
        getEvenements().clear();
        Evenement evenementInitial = new Evenement();
        ajouterEvenement(evenementInitial);
    
        int compteurId = 1;
    
        for (Tache tache : getTaches()) {
            ArrayList<Evenement> evenementPredec = new ArrayList<>();
    
            if (tache.getTachesRequises().isEmpty()) {

                evenementPredec.add(evenementInitial);
                evenementInitial.addTacheSuccesseur(tache);
            } else {
                // Ajout des événements de fin des tâches requises
                for (Tache tacheRequise : tache.getTachesRequises()) {
                    Evenement evenementFinRequise = trouverEvenementParTache(tacheRequise);
                    if (evenementFinRequise == null) {
                        /* Nous avons trié le graphe avant */
                        throw new IllegalStateException("Tâche requise non encore traitée : " + tacheRequise.getLibelle());
                    }
                    evenementPredec.add(evenementFinRequise);
                    evenementFinRequise.addTacheSuccesseur(tache);
                }
            }
    
            // Création de l'événement de fin pour la tâche courante
            Evenement evenementFin = new Evenement(compteurId++, evenementPredec, new ArrayList<>(List.of(tache)));
            for (Evenement evenement : evenementPredec) {
                evenement.addEvenementSuccesseur(evenementFin);
            }
            ajouterEvenement(evenementFin);
        }
        
        creerEvenementFinal();
    }

    /**
     * Crée une étape finale qui regroupe toutes les tâches qui n'ont pas
     * d'étape sortante.
     */
    private void creerEvenementFinal() {
        Evenement evenementFinal = new Evenement("Fin");
        ajouterEvenement(evenementFinal);
    
        for (Evenement evenement : getEvenements()) {
            if (evenement.getEvenementSuccesseurList().isEmpty()) {
                evenement.addEvenementSuccesseur(evenementFinal);
                evenementFinal.addEvenementPredecesseur(evenement);
            }
        }
    }

    /**
     * Trouve l'événement correspondant à une tâche donnée.
     */
    private Evenement trouverEvenementParTache(Tache tache) {
        for (Evenement evenement : getEvenements()) {
            if (evenement.getTachePredecesseurList().contains(tache)) {
                return evenement;
            }
        }
        return null;
    }
    
    /**
     * Crée les événements du graphe en fonction des tâches.
     */
    public void creerEvenements2() {
        if (getTaches() == null || getTaches().isEmpty()) {
            throw new IllegalArgumentException("Le graphe ne contient pas de tâches.");
        }
    
    
        // Nettoyage des anciens événements
        getEvenements().clear();
    
        // Étape 1 : Créer l'événement initial
        Evenement evenementInitial = new Evenement(); // id = 0 par défaut
        ajouterEvenement(evenementInitial);
    
        int compteurId = 1;
    
        // Stocke les événements de fin pour chaque tâche
        ArrayList<Tache> tachesCreees = new ArrayList<>();
        ArrayList<Evenement> evenementsFin = new ArrayList<>();
    
        for (Tache tache : getTaches()) {
            // Liste des événements prédécesseurs
            ArrayList<Evenement> evenementsPred = new ArrayList<>();
            ArrayList<Tache> tachesPred = new ArrayList<>();
    
    
            if (!tachesPred.contains(tache)) {
                tachesPred.add(tache);
            }
            if (tache.getTachesRequises().isEmpty()) {
                // Dépend de l'événement initial
                evenementsPred.add(evenementInitial);
                evenementInitial.addTacheSuccesseur(tache);
            } else {
                // Dépend des événements de fin des tâches requises
                for (Tache tacheRequise : tache.getTachesRequises()) {
                    int index = tachesCreees.indexOf(tacheRequise);
                    if (index == -1) {
                        throw new IllegalStateException("Tâche requise non encore traitée : " + tacheRequise.getLibelle());
                    }
                    Evenement evenementFinRequise = evenementsFin.get(index);
                    evenementsPred.add(evenementFinRequise);
                    evenementFinRequise.addTacheSuccesseur(tache);
                }
            }
            if (evenementsPred.size() != tachesPred.size()) {
                throw new IllegalStateException("Les listes des prédécesseurs ne sont pas synchronisées.");
            }
    
            // Créer l'événement de fin pour la tâche courante
            Evenement evenementFin = new Evenement(compteurId++, evenementsPred, new ArrayList<>(List.of(tache)));
            evenementFin.addTachePredecesseur(tache);
    
            // Lier les prédécesseurs à ce nouvel événement
            for (Evenement e : evenementsPred) {
                e.addEvenementSuccesseur(evenementFin);
            }
    
            // Enregistrer la tâche et son événement de fin
            tachesCreees.add(tache);
            evenementsFin.add(evenementFin);
            ajouterEvenement(evenementFin);
        }
    }
    
    /**
     * Crée les événements du graphe en fonction des tâches.
     * Cette méthode crée un événement initial,
     * un événement de fin pour chaque tâche,
     * puis un événement de fin de projet.
     */
    public void creerEvenements22() {
        if (getTaches() == null || getTaches().isEmpty()) {
            throw new IllegalArgumentException("Le graphe ne contient pas de tâches.");
        }

        // Nettoyage des anciens événements
        getEvenements().clear();

        // Étape 1 : Créer l'événement initial
        Evenement evenementInitial = new Evenement(); // id = 0 par défaut
        ajouterEvenement(evenementInitial);

        int compteurId = 1;

        // Étape 2 : Créer les événements pour chaque tâche
        ArrayList<Tache> tachesCreees = new ArrayList<>();
        ArrayList<Evenement> evenementsFin = new ArrayList<>();

        for (Tache tache : getTaches()) {
            ArrayList<Evenement> evenementPredec = new ArrayList<>();
            ArrayList<Tache> tachesPredec = new ArrayList<>();

            if (tache.getTachesRequises().isEmpty()) {
                // Si la tâche n'a pas de tâches requises, elle dépend de l'événement initial
                evenementPredec.add(evenementInitial);
                evenementInitial.addTacheSuccesseur(tache);
            } else {
                // Dépend des événements de fin des tâches requises
                for (Tache tacheRequise : tache.getTachesRequises()) {
                    int index = tachesCreees.indexOf(tacheRequise);
                    if (index == -1) {
                        throw new IllegalStateException("Tâche requise non encore traitée : " + tacheRequise.getLibelle());
                    }
                    Evenement evenementFinRequise = evenementsFin.get(index);
                    evenementPredec.add(evenementFinRequise);
                    evenementFinRequise.addTacheSuccesseur(tache);
                }
            }

            // Vérification de la synchronisation des listes
            tachesPredec.add(tache);
            if (evenementPredec.size() != tachesPredec.size()) {
                throw new IllegalStateException("Les listes des prédécesseurs ne sont pas synchronisées.");
            }

            // Créer l'événement de fin pour la tâche courante
            Evenement evenementFin = new Evenement(compteurId++, evenementPredec, new ArrayList<>(List.of(tache)));
            evenementFin.addTachePredecesseur(tache);

            // Lier les prédécesseurs à ce nouvel événement
            for (Evenement evenement : evenementPredec) {
                evenement.addEvenementSuccesseur(evenementFin);
            }

            // Enregistrer la tâche et son événement de fin
            tachesCreees.add(tache);
            evenementsFin.add(evenementFin);
            ajouterEvenement(evenementFin);
        }

        // Étape 3 : Créer l'événement de fin du projet
        Evenement evenementFinProjet = new Evenement(compteurId++, new ArrayList<>(), new ArrayList<>());
        for (Evenement evenement : getEvenements()) {
            if (evenement.getEvenementSuccesseurList().isEmpty()) {
                evenementFinProjet.addEvenementPredecesseur(evenement);
                evenement.addEvenementSuccesseur(evenementFinProjet);
            }
        }
        ajouterEvenement(evenementFinProjet);
    }
    
    /**
     * TODO commenter le rôle de cette méthode (SRP)
     */
    public void creerEvenements222() {
        if (getTaches() == null || getTaches().isEmpty()) {
            throw new IllegalArgumentException("Le graphe ne contient pas de tâches.");
        }

        // 1) On vide la liste des événements s'il en existe déjà
        getEvenements().clear();

        // 2) Créer l'événement initial (ID = 0) via le constructeur par défaut
        Evenement evenementInitial = new Evenement(); // id = 0
        ajouterEvenement(evenementInitial);

        int compteurId = 1;
        // Pour mémoriser, pour chaque tâche, l'événement de fin créé
        ArrayList<Tache>   tachesCreees = new ArrayList<>();
        ArrayList<Evenement> evtsFin    = new ArrayList<>();

        // 3) Pour chaque tâche du graphe, créer son événement de fin
        for (Tache tache : getTaches()) {
            // 3.a) Construire la liste des événements prédécesseurs pour cette tâche
            ArrayList<Evenement> listePred = new ArrayList<>();

            if (tache.getTachesRequises().isEmpty()) {
                // Si la tâche n'a pas de dépendance, elle part de l'événementInitial
                listePred.add(evenementInitial);
                // On rattache la tâche à l'événementInitial pour la navigation
                evenementInitial.addTacheSuccesseur(tache);
            } else {
                // Sinon, on récupère, pour chaque tâche requise, son événement de fin précédent
                for (Tache tReq : tache.getTachesRequises()) {
                    int index = tachesCreees.indexOf(tReq);
                    if (index == -1) {
                        throw new IllegalStateException(
                            "Tâche requise non encore traitée : " + tReq.getLibelle()
                        );
                    }
                    Evenement evtFinReq = evtsFin.get(index);
                    listePred.add(evtFinReq);
                    // On rattache la tâche comme successeur de evtFinReq
                    evtFinReq.addTacheSuccesseur(tache);
                }
            }

            // 3.b) Créer l'événement de fin pour cette tâche (avec au moins un prédécesseur)
            Evenement evtFinTache = new Evenement(
                compteurId++,
                listePred,
                new ArrayList<>(List.of(tache))
            );

            // 3.c) Pour chaque prédécesseur, lier son successeur
            for (Evenement pred : listePred) {
                pred.addEvenementSuccesseur(evtFinTache);
            }
            // 3.d) Dans l'événement de fin, faire le lien retour vers la tâche
            evtFinTache.addTachePredecesseur(tache);

            // 3.e) Sauvegarder la tâche et son événement pour les prochaines itérations
            tachesCreees.add(tache);
            evtsFin.add(evtFinTache);
            ajouterEvenement(evtFinTache);
        }

        // 4) Construire l'événement final du projet
        //    – On récupère d'abord la liste de tous les événements qui n'ont PAS de successeur
        ArrayList<Evenement> listePredFinal = new ArrayList<>();
        for (Evenement e : getEvenements()) {
            if (e.getEvenementSuccesseurList().isEmpty()) {
                listePredFinal.add(e);
            }
        }

        //    – On vérifie qu'il y a au moins un « feuille »
        if (listePredFinal.isEmpty()) {
            throw new IllegalStateException("Impossible de créer l'événement final : pas de feuille détectée.");
        }

        // 4.a) Créer l'événement final AVEC la liste de prédécesseurs non vide
        Evenement evtFinProjet = new Evenement(
            compteurId++,
            listePredFinal,
            new ArrayList<>() // pas de tâche associée au final
        );

        // 4.b) Pour chaque roue d'où on provenait, lier ce nouvel événement comme successeur
        for (Evenement pred : listePredFinal) {
            pred.addEvenementSuccesseur(evtFinProjet);
        }

        ajouterEvenement(evtFinProjet);
    }


    @Override
    public String toString() {
        String graphe = getTitre();
        graphe += ", unite = " + getUnite();
        graphe += "\nDate de fin de projet : " + Outils.calculerFinProjet(this);
        graphe += "\n" + getTaches();
        graphe += "\n" + getEvenements();
        return graphe;
    }
}