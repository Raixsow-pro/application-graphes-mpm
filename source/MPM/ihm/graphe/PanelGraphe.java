/** Classe PanelGraphe pour la [SAE 2.01 2.02 2.05 - Développement d'une application] 
 *  @author : Flem Anthony / Freret Alexandre / Martin Erwan / El Maknassi Lucas - Equipe n°06
 *  @version: 1.0
 */

package MPM.ihm.graphe;

import MPM.Controleur;
import MPM.metier.CheminCritique;
import MPM.metier.Tache;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import static java.lang.Math.PI;
import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import javax.swing.*;

public class PanelGraphe extends JPanel
{
	/* ----------- */
	/* Attributs   */
	/* ----------- */
	private static final Color BLEU = new Color(0, 128, 128);
	private static final Color ROUGE = new Color(190, 81, 81);

	private Controleur ctrl;
	private FrameMPM frameMere;

	public PanelGraphe(Controleur ctrl, FrameMPM frameMPM)
	{
		this.ctrl      = ctrl;
		this.frameMere = frameMPM;

		this.setBackground( new Color(247, 247, 247) );

		/* ----------------------------- */
		/* Activation des Composants     */
		/* ----------------------------- */
		GereSouris gs = new GereSouris();

		this.addMouseListener      (gs);
		this.addMouseMotionListener(gs);
	}

	/* ---------------- */
	/* Autres méthodes */
	/* ---------------- */
	public void paintComponent(Graphics g)
	{
		Tache t;
		int xRect, yRect, tailleXRect, tailleYRect;
		int tailleXCarre, xCarre, yCarre;
		int textRectX, textRectY, textCarreX, textCarreY;

		Graphics2D g2 = (Graphics2D) g;

		super.paintComponent(g);

		// Dessiner l'ensemble des Tâches
		for (int cpt = 0; cpt < this.ctrl.getNbTaches(); cpt++)
		{
			t = this.ctrl.getTache(cpt);

			// Création du positionnement des tâches:
			xRect       = t.getPos().getCentreX() - t.getPos().getTailleX() / 2;
			yRect       = t.getPos().getCentreY() - t.getPos().getTailleY() / 2;
			tailleXRect = t.getPos().getTailleX();
			tailleYRect = t.getPos().getTailleY();

			tailleXCarre = tailleXRect / 2;
			xCarre       = xRect + tailleXCarre;
			yCarre       = yRect + tailleYRect;

			// Création du positionnement des dates (au plut tôt et au plus
			// tard):
			FontMetrics fm = g2.getFontMetrics();

			textRectX  = xRect  + ( tailleXRect  - fm.stringWidth( t.getNom() )            ) / 2;
			textRectY  = yRect  + ( tailleYRect  + fm.getAscent()                          ) / 2;
			textCarreX = xCarre + ( tailleXCarre - fm.stringWidth("" + t.affichageMax() )  ) / 2;
			textCarreY = yCarre + ( tailleYRect  + fm.getAscent()                          ) / 2;

			// Construction de la tâche graphiquement:
			g2.drawRect( xRect , yRect , tailleXRect , tailleYRect );
			g2.drawRect( xRect , yCarre, tailleXCarre, tailleYRect );
			g2.drawRect( xCarre, yCarre, tailleXCarre, tailleYRect );

			// Construction des informations des tâches quand on clique sur "+
			// tot" ou sur "+ tard":
			g2.drawString( t.getNom(), textRectX, textRectY );
			this.construireInformations( g2, t, textCarreX, textCarreY, tailleXCarre );

			// Construction des liens entre les tâches:
			this.construireLiens( g2, t, xRect, yRect, tailleXRect, tailleYRect );
		}
	}

	public void construireInformations(Graphics2D g2, Tache t, int textX, int textY, int tailleX)
	{
		for (int cptE = 0; cptE <= this.ctrl.getEtape(); cptE++)
		{
			// Pour les dates au plus tôt:
			if (t.getNiveau() == cptE)
			{
				g2.setColor( PanelGraphe.BLEU );
				g2.drawString( "" + t.affichageMin(), textX - tailleX, textY );
				g2.setColor( Color.BLACK );
			}

			// Pour les dates au plus tard:
			if (t.getNiveau() == this.ctrl.getNbNiveaux() * 2 + 1 - cptE)
			{
				g2.setColor( PanelGraphe.ROUGE );
				g2.drawString("" + t.affichageMax(), textX, textY);
				g2.setColor( Color.BLACK );
			}
		}
	}

	public void construireLiens(Graphics2D g2, Tache t, int x, int y, int tailleX, int tailleY)
	{
		for ( Tache svt : t.getSvts() )
		{
			Color c = Color.BLUE;
			for ( CheminCritique ch : this.ctrl.getChemins() )
				if ( ch.contient( t   )                                &&
				     ch.contient( svt )                                &&
					 this.ctrl.critiqueActive()                        &&
					 t.getDateMin() + t.getDuree() == svt.getDateMin()    ) c = Color.RED;

			// Création du postionnement pour la ligne, les flèches et la chaine
			// durée:
			int svtXRect       = svt.getPos().getCentreX() - svt.getPos().getTailleX() / 2;
			int svtYRect       = svt.getPos().getCentreY() - svt.getPos().getTailleY() / 2;
			int svtTailleYRect = svt.getPos().getTailleY();

			int tFleche = 5;
			double angle = atan2( (svtYRect + svtTailleYRect) - (y + tailleY), (svtXRect) - (x + tailleX) );

			int xFleche1 = (int) ( ( svtXRect                 ) - tFleche * cos( angle - PI / 6 ) );
			int yFleche1 = (int) ( ( svtYRect + svtTailleYRect) - tFleche * sin( angle - PI / 6 ) );
			int xFleche2 = (int) ( ( svtXRect                 ) - tFleche * cos( angle + PI / 6 ) );
			int yFleche2 = (int) ( ( svtYRect + svtTailleYRect) - tFleche * sin( angle + PI / 6 ) );

			int midFlecheX = ( ( svtXRect                  ) + ( x + tailleX ) ) / 2;
			int midFlecheY = ( ( svtYRect + svtTailleYRect ) + ( y + tailleY ) ) / 2;

			// Construction de la ligne et de la durée de la tâche:
			g2.setColor(c);
			g2.drawLine( x + tailleX, y + tailleY, svtXRect, svtYRect + svtTailleYRect );

			g2.setColor(Color.RED);
			g2.drawString("" + t.getDuree(), midFlecheX, midFlecheY);

			g2.setColor(c);
			g2.drawLine( svtXRect, svtYRect + svtTailleYRect, xFleche1, yFleche1 );
			g2.drawLine( svtXRect, svtYRect + svtTailleYRect, xFleche2, yFleche2 );

			g2.setColor(Color.BLACK);
		}
	}

	public void majGraphe()
	{
		this.ctrl.calculerCheminsCritiques();
		this.repaint();
	}

	/* ---------------- */
	/* Autres Classes */
	/* ---------------- */
	private class GereSouris extends MouseAdapter
	{
		Integer numTacheActive = 0;
		int x, y;

		public void mousePressed(MouseEvent e)
		{
			this.numTacheActive = PanelGraphe.this.ctrl.getIndiceTache(e.getX(), e.getY());
			this.x = e.getX();
			this.y = e.getY();
		}

		public void mouseClicked(MouseEvent e)
		{
			this.numTacheActive = PanelGraphe.this.ctrl.getIndiceTache(e.getX(), e.getY());

			if (this.numTacheActive != null)
			{
				if (e.getClickCount() == 2)
					if ( this.numTacheActive != 0 && this.numTacheActive != PanelGraphe.this.ctrl.getNbTaches() - 1 )
						PanelGraphe.this.frameMere.majLabel(PanelGraphe.this.ctrl.getTache(this.numTacheActive));

				if (SwingUtilities.isRightMouseButton(e))
					if ( this.numTacheActive != 0 && this.numTacheActive != PanelGraphe.this.ctrl.getNbTaches() - 1 )
						PanelGraphe.this.ctrl.creerFrameTache(PanelGraphe.this.ctrl.getTache(this.numTacheActive) );
			}
		}

		public void mouseDragged(MouseEvent e)
		{
			if ( this.numTacheActive != null )
				PanelGraphe.this.ctrl.deplacerTache( this.numTacheActive, e.getX() - x, e.getY() - y );

			this.x = e.getX();
			this.y = e.getY();

			PanelGraphe.this.repaint();
		}
	}
}