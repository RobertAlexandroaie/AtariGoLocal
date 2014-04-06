package atarigo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

public class Draw implements Serializable{
	private static final long serialVersionUID = 1L;
	int[][] table;
	char firstPlayer;
	char secondPlayer;
	char noPlayer;
	char horizontalline;
	char verticalline;
	Draw(FileInputStream inifile){
		Properties ini = new Properties();
		try {
			ini.load(inifile);
			firstPlayer=(ini.getProperty("firstplayer","x")).charAt(0);
			secondPlayer=(ini.getProperty("secondplayer","0")).charAt(0);
			noPlayer= (ini.getProperty("noplayer","-")).charAt(0);
			horizontalline= (ini.getProperty("horizontalline","-")).charAt(0);
			verticalline= (ini.getProperty("verticalline","|")).charAt(0);
		} catch (IOException ex) {
 		ex.printStackTrace();
		}
	}
	public void associateTable(int[][] newtable){
		table=newtable;
	}
/*	public void ilustrate2(){
		int i,j;
		System.out.print("  ");
		for(i=0;i<table.length;i++){
			System.out.print(i+1);
			if(i<9){
				System.out.print(" ");
			}
		}
		System.out.println();
		for(i=0;i<table.length;i++){
			System.out.print(i+1);
			if(i<9){
				System.out.print(" ");
			}
			for(j=0;j<table[0].length;j++){
				if(table[i][j]==0){
					System.out.print(noPlayer);
				} else if (table[i][j]==1){
					System.out.print(firstPlayer);
				} else if (table[i][j]==2){
					System.out.print(secondPlayer);
				}
				System.out.print(" ");
				if(j>=9){
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
*/
	/*
	 * Desenarea starii jocului
	 * In loc de valorile 0,1,2 se pun caracterele date prin fisierul de initializare
	 */
	public void ilustrate(){
		int i,j;
		System.out.print("   ");
		for(i=0;i<table.length;i++){
			System.out.print(i+1);
			if(i<9){
				System.out.print(" ");
			}
		}
		System.out.println();
		System.out.print("   ");
		for(i=0;i<table.length;i++){
			System.out.print(horizontalline);
			System.out.print(horizontalline);
		}
		System.out.println();
		
		
		for(i=0;i<table.length;i++){
			System.out.print(i+1);
			if(i<9){
				System.out.print(" ");
			}
			System.out.print(verticalline);
			for(j=0;j<table[0].length;j++){
				if(table[i][j]==0){
					System.out.print(noPlayer);
				} else if (table[i][j]==1){
					System.out.print(firstPlayer);
				} else if (table[i][j]==2){
					System.out.print(secondPlayer);
				}
				System.out.print(" ");
				if(j>=9){
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
}
