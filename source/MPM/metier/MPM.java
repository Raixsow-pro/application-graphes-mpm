/** Classe MPM pour la [SAE 2.01 2.02 2.05 - Développement d'une application] 
 *  @author : Flem Anthony / Freret Alexandre / Martin Erwan / El Maknassi Lucas - Equipe n°06
 *  @version: 1.0
 */

package MPM.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class MPM
{
	/* ----------- */
	/* Attributs   */
	/* ----------- */
	private int nbNiveaux;
	private int etape;

	private int lgGraphe;
	private int htGraphe;

	private List<Tache> ensTaches;
	private List<CheminCritique> ensChemins;

	/* --------------- */
	/* Constructeur(s) */
	/* --------------- */
	public MPM()
	{
		this.ensTaches  = new ArrayList<Tache>         ();
		this.ensChemins = new ArrayList<CheminCritique>();

		this.etape = 0;

		this.lgGraphe = 500;
		this.htGraphe = 500;
	}

	/* -------------- */
	/* Modificateurs  */
	/* -------------- */
	public void setLongueurGraphe(int lg) { this.lgGraphe = lg; }
	public void setHauteurGraphe (int ht) { this.htGraphe = ht; }

	/* ----------- */
	/* Accesseurs  */
	/* ----------- */
	public int                  getEtape    ()       { return this.etape;             }
	public int                  getNbNiveaux()       { return this.nbNiveaux;         }
	public int                  getNbTaches ()       { return this.ensTaches.size();  }
	public Tache                getTache    (int id) { return this.ensTaches.get(id); }
	public List<CheminCritique> getChemins  ()       { return this.ensChemins;        }

	public Integer getIndiceTache(int x, int y)
	{
		for (int cpt = 0; cpt < this.ensTaches.size(); cpt++)
			if (this.ensTaches.get(cpt).possede(x, y))
				return cpt;

		return null;
	}

	public int getNbTachesMax()
	{
		int tmp = 0;
		int max = 0;

		for (int cpt = 0; cpt < this.getNbNiveaux() + 1; cpt++)
		{
			tmp = 0;

			for (Tache t : this.ensTaches)
				if (t.getNiveau() == cpt)
					tmp++;

			if (tmp >= max)
				max = tmp;
		}

		return max;
	}

	/* --------------- */
	/* Méthodes de maj */
	/* --------------- */
	public void majCalculs()
	{
		this.majLien();
		this.calculNiveaux();
		this.calculerDatePlusTot();
		this.calculerDatePlusTard();
	}

	public String afficheChemins(int indice)
	{
		String sCh = "";
		for (int cpt = 1; cpt < this.ensChemins.size(); cpt++)
		{
			if (cpt == indice)
				sCh += (this.ensChemins.get(cpt).toString());
		}
		return sCh;
	}

	public void varierEtape(int niveau)
	{
		this.etape += niveau;
	}

	public void deplacerTache(int idTache, int x, int y)
	{
		if (idTache >= 0 && idTache < this.ensTaches.size())
		{
			this.ensTaches.get(idTache).getPos().deplacerX(x);
			this.ensTaches.get(idTache).getPos().deplacerY(y);
		}
	}

	public void supprimerTache(Tache tache)
	{
		if (tache != null)
		{
			this.ensTaches.remove(tache);

			for (Tache t : this.ensTaches)
			{
				t.supprimerPrc(tache);
				t.supprimerSvt(tache);
			}
		}
	}

	public void ajouterTache()
	{
		if (!this.ensTaches.isEmpty())
			this.ensTaches.add(this.getNbTaches() - 1, new Tache());
	}

	public void majLien()
	{
		for (Tache tache : this.ensTaches)
		{
			if (!tache.getNom().equals(""))
			{
				if (tache.getPrcs().isEmpty())
				{
					if (!tache.getNom().equals("DEBUT"))
					{
						tache.ajouterPrc(this.ensTaches.get(0));
						this.ensTaches.get(0).ajouterSvt(tache);
					}
				}
				else
				{
					if (tache.getPrcs().size() > 1)
					{
						for (Tache t : tache.getPrcs())
						{
							if (t.getNom().equals("DEBUT"))
							{
								tache.supprimerPrc(t);
								t.supprimerSvt(tache);
								break;
							}
						}
					}
				}

				if (tache.getSvts().isEmpty())
				{
					if (!tache.getNom().equals("FIN"))
					{
						tache.ajouterSvt(this.ensTaches.get(this.getNbTaches() - 1));
						this.ensTaches.get(this.getNbTaches() - 1).ajouterPrc(tache);
					}
				}
				else
				{
					if (tache.getSvts().size() > 1)
					{
						for (Tache t : tache.getSvts())
						{
							if (t.getNom().equals("FIN"))
							{
								tache.supprimerSvt(t);
								t.supprimerPrc(tache);
								break;
							}
						}
					}
				}
			}
		}
	}

	public Object[] modifierTache(String nom, String duree, String[] prcs, String[] svts, Tache tache)
	{
		Object[] retErreur = new Object[4];
		int dureeEntier = 0;

		String[] prcsSauve = new String[tache.getPrcs().size()];
		String[] svtsSauve = new String[tache.getSvts().size()];

		for (int cpt = 0; cpt < prcsSauve.length; cpt++)
			prcsSauve[cpt] = tache.getPrcs().get(cpt).getNom();

		for (int cpt = 0; cpt < svtsSauve.length; cpt++)
			svtsSauve[cpt] = tache.getSvts().get(cpt).getNom();

		/*
		 * 0 : Erreur nom 1 : Erreur durée 2 : Erreur antécédents 3 : Erreur
		 * suivants 4 : Erreur globale
		 **/
		if ( nom.equals("") )
			retErreur[0] = "Nom obligatoire";
		else
		{
			if ( nom.contains(",") )
				retErreur[0] = "Nom invalide";
			else
			{
				retErreur[0] = "";

				for (Tache t : this.ensTaches)
				{
					if ( t.getNom().equals(nom) && ! t.getNom().equals(tache.getNom() ) )
					{
						retErreur[0] = "Nom déjà utilisé";
						break;
					}
				}
			}
		}

		try
		{
			dureeEntier = Integer.parseInt(duree);

			if ( dureeEntier < 0 )
				retErreur[1] = "Durée négative";
			else
				retErreur[1] = "";
		}
		catch (NumberFormatException e) { retErreur[1] = "Durée invalide"; }

		retErreur[2] = "";
		retErreur[3] = "";

		this.determinerPrcs(tache, prcs);
		this.determinerSvts(tache, svts);

		if ( this.aDesCycles() )
		{
			retErreur[2] = "Liaison impossible";
			retErreur[3] = true;
		}

		this.determinerPrcs( tache, prcsSauve );
		this.determinerSvts( tache, svtsSauve );

		for (int cpt = 0; cpt < retErreur.length - 1; cpt++)
		{
			if (!retErreur[cpt].equals(""))
			{
				retErreur[3] = true;
				return retErreur;
			}
			else
				retErreur[3] = false;
		}

		tache.setNom  ( nom         );
		tache.setDuree( dureeEntier );

		this.determinerPrcs( tache, prcs );
		this.determinerSvts( tache, svts );

		return retErreur;
	}

	/* --------------- */
	/* Autres méthodes */
	/* --------------- */
	public boolean initListeTache(String fichier)
	{
		Scanner sc;
		String ligne;
		String[] tabString;
		String[] tabAntecs;

		Tache tacheTmp = null;
		String nom;
		int duree;

		this.etape = 0;
		this.ensTaches.clear();

		//this.ensTaches.add( new Tache("DEBUT", 0, 0, 0) );

		try
		{
			// On initialise tous les sommets du graphe:
			sc = new Scanner(new FileInputStream(fichier));
			while (sc.hasNextLine())
			{
				ligne = sc.nextLine();

				tabString = ligne.split("\\|");

				if ( tabString[0].equals("DEBUT"))
				{
					this.ensTaches.add( 0, new Tache("DEBUT", 0, 0, 0) );
					continue;
				}

				if (tabString[0].equals("FIN"))
				{
					this.ensTaches.add( new Tache("FIN", 0) );
					continue;
				}

				nom = tabString[0];
				duree = Integer.parseInt(tabString[1]);

				this.ensTaches.add( new Tache(nom, duree) );
			}
			sc.close();

			if ( ! this.ensTaches.get(0).getNom().equals("DEBUT" ) )
			{
				this.ensTaches.add( 0, new Tache("DEBUT", 0, 0, 0 ) );
			}

			// Initialisation des antécédents:
			sc = new Scanner(new FileInputStream(fichier));
			while (sc.hasNextLine())
			{
				ligne = sc.nextLine();

				tabString = ligne.split("\\|");

				if ( tabString.length >= 3 )
				{
					for (Tache t : this.ensTaches)
						if ( t.getNom().equals( tabString[0] ) )
							tacheTmp = t;

					tabAntecs = tabString[2].split(",");

					for (int cpt = 0; cpt < tabAntecs.length; cpt++)
					{
						for (Tache tache : this.ensTaches)
						{
							if ( tache.getNom().equals( tabAntecs[cpt] ) )
							{
								tacheTmp.ajouterPrc(tache);
								tache.ajouterSvt(tacheTmp);
							}
						}
					}
				}
			}
			sc.close();
		}
		catch ( Exception e ) { e.printStackTrace(); }

		// Ajout des suivants à DEBUT:
		for (Tache t : this.ensTaches)
		{
			if ( t.getNbPrcs() == 0 && ! t.getNom().equals( "DEBUT" ) )
			{
				t.ajouterPrc( this.ensTaches.get( 0 ) );
				this.ensTaches.get( 0 ).ajouterSvt( t );
			}
		}

		// Ajout des précédents à FIN:
		if (!this.ensTaches.get( this.ensTaches.size()-1 ).getNom().equals("FIN"))
		{
			this.ensTaches.add( new Tache( "FIN", 0 ) );
		}

		for (Tache t : this.ensTaches)
		{
			if ( t.getNbSvts() == 0 && ! t.getNom().equals( "FIN" ) )
			{
				this.ensTaches.get(this.ensTaches.size() - 1).ajouterPrc(t);
				t.ajouterSvt(this.ensTaches.get(this.ensTaches.size() - 1));
			}
		}

		boolean bRet = (boolean) this.grapheValide()[3];
		if (bRet)
		{
			this.ensTaches.clear();
			return bRet;
		}

		this.calculNiveaux();

		return bRet;
	}

	public void initPosition(String fichier)
	{
		Scanner sc;

		String ligne;
		String[] tabString;
		String[] tabPos;

		int centreX;
		int centreY;

		boolean bPosErr = false;

		Tache tacheTmp = null;

		try
		{
			// On initialise tous les sommets du graphe:
			sc = new Scanner( new FileInputStream( fichier ) );
			while ( sc.hasNextLine() )
			{
				ligne = sc.nextLine();

				tabString = ligne.split("\\|");

				if ( tabString.length >= 4 )
				{
					for (Tache t : this.ensTaches)
						if ( t.getNom().equals(tabString[0]) )
							tacheTmp = t;

					tabPos = tabString[3].split(":");

					try
					{
						centreX = Integer.parseInt( tabPos[0] );
						centreY = Integer.parseInt( tabPos[1] );

						tacheTmp.getPos().setCentreX( centreX );
						tacheTmp.getPos().setCentreY( centreY );
					}
					catch (Exception e) {}
				}
				else
				{
					bPosErr = true;
				}
			}

			if ( bPosErr )
				this.repositioner();

			sc.close();
		} catch (Exception e) {}
	}

	public void repositioner()
	{
		int centreX = 0;
		int centreY = 0;
		int espX    = 0;
		int margeX  = 100;

		if ( this.nbNiveaux == 0 ) return;


		espX = ( this.lgGraphe - 2 * margeX ) / this.nbNiveaux;

		for (int niveau = 0; niveau < this.nbNiveaux + 1; niveau++)
		{
			List<Tache> tachesParNiveau = new ArrayList<Tache>();

			for (Tache t : this.ensTaches)
			{
				if ( t.getNiveau() == niveau )
					tachesParNiveau.add(t);
			}

			for (int cpt = 0; cpt < tachesParNiveau.size(); cpt++)
			{
				Tache t = tachesParNiveau.get(cpt);

				if      ( t.getNom().equals("DEBUT") ) centreX = margeX;
				else if ( t.getNom().equals("FIN"  ) ) centreX = this.lgGraphe - margeX;
				else                                     centreX = margeX + niveau * espX;

				centreY = ( cpt + 1 ) * ( this.htGraphe / ( tachesParNiveau.size() + 1 ) );

				t.getPos().setCentreX( centreX );
				t.getPos().setCentreY( centreY );
			}
		}
	}

	public void calculNiveaux()
	{
		int maxNiveau = 0;
		int maxNiveauPrc = 0;
		boolean antRestant = true;

		for (int cpt = 1; cpt < this.ensTaches.size(); cpt++)
			this.ensTaches.get(cpt).setNiveau(-1);

		while (antRestant)
		{
			antRestant = false;
			for (int cpt = 1; cpt < this.ensTaches.size(); cpt++)
			{
				maxNiveauPrc = 0;
				if ( ! this.ensTaches.get(cpt).getNom().equals("") )
				{
					for (Tache prc : this.ensTaches.get(cpt).getPrcs())
					{
						if ( prc.getNiveau() == -1 )
						{
							antRestant = true;
							maxNiveauPrc = -2;
							break;
						}
						else
							maxNiveauPrc = Math.max(prc.getNiveau(), maxNiveauPrc);
					}

					if ( this.ensTaches.get(cpt).getNiveau() == -1 )
					{
						this.ensTaches.get(cpt).setNiveau( maxNiveauPrc + 1 );
						maxNiveau = Math.max( maxNiveauPrc + 1, maxNiveau );
					}
				}
			}
		}

		this.nbNiveaux = maxNiveau;
	}

	public void calculerDatePlusTot()
	{
		if ( this.ensTaches.isEmpty() ) return;

		for (int cptE = 0; cptE < this.etape; cptE++)
			for (Tache t : this.ensTaches)
				t.calculerDatePlusTot();
	}

	public void calculerDatePlusTard()
	{
		if ( this.ensTaches.isEmpty() ) return;

		for (int cptE = 0; cptE < this.etape; cptE++)
			for (int cpt = this.ensTaches.size() - 1; cpt >= 0; cpt--)
				this.ensTaches.get(cpt).calculerDatePlusTard();
	}

	public void calculerMinDate(String sens, int j, int m)
	{
		for (Tache t : this.ensTaches)
		{
			int valeurJour = j;
			int valeurMois = m;
			if ( sens.equals("+") )
			{
				valeurJour += t.getDateMin();
				while ( valeurJour > 30 )
				{
					valeurJour = valeurJour - 30;
					valeurMois += 1;
					if (valeurMois > 12)
					{
						valeurMois = 1;
					}
				}
				t.setMinDate(valeurJour, valeurMois);
			}
			if ( sens.equals("-") && t.getNom().equals("FIN") )
			{
				for (int cpt = 0; cpt < t.getDateMin(); cpt++)
				{
					valeurJour -= 1;
					if (valeurJour < 1)
					{
						valeurJour = 30 - valeurJour;
						valeurMois -= 1;
						if (valeurMois < 1)
						{
							valeurMois = 12;
						}
					}
				}

				this.calculerMinDate( "+", valeurJour, valeurMois );
			}
		}
	}

	public void calculerMaxDate(String sens, int j, int m)
	{
		for (Tache t : this.ensTaches)
		{
			int valeurJour = j;
			int valeurMois = m;

			if ( sens.equals("+") )
			{
				valeurJour += t.getDateMax();
				while ( valeurJour > 30 )
				{
					valeurJour = valeurJour - 30;

					valeurMois += 1;

					if ( valeurMois > 12 )
					{
						valeurMois = 1;
					}
				}
				t.setMaxDate(valeurJour, valeurMois);
			}
			if ( sens.equals("-") && t.getNom().equals("FIN") )
			{
				for (int cpt = 0; cpt < t.getDateMax(); cpt++)
				{
					valeurJour -= 1;
					if ( valeurJour < 1 )
					{
						valeurJour = 30 - valeurJour;

						valeurMois -= 1;

						if ( valeurMois < 1 )
						{
							valeurMois = 12;
						}
					}
				}

				this.calculerMaxDate("+", valeurJour, valeurMois);
			}
		}
	}

	public void affichageEnDate(boolean enDate)
	{
		for (Tache t : this.ensTaches)
			t.setEnDate(enDate);
	}

	public void calculerCheminsCritiques()
	{
		if ( this.ensTaches.isEmpty() ) return;
		this.ensChemins.clear();

		this.ensChemins.add( new CheminCritique( new ArrayList<Tache>() ) );
		this.trouverCheminCritique( this.ensTaches.get( 0 ), this.ensChemins.get( 0 ) );
	}

	public void trouverCheminCritique(Tache t, CheminCritique ch)
	{
		ch.ajouterTache( t );

		if ( t.getNom().equals( "FIN" ) )
			this.ensChemins.add( new CheminCritique( new ArrayList<Tache>( ch.getEnsTaches() ) ) );
		else
			for (Tache svt : t.getSvts())
				if ( t  .getDateMin() + t.getDuree() == svt.getDateMin() &&
				     t  .getMarge  () == 0                               &&
					 svt.getMarge  () == 0                                  )
					this.trouverCheminCritique(svt, ch);

		ch.supprimerTache( ch.getEnsTaches().size() - 1 );
	}

	public void sauvegarder(String fichier)
	{
		try
		{
			PrintWriter pw = new PrintWriter       (
			                 new OutputStreamWriter(
			                 new FileOutputStream  ( fichier ), "UTF8" ) );

			for (Tache t : this.ensTaches)
			{
					pw.print( t.getNom() + "|" + t.getDuree() + "|" );

					for (Tache prc : t.getPrcs())
					{
						pw.print( prc.getNom() + ( t.getPrcs().get( t.getPrcs().size() - 1 ) == prc ? "" : "," )  );
					}

					pw.println( "|" + t.getPos().toString() );
			}

			pw.close();
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	private void determinerPrcs(Tache tache, String[] tabAntecs)
	{
		for (Tache t : tache.getPrcs())
			t.supprimerSvt(tache);

		tache.getPrcs().clear();

		for (String prc : tabAntecs)
		{
			for (Tache t : this.ensTaches)
			{
				if ( t.getNom().equals(prc) && ! tache.getPrcs().contains( t ) && ! t.getNom().equals( "" ) )
				{
					tache.ajouterPrc( t );
					t.ajouterSvt( tache );
				}
			}
		}
	}

	private void determinerSvts(Tache tache, String[] tabSvts)
	{
		for (Tache t : tache.getSvts())
			t.supprimerPrc( tache );

		tache.getSvts().clear();

		for (String svt : tabSvts)
			for (Tache t : this.ensTaches)
			{
				if ( t.getNom().equals( svt ) && ! tache.getSvts().contains( t ) && ! t.getNom().equals( "" ) )
				{
					tache.ajouterSvt( t );
					t.ajouterPrc( tache );
				}
			}
	}

	private boolean detecterCycle(Tache tache, List<Tache> enCours, List<Tache> visites)
	{
		if ( enCours.contains( tache ) )
			return true;
		if ( visites.contains( tache ) )
			return false;

		enCours.add( tache );

		for (Tache prc : tache.getPrcs())
			if ( this.detecterCycle( prc, enCours, visites ) )
				return true;

		enCours.remove( tache );
		visites.add( tache );

		return false;
	}

	private boolean aDesCycles()
	{
		List<Tache> enCours = new ArrayList<>();
		List<Tache> visites = new ArrayList<>();

		for (Tache t : this.ensTaches)
			if ( ! t.getNom().equals( "" ) )
				if ( this.detecterCycle( t, enCours, visites ) )
					return true;

		return false;
	}

	private Object[] grapheValide()
	{
		Object[] retErreur = new Object[4];
		int dureeEntier = 0;

		for (Tache tache : this.ensTaches)
		{
			/*
			 * 0 : Erreur nom 1 : Erreur durée 2 : Erreur antécédents 3 : Erreur
			 * suivants 4 : Erreur globale
			 */
			if ( tache.getNom().equals( "" ) )
				retErreur[0] = "Nom obligatoire";
			else
			{
				if ( tache.getNom().contains( "," ) )
					retErreur[0] = "Nom invalide";
				else
				{
					retErreur[0] = "";

					for (Tache t : this.ensTaches)
					{
						if ( t.getNom().equals( tache.getNom() ) && ! t.getNom().equals( tache.getNom() ) )
						{
							retErreur[0] = "Nom déjà utilisé";
							break;
						}
					}
				}
			}

			try
			{
				if ( tache.getDuree() < 0 )
					retErreur[1] = "Durée négative";
				else
					retErreur[1] = "";
			}
			catch (NumberFormatException e) { retErreur[1] = "Durée invalide"; }

			retErreur[2] = "";

			if ( this.aDesCycles() )
			{
				retErreur[2] = "Liaison impossible";
				retErreur[3] = true;
			}

			for (int cpt = 0; cpt < retErreur.length - 1; cpt++)
			{	
				if (! retErreur[cpt].equals("")) retErreur[3] = true;
				else                             retErreur[3] = false;
			}
		}

		return retErreur;
	}
}