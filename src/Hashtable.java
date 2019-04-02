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
			if(p==n){return true;}
		}	
		System.out.println(n+" IS NOT a prime number between 653 and 1009, please select an appropriate number.");
		return false;
	}

	public static void Random_KeyGen(int tableSize,String filename){
		String[] keys=new String[500];
		String line = null;
		try{

			BufferedReader filereader = new BufferedReader(new FileReader("tabledata/"+filename));
			int num=0;
			while ((line = filereader.readLine())!=null){
				String [] lineinfo = line.split(",");
				keys[num]=lineinfo[0];
				num++;
			}
			List<String> strList = Arrays.asList(keys);
			Collections.shuffle( strList, new Random(tableSize) );
			for(String x: strList){System.out.println(x);}
		}
		
		catch (Exception e){ e.printStackTrace();}
		
	}
	
	/**
	method to make hash Table of given size
	@param table_size takes in the table size from the command line arguments	
	@param probe_Type sets the type of probing used for collsion resolution
	*/
	public static void makeTable(int table_size, String probe_Type, String filename, String Search_Status){
		String line = null;
		 Data[] dates = new Data[table_size];
		//adjust for type data not string
		int no_of_quad_probes=0;
		int no_of_linear_probes=0;
		double positions_Taken=0;
		try{
			BufferedReader filereader = new BufferedReader(new FileReader("tabledata/"+filename));
			while ((line = filereader.readLine())!=null){
			String [] lineinfo = line.split(",");
			t=new Data(lineinfo[0], lineinfo[1], lineinfo[2]);
			if ( dates[hash_key(lineinfo[0],table_size)]==null ){
				dates[hash_key(lineinfo[0],table_size)]=t; 
				positions_Taken=positions_Taken+1.00;}
			else{
				//LINEAR PROBE IMPLEMENTATION
				int hash_No = hash_key(lineinfo[0],table_size);
				int i = 1;
				if(probe_Type.equals("linear_Probe")){
					while(dates[hash_No]!=null){
						hash_No = hash_key_Lprobe (table_size, hash_No, i );//hash func for linear probe
						if(dates[hash_No]==null){dates[hash_No]=t;
							positions_Taken=positions_Taken+1.00;break;}
						else{i++;no_of_linear_probes++;}
					}

				}

				else{

					//QUADRATIC PROBE IMPLEMENTATION
					if(probe_Type.equals("quad_Probe")){
						while(dates[hash_No]!=null){
							hash_No = hash_key_Qprobe ( table_size, hash_No, i );//hash func for quad probe
							if(dates[hash_No]==null){dates[hash_No]=t;
								positions_Taken=positions_Taken+1.00;break;}
							else{i++;no_of_quad_probes++;
						}
					}
				}
			}}
			}
			if (no_of_quad_probes>table_size){System.out.println("Probes exceeding table size,please choose bigger table size");}
			else{
				double load_factor = positions_Taken/(double)table_size;
				DecimalFormat lf = new DecimalFormat("###.###");
				if(probe_Type.equals("quad_Probe")){System.out.print(lf.format(load_factor)+";"+no_of_quad_probes);}
				if(probe_Type.equals("linear_Probe")){System.out.print(lf.format(load_factor)+";"+no_of_linear_probes);}
			}
			if( Search_Status.equals("null") ){}
			else{
			//SEARCH CODE

			String line_two= null;
			try{
				BufferedReader filereader_two = new BufferedReader( new FileReader("GraphInfo/Rand_"+Integer.toString(table_size)+"_Key.txt") );
				int numKeys=0;
				int probe=0;
					while ((line_two = filereader_two.readLine())!=null){
						numKeys++;
						if(numKeys==401){break;}
						else{
							for(int t=0;t<table_size;t++){
							if(dates[t]==null){probe++;}else{
							if ( line_two.equals(dates[t].date) ){probe++;break;}
								else{probe++;}
							}}
						}
				}
					System.out.println(";"+probe);
			
			}
			catch (Exception e){ e.printStackTrace();}

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
		int powerNum=31;
		for( int i = 0; i < date.length(); i++ ){
			hashVal += ( powerNum*hashVal )+date.charAt(i) ;
		}
		if(hashVal<0){hashVal=-1*hashVal;}
		return hashVal%tableSize;
	}

	public static int hash_key_Lprobe ( int tableSize, int RemValue, int increment ){
		return (RemValue+increment) % tableSize;
	}

	public static int hash_key_Qprobe ( int tableSize, int RemValue, int increment ){
		return ( RemValue+(increment*increment) ) % tableSize;
	}





	public static void chaining_Table(int table_size,String filename, String Search_Status){		
		String line = null;
		LinkedList[] dates = new LinkedList[table_size];

		//adjust for type data not string
		int no_of_chain_probes=0;
		double positions_Taken=0;
		try{
			BufferedReader filereader = new BufferedReader(new FileReader("tabledata/"+filename));
			for (int lists =0;lists<table_size;lists++){				
				LinkedList <Data> key_list = new LinkedList<>();
				dates[lists]=key_list;
			}
			no_of_chain_probes=0;
			while ((line = filereader.readLine())!=null){
				String [] lineinfo = line.split(",");
				t=new Data(lineinfo[0], lineinfo[1], lineinfo[2]);		
				dates[hash_key(lineinfo[0],table_size)].add(t);
				positions_Taken=positions_Taken+1.00;	
			}
			int num_chains=0;
			for (int lists_two =0;lists_two<table_size;lists_two++){
				LinkedList<Data> dSimp = dates[lists_two];
				if (dSimp.size()>1){
				no_of_chain_probes= no_of_chain_probes +dSimp.size();				
				num_chains++;
				}
			}
			if( Search_Status.equals("null") ){}
			else{
			//SEARCH CODE
			String line_two= null;
			try{
				BufferedReader filereader_two = new BufferedReader( new FileReader("GraphInfo/Rand_"+Integer.toString(table_size)+"_Key.txt") );
				int numKeys=0;
				int probe=0;
					while ((line_two = filereader_two.readLine())!=null){
						numKeys++;
						if(numKeys==401){break;}
						else{
							for (int lists_two =0;lists_two<table_size;lists_two++){
								LinkedList<Data> dSimp = dates[lists_two];
								for(Data obj : dSimp ){if(obj.date=="null"){probe++;}
									else{
										if ( line_two.equals(obj.date) ){probe++;break;}
											else{probe++;}
									}
								}
							}
						}
			
			}
			System.out.print(probe+";");
			}
			catch (Exception e){ e.printStackTrace();}
			}
			double load_factor = positions_Taken/(double)table_size;
			DecimalFormat lf = new DecimalFormat("###.###");
			System.out.println(lf.format(load_factor)+";"+no_of_chain_probes/10);
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
			if( args[1].equals("chaining") ){chaining_Table( Integer.parseInt(args[0]), args[2], args[3] );}
			else{ makeTable( Integer.parseInt(args[0]), args[1], args[2], args[3] ); }
			if( args[1].equals("Rand") ){Random_KeyGen( Integer.parseInt(args[0]), args[2] );}
		}
	}
}
