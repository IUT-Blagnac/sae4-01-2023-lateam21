CREATE table if not exists tournois(
  id_tournoi int GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) primary key,
  nb_matchs INT,
  nom_tournoi varchar(30),
  statut INTEGER
);

CREATE table if not exists equipes(
  id_equipe int GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) primary key,
  num_equipe INT,
  id_tournoi INT,
  nom_j1 varchar(30),
  nom_j2 varchar(30),
  FOREIGN KEY(id_tournoi) REFERENCES tournois (id_tournoi)
);
CREATE table if not exists matchs(
  id_match int GENERATED BY DEFAULT AS IDENTITY (START WITH 1, INCREMENT BY 1) primary key,
  id_tournoi INT,
  num_tour INT,
  equipe1 INT,
  equipe2 INT,
  score1  INT,
  score2  INT,
  termine varchar(3)
)