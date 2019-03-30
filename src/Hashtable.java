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
import java.text.*;

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
	method extract Data to take the needed infomation from the .csv file and save to .txt file
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
	@param probe_Type sets the type of probing used for collsion resolution
	*/
	public static void makeTable(int table_size, String probe_Type){
		String line = null;

		Data[] dates = new Data[table_size];
		//adjust for type data not string
		int no_of_probes=0;
		double positions_Taken=0;
		try{
			BufferedReader filereader = new BufferedReader(new FileReader("tabledata/data.txt"));
			while ((line = filereader.readLine())!=null){
			no_of_probes=0;
			String [] lineinfo = line.split(",");
			t=new Data(lineinfo[0], lineinfo[1], lineinfo[2]);
			if ( dates[hash_key(lineinfo[0],table_size)]==null ){dates[hash_key(lineinfo[0],table_size)]=t; 
				positions_Taken=positions_Taken+1.00;}
			else{

				int hash_No = hash_key(lineinfo[0],table_size);
				int i = 1;
				//Implementing linear probe here
				if(probe_Type.equals("linear_Probe")){

					System.out.println("Into linear probe function");
					while(dates[hash_No]!=null){
						hash_No = (hash_No+i)%table_size;//hash func for linear probe
						if(dates[hash_No]==null){dates[hash_No]=t;
							positions_Taken=positions_Taken+1.00;break;}
						else{i++;no_of_probes++;}
					}

				}

				else{

					//here we are implementing the apparently more efficient quadratic probing method
					if(probe_Type.equals("quad_Probe")){
						System.out.println("Into quad probe function");
						while(dates[hash_No]!=null){
							hash_No = ( hash_No+(i<<1)-1 )%table_size;//hash func for quad probe
							if(dates[hash_No]==null){dates[hash_No]=t;
								positions_Taken=positions_Taken+1.00;break;}
							else{i++;no_of_probes++;
							if (no_of_probes>table_size){System.out.println("Probes exceeding table size, please choose bigger table size");
							break;							
							}
						}
					}
				}
			}}
			System.out.println("Number of probes made is "+no_of_probes);
			//System.out.println(t.date+" "+t.power+" "+t.voltage);
			}
			double load_factor = positions_Taken/(double)table_size;
			DecimalFormat lf = new DecimalFormat("###.###");
			System.out.println("Load factor for table of size "+table_size+" is "+lf.format(load_factor) );
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

	public static void chaining_Table(int table_size){
		System.out.println("Enter the chaining");		
		String line = null;
		LinkedList[] dates = new LinkedList[table_size];
		//adjust for type data not string
		int no_of_probes=0;
		double positions_Taken=0;
		try{
			BufferedReader filereader = new BufferedReader(new FileReader("tabledata/data.txt"));
			while ((line = filereader.readLine())!=null){
				no_of_probes=0;
				String [] lineinfo = line.split(",");
				t=new Data(lineinfo[0], lineinfo[1], lineinfo[2]);
				for (int lists =0;lists<table_size;lists++){				
					String keylist = "keylist"+Integer.toString(lists);
					LinkedList <Data> key_list = new LinkedList<>();
					dates[lists]=key_list;
				}
				String keylist_two = "keylist"+Integer.toString( hash_key(lineinfo[0],table_size) );			
				dates[hash_key(lineinfo[0],table_size)].add(t);
				//System.out.println( t.date+" "+t.power+" "+t.voltage );
				//System.out.println( dates[hash_key(lineinfo[0],table_size)] );
				positions_Taken=positions_Taken+1.00;
				no_of_probes++;	
			}
			for (int lists_two =0;lists_two<table_size;lists_two++){				
				for(Object o : dates[lists_two] ){
					for (Data key : o){System.out.println(key+" working");}
				}
			}
			System.out.println("Number of probes made is "+no_of_probes);
			System.out.println(t.date+" "+t.power+" "+t.voltage);

			double load_factor = positions_Taken/(double)table_size;
			DecimalFormat lf = new DecimalFormat("###.###");
			System.out.println("Load factor for table of size "+table_size+" is "+lf.format(load_factor) );
		}
		catch (Exception e){ e.printStackTrace();}
	}

	/**
	method for linear probing, the big one
	*/
	public static void main(String[] args){
		isPrime();
		extractData();
		if ( checkPrime(Integer.parseInt(args[0])) ){
			if( args[1].equals("chaining") ){chaining_Table( Integer.parseInt(args[0]) );}
			else{ makeTable( Integer.parseInt(args[0]), args[1]); }
		}
	}
}
