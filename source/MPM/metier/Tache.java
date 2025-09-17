/** Classe Tache pour la [SAE 2.01 2.02 2.05 - Développement d'une application] 
 *  @author : Flem Anthony / Freret Alexandre / Martin Erwan / El Maknassi Lucas - Equipe n°6
 *  @version: 1.0
 */

package MPM.metier;

import java.util.ArrayList;
import java.util.List;

public class Tache
{
	/* ----------- */
	/* Attributs   */
	/* ----------- */
	private String minDate;
	private String maxDate;
	private boolean enDate;

	private String nom;
	private int duree;
	private int dateMin;
	private int dateMax;
	private int niveau;
	private boolean critique;

	private List<Tache> prcs;
	private List<Tache> svts;

	private Position pos;

	/* --------------- */
	/* Constructeur(s) */
	/* --------------- */
	public Tache(String nom, int duree, int dateMin, int niveau)
	{
		this.prcs = new ArrayList<Tache>();
		this.svts = new ArrayList<Tache>();
		this.pos  = new Position();

		this.nom = nom;
		this.duree = duree;
		this.dateMin = dateMin;
		this.dateMax = 0;
		this.niveau = niveau;
		this.critique = false;
	}

	public Tache(String nom, int duree)
	{
		this(nom, duree, -1, -1);
	}

	public Tache()
	{
		this("", 0, -1, -1);
		this.pos = new Position();
	}

	/* -------------- */
	/* Modificateurs  */
	/* -------------- */
	public void setNom     (String  nom     ) { this.nom      = nom;              }
	public void setDuree   (int     duree   ) { this.duree    = duree;            }
	public void setDateMin (int     date    ) { this.dateMin  = date;             }
	public void setDateMax (int     date    ) { this.dateMax  = date;             }
	public void setNiveau  (int     niv     ) { this.niveau   = niv;              }
	public void setCritique(boolean critique) { this.critique = critique;         }

	public void setMinDate (int j, int m ) { this.minDate = "" + j + "/" + m; }
	public void setMaxDate (int j, int m ) { this.maxDate = "" + j + "/" + m; }
	public void setEnDate  (boolean b    ) { this.enDate  = b; }


	/* ----------- */
	/* Accesseurs  */
	/* ----------- */
	public List<Tache> getPrcs  () { return this.prcs;        }
	public int         getNbPrcs() { return this.prcs.size(); }

	public List<Tache> getSvts  () { return this.svts;        }
	public int         getNbSvts() { return this.svts.size(); }

	public Position getPos() { return this.pos; }

	public String getNom    () { return this.nom;                    }
	public int    getDuree  () { return this.duree;                  }
	public int    getDateMin() { return this.dateMin;                }
	public int    getDateMax() { return this.dateMax;                }
	public int    getNiveau () { return this.niveau;                 }
	public int    getMarge  () { return this.dateMax - this.dateMin; }


	/* ---------------- */
	/* Autres méthodes  */
	/* ---------------- */
	public boolean estPrc(Tache autretache)
	{
		for (Tache tacheCourante : this.prcs)
			if (autretache == tacheCourante)
				return true;

		return false;
	}

	public boolean possede(int x, int y)
	{
		return x >= this.pos.getCentreX() - ( this.pos.getTailleX() / 2                         ) &&
		       x <= this.pos.getCentreX() + ( this.pos.getTailleX() / 2                         ) &&

		       y >= this.pos.getCentreY() - ( this.pos.getTailleY() / 2                         ) &&
			   y <= this.pos.getCentreY() + ( this.pos.getTailleY() + this.pos.getTailleY() / 2 );
	}

	public void ajouterPrc(Tache t)
	{
		if ( t != null )
			this.prcs.add(t);
	}

	public void ajouterSvt(Tache t)
	{
		if ( t != null )
			this.svts.add(t);
	}

	public void supprimerPrc(Tache t)
	{
		if ( t != null )
			this.prcs.remove(t);
	}

	public void supprimerSvt(Tache t)
	{
		if ( t != null )
			this.svts.remove(t);
	}

	public void calculerDatePlusTot()
	{
		Tache tmp;
		int max;

		if ( this.prcs.isEmpty() ) return;

		// Calcul de la date au plus tôt de la tâche:
		tmp = this.prcs.get(0);
		max = this.prcs.get(0).getDateMin() + tmp.getDuree();

		for (int cpt = 1; cpt < this.getNbPrcs(); cpt++)
		{
			if (this.prcs.get(cpt).getDateMin() + this.prcs.get(cpt).getDuree() > max)
			{
				tmp = this.prcs.get(cpt);
				max = this.prcs.get(cpt).getDateMin() + tmp.getDuree();
			}
		}

		this.dateMin = max;
	}

	public void calculerDatePlusTard()
	{
		Tache tmp;
		int min;

		if ( this.nom.equals("FIN") ) this.dateMax = this.dateMin;
		if ( this.svts.isEmpty()    ) return;

		// Calcul de la date au plus tard de la tâche:
		tmp = this.svts.get(0);
		min = this.svts.get(0).getDateMax() - this.duree;

		for (int cpt = 1; cpt < this.getNbSvts(); cpt++)
		{
			if ( this.svts.get(cpt).dateMax - this.duree < min )
			{
				tmp = this.svts.get(cpt);
				min = this.svts.get(cpt).dateMax - this.duree;
			}
		}

		this.dateMax = min;
	}

	public String affichageMin()
	{
		String sh = "";

		if ( this.enDate ) sh =      this.minDate;
		else               sh = "" + this.dateMin;

		return sh;
	}

	public String affichageMax()
	{
		String sh = "";

		if (this.enDate) sh =      this.maxDate;
		else             sh = "" + this.dateMax;

		return sh;
	}
}