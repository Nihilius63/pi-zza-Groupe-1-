#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------
SET NAMES utf8mb4;
DROP DATABASE IF EXISTS pi_zza;
CREATE DATABASE pi_zza;
USE pi_zza;

#------------------------------------------------------------
# Table: tables
#------------------------------------------------------------

CREATE TABLE tables(
        idTables     Int  Auto_increment  NOT NULL ,
        nbPlaces Int NOT NULL,
        nbPersonne Int NOT NULL
	,CONSTRAINT tables_PK PRIMARY KEY (idTables)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: historique
#------------------------------------------------------------

CREATE TABLE historique(
        idHistorique Int  Auto_increment  NOT NULL ,
        nomProduit   Varchar (50) NOT NULL ,
        quantite     Int NOT NULL ,
        prixProduit  Float NOT NULL ,
        dateCommande Date NOT NULL ,
        numeroTables  Int NOT NULL
	,CONSTRAINT historique_PK PRIMARY KEY (idHistorique)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: commande
#------------------------------------------------------------

CREATE TABLE commande(
        idCommande Int  Auto_increment  NOT NULL ,
        idTables   Int NOT NULL
	,CONSTRAINT commande_PK PRIMARY KEY (idCommande)

	,CONSTRAINT commande_tables_FK FOREIGN KEY (idTables) REFERENCES tables(idTables)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Taille
#------------------------------------------------------------

CREATE TABLE Taille(
        idTaille  Int  Auto_increment  NOT NULL ,
        nomTaille Varchar (50) NOT NULL
	,CONSTRAINT Taille_PK PRIMARY KEY (idTaille)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Categorie
#------------------------------------------------------------

CREATE TABLE Categorie(
        idCategorie  Int  Auto_increment  NOT NULL ,
        nomCategorie Varchar (50) NOT NULL
	,CONSTRAINT Categorie_PK PRIMARY KEY (idCategorie)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: produit
#------------------------------------------------------------

CREATE TABLE produit(
        idProduit    Int  Auto_increment  NOT NULL ,
        nomProduit   Varchar (50) NOT NULL ,
        prixProduit  Float NOT NULL ,
        imageProduit Varchar (255) NOT NULL ,
        idCategorie  Int NOT NULL
	,CONSTRAINT produit_PK PRIMARY KEY (idProduit)

	,CONSTRAINT produit_Categorie_FK FOREIGN KEY (idCategorie) REFERENCES Categorie(idCategorie)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: contient
#------------------------------------------------------------

CREATE TABLE contient(
        idCommande Int NOT NULL ,
        idProduit  Int NOT NULL
	,CONSTRAINT contient_PK PRIMARY KEY (idCommande,idProduit)

	,CONSTRAINT contient_commande_FK FOREIGN KEY (idCommande) REFERENCES commande(idCommande)
	,CONSTRAINT contient_produit0_FK FOREIGN KEY (idProduit) REFERENCES produit(idProduit)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: Avoir
#------------------------------------------------------------

CREATE TABLE Taille_Produit(
        idTaille  Int NOT NULL ,
        idProduit Int NOT NULL
	,CONSTRAINT Avoir_PK PRIMARY KEY (idTaille,idProduit)

	,CONSTRAINT Avoir_Taille_FK FOREIGN KEY (idTaille) REFERENCES Taille(idTaille)
	,CONSTRAINT Avoir_produit0_FK FOREIGN KEY (idProduit) REFERENCES produit(idProduit)
)ENGINE=InnoDB;

