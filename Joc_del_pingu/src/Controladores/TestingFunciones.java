package Controladores;

/**
 * Classe auxiliar de proves per testejar funcions de GestorBBDD.
 * Només s'utilitza durant el desenvolupament per comprovar el correcte
 * funcionament dels mètodes de la base de dades.
 */
public class TestingFunciones {

	/**
	 * Mètode principal d'execució per a proves.
	 * Crida directament funcions de GestorBBDD per verificar els resultats.
	 *
	 * @param args Arguments de la línia de comandes (no s'utilitzen)
	 */
	public static void main(String[] args) {
		
		System.out.println(GestorBBDD.obtenerMedia());
	}
}