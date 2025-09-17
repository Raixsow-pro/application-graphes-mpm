/** Classe PanelAction pour la [SAE 2.01 2.02 2.05 - Développement d'une application] 
 *  @author : Flem Anthony / Freret Alexandre / Martin Erwan / El Maknassi Lucas - Equipe n°06
 *  @version: 1.0
 */

package MPM.ihm.graphe;

import MPM.Controleur;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PanelAction extends JPanel implements ActionListener, ItemListener
{
	/* ----------- */
	/* Attributs   */
	/* ----------- */
	private Controleur ctrl;

	private String  sens;
	private int     jour;
	private int     mois;
	private boolean enDate;
	private boolean critActive;

	private JPanel panelAction;

	private JButton btnPlusTot;
	private JButton btnPlusTard;
	private JButton btnCheminCritique;

	private JButton btnNouvelleTache;
	private JButton btnRepositionner;

	private JRadioButton rbDate;
	private JRadioButton rbNbJour;

	private JRadioButton rbDebut;
	private JRadioButton rbFin;
	private JTextField   txtDate;

	/* --------------- */
	/* Constructeur(s) */
	/* --------------- */
	public PanelAction(Controleur ctrl)
	{
		JPanel panelPlus, panelDebutFin;

		ButtonGroup groupeAffichage, groupeDebutFin;

		this.ctrl = ctrl;
		this.enDate = false;
		this.sens = "-";

		/* ----------------------------- */
		/* Création des Composants */
		/* ----------------------------- */
		this.panelAction = new JPanel( new GridLayout( 20, 1, 10, 10 ) );
		panelPlus        = new JPanel( new GridLayout( 1 , 2, 10, 10 ) );
		panelDebutFin    = new JPanel( new GridLayout( 1 , 2, 10, 10 ) );

		this.btnPlusTot        = new JButton( "+ tôt"           );
		this.btnPlusTard       = new JButton( "+ tard"          );
		this.btnCheminCritique = new JButton( "Chemin Critique" );
		this.btnNouvelleTache  = new JButton( "Nouvelle Tâche"  );
		this.btnRepositionner  = new JButton( "Repostionner"    );

		groupeAffichage = new ButtonGroup();
		this.rbNbJour   = new JRadioButton( "Nombre de jours" );
		this.rbDate     = new JRadioButton( "Date"            );

		groupeDebutFin = new ButtonGroup();
		this.rbDebut   = new JRadioButton( "début" );
		this.rbFin     = new JRadioButton( "fin"   );

		this.txtDate = new JTextField(5);

		this.critActive = false;

		// Créations diverses:
		this.btnPlusTard      .setEnabled( false );
		this.btnCheminCritique.setEnabled( false );

		this.btnPlusTot      .setBackground( Color.GREEN );
		this.btnPlusTard     .setBackground( Color.RED   );

		this.rbDate    .setSelected( false );
		this.rbNbJour  .setSelected( true  );
		this.rbDate    .setEnabled ( false );

		groupeAffichage.add( this.rbDate   );
		groupeAffichage.add( this.rbNbJour );

		groupeDebutFin.add( this.rbDebut );
		groupeDebutFin.add( this.rbFin   );

		this.txtDate.setEditable( false );

		/* ----------------------------- */
		/* Positionnement des Composants */
		/* ----------------------------- */
		this.panelAction.add( new JLabel("Actions", JLabel.CENTER ) );

		panelPlus       .add( this.btnPlusTot  );
		panelPlus       .add( this.btnPlusTard );
		this.panelAction.add( panelPlus        );

		this.panelAction.add( this.btnCheminCritique                        );
		this.panelAction.add( new JLabel()                                  );
		this.panelAction.add( this.btnNouvelleTache                         );
		this.panelAction.add( this.btnRepositionner                         );
		this.panelAction.add( new JLabel()                                  );
		this.panelAction.add( new JLabel( "Affichage", JLabel.CENTER )      );
		this.panelAction.add( this.rbNbJour                                 );
		this.panelAction.add( this.rbDate                                   );
		this.panelAction.add( new JLabel()                                  );
		this.panelAction.add( new JLabel( "Date du projet", JLabel.CENTER ) );

		panelDebutFin.add( this.rbDebut );
		panelDebutFin.add( this.rbFin   );

		this.panelAction.add( panelDebutFin );
		this.panelAction.add( this.txtDate  );

		this.add( this.panelAction );

		/* ----------------------------- */
		/* Activation des Composants     */
		/* ----------------------------- */
		this.btnPlusTot       .addActionListener( this );
		this.btnPlusTard      .addActionListener( this );
		this.btnCheminCritique.addActionListener( this );
		this.btnNouvelleTache .addActionListener( this );
		this.btnRepositionner .addActionListener( this );
		this.txtDate          .addActionListener( this );

		this.rbNbJour.addItemListener( this );
		this.rbDate  .addItemListener( this );
		this.rbDebut .addItemListener( this );
		this.rbFin   .addItemListener( this );
	}

	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == this.btnPlusTot)
		{
			if ( this.ctrl.getEtape() < this.ctrl.getNbNiveaux() )
			{
				this.ctrl.varierEtape(1);
				this.ctrl.calculerDatePlusTot();

				if (this.ctrl.getEtape() >= this.ctrl.getNbNiveaux())
				{
					this.btnPlusTard.setEnabled(true);
					this.btnPlusTot.setEnabled(false);
				}
			}
		}

		if ( e.getSource() == this.btnPlusTard )
		{
			if ( this.ctrl.getEtape() < this.ctrl.getNbNiveaux() * 2 + 1 )
			{
				this.ctrl.varierEtape(1);
				this.ctrl.calculerDatePlusTard();

				if ( this.ctrl.getEtape() >= this.ctrl.getNbNiveaux() * 2 + 1 )
				{
					this.btnCheminCritique.setEnabled( true  );
					this.btnPlusTard      .setEnabled( false );
				}
			}
		}

		if (e.getSource() == this.btnNouvelleTache)
		{
			this.ctrl.ajouterTache();

			this.ctrl.majCalculs();
		}

		if (e.getSource() == this.btnRepositionner)
		{
			this.ctrl.majTailleGraphe();
			this.ctrl.repositionner();
			this.ctrl.majGraphe();
		}

		if (e.getSource() == this.btnCheminCritique)
		{
			this.critActive = ! this.critActive;

			this.ctrl.calculerCheminsCritiques();
		}

		if (e.getSource() == this.txtDate)
		{
			String s = "";
			String[] tabString = this.txtDate.getText().split("/");
			if ( tabString[0].length()            <= 2  &&
				 tabString[1].length()            <= 2  &&
				 tabString[2].length()            == 4  &&
				 Integer.parseInt( tabString[0] ) <= 31 &&
				 Integer.parseInt( tabString[1] ) <= 12    )
			{
				this.jour = Integer.parseInt( tabString[0] );
				this.mois = Integer.parseInt( tabString[1] );

				this.ctrl.calculerEnDate( this.sens, this.jour, this.mois );
	
				this.rbDate.setSelected( true );
				this.rbDate.setEnabled ( true );
			}
		}

		this.ctrl.majGraphe();
	}

	public void itemStateChanged(ItemEvent e)
	{
		if ( e.getItem() == this.rbNbJour ) { this.enDate = false; }
		if ( e.getItem() == this.rbDate   ) { this.enDate = true;  }

		if ( this.rbDebut.isSelected() ) { this.sens = "+"; }
		if ( this.rbFin  .isSelected() ) { this.sens = "-"; }

		this.txtDate.setEditable(true);

		this.ctrl.majGraphe();
		this.ctrl.affichageEnDate( enDate, this.sens, this.jour, this.mois );
	}

	public void majIhm()
	{
		this.btnPlusTot       .setEnabled( true  );
		this.btnPlusTard      .setEnabled( false );
		this.btnCheminCritique.setEnabled( false );

		this.rbDate.setEnabled( false );

		this.txtDate.setEditable( false );

		this.critActive = false;
		this.enDate     = false;
		this.sens       = "";
	}

	/*
	public void majLabel()
	{
		this.ctrl.calculerCheminsCritiques();

		if ( this.critActive == false && ! this.lblChemins.isEmpty() &&  )
		{
			int maxPanel  = this.panelAction.getComponentCount();
			int taillelbl = this.lblChemins.size();

			for (int cpt = maxPanel; cpt > maxPanel - taillelbl; cpt--)
				this.panelAction.remove(cpt);

			this.lblChemins.clear();
		}
		else
		{
			for (int cpt = 0; cpt < this.ctrl.getChemins().size(); cpt++)
			{
				this.lblChemins.add( new JLabel( "", JLabel.LEFT ) );
	
				this.panelAction.add( this.lblChemins.get( cpt ) );
	
				if (cpt != 0)
					this.lblChemins.get(cpt).setText("n°" + cpt + " " + this.ctrl.afficheChemins( cpt ) );
				else
					this.lblChemins.get(cpt).setText( "Chemins Critique :" );

				this.lblChemins.get(cpt).setVisible( critActive );
			}
		}
	}
	*/

	public int derniereEtape()
	{
		if ( ! this.btnPlusTot.isEnabled() && ! this.btnPlusTard.isEnabled() ) return 2;
		if ( ! this.btnPlusTot.isEnabled()                                   ) return 1;

		return 0;
	}

	public boolean critActive()
	{
		return this.critActive;
	}
}
 