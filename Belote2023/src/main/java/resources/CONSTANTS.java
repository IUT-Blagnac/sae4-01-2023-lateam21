package resources;

/**
 * The type Constants.
 */
public abstract class CONSTANTS {
    /**
     * The constant B_TOURNAMENT.
     */
    public final static String B_TOURNAMENT = "Tournois";
    /**
     * The constant B_TEAMS.
     */
    public final static String B_TEAMS = "Equipes";
    /**
     * The constant B_GAMES.
     */
    public final static String B_GAMES = "Matchs";
    /**
     * The constant B_PARAMS.
     */
    public static final String B_PARAMS = "Paramètres";
    /**
     * The constant B_RESULTS.
     */
    public static final String B_RESULTS = "Résultats";
    /**
     * The constant B_TOURNAMENT_NEW.
     */
    public static final String B_TOURNAMENT_NEW = "Créer un nouveau tournoi";
    /**
     * The constant B_TOURNAMENT_SELECT.
     */
    public static final String B_TOURNAMENT_SELECT = "Sélectionner le tournoi";
    /**
     * The constant B_TOURNAMENT_DEL.
     */
    public static final String B_TOURNAMENT_DEL = "Supprimer le tournoi";
    /**
     * The constant B_TEAM_ADD.
     */
    public static final String B_TEAM_ADD = "Ajouter une équipe";
    /**
     * The constant B_TEAM_DEL.
     */
    public static final String B_TEAM_DEL = "Supprimer une équipe";
    /**
     * The constant B_TEAM_CONFIRM.
     */
    public static final String B_TEAM_CONFIRM = "Valider les équipes";
    /**
     * The constant B_ROUND_ADD.
     */
    public static final String B_ROUND_ADD = "Ajouter un tour";
    /**
     * The constant B_ROUND_DEL.
     */
    public static final String B_ROUND_DEL = "Supprimer le dernier tour";
    /**
     * The constant B_RESULTS_SHOW.
     */
    public static final String B_RESULTS_SHOW = "Afficher les résultats";
    /**
     * The constant BD_GET_EQUIPE.
     */
    public static final String BD_GET_EQUIPE = "equipe";
    /**
     * The constant BD_GET_JOUEUR1.
     */
    public static final String BD_GET_JOUEUR1 = "joueur1";
    /**
     * The constant BD_GET_JOUEUR2.
     */
    public static final String BD_GET_JOUEUR2 = "joueur2";
    /**
     * The constant BD_GET_SCORE.
     */
    public static final String BD_GET_SCORE = "score";
    /**
     * The constant BD_GET_MATCHS_GAGNES.
     */
    public static final String BD_GET_MATCHS_GAGNES = "matchs_gagnes";
    /**
     * The constant BD_GET_MATCHS_JOUES.
     */
    public static final String BD_GET_MATCHS_JOUES = "matchs_joues";
    /**
     * The constant BD_NUM_TOUR.
     */
    public static final String BD_NUM_TOUR = "num_tour";
    /**
     * The constant BD_TMATCHS.
     */
    public static final String BD_TMATCHS = "tmatchs";
    /**
     * The constant BD_TERMINES.
     */
    public static final String BD_TERMINES = "termines";
    /**
     * The constant BD_ID_EQUIPE.
     */
    public static final String BD_ID_EQUIPE = "id_equipe";
    /**
     * The constant BD_NUM_EQUIPE.
     */
    public static final String BD_NUM_EQUIPE = "num_equipe";
    /**
     * The constant BD_NOM_J_1.
     */
    public static final String BD_NOM_J_1 = "nom_j1";
    /**
     * The constant BD_NOM_J_2.
     */
    public static final String BD_NOM_J_2 = "nom_j2";
    /**
     * The constant BD_ID_TOURNOI.
     */
    public static final String BD_ID_TOURNOI = "id_tournoi";
    /**
     * The constant BD_NB_MATCHS.
     */
    public static final String BD_NB_MATCHS = "nb_matchs";
    /**
     * The constant BD_STATUT.
     */
    public static final String BD_STATUT = "statut";
    /**
     * The constant BD_NOM_TOURNOI.
     */
    public static final String BD_NOM_TOURNOI = "nom_tournoi";
    /**
     * The constant LABEL_TOURNAMENT_LIST.
     */
    public static final String LABEL_TOURNAMENT_LIST = "Liste des tournois";
    /**
     * The constant LABEL_TOURNAMENT_DETAILS.
     */
    public static final String LABEL_TOURNAMENT_DETAILS = "Détail du tournoi";
    /**
     * The constant LABEL_TOURNAMENT_TEAMS.
     */
    public static final String LABEL_TOURNAMENT_TEAMS = "Equipes du tournoi";
    /**
     * The constant LABEL_TOURNAMENT_NAME.
     */
    public static final String LABEL_TOURNAMENT_NAME = "Nom du tournoi";
    /**
     * The constant LABEL_STATUS.
     */
    public static final String LABEL_STATUS = "Statut";
    /**
     * The constant LABEL_ROUNDS.
     */
    public final static String LABEL_ROUNDS = "Tours";
    /**
     * The constant LABEL_ROUNDS_NUM.
     */
    public static final String LABEL_ROUNDS_NUM = "Nombre de tours:";
    /**
     * The constant LABEL_ROUNDS_END_PLEASE.
     */
    public static final String LABEL_ROUNDS_END_PLEASE = "Pour pouvoir ajouter un tour, terminez tous les matchs du précédent.";
    /**
     * The constant LABEL_ROUNDS_MAX.
     */
    public static final String LABEL_ROUNDS_MAX = "Le nombre maximum de tours est \"le nombre total d'équipes - 1\"";
    /**
     * The constant LABEL_TEAMS_ODD.
     */
    public static final String LABEL_TEAMS_ODD = "Dans le cas de nombre d'équipes impair, créer une équipe virtuelle";
    /**
     * The constant LABEL_GAMES_PLAYED.
     */
    public static final String LABEL_GAMES_PLAYED = "?? Matchs joués";
    /**
     * The constant LABEL_TOURNAMENT_GAMES.
     */
    public static final String LABEL_TOURNAMENT_GAMES = "Matchs du tournoi";
    /**
     * The constant LABEL_TOURNAMENT_RESULTS.
     */
    public static final String LABEL_TOURNAMENT_RESULTS = "Résultats du tournoi";
    /**
     * The constant LABEL_WINNER.
     */
    public static final String LABEL_WINNER = "Gagnant : Equipe n°";
    /**
     * The constant COLUMN_ADD_ROUND_NUMBER.
     */
    public static final String COLUMN_ADD_ROUND_NUMBER = "Numéro du tour";
    /**
     * The constant COLUMN_ADD_GAMES_NUMBER.
     */
    public static final String COLUMN_ADD_GAMES_NUMBER = "Nombre de matchs";
    /**
     * The constant COLUMN_ADD_GAMES_PLAYED.
     */
    public static final String COLUMN_ADD_GAMES_PLAYED = "Matchs joués";
    /**
     * The constant COLUMN_ADD_GAMES_WON.
     */
    public static final String COLUMN_ADD_GAMES_WON = "Matchs gagnés";
    /**
     * The constant TEAM_NUMBER.
     */
    public static final String TEAM_NUMBER = "Numéro d'équipe";
    /**
     * The constant PLAYER_1.
     */
    public static final String PLAYER_1 = "Joueur 1";
    /**
     * The constant PLAYER_2.
     */
    public static final String PLAYER_2 = "Joueur 2";
    /**
     * The constant MISSING.
     */
    public static final String MISSING = "??";
    /**
     * The constant COLUMN_ADD_TEAM_NUMBER.
     */
    public static final String COLUMN_ADD_TEAM_NUMBER = TEAM_NUMBER;
    /**
     * The constant COLUMN_ADD_NAME_PLAYER_1.
     */
    public static final String COLUMN_ADD_NAME_PLAYER_1 = "Nom joueur 1";
    /**
     * The constant COLUMN_ADD_NAME_PLAYER_2.
     */
    public static final String COLUMN_ADD_NAME_PLAYER_2 = "Nom joueur 2";
    /**
     * The constant COLUMN_ADD_SCORE.
     */
    public static final String COLUMN_ADD_SCORE = "Score";
    /**
     * The constant PLAYERS_SIGNED.
     */
    public static final String PLAYERS_SIGNED = "Inscription des joueurs";
    /**
     * The constant GAMES_GENERATED.
     */
    public static final String GAMES_GENERATED = "Génération des matchs";
    /**
     * The constant GAMES_IN_PROGRESS.
     */
    public static final String GAMES_IN_PROGRESS = "Matchs en cours";
    /**
     * The constant ENDED.
     */
    public static final String ENDED = "Terminé";
    /**
     * The constant NULL.
     */
    public static final String NULL = "Inconnu";
    /**
     * The constant DETAILS.
     */
    public final static String DETAILS = "Paramètres du tournois";
    /**
     * The constant RESULTS.
     */
    public final static String RESULTS = "Resultats";
    /**
     * The constant deftState.
     */
    public final static String deftState = "Gestion de tournois de main.Belote v1.0 - ";
    /**
     * The constant TITLE.
     */
    public static final String TITLE = "Gestion de tournoi de main.Belote";
    /**
     * The constant STATUS_SELECT_NULL.
     */
    public static final String STATUS_SELECT_NULL = "Pas de tournoi sélectionné";
    /**
     * The constant STATUS_SELECT_TOURNAMENT.
     */
    public static final String STATUS_SELECT_TOURNAMENT = "Sélection d'un tournoi";
    /**
     * The constant SIGNATURE.
     */
    public static final String SIGNATURE = "Gestion des tournois\nStrzeszewski Longéque, Avril 2023";
    /**
     * The constant ROUND.
     */
    public static final String ROUND = "Tour";
    /**
     * The constant TEAM_1.
     */
    public static final String TEAM_1 = "Équipe 1";
    /**
     * The constant TEAM_2.
     */
    public static final String TEAM_2 = "Équipe 2";
    /**
     * The constant SCORE_TEAM_1.
     */
    public static final String SCORE_TEAM_1 = "Score "+TEAM_1;
    /**
     * The constant SCORE_TEAM_2.
     */
    public static final String SCORE_TEAM_2 = "Score "+TEAM_2;
    /**
     * The constant GAMES_ENDED.
     */
    public static final String GAMES_ENDED = " matchs terminés";
    /**
     * The constant ERROR_DELETE.
     */
    public static final String ERROR_DELETE = "Erreur suppression : ";
    /**
     * The constant ERROR_UNKNOWN.
     */
    public static final String ERROR_UNKNOWN = "Erreur inconnue";
    /**
     * The constant SQL_CONNECTION_ERROR.
     */
    public static final String SQL_CONNECTION_ERROR = "Impossible de se connecter à la base de donn�e. Vérifier qu'une autre instance du logiciel n'est pas déjà ouverte.";
    /**
     * The constant BD_ID_MATCH.
     */
    public static final String BD_ID_MATCH = "id_match";
    /**
     * The constant BD_GET_NB_MATCHS.
     */
    public static final String BD_GET_NB_MATCHS = "nb_matchs";
}

