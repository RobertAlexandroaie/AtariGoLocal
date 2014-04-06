package atarigo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import java.util.Scanner;

public class Menu {
	public static AtariGo game;
	public static boolean isplaying=false;
 	public static void newgame(){
		Player firstPlayer;
		Player secondPlayer;
		Draw drawmethod=null;
		int size;
		Scanner input=new Scanner(System.in);
		System.out.print("Introdu numele primului jucator: ");
		String name=input.next();
		firstPlayer=new HumanPlayer(name);
		while(true){
			System.out.print("Doriti sa jucati cu calculatorul?y/n");
			String option=input.next();
			if(option.startsWith("y")){
				secondPlayer=new RandomComputerPlayer("CPU");
				break;
			} else if(option.startsWith("n")){
				System.out.print("Introdu numele celuilalt jucator: ");
				String secondname=input.next();
				secondPlayer=new HumanPlayer(secondname);
				break;
			}
		}
		while(drawmethod==null){
			try {
				System.out.print("Introdu numele fisierului de initializare: ");
				String inifile=input.next();
				drawmethod=new Draw(new FileInputStream(inifile));
				break;
			} catch (FileNotFoundException e) {
				System.out.println("Nu exista fisierul specificat.");
			} 
		}
		System.out.print("Introdu marimea tabelei: ");
		while(true){
			String option;
			option=input.next();
			if(option.matches("[3-9]|([1-9][0-9]*)")){
				size=Integer.parseInt(option);
				break;
			} else{
				System.out.println("Valoare nerecunoscuta!");
			}
		}
		game=new AtariGo(firstPlayer, secondPlayer, drawmethod, size);
		
		
	}
	/*
	 * Deserializarea unei instante a jocului
	 */
 	public static void loadgame(){
		Scanner input=new Scanner(System.in);
		while(true){
			try { 
				String option;
				System.out.print("Introduceti fisierul din care doriti sa incarcati jocul: ");
				option=input.next();
				FileInputStream fis = new FileInputStream(option);
				ObjectInputStream ois = new ObjectInputStream(fis); 
				game = (AtariGo)ois.readObject();
				ois.close(); 
				break;
			}catch(Exception e) { 
				System.out.println("Deserializare nereusita: " + e.getMessage()); 
			} 
		}
	}
	/*
	 * Serializarea jocului
	*/
	public static void savegame(){
		Scanner input=new Scanner(System.in);
		String option;
		System.out.print("Introduceti fisierul in care doriti sa salvati: ");
		option=input.next();
		try { 
			FileOutputStream fos = new FileOutputStream(option); 
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(game);
			oos.flush(); 
			oos.close(); 
		}catch(Exception e) { 
			System.out.println("Exception during serialization: " + e); 
			System.exit(0); 
		}
	}
	/*
	 * Crearea fisierului de initializare
	 */
	public static void makefile(){
		Scanner input=new Scanner(System.in);
		Properties prop = new Properties();
		String player1;
		String player2;
		String player3;
		String horizontal;
		String vertical;
		String filelocation;
		System.out.print("Numele fisierului: ");
		filelocation=input.next();
		System.out.print("Caracterul pentru primul jucator: ");
		player1=input.next();
		System.out.print("Caracterul pentru al doilea jucator: ");
		player2=input.next();
		System.out.print("Caracterul pentru spatiul liber: ");
		player3=input.next();
		System.out.print("Caracterul pentru marginea de sus: ");
		horizontal=input.next();
		System.out.print("Caracterul pentru marginea din stanga: ");
		vertical=input.next();
		try {
    		prop.setProperty("firstplayer", player1);
    		prop.setProperty("secondplayer", player2);
    		prop.setProperty("noplayer", player3);
    		prop.setProperty("horizontalline", horizontal);
    		prop.setProperty("verticalline", vertical);
    		prop.store(new FileOutputStream(filelocation), null);
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
	}
	/*
	 * Continuarea jocului
	 * actiunea depinde de tipul jocului(singleplayer,multiplayer)
	 */
	public static void playgame(){
		int result;
		game.draw();
		if(!game.singleplayer){
			while(true){
				result=game.nextMove();
				if(result==-1){
					System.out.println("Mutare incorecta!");
				} else if(result==0){
					game.draw();
					System.out.println("Mutare efectuata cu succes!");
					System.out.println("Urmeaza urmatorul jucator: ");
					game.changePlayer();
				} else if(result==1||result==2){
					if(result==1){
						System.out.println("A castigat primul jucator!");
					} else {
						System.out.println("A castigat al doilea jucator!");
					}
					isplaying=false;
					break;
				} else if(result==10)
					break;
			}
		}else{
			boolean turn=true;
			while(true){
				result=game.nextMove();
				if(result==-1){
					System.out.println("Mutare incorecta!");
				} else if(result==0){
					if(!turn){
						game.draw();
					} else{
						System.out.println("Mutare efectuata cu succes!");
						System.out.println("Urmeaza urmatorul jucator: ");
					}
					game.changePlayer();
					if(turn)
						turn=false;
					else
						turn=true;
				} else if(result==1||result==2){
					if(result==1){
						System.out.println("A castigat primul jucator!");
					} else {
						System.out.println("A castigat al doilea jucator!");
					}
					isplaying=false;
					break;
				} else if(result==10)
					break;
			}
		}
		
	}
	/*
	 * Meniul principal
	 */
	public static void main(String[] args) {
		while(true){
			Scanner input=new Scanner(System.in);
			System.out.println("=========Meniu=========");
			System.out.println("1. Joc nou");
			System.out.println("2. Incarcare joc");
			System.out.println("3. Salvare joc");
			System.out.println("4. Continuare joc");
			System.out.println("5. Creare fisier initializare");
			System.out.println("6. Iesire");
			System.out.println("=======================");
			System.out.print("     Optiune:");
			String option=input.next();
			if(option.startsWith("1")){
				newgame();
				isplaying=true;
			} else if (option.startsWith("2")){
				loadgame();
				isplaying=true;
			} else if (option.startsWith("3") && isplaying==true){
				savegame();
			} else if (option.startsWith("3") && isplaying==false){
				System.out.println("Nu exita niciun joc in desfasurare");
			} else if (option.startsWith("4") && isplaying==true){
				playgame();
			} else if (option.startsWith("4") && isplaying==false){
				System.out.println("Nu exita niciun joc in desfasurare");
			} else if (option.startsWith("5")){
				makefile();
			} else if (option.startsWith("6")){
				break;
			} else{
				System.out.println("Optiune invalida!");
			}
		}
	}

}
