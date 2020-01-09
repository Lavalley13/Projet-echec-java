import java.util.*;
import java.io.*;
public class Partie{

	private Plateau plateau; // current plateau
	private ArrayList<Player> players = new ArrayList<Player>(); // Listes des joueurs
	private ArrayList<String> playerNom = new ArrayList<String>(); //
	private boolean turn; // current turn
	// constructeur
	public Partie(){
		// initialize
	}

	// get plateau
	public Plateau getPlateau(){
		return plateau;
	}
	//get liste des players
	public ArrayList<Player> getPlayers(){
		return players;
	}
	// main function pour test
	public static void main(String[] args){
		// Main Menu
		for(int i = 0; i < 5; i++){
			System.out.println();
		}
		System.out.println(" Bonjours! Bienvenus dans le meilleur jeu d'échec au monde!!");
		System.out.println(" Regarder les option du menu pour débuter une partie");
		boolean mainMenu = true; // true --> Jeu toujours en cours
		mainMenuLoop:
			while(mainMenu){
				for(int i = 0; i < 5; i++){
					System.out.println();
				}
				System.out.println(" ------------------------------------");
				System.out.println("                 MENU                ");
				System.out.println(" ------------------------------------");
				System.out.println("   1. Débuter une nouvelle partie.   ");
				System.out.println(" ------------------------------------");
				System.out.println("   2. Afficher le menu d'aide");
				System.out.println(" ------------------------------------");
				System.out.println(" Veuillez tapez le numéros d'une option ");
				Scanner userInput = new Scanner(System.in);
				String inputString;
				inputString = userInput.nextLine();
				if(inputString.charAt(0) == '1'){ // nouvelle partie
					System.out.println();
					Plateau chessPlateau = new Plateau();
					Deplacement mover = new Deplacement(chessPlateau);
					Help help = new Help();
					Player blanc = new Player(0);
					System.out.println("Bonjour joueur numéros 1. Veuillez inserez votre pseudo: ");
					blanc.setNom(userInput.nextLine());
					System.out.println();
					Player noir = new Player(1);
					System.out.println("Bonjour joueur numéros 2. Veuillez inserez votre pseudo: ");
					noir.setNom(userInput.nextLine());
					System.out.println();
					System.out.println("Merci,quand vous serez pret tapez 0 pour debutez la partie");

					chessPlateau.initPlateau(); // initialize le plateau
					boolean checkMate = false; // pas de checkMate au début du jeu
					boolean turn = false; // les blancs commence la partie
					String source, destin;
					play:
						while(true){
							if(!turn){
								for(int i = 0; i < 5; i++){
									System.out.println();
								}
								chessPlateau.setTurn(0);
								if(mover.checkCheck(blanc.getCouleur())){
									System.out.println(blanc.getNom() + ", vous etes en échec, faites attention!");
								} else{
									System.out.println(blanc.getNom() + " c'est votre tour. (Blanc)");
								}
								System.out.println("Inserez les coordonnée de la piece que vous voulez bouger (ex:b6).");
								System.out.println("Tapez H pour accéder au menus.");
								source = userInput.nextLine();
								if(source.charAt(0) == 'H'){ // help menu!
									help.display();
									continue play; // Si le menu help est executer correctement, retourne a la partie
								}
								System.out.println("Inserez les coordonnée de votre destination (ex:b5).");
								System.out.println("Tapez H pour accéder au menus.");
								destin = userInput.nextLine();
								if(destin.charAt(0) == 'H'){ // help menu!
									help.display();
									continue play; // Si le menu help est executer correctement, retourne a la partie
								}
								mover.deplacement(source, destin, blanc);
								
								turn = true;
							} else { // Tour du joueurs 2(noir)
								for(int i = 0; i < 5; i++){
									System.out.println();
								}
								chessPlateau.setTurn(1);
								if(mover.checkCheck(noir.getCouleur())){
									System.out.println(noir.getNom() + ", vous etes en échec, faites attention!");
								} else{
									System.out.println(noir.getNom() + " c'est votre tour. (Noir");
								}
								System.out.println("Inserez les coordonnée de la piece que vous voulez bouger (ex:b6)");
								System.out.println("Tapez H pour accéder au menus.");
								source = userInput.nextLine();
								if(source.charAt(0) == 'H'){ // help menu!
									help.display();
									continue play; // Si le menu help est executer correctement, retourne a la partie
								}
								System.out.println("Inserez les coordonnée de votre destination (ex:b5).");
								System.out.println("Tapez H pour accéder au menus.");
								destin = userInput.nextLine();
								if(destin.charAt(0) == 'H'){ // help menu!
									help.display();
									continue play; // Si le menu help est executer correctement, retourne a la partie
								}
								mover.deplacement(source, destin, noir);
								turn = false;
							}
						}
				} else if(inputString.charAt(0) == '2'){ // display help!
					Help help = new Help();
					help.display();
				} else {
					System.out.println("Je ne reconnait pas cette commande.");
				}
			}
	}
}
