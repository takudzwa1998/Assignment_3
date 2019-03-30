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

	class Data{

		String date;
		String power;
		String voltage;
		
		public Data(){
			date= null;
			power=null;
			voltage=null;
		}
		
		public Data(String d, String p, String v){
			date = d;
			power=p;
			voltage=v;
		}

	}

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

		Data[] dates = new Data[table_size];
		//adjust for type data not string
		int no_of_probes=0;
		try{
			BufferedReader filereader = new BufferedReader(new FileReader("tabledata/data.txt"));
			filereader.readLine();
			while ((line = filereader.readLine())!=null){
			no_of_probes=0;
			String [] lineinfo = line.split(",");
			t=new Data(lineinfo[0], lineinfo[1], lineinfo[2]);
			if ( dates[hash_key(lineinfo[0],table_size)]==null ){dates[hash_key(lineinfo[0],table_size)]=t; System.out.println(hash_key(lineinfo[0],table_size));}
			else{
				System.out.println(hash_key(lineinfo[0],table_size)+" is taken already");
				int hash_No = hash_key(lineinfo[0],table_size);
				int i = 1;
				while(dates[hash_No]!=null){
					hash_No = (hash_No+i)%table_size;
					if(dates[hash_No]==null){dates[hash_No]=t;System.out.println("position now found after probes");break;}
					else{i++;no_of_probes++;}
				}
				
			}
			System.out.println("Number of linear probes made is "+no_of_probes);
			System.out.println(t.date+" "+t.power+" "+t.voltage);
			}
		}
		catch (Exception e){ e.printStackTrace();}
	}

	/**
	method to hash.
	@param key the string key to be hashed
	@return hashVal % tableSize returns position of array
	*/
	public static int hash_key ( String date, int tableSize ){

		int hashVal = 0;
		for( int i = 0; i < date.length(); i++ )
			hashVal += date.charAt(i);
		return hashVal % tableSize;
	}

	/**
	method for linear probing, the big one
	*/
	//public int linear_probe(int hash){}

	public static void main(String[] args){
		isPrime();
		extractData();
		makeTable(653);

	}
}
