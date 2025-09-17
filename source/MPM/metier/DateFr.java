/** Récupération de la classe DateFr pour la [SAE 2.01 2.02 2.05 - Développement d'une application]
 *  @author : Flem Anthony / Freret Alexandre / Martin Erwan / El Maknassir Lucas - Equipe n°06
 *  @version: 1.0
 */

package MPM.metier;

import java.util.GregorianCalendar;

/* ----------------------- */
/*      Exercice 5.7       */
/* ----------------------- */
public class DateFr extends GregorianCalendar
{
	private static final String[] tabMois = { "janvier" , "février" , "mars", "avril"    , "mai"    ,
	                                          "juin"    , "juillet" , "août", "septembre", "octobre",
	                                          "novembre", "décembre"                                  };

	private static final String[] tabJour = { "Lundi", "Mardi"   , "Mercredi",
	                                          "Jeudi", "Vendredi", "Samedi"  ,
	                                          "Dimanche"                       };

	public DateFr()
	{
		super();
	}

	public DateFr(int jour, int mois, int annee)
	{
		super(annee, mois-1, jour, 0, 0 ,0);
	}

	public DateFr(int jour, int mois, int annee, int heure, int minute)
	{
		super(annee, mois-1, jour, heure, minute, 0);
	}

	public DateFr(int jour, int mois, int annee, int heure, int minute, int seconde)
	{
		super(annee, mois-1, jour, heure, minute, seconde);
	}

	public DateFr(String date)
	{
		super();

		int jour  = Integer.parseInt( date.substring( 0, 2) );
		int mois  = Integer.parseInt( date.substring( 3, 5) );
		int annee = Integer.parseInt( date.substring( 6 )            );

		this.set( DateFr.DAY_OF_MONTH, jour  );
		this.set( DateFr.MONTH       , mois  );
		this.set( DateFr.YEAR        , annee );
	}

	public DateFr(DateFr autreDate)
	{
		this( autreDate.get( DateFr.DAY_OF_MONTH ),
			  autreDate.get( DateFr.MONTH        ),
			  autreDate.get( DateFr.YEAR         ),
			  autreDate.get( DateFr.HOUR_OF_DAY  ),
			  autreDate.get( DateFr.MINUTE       ),
			  autreDate.get( DateFr.SECOND       )  );
	}

	public int get(int field)
	{ 
		switch (field)
		{
			case DateFr.DAY_OF_MONTH,
				 DateFr.YEAR        ,
				 DateFr.HOUR_OF_DAY ,
				 DateFr.MINUTE      ,
				 DateFr.SECOND       -> { return super.get(field);                                   }
			case DateFr.MONTH        -> { return super.get(field) + 1;                               }
			case DateFr.HOUR         -> { return super.get(DateFr.HOUR_OF_DAY);                      }
			case DateFr.DAY_OF_WEEK  -> { return (super.get(field) == 1) ? 7 : super.get(field) - 1; }
			default                  -> { return -1;                                                 }
		}
	}

	public String toString()
	{
		return String.format("%02d", this.get( DateFr.DAY_OF_MONTH ) ) + "/" +
			   String.format("%02d", this.get( DateFr.MONTH        ) ) + "/" +
			   String.format("%4d" , this.get( DateFr.YEAR         ) ) + " " +
			   String.format("%02d", this.get( DateFr.HOUR_OF_DAY  ) ) + ":" +
			   String.format("%02d", this.get( DateFr.MINUTE       ) ) + ":" +
			   String.format("%02d", this.get( DateFr.SECOND       ) );
	}

	public int differenceJour( DateFr autreDate )
	{
		return this.get( DateFr.DAY_OF_WEEK ) - autreDate.get( DateFr.DAY_OF_WEEK );
	}

	public String toString( String format )
	{
		int mois = this.get(DateFr.MONTH);
		int jour = this.get(DateFr.DAY_OF_WEEK);
		int taille;

		format = format.replace( "aaaa", "" + this.get(DateFr.YEAR   )       );
		format = format.replace( "aa"  , "" + this.get(DateFr.YEAR   ) % 100 );
		format = format.replace( "mmmm", "" + DateFr.tabMois[mois - 1]       );

		if ( mois == 4  || mois == 5  || mois >= 10 ) taille = 3; else taille = 4;

		format = format.replace( "mmm" ,      DateFr.tabMois[mois - 1].substring(0, taille) );
		format = format.replace( "mm"  ,      String.format("%02d", mois)                   );
		format = format.replace( "jjjj", "" + DateFr.tabJour[jour]                          );
		format = format.replace( "jjj" , "" + DateFr.tabJour[jour].substring(0, 3)          );
		format = format.replace( "jj"  ,      String.format("02d", jour )                   );
		format = format.replace( "hh"  , "" + this.get(DateFr.HOUR   )                      );
		format = format.replace( "mm"  , "" + this.get(DateFr.MINUTE )                      );
		format = format.replace( "ss"  , "" + this.get(DateFr.SECOND )                      );

		return format;
	}
}