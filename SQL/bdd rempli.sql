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
(2, 'Calzones'),
(3, 'Boissons'),
(4, 'Desserts');

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
(1, 'Orientale', 12.5, '../img/Orientale.webp', 1),
(2, 'Savoyarde', 12.5, '../img/Savoyarde.webp', 1),
(3, 'Deliziosa', 12.5, '../img/Deliziosa.webp', 1),
(4, 'Vegan Peppina', 12.5, '../img/Vegan Peppina.webp', 1),
(5, 'Chèvre Miel', 10.5, '../img/chevre miel.webp', 1),
(6, '4 fromages', 10.5, '../img/4fromages.webp', 1),
(7, 'Hawaienne Jambon', 8.5, '../img/Hawaienne.webp', 1),
(8, 'Saumoneta', 10.5, '../img/Saumoneta.webp', 1),
(9, 'Margherita', 8.5, '../img/Margherita.webp', 1),
(10, 'Original Pepperonni', 8.5, '../img/pepperonni.webp', 1),
(13, 'Reine', 8.5, '../img/Reine.webp', 1),
(14, 'Steak & Cheese', 11, '../img/Steak.webp', 1),
(15, 'Peppina', 11, '../img/Peppina.webp', 1),
(16, 'Deluxe', 11, '../img/Deluxe.webp', 1),
(17, 'Samourai', 11.99, '../img/Samouraic.webp', 2),
(18, 'Chèvre Miel', 11.99, '../img/chevre mielc.webp', 2),
(19, 'Kebab', 11.99, '../img/Kebabc.webp', 2),
(20, 'Poulet-Curry', 11.99, '../img/poulet-curryc.webp', 2),
(22, 'Coca-Cola', 2.5, '../img/coca.webp', 3),
(23, 'Oasis', 2.5, '../img/Oasis.webp', 3),
(24, 'Evian', 2.5, '../img/evian.webp', 3),
(25, 'Badoit', 2.5, '../img/badoit.webp', 3),
(26, 'Sprite', 2.5, '../img/Sprite.webp', 3),
(27, 'Mini beignets choco-noisette ', 3, '../img/beignets.webp', 4),
(28, 'Moelleux au Chocolat', 4.99, '../img/moelleux.webp', 4);

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
  ADD CONSTRAINT `commande_tables_FK` FOREIGN KEY (`idTables`) REFERENCES `tables` (`idTables`);

ALTER TABLE `contient`
  ADD CONSTRAINT `contient_commande_FK` FOREIGN KEY (`idCommande`) REFERENCES `commande` (`idCommande`),
  ADD CONSTRAINT `contient_produit0_FK` FOREIGN KEY (`idProduit`) REFERENCES `produit` (`idProduit`);

ALTER TABLE `produit`
  ADD CONSTRAINT `produit_Categorie_FK` FOREIGN KEY (`idCategorie`) REFERENCES `categorie` (`idCategorie`);

COMMIT;
