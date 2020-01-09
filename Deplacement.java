import java.util.*;

public class Deplacement{
	private Plateau plateau; // Echéquier
	private boolean moveValid; // true si mouvement valide, false sinon

	// Constructeur
	public Deplacement(Plateau plateau){
		this.plateau = plateau;
	}

	// get plateau
	public Plateau getPlateau(){
		return plateau;
	}

	// Trouve la piece a la col et lig mis en parametre
	public Piece trouvPiece(int lig, int col){
		boolean flag = false; // piece trouve = true
		ArrayList<Piece> pieces = getPlateau().getPieces(); // pieces en jeu
		Piece trouvPiece = null;
		findLoop: for(Piece p: pieces){ // boucle pour trouver la piece
			trouvPiece = p;
			if(trouvPiece.getLig() == lig && trouvPiece.getCol() == col){
				flag = true; // piece trouver, fin de la boucle
				break findLoop;
			}
		}
		// Si le flag est true alors retourne la piece touver, sinon null
		if(flag){
			return trouvPiece;
		} else {
			return null;
		}
	}

	// Convertie l'entrée string en lig int
	private int inputToLig(String input){
		char r = input.charAt(1);
		int lig;
		switch(r){ // la valeur '0' corresponds a '8', etc
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

	// Convertie l'entrée String col int
	private int inputToCol(String input){
		char c = input.charAt(0);
		int col;
		switch(c){ // '0' value corresponds to 'a', etc
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

	// Déplace la piece a l'endroit spécifier
	// Retourne vrai si le dépassement a réussi
	public boolean deplacement(String init, String dest, Player player){
		// Convertie les entrée string en lig et col
		int initLig, initCol, destLig, destCol;
		initLig = inputToLig(init);
		initCol = inputToCol(init);
		destLig = inputToLig(dest);
		destCol = inputToCol(dest);
		if((initLig >= 0 && initLig < 8) && (initCol >= 0 && initCol < 8)){ // check la case de départ
			if((destLig >= 0 && destLig < 8) && (destCol >= 0 && destCol < 8)){ // check la case d'arriver
				Piece piece = trouvPiece(initLig, initCol);
				if(piece.getCouleur() == player.getCouleur()){ // check si le joueur joue une piece a lui
					ArrayList<ArrayList<Integer>> moveValids = legalPieceMoves(piece, false); // cherche les moves autorisés
					ArrayList<Integer> r = moveValids.get(0); // ligne
					ArrayList<Integer> c = moveValids.get(1); // colonne
					ListIterator<Integer> ligIter = r.listIterator(); // fais une itération sur lig et col
					ListIterator<Integer> colIter = c.listIterator();
					int rNext, cNext;
					while(ligIter.hasNext() && colIter.hasNext()){ // Tant que les iterateur ont une valeur
						rNext = ligIter.next();
						cNext = colIter.next();
						if(initLig == rNext && destCol == cNext){ // Si le move est valide alors ...
							getPlateau().removePiece(destLig, destCol); // Supprime la piece adversse(si elle se trouve sur la case)
							piece.setLig(destLig); // place la pice au bonne endroit
							piece.setCol(destCol);
							getPlateau().updatePlateau(); // update le plateau
							getPlateau().afficherPlateau(); // affiche le tableau
							getPlateau().pionPromotion(player.getCouleur()); // promote pions si neccessaire
							moveValid = true; // Déplacement réussi
							return true;
						}
					}
				}
			} else {
				System.out.println("Impossible de bouger sur cette case, recommence!");
				moveValid = false;
			}
		} else {
			System.out.println("Impossible de bouger sur cette case, recommence!");
			moveValid = false;
		}
		return false; // move Impossible
	}

	// check si le roi est sur la case demander
	public boolean roiOnSpace(int r, int c, int couleur){
			for(Piece p : getPlateau().getPieces()){ // Cherche dans les pieces
			if(p.getCouleur() == couleur){ // chek la couleur
				if(p.getLig() == r && p.getCol() == c){ // chek la case
					if(p.getType() == 1){ // check le type de la piece (roi=1)
						return true;
					}
				}
			}
		}
		return false; // Pas de roi sur la case
	}

	// Retourn la liste des moves autorisés
	public ArrayList<ArrayList<Integer>> legalPieceMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> lig, col; // liste de coordonnée
		lig = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> roi, reine, tour, cavalier, fou, pion; // liste pour les différents type de pièces
		ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<ArrayList<Integer>>(); // liste qui retourne les coup autorisés
		switch(piece.getType()){ // switch entre différents type
			// roi
			case 1:
				roi = possRoiMoves(piece, checkMate); // roi prend tous les moves possible
				lig = roi.get(0); // les valeurs de la ligne sont stocker dans le 1er ArrayList
				col = roi.get(1); // les valeurs de la colonnne sont stocker dans le 2e ArrayList
				break;
			// reine
			case 2:
				reine = possReineMoves(piece, checkMate);
				lig = reine.get(0);
				col = reine.get(1);
				break;
			// tour
			case 3:
				tour = possTourMoves(piece, checkMate);
				lig = tour.get(0);
				col = tour.get(1);
				break;
			// cavalier
			case 4:
				cavalier = possCavalierMoves(piece, checkMate);
				lig = cavalier.get(0);
				col = cavalier.get(1);
				break;
			// fou
			case 5:
				fou = possFouMoves(piece, checkMate);
				lig = fou.get(0);
				col = fou.get(1);
				break;
			// pion
			case 6:
				pion = possPionMoves(piece, checkMate);
				lig = pion.get(0);
				col = pion.get(1);
				break;
			default:
				break;
		}
		legalMoves.add(lig); // ajoute lig, col a legalMoves
		legalMoves.add(col);
		return legalMoves; // return list de legalMoves
	}

	// check si les coordonnée sont sur le plateau
	private boolean checkPlateau(int r, int c){
		if((r >= 0 && r <= 7) && (c >= 0 && c <= 7)){
			return true;
		} else {
			return false;
		}
	}

	// check si pièce est sur la case
	public boolean pieceOnSpace(int r, int c){
		for(Piece p : getPlateau().getPieces()){ // boucle qui check les pieces sur le plateau
			if(p.getLig() == r && p.getCol() == c){ // find piece
				return true;
			}
		}
		return false; // no piece found
	}
	// check for piece on input space, input: lig, colonne, couleur
	public boolean pieceOnSpace(int r, int c, int couleur){
		for(Piece p : getPlateau().getPieces()){ // iterate through pieces
			if(p.getCouleur () == couleur){ // check couleur
				if(p.getLig() == r && p.getCol() == c){ // trouve la piece
					return true;
				}
			}
		}
		return false; // Pas de piece trouver
	}

	// mouvement possible du pion : retourne ArrayList<ArrayList<Integer>>
	private ArrayList<ArrayList<Integer>> possPionMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> lig, col;
		lig = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> ligAndCol = new ArrayList<ArrayList<Integer>>(); // liste commune a retourner
		int r = piece.getLig(); // ligne
		int c = piece.getCol(); // colonne
		int couleur = piece.getCouleur(); // couleur
		int oppCouleur = piece.getEnnemieCouleur(); // ennemie couleur
		// Mouvement vers l'avant
		if(piece.getType() == 6 && piece.getCouleur() == 0){ // check si pion est blanc
			if(r == 6) { // si pion n'a pas bouger, peu bouger d'une ou deux case
				if(!pieceOnSpace(r-1, c)){ // Vérifier que la case est vide
					lig.add(r-1); // ajoute lig, col au moves possible
					col.add(c);
					if(!pieceOnSpace(r-2, c)){
						lig.add(r-2);
						col.add(c);
					}
				}
			} else { // Pas sur la case de départ, peu seulement bouger d'une case
				if((r-1)>=0 && !pieceOnSpace(r-1, c)){ // Vérifie que la case est libre / le pion ne sort pas du plateau
					lig.add(r-1);
					col.add(c);
				}
			}
			// Diagonal vers l'avant: droite/gauche pour capturer les pieces adverses
			if(pieceOnSpace(r-1, c+1, oppCouleur)){ // check si une piece adverse se trouve a droite
				if(checkMate){ // si checkMate = true, La partie est fini
					lig.add(r-1);
					col.add(c+1);
				} else {  // La partie n'est pas encore fini
					if(!roiOnSpace(r-1, c+1, oppCouleur)){ // check que le roi n'est pas sur la case
						lig.add(r-1);
						col.add(c+1);
					}
				}
			}
			if(pieceOnSpace(r-1, c-1, oppCouleur)){ // diagonal gauche
				if(checkMate){
					lig.add(r-1);
					col.add(c-1);
				} else {
					if(!roiOnSpace(r-1, c-1, oppCouleur)){
						lig.add(r-1);
						col.add(c-1);
					}
				}
			}

		} else if(piece.getType() == 6 && piece.getCouleur() == 1){ // pion noir
			// Vert l'avant
			if(r == 1) {
				if(!pieceOnSpace(r+1, c)){
					lig.add(r+1);
					col.add(c);
					if(!pieceOnSpace(r+2, c)){
						lig.add(r+2);
						col.add(c);
					}
				}
			} else {
				if((r+1)>=0 && !pieceOnSpace(r+1, c)){
					lig.add(r+1);
					col.add(c);
				}
			}
			// diagonale vers le bas: droite/gauche pour capturer les pieces adverses
			if(pieceOnSpace(r+1, c+1, oppCouleur)){ // bas droite
				if(checkMate){
					lig.add(r+1);
					col.add(c+1);
				} else {
					if(!roiOnSpace(r+1, c+1, oppCouleur)){
						lig.add(r+1);
						col.add(c+1);
					}
				}
			}
			if(pieceOnSpace(r+1, c-1, oppCouleur)){ // bas gauche
				if(checkMate){
					lig.add(r+1);
					col.add(c-1);
				} else {
					if(!roiOnSpace(r+1, c-1, oppCouleur)){
						lig.add(r+1);
						col.add(c-1);
					}
				}
			}

		}
		ligAndCol.add(lig); // ajoute la ligne et colonne a la liste commune
		ligAndCol.add(col);
		return ligAndCol; // retourne les déplacement du pion
	}

	// possible fou movements: retourne ArrayList<ArrayList<Integer>>
	private ArrayList<ArrayList<Integer>> possFouMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> lig, col; // lists pour lig et col
		lig = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> ligAndCol = new ArrayList<ArrayList<Integer>>(); // liste commune
		int r = piece.getLig(); // lig
		int c = piece.getCol(); // colonne
		int couleur = piece.getCouleur(); // couleur
		int oppCouleur = piece.getEnnemieCouleur(); // couleur ennemie
		// check la diagonal droite vers le haut
		int j = c+1; // compteur colonne
		int i = r-1; // compteur ligne
		while(i >= 0 && j <= 7){ // Tant qu'on est sur le plateau
			if(pieceOnSpace(i, j, couleur)){ // Si une piece de la meme couleur est sur la case, pas de add
				break;
			} else if(pieceOnSpace(i, j, oppCouleur)){ // check si ennemie sur case, alors add et break
				if(checkMate){ // check checkmate, fin du jeu si true
					lig.add(i);
					col.add(j);
				} else { // pas checkmate
					if(!roiOnSpace(i, j, oppCouleur)){ //Si la piece n'est pas un roi, alors capture
						lig.add(i);
						col.add(j);
					}
				}
				break;
			} else { // Case vide
				lig.add(i);
				col.add(j);
			}
			i--; // itération des compteur
			j++;
		}
		// check les diagonal gauche vers le haut
		j = c-1;
		i = r-1;
		while(i >= 0 && j >= 0){ /// Tant qu'on est sur le plateau
			if(pieceOnSpace(i, j, couleur)){ // si meme couleur break
				break;
			} else if(pieceOnSpace(i, j, oppCouleur)){ // Vérifie si il y'a une piece ennemie
				if(checkMate){ // capture roi
					lig.add(i);
					col.add(j);
				} else {
					if(!roiOnSpace(i, j, oppCouleur)){
						lig.add(i);
						col.add(j);
					}
				}
				break;
			} else {
				lig.add(i);
				col.add(j);
			}
			i--;
			j--;
		}
		//  diagonale droite, bas
		j = c+1;
		i = r+1;
		while(i <= 7 && j <= 7){ // Tant qu'on est sur le plateau
			if(pieceOnSpace(i, j, couleur)){ // si meme couleur break
				break;
			} else if(pieceOnSpace(i, j, oppCouleur)){ // Vérifie si il y'a une pice ennemie
				if(checkMate){ // capture roi
					lig.add(i);
					col.add(j);
				} else {
					if(!roiOnSpace(i, j, oppCouleur)){
						lig.add(i);
						col.add(j);
					}
				}
				break;
			} else {
				lig.add(i);
				col.add(j);
			}
			i++;
			j++;
		}
		// check diagonale, Gauche
		j = c-1;
		i = r+1;
		while(i <= 7 && j >= 0){ // Tant qu'on est sur le plateau
			if(pieceOnSpace(i, j, couleur)){ // si meme couleur break
				break;
			} else if(pieceOnSpace(i, j, oppCouleur)){ // Vérifie si il y'a une pice ennemie
				if(checkMate){ // capture roi
					lig.add(i);
					col.add(j);
				} else {
					if(!roiOnSpace(i, j, oppCouleur)){
						lig.add(i);
						col.add(j);
					}
				}
				break;
			} else {
				lig.add(i);
				col.add(j);
			}
			i++;
			j--;
		}
		ligAndCol.add(lig);
		ligAndCol.add(col);
		return ligAndCol;
	}

	// possible cavalier movements: retourne ArrayList<ArrayList<Integer>>
	private ArrayList<ArrayList<Integer>> possCavalierMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> lig, col; // lig, colonne lists
		lig = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> ligAndCol = new ArrayList<ArrayList<Integer>>();
		int r = piece.getLig(); // lig
		int c = piece.getCol(); // colonne
		int couleur = piece.getCouleur(); // couleur
		int oppCouleur = piece.getEnnemieCouleur(); // ennemie couleur
		// check haut, droite cases (2 options)
		// option 1: 2 haut, 1 droite
		if(pieceOnSpace(r-2, c+1, couleur)){
			// Si meme couleur pas de add
		} else if(pieceOnSpace(r-2, c+1, oppCouleur)){ // Vérifie si il y'a une piece ennemie
			if(checkMate){ // capture roi
				lig.add(r-2);
				col.add(c+1);
			} else {
				if(!roiOnSpace(r-2, c+1, oppCouleur)){
					lig.add(r-2);
					col.add(c+1);
				}
			}
		} else {
			lig.add(r-2);
			col.add(c+1);
		}
		// option 2: 1 haut, 2 droite
		if(pieceOnSpace(r-1, c+2, couleur)){
			// Si meme couleur pas de add
		} else if(pieceOnSpace(r-1, c+2, oppCouleur)){ // Vérifie si il y'a une piece ennemie
			if(checkMate){ // capture roi
				lig.add(r-1);
				col.add(c+2);
			} else { // Vérifie que la piece n'est pas un roi
				if(!roiOnSpace(r-1, c+2, oppCouleur)){
					lig.add(r-1);
					col.add(c+2);
				}
			}
		} else {
			lig.add(r-1);
			col.add(c+2);
		}
		// check haut, Gauches  (2 options)
		//option 1: 2 haut, 1 gauche
		if(pieceOnSpace(r-2, c-1, couleur)){
			// Si meme couleur pas de add
		} else if(pieceOnSpace(r-2, c-1, oppCouleur)){ // Vérifie si il y'a une pice ennemie
			if(checkMate){ // capture roi
				lig.add(r-2);
				col.add(c-1);
			} else { // Vérifie que la piece n'est pas un roi
				if(!roiOnSpace(r-2, c-1, oppCouleur)){
					lig.add(r-2);
					col.add(c-1);
				}
			}
		} else {
			lig.add(r-2);
			col.add(c-1);
		}
		// option 2: 1 haut, 2 gauche
		if(pieceOnSpace(r-1, c-2, couleur)){
			// Si meme couleur pas de add
		} else if(pieceOnSpace(r-1, c-2, oppCouleur)){ // Vérifie si il y'a une pice ennemie
			if(checkMate){ // capture roi
				lig.add(r-1);
				col.add(c-2);
			} else { // Vérifie que la piece n'est pas un roi
				if(!roiOnSpace(r-1, c-2, oppCouleur)){
					lig.add(r-1);
					col.add(c-2);
				}
			}
		} else {
			lig.add(r-1);
			col.add(c-2);
		}
		// check bas, droites (2 options)
		//option 1: 2 bas , 1 droite
		if(pieceOnSpace(r+2, c+1, couleur)){
			// Si meme couleur pas de add
		} else if(pieceOnSpace(r+2, c+1, oppCouleur)){ // Vérifie si il y'a une pice ennemie
			if(checkMate){ // capture roi
				lig.add(r+2);
				col.add(c+1);
			} else { // Vérifie que la piece n'est pas un roi
				if(!roiOnSpace(r+2, c+1, oppCouleur)){
					lig.add(r+2);
					col.add(c+1);
				}
			}
		} else {
			lig.add(r+2);
			col.add(c+1);
		}
		// option 2: 1 bas,2 droite
		if(pieceOnSpace(r+1, c+2, couleur)){
			// Si meme couleur pas de add
		} else if(pieceOnSpace(r+1, c+2, oppCouleur)){ // Vérifie si il y'a une pice ennemie
			if(checkMate){ // capture roi
				lig.add(r+1);
				col.add(c+2);
			} else { // Vérifie que la piece n'est pas un roi
				if(!roiOnSpace(r+1, c+2, oppCouleur)){
					lig.add(r+1);
					col.add(c+2);
				}
			}
		} else {
			lig.add(r+1);
			col.add(c+2);
		}
		// check bas, gauche (2 options)
		//option 1: bas 2, gauche 1
		if(pieceOnSpace(r+2, c-1, couleur)){
			// Si meme couleur pas de add
		} else if(pieceOnSpace(r+2, c-1, oppCouleur)){ // Vérifie si il y'a une pice ennemie
			if(checkMate){ // capture roi
				lig.add(r+2);
				col.add(c-1);
			} else { // Vérifie que la piece n'est pas un roi
				if(!roiOnSpace(r+2, c-1, oppCouleur)){
					lig.add(r+2);
					col.add(c-1);
				}
			}
		} else {
			lig.add(r+2);
			col.add(c-1);
		}
		// option 2: bas 1, gauche 2
		if(pieceOnSpace(r+1, c-2, couleur)){
			// Si meme couleur pas de add
		} else if(pieceOnSpace(r+1, c-2, oppCouleur)){ // Vérifie si il y'a une pice ennemie
			if(checkMate){ // capture roi
				lig.add(r+1);
				col.add(c-2);
			} else { // Vérifie que la piece n'est pas un roi
				if(!roiOnSpace(r+1, c-2, oppCouleur)){
					lig.add(r+1);
					col.add(c-2);
				}
			}
		} else {
			lig.add(r+1);
			col.add(c-2);
		}
		ligAndCol.add(lig); // add lig, colonne to combined list
		ligAndCol.add(col);
		return ligAndCol; // return combined list
	}

	// possible tour movements: returns ArrayList<ArrayList<Integer>>
	private ArrayList<ArrayList<Integer>> possTourMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> lig, col;
		lig = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> ligAndCol = new ArrayList<ArrayList<Integer>>();
		int r = piece.getLig(); // lig
		int c = piece.getCol(); // colonne
		int couleur = piece.getCouleur(); // couleur
		int oppCouleur = piece.getEnnemieCouleur(); // ennemie couleur
		// check toutes les cases vers le haut
		for(int i = r-1; i >= 0; i--){
			if(pieceOnSpace(i, c, couleur)){ // si meme couleur break
				break;
			} else if(pieceOnSpace(i, c, oppCouleur)){ // Vérifie si il y'a une pice ennemie
				if(checkMate){ // capture roi
					lig.add(i);
					col.add(c);
				} else {
					if(!roiOnSpace(i, c, oppCouleur)){
						lig.add(i);
						col.add(c);
					}
				}
				break;
			} else {
				lig.add(i);
				col.add(c);
			}
		}
		// check toutes les cases vers le bas
		for(int i = r+1; i < 8; i++){
			if(pieceOnSpace(i, c, couleur)){ // si meme couleur break
				break;
			} else if(pieceOnSpace(i, c, oppCouleur)){ // Vérifie si il y'a une pice ennemie
				if(checkMate){ // capture roi
					lig.add(i);
					col.add(c);
				} else {
					if(!roiOnSpace(i, c, oppCouleur)){
						lig.add(i);
						col.add(c);
					}
				}
				break;
			} else {
				lig.add(i);
				col.add(c);
			}
		}
		// check toutes les cases vers la droite
		for(int i = c+1; i < 8; i++){
			if(pieceOnSpace(i, c, couleur)){ // si meme couleur break
				break;
			} else if(pieceOnSpace(i, c, oppCouleur)){ // Vérifie si il y'a une piece ennemie
				if(checkMate){ // capture roi
					lig.add(i);
					col.add(c);
				} else { // pas de checkMate, on vérifie que la piece n'est pas un roi
					if(!roiOnSpace(i, c, oppCouleur)){
						lig.add(i);
						col.add(c);
					}
				}
				break;
			} else { // case vides
				lig.add(i);
				col.add(c);
			}
		}
		// check toute les cases a gauches de la pièce
		for(int i = c-1; i >= 0; i--){
			if(pieceOnSpace(i, c, couleur)){ // si meme couleur break
				break;
			} else if(pieceOnSpace(i, c, oppCouleur)){ // Vérifie si il y'a une piece ennemie
				if(checkMate){ // capture roi
					lig.add(i);
					col.add(c);
				} else {
					if(!roiOnSpace(i, c, oppCouleur)){
						lig.add(i);
						col.add(c);
					}
				}
				break;
			} else {
				lig.add(i);
				col.add(c);
			}
		}
		ligAndCol.add(lig);
		ligAndCol.add(col);
		return ligAndCol;
	}

	// possible roi movements: retourne ArrayList<ArrayList<Integer>>
	private ArrayList<ArrayList<Integer>> possRoiMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> lig, col;
		lig = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> ligAndCol = new ArrayList<ArrayList<Integer>>();
		int r = piece.getLig(); // lig
		int c = piece.getCol(); // colonne
		int couleur = piece.getCouleur(); // couleur
		int oppCouleur = piece.getEnnemieCouleur(); // ennemie couleur



		// possible mouvement horizontale / verticale / diagonale
		// Case vers la droite
		if(checkPlateau(r, c+1) && !pieceOnSpace(r, c+1, couleur)){ // Sur le plateau et pas de la meme couleurs
			if(checkMate){ // capture roi
				lig.add(r);
				col.add(c+1);
			} else {
				if(!roiOnSpace(r, c+1, oppCouleur)){
					lig.add(r);
					col.add(c+1);
				}
			}
		}
		// Case vers la gauche
		if(checkPlateau(r, c-1) && !pieceOnSpace(r, c-1, couleur)){ // Sur le plateau et pas de la meme couleurs
			if(checkMate){ // capture roi
				lig.add(r);
				col.add(c-1);
			} else {
				if(!roiOnSpace(r, c-1, oppCouleur)){
					lig.add(r);
					col.add(c-1);
				}
			}
		}
		// Vers le haut
		if(checkPlateau(r-1, c) && !pieceOnSpace(r-1, c, couleur)){ // Sur le plateau et pas de la meme couleurs
			if(checkMate){ // capture roi
				lig.add(r-1);
				col.add(c);
			} else {
				if(!roiOnSpace(r-1, c, oppCouleur)){
					lig.add(r-1);
					col.add(c);
				}
			}
		}
		// Vers le bas
		if(checkPlateau(r+1, c) && !pieceOnSpace(r+1, c, couleur)){ // Sur le plateau et pas de la meme couleurs
			if(checkMate){ // capture roi
				lig.add(r+1);
				col.add(c);
			} else {
				if(!roiOnSpace(r+1, c, oppCouleur)){
					lig.add(r+1);
					col.add(c);
				}
			}
		}
		// diagonalz haut, droite
		if(checkPlateau(r-1, c+1) && !pieceOnSpace(r-1, c+1, couleur)){ // Sur le plateau et pas de la meme couleurs
			if(checkMate){ // capture roi
				lig.add(r-1);
				col.add(c+1);
			} else {
				if(!roiOnSpace(r-1, c+1, oppCouleur)){
					lig.add(r-1);
					col.add(c+1);
				}
			}
		}
		// diagonal haut, gauche
		if(checkPlateau(r-1, c-1) && !pieceOnSpace(r-1, c-1, couleur)){ // Sur le plateau et pas de la meme couleurs
			if(checkMate){ // capture roi
				lig.add(r-1);
				col.add(c-1);
			} else {
				if(!roiOnSpace(r-1, c-1, oppCouleur)){
					lig.add(r-1);
					col.add(c-1);
				}
			}
		}
		// diagonale bas, droite
		if(checkPlateau(r+1, c+1) && !pieceOnSpace(r+1, c+1, couleur)){ // Sur le plateau et pas de la meme couleurs
			if(checkMate){ // capture roi
				lig.add(r+1);
				col.add(c+1);
			} else {
				if(!roiOnSpace(r+1, c+1, oppCouleur)){
					lig.add(r+1);
					col.add(c+1);
				}
			}
		}
		// diagonale bas, gauche
		if(checkPlateau(r+1, c-1) && !pieceOnSpace(r+1, c-1, couleur)){ // Sur le plateau et pas de la meme couleurs
			if(checkMate){ // capture roi
				lig.add(r+1);
				col.add(c-1);
			} else {
				if(!roiOnSpace(r+1, c-1, oppCouleur)){
					lig.add(r+1);
					col.add(c-1);
				}
			}
		}
		ligAndCol.add(lig);
		ligAndCol.add(col);
		return ligAndCol;
	}
	// possible reine movements: retourne ArrayList<ArrayList<Integer>>
	private ArrayList<ArrayList<Integer>> possReineMoves(Piece piece, boolean checkMate){
		ArrayList<Integer> lig, col; // lig, col list
		lig = new ArrayList<Integer>();
		col = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> ligAndCol = new ArrayList<ArrayList<Integer>>();
		// La reine est une combinaison du fou et de la tour on peut donc les récupérer
		ArrayList<ArrayList<Integer>> fou, tour;
		fou = possFouMoves(piece, checkMate);
		tour = possTourMoves(piece, checkMate);
		lig.addAll(fou.get(0));
		col.addAll(fou.get(1));
		lig.addAll(tour.get(0));
		col.addAll(tour.get(1));
		ligAndCol.add(lig);
		ligAndCol.add(col);
		return ligAndCol;
	}

	// possible ennemie  a attaquer
	public ArrayList<Piece> possEnnemieCible(Piece piece){
		ArrayList<Piece> ennemieCible = new ArrayList<Piece>(); // liste de cible a retourner
		ArrayList<ArrayList<Integer>> moveValid = legalPieceMoves(piece, false); // Mouvement valide de la piece
		ArrayList<Integer> r = moveValid.get(0); // ligne list
		ArrayList<Integer> c = moveValid.get(1); // colonne list
		ListIterator<Integer> ligIter = r.listIterator(); //
		ListIterator<Integer> colIter = c.listIterator();
		int rNext, cNext;
		while(ligIter.hasNext() && colIter.hasNext()){ // tant que les iterator ne sont pas null
			rNext = ligIter.next();
			cNext = colIter.next();
			if(pieceOnSpace(rNext, cNext, piece.getEnnemieCouleur())){ // check si l'ennemie est sur la case, si oui on l'ajoute a la liste
				ennemieCible.add(plateau.getPieces(rNext, cNext, piece.getEnnemieCouleur()));
			}
		}
		return ennemieCible; // retourne la liste de toutes les cibles
	}


	// Si le roi du joueur en cours est dans la list possEnnemieCible de l'adversaire
	public boolean checkCheck(int couleur){
		// ArrayList des pieces en jeu
		ArrayList<Piece> pieces = getPlateau().getPieces();
		for(Piece p : pieces){ // boucle sur toute les pieces
			ArrayList<Piece> ennemieCible = possEnnemieCible(p);
			ListIterator<Piece> ennemieIter = ennemieCible.listIterator();
			Piece nextEnnemie = new Piece();
			while(ennemieIter.hasNext()){ // tant que non nul
				nextEnnemie = ennemieIter.next();
				if(nextEnnemie.getType() == 1 && (p.getCouleur() != couleur)){ // si la prochaine cible est un roi et d'une couleur différentes

					System.out.println("Vous ètes en échecs");
					return true;
				}
			}
		}
		return false;
	}


}
