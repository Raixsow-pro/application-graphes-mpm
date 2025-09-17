/** Classe CheminCritique pour la [SAE 2.01 2.02 2.05 - Développement d'une application] 
 *  @author : Flem Anthony / Freret Alexandre / Martin Erwan / El Maknassi Lucas - Equipe n°06
 *  @version: 1.0
 */

package MPM.metier;

import java.util.List;
import java.util.ArrayList;

public class CheminCritique
{
	/* ----------- */
	/* Attributs   */
	/* ----------- */
	private int              nbTache;
	private ArrayList<Tache> cheminCritique;

	/* --------------- */
	/* Constructeur(s) */
	/* --------------- */
	public CheminCritique( List<Tache> lstTache ) 
	{
		this.cheminCritique = new ArrayList<Tache>(lstTache);
		this.nbTache        = 0;
	}

	/* ----------- */
	/* Accesseurs  */
	/* ----------- */
	public ArrayList<Tache> getEnsTaches() { return this.cheminCritique; }
	public int              getNbTache  () { return this.nbTache;        }

	/* --------------- */
	/* Autres méthodes */
	/* --------------- */
	public void ajouterTache(Tache t)
	{
		if ( t != null )
			this.cheminCritique.add(t);
	}

	public void supprimerTache(int indice)
	{
		if ( indice >= 0 || indice < this.cheminCritique.size() )
			this.cheminCritique.remove(indice);
	}

	public boolean contient(Tache t)
	{
		return this.cheminCritique.contains(t);
	}
}