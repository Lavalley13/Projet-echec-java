import java.util.*;
import java.io.*;
public class Help{
	private Plateau plateau;
	// default constructor
	public Help(){
	}
	// get plateau method
	public Plateau getPlateau(){
		return plateau;
	}

	// app info
	public void appInfo(){
		for(int i = 0; i < 5; i++){
			System.out.println();
		}
		System.out.println("Créateur: Tony Bernis, Rida Moustaoui");
		System.out.println("Projet Java");
		System.out.println("Université Paris 13");
	}
	// Basic Chess Rules
	public void basicChessInfo(){
		for(int i = 0; i < 5; i++){
			System.out.println();
		}
		System.out.println("Pour apprendre les règles");
		System.out.println("http://www.intuitor.com/chess/");

	}
	// scramble chess rules
	public void scrambleChessRules(){
		for(int i = 0; i < 5; i++){
			System.out.println();
		}
		System.out.println("Scramble Chess Rules!");
		System.out.println("Different from regular chess in two ways:");
		System.out.println("	1. Before the game, each player can rearrange their back lines.");
		System.out.println("		Every piece gets to be changed except for the pawns and king.");
		System.out.println("	2. During gameplay, pawns can move one space horizontally");
		System.out.println("		as well as vertically.");
		System.out.println("		Pawns still capture pieces diagonally however.");
	}
	// quit game
	public void quitGame(){
		for(int i = 0; i < 5; i++){
			System.out.println();
		}
		System.out.println("GAME OVER. (Press Control+c to exit game)");
		// quit game
	}

	// display help menu, should allow user to navigate through help
	public void display(){
		for(int i = 0; i < 5; i++){
			System.out.println();
		}
		Scanner scan = new Scanner(System.in);
		System.out.println("Ceci est la classe help!");
		while(true){
			System.out.println("Voila vos options:");
			System.out.println("1. App Info");
			System.out.println("2. Règle des échecs");
			System.out.println("3. Quiter le jeu");
			System.out.println();
			System.out.println("Entrez le num de votre choix.");
			String choice = scan.nextLine();
			if(choice.charAt(0) == '1'){
				appInfo();
				break;
			} else if(choice.charAt(0) == '2'){
				basicChessInfo();
				break;
			} else if(choice.charAt(0) == '3'){
				quitGame();
				break;
			} else if(choice.charAt(0) == '4'){
				System.out.println("This is where the player would castle.");
				//castle();
				break;
			} else if(choice.charAt(0) == '5'){
				quitGame();
				break;
			} else {
				System.out.println("That is not an option, try again.");
			}
		}
	}
}
