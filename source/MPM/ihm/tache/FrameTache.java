
/** Classe FrameTache pour la [SAE 2.01 2.02 2.05 - Développement d'une application] 
 *  @author : Flem Anthony / Freret Alexandre / Martin Erwan / El Maknassi Lucas - Equipe n°06
 *  @version: 1.0
 */

package MPM.ihm.tache;

import MPM.Controleur;
import MPM.metier.Tache;

import javax.swing.*;

public class FrameTache extends JFrame
{
	private Controleur ctrl;

	private PanelTache panelTache;
	private Tache tache;

	public FrameTache(Controleur ctrl, Tache tache)
	{
		this.ctrl = ctrl;
		this.tache = tache;

		if ( this.tache.getNom().equals("") )
			this.setTitle( "Nouvelle Tache" );
		else
			this.setTitle( "Tache - " + tache.getNom() );

		this.setSize( 400, 250 );

		int centreXFrame = (int) getToolkit().getScreenSize().getWidth() / 2;
		int centreYFrame = (int) getToolkit().getScreenSize().getHeight() / 2;

		this.setLocation( ( centreXFrame - this.getWidth() / 2 ), ( centreYFrame - this.getHeight() / 2 ) );

		/* ------------------------- */
		/* Création des Composants   */
		/* ------------------------- */
		this.panelTache = new PanelTache( this.ctrl, this, tache );


		/* ----------------------------- */
		/* Positionnement des Composants */
		/* ----------------------------- */
		this.add( panelTache );


		this.setVisible( true );
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
}