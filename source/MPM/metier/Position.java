/** Classe Position pour la [SAE 2.01 2.02 2.05 - Développement d'une application] 
 *  @author : Flem Anthony / Freret Alexandre / Martin Erwan / El Maknassi Lucas - Equipe n°06
 *  @version: 1.0
 */

 package MPM.metier;

 public class Position
 {
	 /* ----------- */
	 /* Attributs   */
	 /* ----------- */
	 private int centreX;
	 private int centreY;
	 private int tailleX;
	 private int tailleY;
 
	 /* ---------------- */
	 /* Constructeur(s)  */
	 /* ---------------- */
	 public Position(int centreX, int centreY, int tailleX, int tailleY)
	 {
		 this.centreX = centreX;
		 this.centreY = centreY;
		 this.tailleX = tailleX;
		 this.tailleY = tailleY;
	 }
 
	 public Position()
	 {
		 this( 50, 25, 80 , 40 );
	 }
 
	 /* ----------- */
	 /* Accesseurs  */
	 /* ----------- */
	 public int getCentreX() { return this.centreX; }
	 public int getCentreY() { return this.centreY; }
	 public int getTailleX() { return this.tailleX; }
	 public int getTailleY() { return this.tailleY; }
 
	 /* -------------- */
	 /* Modificateurs  */
	 /* -------------- */
	 public void deplacerX (int x)       { this.centreX+= x;       }
	 public void deplacerY (int y)       { this.centreY+= y;       }
	 public void setTailleX(int tailleX) { this.tailleX = tailleX; }
	 public void setTailleY(int tailleY) { this.tailleY = tailleY; }
	 public void setCentreX(int centreX) { this.centreX = centreX; }
	 public void setCentreY(int centreY) { this.centreY = centreY; }
 
	 public String toString()
	 {
		 return this.centreX + ":" + this.centreY;
	 }
 }