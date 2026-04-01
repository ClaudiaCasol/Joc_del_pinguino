package Controladores;

import java.sql.Connection;

import Modelos.*;

public class GestorBBDD {

	private BBDD bbdd;
    private String urlBBDD;
    private String username;
    private String password;

    public String getUrlBBDD() {
        return urlBBDD;
    }

    public void setUrlBBDD(String urlBBDD) {
        this.urlBBDD = urlBBDD;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void guardarBBDD(Partida p) {
        Connection con = bbdd.conectarBaseDatos();
        
        
    }

    public Partida cargarBBDD(int id) {
        // TODO: implementar carga desde base de datos
        return null;
    }
}