package Joc_del_pingu;

import java.util.ArrayList;
import java.util.Random;

public class Inventario {

	Random rand = new Random();
	
	
	private ArrayList<Dado> dado = new ArrayList<Dado>();
	private ArrayList<BolaNieve> bolaNieve = new ArrayList<BolaNieve>();
	private ArrayList<Pez> pez = new ArrayList<Pez>();
	
	public Inventario(ArrayList<Dado> dado, ArrayList<BolaNieve> bolaNieve, ArrayList<Pez> pez) {
		this.dado = dado;
		this.bolaNieve = bolaNieve;
		this.pez = pez;
	}
	
	public ArrayList<Dado> getDado() {
		return dado;
	}
		
	public void setDado(ArrayList<Dado> dado) {
		this.dado = dado;
	}
	
	public ArrayList<BolaNieve> getBolaNieve() {
		return bolaNieve;
	}
	
	public void setBolaNieve(ArrayList<BolaNieve> bolaNieve) {
		this.bolaNieve = bolaNieve;
	}
	
	public ArrayList<Pez> getPez() {
		return pez;
	}
	
	public void setPez(ArrayList<Pez> pez) {
		this.pez = pez;
	}
	
	
	public void agregarDado(Dado dado) {
		
		if(this.dado.size() < 3) {
			this.dado.add(dado);
		} else {
			System.out.println("No se puede añadir otro dado, tienes los dados máximos.");
		}		
	}
	
	public void agregarBolaNieve(BolaNieve bolaNieve) {
		
		if(this.bolaNieve.size() < 6) {
			this.bolaNieve.add(bolaNieve);
		} else {
			System.out.println("No se pueden añadir más bolas de nieve, tienes las bolas de nieve máximas.");
		}
	}
	
	public void agregarPez(Pez pez) {
		
		if(this.pez.size() < 2) {
			this.pez.add(pez);
		} else {
			System.out.println("No se pueden añadir más peces, tienes los peces máximos.");
		}
	}
	
	public void eliminarDado(Dado dado) {
		
		if(this.dado.size() > 1 && this.dado.size() <= 3) {
			this.dado.remove(dado);
		}
	}
	
	public void eliminarBolaNieve(BolaNieve bolaNieve) {
		
		if(this.bolaNieve.size() > 0 && this.bolaNieve.size() <= 6) {
			this.bolaNieve.remove(bolaNieve);
		}
	}
	
	public void eliminarPez(Pez pez) {
		
		if(this.pez.size() > 0 && this.pez.size() <= 2) {
			this.pez.remove(pez);
		}
	}
	
	public void perderObjetoAleatorio() {
		int opcion = rand.nextInt(3);
		
		switch(opcion) {
		case 0: {
			Dado d = dado.get(rand.nextInt(2) + 1);
			this.eliminarDado(d);
			break;
		}
		case 1: {
			BolaNieve b = bolaNieve.get(0);
			this.eliminarBolaNieve(b);
			break;
		}
		case 2: {
			Pez p = pez.get(0);
			this.eliminarPez(p);
			break;
		}
		}
	}
	
	public void perderMitadInventario() {
		
		int mitadDado = dado.size() / 2;
		int mitadBolaNieve = bolaNieve.size() / 2;
		int mitadPez = pez.size() / 2;
		
		for(int i = 0; i < mitadDado; i++) {
			if(!dado.isEmpty()) { //si la lista NO está vacía.
				dado.remove(0); //entonces borra.
			}
		}
		
		for(int i = 0; i < mitadBolaNieve; i++) {
			if(!bolaNieve.isEmpty()) {
				bolaNieve.remove(0);
			}
		}
		
		for(int i = 0; i < mitadPez; i++) {
			if(!pez.isEmpty()) {
				pez.remove(0);
			}
		}
	}
	
	public void totalObjetos() {
		
		int dadoRapido = 0;
		int dadoLento = 0;
		
		for(Dado d : dado) { //recorremos el array con este 
			if(d instanceof Dado_rapido) { //esto es para ver que dado hay.
				dadoRapido++;
			} else if(d instanceof Dado_lento) { //esto es para ver que dado hay.
				dadoLento++;
			} else {
				
			}
		}
		
		System.out.println("INVENTARIO:");
		System.out.println("Tienes 1 dado normal.");
		System.out.println("Tienes " + dadoRapido + " rápido/s");
		System.out.println("Tienes " + dadoLento + " lento/s");
		System.out.println("Tienes" + bolaNieve.size() + " bolas de nieve.");
		System.out.println("Tienes " + pez.size() + " pez/es.");
	}
	
	public boolean tienePez() {		
		
		if(pez.size() != 0) {
			return true;
		} else {
			return false;
		}
	}
}
