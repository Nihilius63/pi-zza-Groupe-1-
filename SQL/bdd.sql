#------------------------------------------------------------
#        Script MySQL.
#------------------------------------------------------------

SET NAMES utf8mb4;

DROP DATABASE IF EXISTS pi-zza;
CREATE DATABASE pi-zza;
USE pi-zza;

#------------------------------------------------------------
# Table: table
#------------------------------------------------------------

CREATE TABLE table(
        idTable  Int  Auto_increment  NOT NULL ,
        nbPlaces Int NOT NULL
	,CONSTRAINT table_PK PRIMARY KEY (idTable)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: produit
#------------------------------------------------------------

CREATE TABLE produit(
        idProduit   Int  Auto_increment  NOT NULL ,
        nomProduit  Varchar (50) NOT NULL ,
        prixProduit Float NOT NULL
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
        prixProduit  Float NOT NULL ,
        dateCommande Date NOT NULL ,
        nbTable      Int NOT NULL
	,CONSTRAINT historique_PK PRIMARY KEY (idHistorique)
)ENGINE=InnoDB;


#------------------------------------------------------------
# Table: commande
#------------------------------------------------------------

CREATE TABLE commande(
        idProduit Int NOT NULL ,
        idTable   Int NOT NULL
	,CONSTRAINT commande_PK PRIMARY KEY (idProduit,idTable)

	,CONSTRAINT commande_produit_FK FOREIGN KEY (idProduit) REFERENCES produit(idProduit)
	,CONSTRAINT commande_table0_FK FOREIGN KEY (idTable) REFERENCES table(idTable)
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

