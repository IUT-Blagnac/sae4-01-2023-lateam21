package models;

public abstract class CONSTANTS {


    public final static String B_TOURNAMENT = "Tournois";
    public final static String B_TEAMS = "Equipes";
    public final static String B_GAMES = "Matchs";
    public static final String B_PARAMS = "Paramètres";
    public static final String B_RESULTS = "Résultats";

    public static final String B_TOURNAMENT_NEW = "Créer un nouveau tournoi";
    public static final String B_TOURNAMENT_SELECT = "Sélectionner le tournoi";
    public static final String B_TOURNAMENT_DEL = "Supprimer le tournoi";

    public static final String B_TEAM_ADD = "Ajouter une équipe";
    public static final String B_TEAM_DEL = "Supprimer une équipe";
    public static final String B_TEAM_CONFIRM = "Valider les équipes";

    public static final String B_ROUND_ADD = "Ajouter un tour";
    public static final String B_ROUND_DEL = "Supprimer le dernier tour";

    public static final String B_RESULTS_SHOW = "Afficher les résultats";

    public static final String BD_GET_EQUIPE = "equipe";
    public static final String BD_GET_JOUEUR1 = "joueur1";
    public static final String BD_GET_JOUEUR2 = "joueur2";
    public static final String BD_GET_SCORE = "score";
    public static final String BD_GET_MATCHS_GAGNES = "matchs_gagnes";
    public static final String BD_GET_MATCHS_JOUES = "matchs_joues";
    public static final String BD_NUM_TOUR = "num_tour";
    public static final String BD_TMATCHS = "tmatchs";
    public static final String BD_TERMINES = "termines";


    public static final String LABEL_TOURNAMENT_LIST = "Liste des tournois";
    public static final String LABEL_TOURNAMENT_DETAILS = "Détail du tournoi";
    public static final String LABEL_TOURNAMENT_TEAMS = "Equipes du tournoi";
    public static final String LABEL_TOURNAMENT_NAME = "Nom du tournoi";

    public static final String LABEL_STATUS = "Statut";

    public final static String LABEL_ROUNDS = "Tours";
    public static final String LABEL_ROUNDS_NUM = "Nombre de tours:";
    public static final String LABEL_ROUNDS_END_PLEASE = "Pour pouvoir ajouter un tour, terminez tous les matchs du précédent.";
    public static final String LABEL_ROUNDS_MAX = "Le nombre maximum de tours est \"le nombre total d'équipes - 1\"";

    public static final String LABEL_TEAMS_ODD = "Dans le cas de nombre d'équipes impair, créer une équipe virtuelle";
    public static final String LABEL_GAMES_PLAYED = "?? Matchs joués";

    public static final String LABEL_TOURNAMENT_GAMES = "Matchs du tournoi";
    public static final String LABEL_TOURNAMENT_RESULTS = "Résultats du tournoi";
    public static final String LABEL_WINNER = "Gagnant:";

    public static final String COLUMN_ADD_ROUND_NUMBER = "Numéro du tour";

    public static final String COLUMN_ADD_GAMES_NUMBER = "Nombre de matchs";
    public static final String COLUMN_ADD_GAMES_PLAYED = "Matchs joués";
    public static final String COLUMN_ADD_GAMES_WON = "Matchs gagnés";

    public static final String TEAM_NUMBER = "Numéro d'équipe";
    public static final String PLAYER_1 = "Joueur 1";
    public static final String PLAYER_2 = "Joueur 2";
    public static final String MISSING = "??";

    public static final String COLUMN_ADD_TEAM_NUMBER = TEAM_NUMBER;
    public static final String COLUMN_ADD_NAME_PLAYER_1 = "Nom joueur 1";
    public static final String COLUMN_ADD_NAME_PLAYER_2 = "Nom joueur 2";
    public static final String COLUMN_ADD_SCORE = "Score";



    public static final String PLAYERS_SIGNED = "Inscription des joueurs";
    public static final String GAMES_GENERATED = "Génération des matchs";
    public static final String GAMES_IN_PROGRESS = "Matchs en cours";
    public static final String ENDED = "Terminé";
    public static final String NULL = "Inconnu";

    public final static String DETAILS = "Paramètres du tournois";
    public final static String RESULTS = "Resultats";

    public final static String deftState = "Gestion de tournois de main.Belote v1.0 - ";
    public static final String TITLE = "Gestion de tournoi de main.Belote";
    public static final String STATUS_SELECT_NULL = "Pas de tournoi sélectionné";
    public static final String STATUS_SELECT_TOURNAMENT = "Sélection d'un tournoi";
    public static final String SIGNATURE = "Gestion des tournois\nStrzeszewski Longéque, Avril 2023";




    public static final String ROUND = "Tour";
    public static final String TEAM_1 = "Équipe 1";
    public static final String TEAM_2 = "Équipe 2";
    public static final String SCORE_TEAM_1 = "Score "+TEAM_1;
    public static final String SCORE_TEAM_2 = "Score "+TEAM_2;
    public static final String TRUC_2 = "truc2";


    public static final String GAMES_ENDED = " matchs terminés";
}

