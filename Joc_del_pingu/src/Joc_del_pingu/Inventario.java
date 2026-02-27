package Joc_del_pingu;

import java.util.ArrayList;
import java.util.Random;

public class Inventario {

	Random rand = new Random();
	Random rando = new Random();
	
	
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
		this.dado.add(dado);
	}
	
	public void agregarBolaNieve(BolaNieve bolaNieve) {
		this.bolaNieve.add(bolaNieve);
	}
	
	public void agregarPez(Pez pez) {
		this.pez.add(pez);
	}
	
	public void eliminarDado(Dado dado) {
		this.dado.remove(dado);
	}
	
	public void eliminarBolaNieve(BolaNieve bolaNieve) {
		this.bolaNieve.remove(bolaNieve);
	}
	
	public void eliminarPez(Pez pez) {
		this.pez.remove(pez);
	}
	
	public void perderObjetoAleatorio() {
		int opcion = rando.nextInt(3);
		
		switch(opcion) {
		case 0: {
			Dado d = dado.get(rand.nextInt(2) + 1);
			this.eliminarDado(d);
			break;
		}
		case 1: {
			BolaNieve b = bolaNieve.get(opcion);
			this.eliminarBolaNieve(b);
			break;
		}
		case 2: {
			Pez p = pez.get(opcion);
			this.eliminarPez(p);
			break;
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
