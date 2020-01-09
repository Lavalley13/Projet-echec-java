import java.util.*;
public class Plateau{
	private Piece[][] plateau = new Piece[8][8]; // dynamic plateau array
	private ArrayList<Piece> pieces = new ArrayList<Piece>(32); // dynamic ArrayList de pieces
	private int turn; // blanc = 0, noir = 1
	private char chosenGameType; // '0' = nouvelle partie, '1' = chargement
	// Constructeur
	public Plateau(){
	}
	//Montre l'état de la partie et l'affiche sur le terminale
	public void afficherPlateau(){
		System.out.println();
		System.out.println(" ------------------------------------");
		for(int i = 0; i < 8; i++){
			System.out.print(" |");
			System.out.print(8-i+" |");
			for(int j = 0; j < 8; j++){
				if(plateau[i][j] == null){ // case vide
					System.out.print("   ");
					System.out.print("|");
				} else { // piece
					System.out.print(plateau[i][j].getPieceNom());
					System.out.print("|");
				}
			}
			if(i < 8) { //
				System.out.println();
				System.out.println(" |--|-------------------------------|");
			}

		}
		//
		System.out.println(" |  | a | b | c | d | e | f | g | h |");
		System.out.println(" ------------------------------------");
		System.out.println();

	}
	// Supprime une piece du plateau
	public void removePiece(int r, int c){
		ArrayList<Piece> pieces = getPieces(); // current pieces
		for(Piece p : pieces){
			if(p.getLig() == r && p.getCol() == c){ // trouver la piece, remove, update
				pieces.remove(p);
				updatePieces(pieces);
				updatePlateau();
				break;
			}
		}
	}
	// ajoute une piece sur le plateau
	public void addPiece(Piece p, int r, int c){
		ArrayList<Piece> pieces = getPieces(); // current pieces
		if(!pieceOnSpace(r, c)){ // if space is clear, add piece
			pieces.add(p);
			updatePieces(pieces); // update game
			updatePlateau();
		} else {
			System.out.println("Il y a déja une piece a cette endroit! Vous ne pouvez pas ajouter une piece ici.");
		}
	}
	// Enleve toute les piece
	public void clearPlateau(){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				plateau[i][j] = null;
			}
		}
	}
	// convertie les String en lig
	private int inputToLig(String input){
		char r = input.charAt(1);
		int lig;
		switch(r){ // '0' corresponds a '8', etc
		case '8':
			lig = 0;
			break;
		case '7':
			lig = 1;
			break;
		case '6':
			lig = 2;
			break;
		case '5':
			lig = 3;
			break;
		case '4':
			lig = 4;
			break;
		case '3':
			lig = 5;
			break;
		case '2':
			lig = 6;
			break;
		case '1':
			lig = 7;
			break;
		default:
			lig = -1;
			break;
		}
		return lig;
	}
	// convertie String en colonne
	private int inputToCol(String input){
		char c = input.charAt(0);
		int col;
		switch(c){ // '0' correspond a 'a', etc
		case 'a':
			col = 0;
			break;
		case 'b':
			col = 1;
			break;
		case 'c':
			col = 2;
			break;
		case 'd':
			col = 3;
			break;
		case 'e':
			col = 4;
			break;
		case 'f':
			col = 5;
			break;
		case 'g':
			col = 6;
			break;
		case 'h':
			col = 7;
			break;
		default:
			col = -1;
			break;
		}
		return col;
	}
	// check si une piece est présente
	public boolean pieceOnSpace(int r, int c){
		for(Piece p : getPieces()){
			if(p.getLig() == r && p.getCol() == c){
				return true; // piece trouver
			}
		}
		return false; // pas de piece trouver
	}
	// créer pieces
	public void createPieces(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Entrer \"0\" pour débuter une nouvelle partie, \"1\" pour charger une partie");
		String type = scan.nextLine();
		chosenGameType = type.charAt(0);
		ArrayList<Piece> pieces = getPieces(); // ArrayList ds 32 pieces du jeu
		if(chosenGameType == '0'){ // nouvelle partie
			for(int i = 0; i < 8; i++){ // 8 pion/player
				pieces.add(new Piece(1, Piece.PION, 1, i)); // Piece(couleur, type, lig, col)
				pieces.add(new Piece(0, Piece.PION, 6, i));
			}
			// 2 tour/player
			pieces.add(new Piece(1, Piece.TOUR, 0, 0));
			pieces.add(new Piece(1, Piece.TOUR, 0, 7));
			pieces.add(new Piece(0, Piece.TOUR, 7, 0));
			pieces.add(new Piece(0, Piece.TOUR, 7, 7));
			// 2 fou/player
			pieces.add(new Piece(1, Piece.FOU, 0, 2));
			pieces.add(new Piece(1, Piece.FOU, 0, 5));
			pieces.add(new Piece(0, Piece.FOU, 7, 2));
			pieces.add(new Piece(0, Piece.FOU, 7, 5));
			// 2 cavaliers/player
			pieces.add(new Piece(1, Piece.CAVALIER, 0, 1));
			pieces.add(new Piece(1, Piece.CAVALIER, 0, 6));
			pieces.add(new Piece(0, Piece.CAVALIER, 7, 1));
			pieces.add(new Piece(0, Piece.CAVALIER, 7, 6));
			// 1 reine/player
			pieces.add(new Piece(1, Piece.REINE, 0, 3));
			pieces.add(new Piece(0, Piece.REINE, 7, 3));
			// 1 roi/player
			pieces.add(new Piece(1, Piece.ROI, 0, 4));
			pieces.add(new Piece(0, Piece.ROI, 7, 4));
		} else { //
			for(int i = 0; i < 8; i++){ // 8 pawns/player
				pieces.add(new Piece(1, Piece.PION, 1, i));
				pieces.add(new Piece(0, Piece.PION, 6, i));
			}
			// 1 roi/player
			pieces.add(new Piece(1, Piece.ROI, 0, 4));
			pieces.add(new Piece(0, Piece.ROI, 7, 4));

			String choice;
			boolean flag = true;

			// Pas obligatoire, a changer
			populatePlateau();
			afficherPlateau();
			while(flag){
				System.out.println("Localisation désirer de la reine blanche:");
				choice = scan.nextLine();
				if((!pieceOnSpace(7, inputToCol(choice))) && (inputToLig(choice) == 7)){
					pieces.add(new Piece(0, Piece.REINE, 7, inputToCol(choice)));
					flag = false;
				} else { //
					System.out.println("Erreur: Il y a déja une piece a cette endroit / l'input lig n'est pas correcte. Try again!");
				}
			}
			while(!flag){
				System.out.println("Localisation désirer de la reine noire:");
				choice = scan.nextLine();
				if((!pieceOnSpace(0, inputToCol(choice))) && (inputToLig(choice) == 0)){ // if space is empty
					pieces.add(new Piece(1, Piece.REINE, 0, inputToCol(choice)));
					flag = true;
				} else { // if piece already there / input lig incorrect, output error message, loop back around
					System.out.println("Erreur: Il y a déja une piece a cette endroit / l'input lig n'est pas correcte. Try again!");
				}
			}
			// add rooks
			for(int i = 0; i < 2; i++){
				populatePlateau();
				afficherPlateau();
				while(flag){
					System.out.println("Localisation désirer de la tour blanche:");
					choice = scan.nextLine();
					if((!pieceOnSpace(7, inputToCol(choice))) && (inputToLig(choice) == 7)){
						pieces.add(new Piece(0, Piece.TOUR, 7, inputToCol(choice)));
						flag = false;
					} else {
					System.out.println("Erreur: Il y a déja une piece a cette endroit / l'input lig n'est pas correcte. Try again!");
					}
				}
				while(!flag){
					System.out.println("Localisation désirer de la tour noire:");
					choice = scan.nextLine();
					if((!pieceOnSpace(0, inputToCol(choice))) && (inputToLig(choice) == 0)){
						pieces.add(new Piece(1, Piece.TOUR, 0, inputToCol(choice)));
						flag = true;
					} else {
					System.out.println("Erreur: Il y a déja une piece a cette endroit / l'input lig n'est pas correcte. Try again!");
					}
				}
			}
			// add knights
			for(int i = 0; i < 2; i++){
				populatePlateau();
				afficherPlateau();
				while(flag){
					System.out.println("Localisation désirer du cavalier blanc:");
					choice = scan.nextLine();
					if((!pieceOnSpace(7, inputToCol(choice))) && (inputToLig(choice) == 7)){
						pieces.add(new Piece(0, Piece.CAVALIER, 7, inputToCol(choice)));
						flag = false;
					} else {
					System.out.println("Erreur: Il y a déja une piece a cette endroit / l'input lig n'est pas correcte. Try again!");
					}
				}
				while(!flag){
					System.out.println("Localisation désirer du cavalier noire:");
					choice = scan.nextLine();
					if((!pieceOnSpace(0, inputToCol(choice))) && (inputToLig(choice) == 0)){
						pieces.add(new Piece(1, Piece.CAVALIER, 0, inputToCol(choice)));
						flag = true;
					} else {
					System.out.println("Erreur: Il y a déja une piece a cette endroit / l'input lig n'est pas correcte. Try again!");
					}
				}
			}
			// add bishops
			for(int i = 0; i < 2; i++){
				populatePlateau();
				afficherPlateau();
				while(flag){
					System.out.println("Localisation désirer du fou blanc:");
					choice = scan.nextLine();
					if((!pieceOnSpace(7, inputToCol(choice))) && (inputToLig(choice) == 7)){
						pieces.add(new Piece(0, Piece.FOU, 7, inputToCol(choice)));
						flag = false;
					} else {
					System.out.println("Erreur: Il y a déja une piece a cette endroit / l'input lig n'est pas correcte. Try again!");
					}
				}
				while(!flag){
					System.out.println("Localisation désirer du fou noire:");
					choice = scan.nextLine();
					if((!pieceOnSpace(0, inputToCol(choice))) && (inputToLig(choice) == 0)){
						pieces.add(new Piece(1, Piece.FOU, 0, inputToCol(choice)));
						flag = true;
					} else {
					System.out.println("Erreur: Il y a déja une piece a cette endroit / l'input lig n'est pas correcte. Try again!");
					}
				}
			}
		}
	}
	// Remplire le plateau de piece
	private void populatePlateau(){
		ArrayList<Piece> pieces = getPieces(); // current pieces
		for(Piece p : pieces){
		plateau[p.getLig()][p.getCol()] = p; // place pieces
		}
	}
	// bool to set type of game
	public boolean chooseGameType(int choice){
		if(choice == 0){ // normal game
			return false;
		} else { // scramble
			return true;
		}
	}
	// initialise le plateau
	public void initPlateau(){
		clearPlateau(); // commence avec un plateau clean
		createPieces(); // créer pieces, update game
		populatePlateau();
		afficherPlateau();
	}
	// update pieces
	public void updatePieces(ArrayList<Piece> pieces){
		this.pieces = pieces;
	}
	// update plateau
	public void updatePlateau(){
		clearPlateau();
		populatePlateau();
	}
	// get pieces
	public ArrayList<Piece> getPieces(){
		return pieces;
	}
	// get turn
	public int getTurn(){
		return turn;
	}
	// set turn
	public void setTurn(int turn){
		this.turn = turn;
	}
	// promote piece a la localisation désirer
	public void promotePiece(Piece newPiece, int r, int c){
		ArrayList<Piece> pieces = getPieces();
		for(Piece p : pieces){
			// trouver piece
			if(p.getLig() == r && p.getCol() == c){
				removePiece(r, c); // remove piece en place
				addPiece(newPiece, r, c); // add nouvelle piece
				updatePieces(pieces); // update game
				updatePlateau();
				break;
			}
		}
	}
	// check si un pion se trouve au bout du plateau(promote en reine)
	public void pionPromotion(int color){
		int endLig;
		if(color == 0){
			endLig = 0;
		} else {
			endLig = 7;
		}
		ArrayList<Piece> pieces = getPieces(); // current pieces
		int count = 0; // iteration
		for(Piece p : pieces){
			if(p.getLig() == endLig && p.getType() == 6 && p.getCouleur() == color){ // pion trouver
				p.setType(2); // changer en reine
				pieces.set(count, p); // replacer piece dans ArrayList
				updatePieces(pieces); // update game
				updatePlateau();
				break;
			}
			count++;
		}
	}
	// get piece d'une case en particulier
	public Piece getPieces(int r, int c){
		ArrayList<Piece> pieces = getPieces(); // current pieces
		for(Piece p : pieces){
			if(p.getLig() == r && p.getCol() == c){
				return p;
			}
		}
		return new Piece(); // Pas de piece trouver
	}
	// get piece d'une caseet couleur en particulier
	public Piece getPieces(int r, int c, int color){
		ArrayList<Piece> pieces = getPieces(); // current pieces
		for(Piece p : pieces){
			if((p.getLig() == r && p.getCol() == c) && p.getCouleur() == color){
				return p;
			}
		}
		return new Piece();
	}
	// get chosen game type
	public char getChosenGameType(){
		return chosenGameType;
	}
	// swap deux pieces mis en input
	public void swapPieces(Piece one, Piece two){
		ArrayList<Piece> pieces = getPieces(); // current pieces
		Piece hold = one; // set piece one égale a la piece en main piece
		int count = 0;
		for(Piece p : pieces){
			if(p == one){ // trouver Piece one
				p.setType(two.getType()); // changer les proprietes de piece 1 en piece 2
				p.setLig(two.getLig());
				p.setCol(two.getCol());
				p.setCouleur(two.getCouleur());
				pieces.set(count, p); // remettre la piece dans ArrayList
				updatePieces(pieces); // update game
				updatePlateau();
				break;
			}
			count++;
		}
		count = 0; // reset compteur
		for(Piece p : pieces){
			if(p == two){
				p.setType(hold.getType()); // changer les proprietes de piece 1 en piece 2
				p.setLig(hold.getLig());
				p.setCol(hold.getCol());
				p.setCouleur(hold.getCouleur());
				pieces.set(count, p); // remettre la piece dans ArrayList
				updatePieces(pieces); // update game
				updatePlateau();
				break;
			}
			count++;
		}
	}
}
