package com.sio.pi_zza.DTO;

public class tableInfoDTO {

    public static int idTable;
    public static int nbPersonne;
    public static int nbPlace;

    public int getIdTable() {
        return idTable;
    }

    public void setIdTable(int idTable) {
        this.idTable = idTable;
    }

    public int getNbPersonne() {
        return nbPersonne;
    }

    public void setNbPersonne(int nbPersonne) {
        this.nbPersonne = nbPersonne;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }

    public tableInfoDTO(int idTable, int nbPersonne, int nbPlace) {
        this.idTable = idTable;
        this.nbPersonne = nbPersonne;
        this.nbPlace = nbPlace;
    }
}
