/** Classe Controleur pour la [SAE 2.01 2.02 2.05 - Développement d'une application] 
 *  @author : Flem Anthony / Freret Alexandre / Martin Erwan / El Maknassi Lucas - Equipe n°06
 *  @version: 1.0
 */

package MPM;

import MPM.metier.CheminCritique;
import MPM.metier.MPM;
import MPM.metier.Tache;
import MPM.ihm.graphe.FrameMPM;
import MPM.ihm.tache.FrameTache;

import java.util.List;

public class Controleur
{
	/* ----------- */
	/* Attributs   */
	/* ----------- */
	private MPM metier;
	private FrameMPM frame;
	private FrameTache frameTache;

	/* --------------- */
	/* Constructeur(s) */
	/* --------------- */
	public Controleur()
	{
		this.metier = new MPM();
		this.frame = new FrameMPM(this);
		this.frameTache = null;
	}

	/* ----------- */
	/* Accesseurs  */
	/* ----------- */
	public Tache                getTache      (int id)       { return this.metier.getTache      (id);   }
	public int                  getNbTaches   ()             { return this.metier.getNbTaches   ();     }
	public int                  getEtape      ()             { return this.metier.getEtape      ();     }
	public int                  getNbNiveaux  ()             { return this.metier.getNbNiveaux  ();     }
	public List<CheminCritique> getChemins    ()             { return this.metier.getChemins    ();     }
	public Integer              getIndiceTache(int x, int y) { return this.metier.getIndiceTache(x, y); }
	public int                  getNbTachesMax()             { return this.metier.getNbTachesMax();     }

	/* ---------------- */
	/* Méthodes de maj  */
	/* ---------------- */
	public String afficheChemins(int indice)
	{
		return this.metier.afficheChemins(indice);
	}

	public boolean critiqueActive()
	{
		return this.frame.critiqueActive();
	}

	public int derniereEtape()
	{
		return this.frame.derniereEtape();
	}

	public void setLongueurGraphe(int lg)
	{
		this.metier.setLongueurGraphe(lg);
	}

	public void setHauteurGraphe(int ht)
	{
		this.metier.setHauteurGraphe(ht);
	}

	public void majCalculs()
	{
		this.metier.majCalculs();
	}

	public void majIhm()
	{
		this.frame.majIhm();
	}

	public void majGraphe()
	{
		this.frame.majGraphe();
	}

	public void majTailleGraphe()
	{
		this.frame.majTailleGraphe();
	}

	/* ---------------- */
	/* Autres méthodes  */
	/* ---------------- */
	public void initPosition(String fichier)
	{
		this.metier.initPosition(fichier);
	}

	public void calculerCheminsCritiques()
	{
		this.metier.calculerCheminsCritiques();
	}

	public void varierEtape(int niveau)
	{
		this.metier.varierEtape(niveau);
	}

	public void deplacerTache(int id, int x, int y)
	{
		this.metier.deplacerTache(id, x, y);
	}

	public void ajouterTache()
	{
		this.metier.ajouterTache();
	}

	public void supprimerTache(Tache tache)
	{
		this.metier.supprimerTache(tache);
	}

	public void calculerDatePlusTot()
	{
		this.metier.calculerDatePlusTot();
	}

	public void calculerDatePlusTard()
	{
		this.metier.calculerDatePlusTard();
	}

	public boolean initListeTache(String fichier)
	{
		return this.metier.initListeTache(fichier);
	}

	public Object[] modifierTache(String nom, String duree, String[] prcs, String[] svts, Tache tache)
	{
		return this.metier.modifierTache(nom, duree, prcs, svts, tache);
	}

	public void creerFrameTache(Tache tache)
	{
		if (this.frameTache != null)
			this.frameTache.dispose();

		this.frameTache = new FrameTache(this, tache);
	}

	public void calculerEnDate(String sens, int j, int m)
	{
		this.metier.calculerMinDate(sens, j, m);
		this.metier.calculerMaxDate(sens, j, m);
	}

	public void affichageEnDate(boolean enDate, String sens, int j, int m)
	{
		this.metier.affichageEnDate(enDate);
		this.calculerEnDate(sens, j, m);
	}

	public void sauvegarder(String fichier)
	{
		this.metier.sauvegarder(fichier);
	}

	public void repositionner()
	{
		this.metier.repositioner();
	}

	/* -------------------- */
	/* Programme Principal  */
	/* -------------------- */
	public static void main(String[] args)
	{
		new Controleur();
	}
}