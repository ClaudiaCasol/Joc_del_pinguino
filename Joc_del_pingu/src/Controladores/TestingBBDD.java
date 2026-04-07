package Controladores;

import Modelos.*;

public class TestingBBDD {
	public static void main(String[] args) {
		
		BBDD bbdd;
        GestorBBDD bases;

        GestorPartida tableta = new GestorPartida();

        tableta.iniciarPartida(4);




        GestorBBDD.guardar(tableta.getPartida());
	}
}
