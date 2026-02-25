package Joc_del_pingu;

import java.util.ArrayList;

public class Inventario {

	
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
		
	}
	
	public void agregarBolaNieve(BolaNieve bolaNieve) {
		
	}
	
	public void agregarPez(Pez pez) {
		
	}
	
	public void eliminarDado(Dado dado) {
		
	}
	
	public void eliminarBolaNieve(BolaNieve bolaNieve) {
		
	}
	
	public void eliminarPez(Pez pez) {
		
	}
	
	public void perderObjetoAleatorio() {
		
	}
	
	public int totalObjetos() {
		
	}
	
	public boolean tienePez() {
		
	}
}
