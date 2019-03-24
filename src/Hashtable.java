/**
Class for hashtable stuff
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Hashtable{

	public static Data t;
	static int[] primes=new int[52];
	/**
	method isprime to extract all prime numbers from 653 to 1009
	*/
	public static void isPrime(){
		int arr=0;
		boolean prime=false;
		for(int i=653;i<=1009;i++){
			for(int x=2;x<i;x++){
				if(i%x==0){prime=false;break;}
				else{prime=true;}
			}
			if(prime){
				primes[arr]=i;
				arr++;
			}
		}
	}
	/**
	method extract Data to take the needed infomation from the .csv file
	*/
	public static void extractData(){

try{

	File data = new File("tabledata/data.txt");
	PrintWriter file = new PrintWriter(data);

		try{
		
		    BufferedReader filereader = new BufferedReader(new FileReader("cleaned_data.csv"));
		    filereader.readLine();
			String line = null;
		    while ((line = filereader.readLine())!=null){
		        String [] lineinfo = line.split(",");
			file.println(lineinfo[0]+","+lineinfo[1]+","+lineinfo[3]);
			}
		}
		catch (Exception e){ e.printStackTrace();}
	file.close();
}
        catch (Exception e){ e.printStackTrace();}

}

	/**
	method checkPrime to check if given table size is a prime number within the given range
	@param n the given size of the table
	@return boolean returns a true or false	
	*/
	public static boolean checkPrime(int n){
		for (int p:primes){
			if(p==n){System.out.println(n+" IS a prime number between 653 and 1009");return true;}
		}	
		System.out.println(n+" IS NOT a prime number between 653 and 1009");
		return false;
	}
	/**
	method to make hash Table of given size
	@param table_size takes in the table size from the command line arguments	
	*/
	public static void makeTable(int table_size){
		String line = null;
		ArrayList <Data> table = new ArrayList<Data>(table_size);
		try{
			BufferedReader filereader = new BufferedReader(new FileReader("tabledata/data.txt"));
			filereader.readLine();
			while ((line = filereader.readLine())!=null){
			String [] lineinfo = line.split(",");
			t=new Data(lineinfo[0], lineinfo[1], lineinfo[2]);
			System.out.println(t.date+" "+t.power+" "+t.voltage);
			}
		}
		catch (Exception e){ e.printStackTrace();}
	}
	public static void main(String[] args){
		extractData();
		isPrime();
		checkPrime(997);
		makeTable(997);
	}


}