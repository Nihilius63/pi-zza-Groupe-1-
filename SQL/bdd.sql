#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------

DROP DATABASE IF EXISTS pi_zza;
CREATE DATABASE pi_zza;
USE pi_zza;

#------------------------------------------------------------
# Table: tables
#------------------------------------------------------------

CREATE TABLE tables(
        idTables     Int  Auto_increment  NOT NULL ,
        nbPlaces Int NOT NULL
	,CONSTRAINT tables_PK PRIMARY KEY (idTables)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: produit
#------------------------------------------------------------

CREATE TABLE produit(
        idProduit        Int  Auto_increment  NOT NULL ,
        nomProduit       Varchar (50) NOT NULL ,
        prixProduit      Float NOT NULL ,
        imageProduit     Varchar (255) NOT NULL ,
        tailleProduit    Varchar (50) NOT NULL ,
        categorieProduit Varchar (50) NOT NULL
	,CONSTRAINT produit_PK PRIMARY KEY (idProduit)
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
        numeroTable  Int NOT NULL
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
# Table: contient
#------------------------------------------------------------

CREATE TABLE contient(
        idCommande Int NOT NULL ,
        idProduit  Int NOT NULL ,
        quantite   Int NOT NULL
	,CONSTRAINT contient_PK PRIMARY KEY (idCommande,idProduit)

	,CONSTRAINT contient_commande_FK FOREIGN KEY (idCommande) REFERENCES commande(idCommande)
	,CONSTRAINT contient_produit0_FK FOREIGN KEY (idProduit) REFERENCES produit(idProduit)
)ENGINE=InnoDB;

