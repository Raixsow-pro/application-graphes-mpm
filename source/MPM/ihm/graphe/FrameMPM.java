/** Classe FrameMPM pour la [SAE 2.01 2.02 2.05 - Développement d'une application] 
 *  @author : Flem Anthony / Freret Alexandre / Martin Erwan / El Maknassi Lucas - Equipe n°06
 *  @version: 1.0
 */

package MPM.ihm.graphe;

import static java.awt.event.KeyEvent.VK_O;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_M;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import static java.awt.event.InputEvent.SHIFT_DOWN_MASK;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import MPM.Controleur;
import MPM.metier.Tache;

public class FrameMPM extends JFrame implements ActionListener
{
	private Controleur ctrl;

	private PanelAction panelAction;
	private PanelGraphe panelGraphe;
	private PanelInfo panelInfo;

	private JMenuItem menuiOuvrir;
	private JMenuItem menuiSauvegarder;
	private JMenuItem menuiEnregistrerSous;
	private JMenuItem menuiQuitter;

	private String fichierEnCours;

	private JScrollPane spPanelGraphe;

	public FrameMPM(Controleur ctrl)
	{
		JPanel panelMPM;
		JMenu menuFichier;
		JMenuBar menuBar;

		this.ctrl = ctrl;
		this.fichierEnCours = "";

		int centreXFrame = (int) getToolkit().getScreenSize().getWidth() / 2;
		int centreYFrame = (int) getToolkit().getScreenSize().getHeight() / 2;

		this.setTitle("MPM - Gestion de Projet");
		this.setSize(800, 600);
		this.setLocation((centreXFrame - this.getWidth() / 2), (centreYFrame - this.getHeight() / 2));



		/* ----------------------------- */
		/* Création des Composants      */
		/* ----------------------------- */
		panelMPM = new JPanel( new BorderLayout() );

		this.panelAction = new PanelAction( this.ctrl       );
		this.panelGraphe = new PanelGraphe( this.ctrl, this );
		this.panelInfo   = new PanelInfo  ( this.ctrl       );


		menuBar = new JMenuBar();

		menuFichier = new JMenu( "Fichier" );

		this.menuiOuvrir          = new JMenuItem( "Ouvrir"           );
		this.menuiSauvegarder     = new JMenuItem( "Sauvegarder"      );
		this.menuiEnregistrerSous = new JMenuItem( "Enregistrer Sous" );
		this.menuiQuitter         = new JMenuItem( "Quitter"          );



		this.spPanelGraphe = new JScrollPane(this.panelGraphe);
		this.spPanelGraphe.getVerticalScrollBar().setUnitIncrement(24);
		this.spPanelGraphe.getHorizontalScrollBar().setUnitIncrement(24);

		this.menuiOuvrir.setAccelerator(KeyStroke.getKeyStroke(VK_O, CTRL_DOWN_MASK));
		this.menuiSauvegarder.setAccelerator(KeyStroke.getKeyStroke(VK_S, CTRL_DOWN_MASK));
		this.menuiEnregistrerSous.setAccelerator(KeyStroke.getKeyStroke(VK_S, CTRL_DOWN_MASK + SHIFT_DOWN_MASK));
		this.menuiQuitter.setAccelerator(KeyStroke.getKeyStroke(VK_Q, CTRL_DOWN_MASK));

		/* ----------------------------- */
		/* Positionnement des Composants */
		/* ----------------------------- */
		this.setJMenuBar(menuBar);
		menuBar.add(menuFichier);
		menuFichier.add(this.menuiOuvrir);
		menuFichier.add(this.menuiSauvegarder);
		menuFichier.add(this.menuiEnregistrerSous);
		menuFichier.addSeparator();
		menuFichier.add(this.menuiQuitter);

		panelMPM.add(spPanelGraphe, BorderLayout.CENTER);
		panelMPM.add(this.panelInfo, BorderLayout.SOUTH);

		this.add(this.panelAction, BorderLayout.WEST);
		this.add(panelMPM, BorderLayout.CENTER);

		/* ----------------------------- */
		/* Activation des Composants     */
		/* ----------------------------- */
		this.menuiOuvrir.addActionListener(this);
		this.menuiSauvegarder.addActionListener(this);
		this.menuiEnregistrerSous.addActionListener(this);
		this.menuiQuitter.addActionListener(this);

		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/* ----------- */
	/* Accesseurs */
	/* ----------- */
	public int getLongueurGraphe() { return (int) this.panelGraphe.getPreferredSize().getWidth();  }
	public int getHauteurGraphe () { return (int) this.panelGraphe.getPreferredSize().getHeight(); }

	/* ---------------- */
	/* Méthodes de maj  */
	/* ---------------- */
	public boolean critiqueActive()
	{
		return this.panelAction.critActive();
	}

	public int derniereEtape()
	{
		return this.panelAction.derniereEtape();
	}

	public void majIhm()
	{
		this.panelAction.majIhm         ();
		this.panelGraphe.majGraphe      ();
		this.panelInfo  .majIhm         ();

		this            .majTailleGraphe();
	}

	public void majGraphe()
	{
		this.panelGraphe.majGraphe();
	}

	public void majLabel(Tache t)
	{
		this.panelInfo.majLabel(t);
	}

	public void majTailleGraphe()
	{
		this.panelGraphe.setPreferredSize(
			new Dimension( 250 * ( this.ctrl.getNbNiveaux() ), 150 * this.ctrl.getNbTachesMax() ) );

		this.spPanelGraphe.revalidate();

		this.ctrl.setLongueurGraphe( this.getLongueurGraphe() );
		this.ctrl.setHauteurGraphe ( this.getHauteurGraphe () );
		this.ctrl.initPosition     ( this.fichierEnCours      );
	}

	public void actionPerformed(ActionEvent e)
	{
		boolean result;

		if (e.getSource() == this.menuiOuvrir)
		{
			JFileChooser fichierChoix = new JFileChooser();

			fichierChoix.setDialogTitle("Ouvrir");
			fichierChoix.showOpenDialog(fichierChoix);

			if (fichierChoix.getSelectedFile() != null)
			{
				this.fichierEnCours = fichierChoix.getSelectedFile().getPath();

				result = this.ctrl.initListeTache( this.fichierEnCours );
				if ( result )
				{
					JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du chargement \n",
							"Erreur de chargement", JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					this.majTailleGraphe();

					this.ctrl.initPosition( this.fichierEnCours );

					this.majIhm();
				}
			}
		}

		if ( e.getSource() == this.menuiSauvegarder )
		{
			if ( ! this.fichierEnCours.equals("") )
				this.ctrl.sauvegarder( this.fichierEnCours );
		}

		if ( e.getSource() == this.menuiEnregistrerSous )
		{
			JFileChooser fichierChoix = new JFileChooser();

			fichierChoix.setDialogTitle("Sauvegarder");
			fichierChoix.showSaveDialog(fichierChoix);

			if ( fichierChoix.getSelectedFile() != null )
				this.ctrl.sauvegarder(fichierChoix.getSelectedFile().getPath());
		}

		if (e.getSource() == this.menuiQuitter)
			System.exit(0);
	}
}
 