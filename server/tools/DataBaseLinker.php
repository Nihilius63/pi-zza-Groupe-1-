<?php

class DatabaseLinker
{
    private static $host = "mysql:host=localhost;dbname=pi_zza;charset=utf8";
    private static $login = "root";
    private static $password = "";
    private static $connex;

    public static function getConnexion()
    {
        if (DatabaseLinker::$connex == null)
        {
            DatabaseLinker::$connex = new PDO(DatabaseLinker::$host, DatabaseLinker::$login, DatabaseLinker::$password);
        }

        return DatabaseLinker::$connex; 
    }
}