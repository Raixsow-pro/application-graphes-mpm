/** Classe PanelInfo pour la [SAE 2.01 2.02 2.05 - Développement d'une application] 
 *  @author : Flem Anthony / Freret Alexandre / Martin Erwan / El Maknassi Lucas - Equipe n°06
 *  @version: 1.0
 */

package MPM.ihm.graphe;

import MPM.Controleur;
import MPM.metier.Tache;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class PanelInfo extends JPanel
{
	/* ----------- */
	/* Attributs   */
	/* ----------- */
	private Controleur ctrl;

	private JLabel lblInfo;
	
	private JLabel lblNom;
	private JLabel lblDuree;
	private JLabel lblPrcs;
	private JLabel lblSvts;
	private JLabel lblDateMin;
	private JLabel lblDateMax;
	
	private JLabel lblCalculNom;
	private JLabel lblCalculDuree;
	private JLabel lblCalculPrcs;
	private JLabel lblCalculSvts;
	private JLabel lblCalculDateMin;
	private JLabel lblCalculDateMax;

	/* --------------- */
	/* Constructeur(s) */
	/* --------------- */
	public PanelInfo(Controleur ctrl)
	{
		JPanel panelInfo;

		JPanel panelGlobal;
		JPanel panelLbl, panelCalcul;


		this.ctrl = ctrl;

		this.setLayout( new BorderLayout() );

		/* ----------------------------- */
		/* Création des Composants       */
		/* ----------------------------- */
		panelInfo = new JPanel();

		panelGlobal = new JPanel( new GridLayout( 1, 2, 5, 5 ) );
		panelLbl    = new JPanel( new GridLayout( 6, 1, 5, 5 ) );
		panelCalcul = new JPanel( new GridLayout( 6, 1, 5, 5 ) );

		this.lblInfo = new JLabel( "", JLabel.CENTER );

		this.lblNom     = new JLabel( "", JLabel.RIGHT );
		this.lblDuree   = new JLabel( "", JLabel.RIGHT );
		this.lblPrcs    = new JLabel( "", JLabel.RIGHT );
		this.lblSvts    = new JLabel( "", JLabel.RIGHT );
		this.lblDateMin = new JLabel( "", JLabel.RIGHT );
		this.lblDateMax = new JLabel( "", JLabel.RIGHT );

		this.lblCalculNom     = new JLabel( "", JLabel.LEFT );
		this.lblCalculDuree   = new JLabel( "", JLabel.LEFT );
		this.lblCalculPrcs    = new JLabel( "", JLabel.LEFT );
		this.lblCalculSvts    = new JLabel( "", JLabel.LEFT );
		this.lblCalculDateMin = new JLabel( "", JLabel.LEFT );
		this.lblCalculDateMax = new JLabel( "", JLabel.LEFT );

		// Créations diverses:
		this.lblInfo.setFont( new Font( "", Font.BOLD, 18 ) );

		this.lblNom    .setFont( new Font( "", Font.BOLD, 16 ) );
		this.lblDuree  .setFont( new Font( "", Font.BOLD, 16 ) );
		this.lblPrcs   .setFont( new Font( "", Font.BOLD, 16 ) );
		this.lblSvts   .setFont( new Font( "", Font.BOLD, 16 ) );
		this.lblDateMin.setFont( new Font( "", Font.BOLD, 16 ) );
		this.lblDateMax.setFont( new Font( "", Font.BOLD, 16 ) );

		this.lblCalculNom    .setFont( new Font( "", Font.PLAIN, 16 ) );
		this.lblCalculDuree  .setFont( new Font( "", Font.PLAIN, 16 ) );
		this.lblCalculPrcs   .setFont( new Font( "", Font.PLAIN, 16 ) );
		this.lblCalculSvts   .setFont( new Font( "", Font.PLAIN, 16 ) );
		this.lblCalculDateMin.setFont( new Font( "", Font.PLAIN, 16 ) );
		this.lblCalculDateMax.setFont( new Font( "", Font.PLAIN, 16 ) );


		/* ----------------------------- */
		/* Positionnement des Composants */
		/* ----------------------------- */
		panelInfo.add ( this.lblInfo );

		panelLbl.add ( this.lblNom     );
		panelLbl.add ( this.lblDuree   );
		panelLbl.add ( this.lblPrcs    );
		panelLbl.add ( this.lblSvts    );
		panelLbl.add ( this.lblDateMin );
		panelLbl.add ( this.lblDateMax );

		panelCalcul.add( this.lblCalculNom     );
		panelCalcul.add( this.lblCalculDuree   );
		panelCalcul.add( this.lblCalculPrcs    );
		panelCalcul.add( this.lblCalculSvts    );
		panelCalcul.add( this.lblCalculDateMin );
		panelCalcul.add( this.lblCalculDateMax );

		panelGlobal.add( panelLbl    );
		panelGlobal.add( panelCalcul );


		this.add( panelInfo  , BorderLayout.NORTH  );
		this.add( panelGlobal, BorderLayout.CENTER );
	}

	/* ---------------- */
	/* Méthodes de maj  */
	/* ---------------- */
	public void majIhm()
	{
		this.lblInfo.setText( "" );

		this.lblNom    .setText( "" );
		this.lblDuree  .setText( "" );
		this.lblPrcs   .setText( "" );
		this.lblSvts   .setText( "" );
		this.lblDateMin.setText( "" );
		this.lblDateMax.setText( "" );

		this.lblCalculNom    .setText( "" );
		this.lblCalculDuree  .setText( "" );
		this.lblCalculPrcs   .setText( "" );
		this.lblCalculSvts   .setText( "" );
		this.lblCalculDateMin.setText( "" );
		this.lblCalculDateMax.setText( "" );
	}

	public void majLabel(Tache t)
	{
		String nomPrcs = "";
		String nomSvts = "";

		this.lblInfo   .setText( "Informations sur la tâche : " + t.getNom() );

		this.lblNom    .setText( "Nom : "        );
		this.lblDuree  .setText( "Durée : "      );
		this.lblPrcs   .setText( "Précédents : " );
		this.lblSvts   .setText( "Suivants : "   );
		this.lblDateMin.setText( "Date Min : "   );
		this.lblDateMax.setText( "Date Max : "   );


		// Gestion du label de nom de la tâche:
		this.lblCalculNom.setText( t.getNom() );

		// Gestion du label de durée de la tâche:
		this.lblCalculDuree.setForeground( Color.RED );
		this.lblCalculDuree.setText( "<html>"                                           +
											"<span style='color:red;'>"                 +
												t.getDuree()                            +
											"</span>"                                   +
											"<span style='color:lightgray;'>"           +
												(t.getDuree() > 1 ? " jours" : " jour") +
											"</span>"                                   +
									"</html>"                                             );

		// Gestion des labels précédents et suivants:
		for (Tache prc : t.getPrcs() )
			nomPrcs += prc.getNom() + ( prc != (t.getPrcs().get( t.getPrcs().size() - 1 ) ) ? "," : "" );
		this.lblCalculPrcs.setText( nomPrcs );

		for (Tache svt : t.getSvts())
			nomSvts += svt.getNom() + ( svt != ( t.getSvts().get( t.getSvts().size() - 1 ) ) ? "," : "" );
		this.lblCalculSvts.setText( nomSvts );

		// Gestion du label de date au plus tôt et au plus tard de la tâche:
		this.majLblDateMin( t );
		this.majLblDateMax( t );
	}

	private void majLblDateMin(Tache t)
	{
		for (Tache prc : t.getPrcs() )
		{
			if ( prc.getDateMin() + prc.getDuree() == t.getDateMin() )
			{
				this.lblCalculDateMin.setText( "<html>"                                    +
													"<span style='color:darkgray;'>"       +
														"(Vient de " + prc.getNom() + ") " +
													"</span>"                              +

													"<span style='color:#008080;'>"        +
														prc.getDateMin()                   +
													"</span>"                              +

													"<span style='color:darkgray;'>"       +
														" + "                              +
													"</span>"                              +

													"<span style='color:#be5151;'>"        +
														prc.getDuree()                     +
													"</span>"                              +

													"<span style='color:darkgray;'>"       +
														" = "                              +
													"</span>"                              +

													"<span style='color:#008080;'>"        +
															t.getDateMin()                 +
													"</span>"                              +
											"</html>"                                        );
			}
			else
			{
				this.lblCalculDateMin.setForeground( Color.BLACK     );
				this.lblCalculDateMin.setText      ( "non renseigné" );
			}
		}
	}

	private void majLblDateMax(Tache t)
	{
		for (Tache svt : t.getSvts() )
		{
			if ( svt.getDateMax() - t.getDuree() == t.getDateMax() )
			{
				this.lblCalculDateMax.setText( "<html>"                                    +
													"<span style='color:darkgray;'>"       +
														"(Vient de " + svt.getNom() + ") " +
													"</span>"                              +

													"<span style='color:#008080;'>"        +
														svt.getDateMax()                   +
													"</span>"                              +

													"<span style='color:darkgray;'>"       +
														" - "                              +
													"</span>"                              +

													"<span style='color:darkgray;'>"       +
														"(Vient de " + t.getNom() + ") "   +
													"</span>"                              +

													"<span style='color:#be5151;'>"        +
														t.getDuree()                       +
													"</span>"                              +

													"<span style='color:darkgray;'>"       +
														" = "                              +
													"</span>"                              +

													"<span style='color:#008080;'>"        +
														t.getDateMax()                     +
													"</span>"                              +
											"</html>"                                        );
			}
			else
			{
				this.lblCalculDateMax.setForeground(Color.BLACK    );
				this.lblCalculDateMax.setText      ("non renseigné");
			}
		}
	}
}