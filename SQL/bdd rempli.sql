SET NAMES utf8mb4;
DROP DATABASE IF EXISTS pi_zza;
CREATE DATABASE pi_zza;
USE pi_zza;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `categorie` (
  `idCategorie` int(11) NOT NULL,
  `nomCategorie` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `categorie` (`idCategorie`, `nomCategorie`) VALUES
(1, 'Pizzas'),
(2, 'Boissons'),
(3, 'Desserts');

CREATE TABLE `commande` (
  `idCommande` int(11) NOT NULL,
  `idTables` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `commande` (`idCommande`, `idTables`) VALUES
(1, 1),
(2, 1),
(3, 1),
(4, 1);


CREATE TABLE `contient` (
  `idCommande` int(11) NOT NULL,
  `idProduit` int(11) NOT NULL,
  `quantite` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `contient` (`idCommande`, `idProduit`, `quantite`) VALUES
(1, 2, 7777),
(2, 2, 7777);

CREATE TABLE `historique` (
  `idHistorique` int(11) NOT NULL,
  `nomProduit` varchar(50) NOT NULL,
  `quantite` int(11) NOT NULL,
  `prixProduit` float NOT NULL,
  `dateCommande` date NOT NULL,
  `numeroTables` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `produit` (
  `idProduit` int(11) NOT NULL,
  `nomProduit` varchar(50) NOT NULL,
  `prixProduit` float NOT NULL,
  `imageProduit` varchar(255) NOT NULL,
  `idCategorie` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `produit` (`idProduit`, `nomProduit`, `prixProduit`, `imageProduit`, `idCategorie`) VALUES
(1, 'Orientale', 12.5, 'images/Orientale.png', 1),
(2, 'Savoyarde', 12.5, 'images/Savoyarde.png', 1),
(3, 'Deliziosa', 12.5, 'images/Deliziosa.png', 1),
(4, 'Vegan Peppina', 12.5, 'images/Vegan_Peppina.png', 1),
(5, 'Chèvre Miel', 10.5, 'images/Chèvre_Miel.png', 1),
(6, '4 fromages', 10.5, 'images/4_fromages.png', 1),
(7, 'Hawaienne Jambon', 8.5, 'images/Hawaienne_Jambon.png', 1),
(8, 'Saumoneta', 10.5, 'images/Saumoneta.png', 1),
(9, 'Margherita', 8.5, 'images/Margherita.png', 1),
(10, 'Original Pepperonni', 8.5, 'images/Original_Pepperonni.png', 1),
(13, 'Reine', 8.5, 'images/Reine.png', 1),
(14, 'Steak & Cheese', 11, 'images/Steak_&_Cheese.png', 1),
(15, 'Peppina', 11, 'images/Peppina.png', 1),
(16, 'Deluxe', 11, 'images/Deluxe.png', 1),
(17, 'Coca-Cola', 2.5, 'images/Coca-Cola.png', 2),
(18, 'Oasis', 2.5, 'images/Oasis.png', 2),
(19, 'Evian', 2.5, 'images/Evian.png', 2),
(20, 'Badoit', 2.5, 'images/Badoit.png', 2),
(21, 'Sprite', 2.5, 'images/Sprite.png', 2),
(22, 'Mini beignets choco-noisette', 3, 'images/Mini_beignets_choco-noisette.png', 3),
(23, 'Moelleux au Chocolat', 4.99, 'images/Moelleux_au_Chocolat.png', 3);

CREATE TABLE `tables` (
  `idTables` int(11) NOT NULL,
  `nbPlaces` int(11) NOT NULL,
  `nbPersonne` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `tables` (`idTables`, `nbPlaces`, `nbPersonne`) VALUES
(1, 10, 0),
(2, 2, 0),
(3, 2, 0),
(4, 4, 0),
(5, 5, 0),
(6, 3, 0),
(7, 6, 0),
(8, 8, 0),
(9, 5, 0),
(10, 12, 0),
(11, 4, 0),
(12, 4, 0),
(13, 6, 0),
(14, 8, 0),
(15, 3, 0);

ALTER TABLE `categorie`
  ADD PRIMARY KEY (`idCategorie`);

ALTER TABLE `commande`
  ADD PRIMARY KEY (`idCommande`),
  ADD KEY `commande_tables_FK` (`idTables`);

ALTER TABLE `contient`
  ADD PRIMARY KEY (`idCommande`,`idProduit`),
  ADD KEY `contient_produit0_FK` (`idProduit`);

ALTER TABLE `historique`
  ADD PRIMARY KEY (`idHistorique`);

ALTER TABLE `produit`
  ADD PRIMARY KEY (`idProduit`),
  ADD KEY `produit_Categorie_FK` (`idCategorie`);

ALTER TABLE `tables`
  ADD PRIMARY KEY (`idTables`);

ALTER TABLE `categorie`
  MODIFY `idCategorie` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `commande`
  MODIFY `idCommande` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

ALTER TABLE `historique`
  MODIFY `idHistorique` int(11) NOT NULL AUTO_INCREMENT;

ALTER TABLE `produit`
  MODIFY `idProduit` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=29;

ALTER TABLE `tables`
  MODIFY `idTables` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

ALTER TABLE `commande`
  ADD CONSTRAINT `commande_tables_FK` FOREIGN KEY (`idTables`) REFERENCES `tables` (`idTables`) ON DELETE CASCADE;

ALTER TABLE `contient`
  ADD CONSTRAINT `contient_commande_FK` FOREIGN KEY (`idCommande`) REFERENCES `commande` (`idCommande`) ON DELETE CASCADE,
  ADD CONSTRAINT `contient_produit0_FK` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`idProduit`) ON DELETE CASCADE;

ALTER TABLE `produit`
  ADD CONSTRAINT `produit_Categorie_FK` FOREIGN KEY (`idCategorie`) REFERENCES `categorie` (`idCategorie`) ON DELETE CASCADE;

COMMIT;
