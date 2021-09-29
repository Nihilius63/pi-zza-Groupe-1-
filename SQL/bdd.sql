#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------

SET NAMES utf8mb4;

DROP DATABASE IF EXISTS pi_zza;
CREATE DATABASE pi_zza;
USE pi_zza;

#------------------------------------------------------------
# Table: table
#------------------------------------------------------------

CREATE TABLE tables(
        idTables  Int  Auto_increment  NOT NULL ,
        nbPlaces Int NOT NULL
	,CONSTRAINT table_PK PRIMARY KEY (idTables)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: produit
#------------------------------------------------------------

CREATE TABLE produit(
        idProduit   Int  Auto_increment  NOT NULL ,
        nomProduit  Varchar (50) NOT NULL ,
        prixProduit Float NOT NULL,
	tailleProduit Varchar (50) NOT NULL
	,CONSTRAINT produit_PK PRIMARY KEY (idProduit)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: ingredient
#------------------------------------------------------------

CREATE TABLE ingredient(
        idIngredient  Int  Auto_increment  NOT NULL ,
        nomIngredient Varchar (50) NOT NULL
	,CONSTRAINT ingredient_PK PRIMARY KEY (idIngredient)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: historique
#------------------------------------------------------------

CREATE TABLE historique(
        idHistorique Int  Auto_increment  NOT NULL ,
        nomProduit   Varchar (50) NOT NULL ,
        quantite Int NOT NULL,
        prixProduit  Float NOT NULL ,
        dateCommande Date NOT NULL ,
        numeroTable      Int NOT NULL
	,CONSTRAINT historique_PK PRIMARY KEY (idHistorique)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: commande
#------------------------------------------------------------

CREATE TABLE commande(
        idProduit Int NOT NULL ,
        idTables   Int NOT NULL,
        quantite Int NOT NULL
	,CONSTRAINT commande_PK PRIMARY KEY (idProduit,idTables)

	,CONSTRAINT commande_produit_FK FOREIGN KEY (idProduit) REFERENCES produit(idProduit)
	,CONSTRAINT commande_table0_FK FOREIGN KEY (idTables) REFERENCES tables(idTables)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: contient
#------------------------------------------------------------

CREATE TABLE contient(
        idIngredient Int NOT NULL ,
        idProduit    Int NOT NULL
	,CONSTRAINT contient_PK PRIMARY KEY (idIngredient,idProduit)

	,CONSTRAINT contient_ingredient_FK FOREIGN KEY (idIngredient) REFERENCES ingredient(idIngredient)
	,CONSTRAINT contient_produit0_FK FOREIGN KEY (idProduit) REFERENCES produit(idProduit)
)ENGINE=InnoDB;

