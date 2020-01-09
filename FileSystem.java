// Andrew Boles - ckj771
// FileSystem.java
import java.util.*;
import java.io.*;
public class FileSystem{
	private String fileName; // file to be manipulated
	private boolean inOrOut; // input = true, output = false
	private Plateau plateau; // dynamic game plateau
	private int turn; // white = 0, black = 1
	// default constructor
	public FileSystem(){}
	// file name constructor
	public FileSystem(String fileName, boolean inOrOut){
		this.fileName = fileName;
		this.inOrOut = inOrOut;
	}
	// get plateau
	public Plateau getPlateau(){
		return plateau;
	}
	// returns list of pieces in file
	public void piecesInFile() throws IOException{
		try{
		if(true){ // outputting to file
			DataOutputStream outputFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
			outputFile.writeUTF(fileName); // write String fileName out to file first
			outputFile.write(getPlateau().getTurn()); // write current turn to second line in file
			ArrayList<Piece> pieces = getPlateau().getPieces(); // get current game pieces
			//ListIterator<Player> playerIter = players.listIterator();
			for(Piece p : pieces){ // iterate through pieces
				if(p.getCouleur() == 0){ // output white rows, cols
					outputFile.write(p.getLig());
					outputFile.write(p.getCol());
				}
			}
			for(Piece p : pieces){ // black piece loop
				if(p.getCouleur() == 1){
					outputFile.write(p.getLig());
					outputFile.write(p.getCol());
				}
			}
			outputFile.close();
		} else {
			// input file
		}
		} catch(Exception except){
			//return null;
		}
	}
}
