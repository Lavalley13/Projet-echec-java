
import java.util.*;
public class Piece{
	//constante integer piece type
	public static final int ROI = 1;
	public static final int REINE = 2;
	public static final int TOUR = 3;
	public static final int CAVALIER = 4;
	public static final int FOU = 5;
	public static final int PION = 6;
	private int couleur; // Blanc = 0, Noir = 1
	private int type; // type de pièce
	private int lig, col; // position sur l'échéquier

	// Constructeur par défaut(Utilsé pour les espaces vides)
	public Piece(){}

	// Constructeur pour créer les pièces
	public Piece(int couleur, int type, int lig, int col){
		this.couleur = couleur;
		this.type = type;
		this.lig = lig;
		this.col = col;
	}

	// Retourne le nom de la pièce
	public String getPieceNom(){
		String nom = "";
		if(getCouleur() == 0 && getType() != 0){
			nom = "B";
		} else {
			nom = "N";
		}
		switch (getType()){
		case 1:
			nom += "Ro";
			break;
		case 2:
			nom += "Re";
			break;
		case 3:
			nom += "To";
			break;
		case 4:
			nom += "Ca";
			break;
		case 5:
			nom += "Fo";
			break;
		case 6:
			nom += "Pi";
			break;
		default: // erreur
			System.out.println("Le type de la pièce n'est pas valide.");
			break;
		}
		return nom;
	}

	// get type
	public int getType(){
		return type;
	}

	// set type
	public void setType(int type){
		this.type = type;
	}

	// get couleur
	public int getCouleur(){
		return couleur;
	}
	// get ennemie couleur
	public int getEnnemieCouleur(){
		if(couleur == 0){
			return 1;
		} else {
			return 0;
		}
	}

	// set couleur
	public void setCouleur(int couleur){
		this.couleur = couleur;
	}

	// get lig
	public int getLig(){
		return lig;
	}

	// set lig
	public void setLig(int lig){
		this.lig = lig;
	}

	// get colonne
	public int getCol(){
		return col;
	}

	// set colonne
	public void setCol(int col){
		this.col = col;
	}
}
