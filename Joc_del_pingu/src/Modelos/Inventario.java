package Modelos;

import java.util.ArrayList;
import java.util.Random;

public class Inventario {

    private final Random rand = new Random();

    private ArrayList<Dado> dado = new ArrayList<>();
    private ArrayList<BolaNieve> bolaNieve = new ArrayList<>();
    private ArrayList<Pez> pez = new ArrayList<>();

    public Inventario(ArrayList<Dado> dado, ArrayList<BolaNieve> bolaNieve, ArrayList<Pez> pez) {
        this.dado = dado;
        this.bolaNieve = bolaNieve;
        this.pez = pez;
    }

    public Inventario() {}

    public ArrayList<Dado> getDado() { return dado; }
    public void setDado(ArrayList<Dado> dado) { this.dado = dado; }

    public ArrayList<BolaNieve> getBolaNieve() { return bolaNieve; }
    public void setBolaNieve(ArrayList<BolaNieve> bolaNieve) { this.bolaNieve = bolaNieve; }

    public ArrayList<Pez> getPez() { return pez; }
    public void setPez(ArrayList<Pez> pez) { this.pez = pez; }

    public void agregarDado(Dado item) {
        if (dado.size() < 3) {
            dado.add(item);
        } else {
            System.out.println("No es pot afegir un altre dau. Ja tens el màxim (3).");
        }
    }

    public void agregarBolaNieve(BolaNieve item) {
        if (bolaNieve.size() < 6) {
            bolaNieve.add(item);
        } else {
            System.out.println("No es poden afegir més boles de neu. Ja tens el màxim (6).");
        }
    }

    public void agregarPez(Pez item) {
        if (pez.size() < 2) {
            pez.add(item);
        } else {
            System.out.println("No es poden afegir més peixos. Ja tens el màxim (2).");
        }
    }

    public void eliminarDado(Dado item) { dado.remove(item); }
    public void eliminarBolaNieve(BolaNieve item) { bolaNieve.remove(item); }
    public void eliminarPez(Pez item) { pez.remove(item); }

    public void perderObjetoAleatorio() {
        ArrayList<Integer> disponibles = new ArrayList<>();
        if (!dado.isEmpty())      disponibles.add(0);
        if (!bolaNieve.isEmpty()) disponibles.add(1);
        if (!pez.isEmpty())       disponibles.add(2);

        if (disponibles.isEmpty()) {
            System.out.println("L'inventari és buit, no es pot perdre cap objecte.");
            return;
        }

        int opcion = disponibles.get(rand.nextInt(disponibles.size()));

        switch (opcion) {
            case 0: dado.remove(0);      System.out.println("Has perdut un dau.");         break;
            case 1: bolaNieve.remove(0); System.out.println("Has perdut una bola de neu."); break;
            case 2: pez.remove(0);       System.out.println("Has perdut un peix.");         break;
        }
    }

    public void perderMitadInventario() {
        int mitadDado      = dado.size() / 2;
        int mitadBolaNieve = bolaNieve.size() / 2;
        int mitadPez       = pez.size() / 2;

        for (int i = 0; i < mitadDado;      i++) { if (!dado.isEmpty())      dado.remove(0); }
        for (int i = 0; i < mitadBolaNieve; i++) { if (!bolaNieve.isEmpty()) bolaNieve.remove(0); }
        for (int i = 0; i < mitadPez;       i++) { if (!pez.isEmpty())       pez.remove(0); }
    }

    public void totalObjetos() {
        int dadoRapido = 0, dadoLento = 0, dadoNormal = 0;
        for (Dado d : dado) {
            if      (d instanceof Dado_rapido) dadoRapido++;
            else if (d instanceof Dado_lento)  dadoLento++;
            else                               dadoNormal++;
        }
        System.out.println("INVENTARI:");
        System.out.println("Daus normals: " + dadoNormal);
        System.out.println("Daus ràpids: "  + dadoRapido);
        System.out.println("Daus lents: "   + dadoLento);
        System.out.println("Boles de neu: " + bolaNieve.size());
        System.out.println("Peixos: "       + pez.size());
    }

    public boolean tienePez() { return !pez.isEmpty(); }

    public boolean usarPez() {
        if (!pez.isEmpty()) {
            pez.remove(0);
            return true;
        }
        return false;
    }

    public int getTotalObjetos() {
        return dado.size() + bolaNieve.size() + pez.size();
    }
}