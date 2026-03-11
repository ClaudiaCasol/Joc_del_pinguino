package Joc_del_pingu;

import java.sql.Connection;
import java.util.Scanner;

public class GestorBBDD{

    private Connection con;

    public GestorBBDD(){
        con = null;
    }

    public void conectar(Scanner scan){

        con = BBDD.conectarBaseDatos(scan);
    }

public void guardarPartida(Partida p){
    if(p == null){
        return;
    }
    String sql = "INSERT INTO PARTIDA (...) VALUES (...)";

    BBDD.insert(con, sql);
}
}