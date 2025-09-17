/** Classe PanelTache pour la [SAE 2.01 2.02 2.05 - Développement d'une application] 
 *  @author : Flem Anthony / Freret Alexandre / Martin Erwan / El Maknassi Lucas - Equipe n°06
 *  @version: 1.0
 */

package MPM.ihm.tache;

import java.awt.event.*;
import javax.swing.*;

import MPM.Controleur;
import MPM.metier.Tache;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

public class PanelTache extends JPanel implements ActionListener
{
	private Controleur ctrl;
	private JFrame frame;
	private Tache tache;

	private JButton btnAnnul;
	private JButton btnModif;
	private JButton btnSuppr;

	private JTextField txtNom;
	private JTextField txtDuree;
	private JTextField txtAntecedant;
	private JTextField txtSuivants;

	private JLabel lblErreurNom;
	private JLabel lblErreurDuree;
	private JLabel lblErreurAntecedant;
	private JLabel lblErreurSuivant;

	public PanelTache(Controleur ctrl, FrameTache frame, Tache tache)
	{
		JPanel panelSaisie;
		JPanel panelAction;

		this.ctrl  = ctrl;
		this.frame = frame;
		this.tache = tache;

		this.setLayout( new BorderLayout() );

		/* ------------------------- */
		/* Création des Composants */
		/* ------------------------- */
		panelSaisie = new JPanel( new GridLayout( 6, 2, 20, 5 ) );
		panelAction = new JPanel( new GridLayout( 1, 4        ) );

		this.btnAnnul = new JButton( "Annuler"   );
		this.btnModif = new JButton( "Modifier"  );
		this.btnSuppr = new JButton( "Supprimer" );

		this.txtNom        = new JTextField( tache.getNom()       , 20 );
		this.txtDuree      = new JTextField( "" + tache.getDuree(), 20 );
		this.txtAntecedant = new JTextField( this.getAntecedents(), 20 );
		this.txtSuivants   = new JTextField( this.getSuivants()   , 20 );

		if ( this.tache.getNom().equals("DEBUT") || this.tache.getNom().equals("FIN") )
		{
			this.btnModif     .setEnabled ( false );
			this.btnSuppr     .setEnabled ( false );

			this.txtNom       .setEditable( false );
			this.txtDuree     .setEditable( false );
			this.txtAntecedant.setEditable( false );
			this.txtSuivants  .setEditable( false );

			this.txtDuree     .setBackground( new Color( 245, 245, 245 ) );
			this.txtNom       .setBackground( new Color( 245, 245, 245 ) );
			this.txtAntecedant.setBackground( new Color( 245, 245, 245 ) );
			this.txtSuivants  .setBackground( new Color( 245, 245, 245 ) );
		}

		this.lblErreurNom        = new JLabel( "", JLabel.CENTER );
		this.lblErreurDuree      = new JLabel( "", JLabel.CENTER );
		this.lblErreurAntecedant = new JLabel( "", JLabel.CENTER );
		this.lblErreurSuivant    = new JLabel( "", JLabel.CENTER );
		this.lblErreurNom       .setForeground( Color.RED );
		this.lblErreurDuree     .setForeground( Color.RED );
		this.lblErreurAntecedant.setForeground( Color.RED );
		this.lblErreurSuivant   .setForeground( Color.RED );


		/* ----------------------------- */
		/* Positionnement des Composants */
		/* ----------------------------- */
		panelSaisie.add( new JLabel( "Nom"  , JLabel.CENTER ) );
		panelSaisie.add( new JLabel( "Durée", JLabel.CENTER ) );
		panelSaisie.add( this.txtNom         );
		panelSaisie.add( this.txtDuree       );
		panelSaisie.add( this.lblErreurNom   );
		panelSaisie.add( this.lblErreurDuree );

		panelSaisie.add( new JLabel( "Antécédent(s)", JLabel.CENTER ) );
		panelSaisie.add( new JLabel( "Suivant(s)"   , JLabel.CENTER ) );
		panelSaisie.add( this.txtAntecedant       );
		panelSaisie.add( this.txtSuivants         );
		panelSaisie.add( this.lblErreurAntecedant );
		panelSaisie.add( this.lblErreurSuivant    );


		panelAction.add(this.btnAnnul);
		panelAction.add(this.btnSuppr);
		panelAction.add(this.btnModif);


		this.add( panelSaisie, BorderLayout.NORTH );
		this.add( panelAction, BorderLayout.SOUTH );

		if (this.tache.getNiveau() != -1)
			this.add( new JLabel( "Niveau " + tache.getNiveau(), JLabel.CENTER ), BorderLayout.CENTER );
		else
			this.add( new JLabel( "Niveau inconnu", JLabel.CENTER ), BorderLayout.CENTER );

		/* ----------------------------- */
		/* Activation des Composants     */
		/* ----------------------------- */
		this.btnModif.addActionListener( this );
		this.btnAnnul.addActionListener( this );
		this.btnSuppr.addActionListener( this );
	}

	public void actionPerformed(ActionEvent e)
	{
		Object[] tabErreur;
		String nom;
		String duree;
		String[] antecedents;
		String[] suivants;
		int niveauCompar = this.ctrl.getNbNiveaux();

		if ( e.getSource() == this.btnModif )
		{
			nom         = this.txtNom       .getText();
			duree       = this.txtDuree     .getText();
			antecedents = this.txtAntecedant.getText().split(",");
			suivants    = this.txtSuivants  .getText().split(",");

			tabErreur = this.ctrl.modifierTache( nom, duree, antecedents, suivants, this.tache );

			this.lblErreurNom       .setText( (String) tabErreur[0] );
			this.lblErreurDuree     .setText( (String) tabErreur[1] );
			this.lblErreurAntecedant.setText( (String) tabErreur[2] );
			this.lblErreurSuivant   .setText( (String) tabErreur[2] );

			if ( ! (boolean) tabErreur[3] )
			{
				this.ctrl.majCalculs();
				this.frame.dispose();
			}
		}

		if ( e.getSource() == this.btnSuppr )
		{
			this.ctrl.supprimerTache( this.tache );
			this.ctrl.majCalculs();
			this.frame.dispose();
		}

		if ( e.getSource() == this.btnAnnul )
			this.frame.dispose();

		if ( niveauCompar < this.ctrl.getNbNiveaux() )
			this.ctrl.varierEtape( this.ctrl.derniereEtape() );
		if ( niveauCompar > this.ctrl.getNbNiveaux() )
			this.ctrl.varierEtape( - this.ctrl.derniereEtape() );

		this.ctrl.majGraphe();
	}

	private String getAntecedents()
	{
		String liste = "";
		for (Tache t : this.tache.getPrcs())
		{
			if ( ! t.getNom().equals("DEBUT") )
				liste += ( this.tache.getPrcs().get(0) != t ? "," : "" ) + t.getNom();
		}

		return liste;
	}

	private String getSuivants()
	{
		String liste = "";
		for (Tache t : this.tache.getSvts())
		{
			if ( ! t.getNom().equals("FIN") )
				liste += ( this.tache.getSvts().get(0) != t ? "," : "" ) + t.getNom();
		}
		return liste;
	}
}