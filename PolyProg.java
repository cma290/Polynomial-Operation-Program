// Name:Cheng Ma
// USC loginid:cma290
// CS 455 PA3
// Fall 2013

import java.util.Scanner;
import java.util.ArrayList;

public class PolyProg{

	private static final int NUM_OF_POLY_OBJS = 10;

	public static void main(String[] args)
	{


		ArrayList<Poly> polyList = new ArrayList<Poly>();
		for ( int i = 0; i < NUM_OF_POLY_OBJS; i++)
		{
			polyList.add ( new Poly() );	//construct 10 empty poly, poly0 to poly9
		}

		Scanner in = new Scanner(System.in);
		while ( true )
		{	//until break under "quit" command
			System.out.print("cmd>");
			String input = in.nextLine();
			ArrayList<String> splited = new ArrayList<String>(); //split input to string array list, each array element contains one single word

			int inputWordsNum = numOfWords ( input, splited);
			switch (inputWordsNum)
			{
			case 1:	// only one word of command, should be quit or help, otherwise it's illegal

				//Help
				if (splited.get(0).equalsIgnoreCase ("help"))
				{
					doHelp();
				}

				//Quit
				else if ( splited.get(0).equalsIgnoreCase ("quit") )
				{
					System.exit(0);
				}
				else
				{
					System.out.println("ERROR: Illegal command.  Type 'help' for command options.");
				}
				break;


			case 2:	//May be create, print, or eval.

				//Create
				if ( splited.get(0).equalsIgnoreCase ("create") )
				{
					if ( isValidIndex ( splited.get(1) ) )
					{	// right index type. the error messages are taken care of in the is validindex method
						doCreate( Integer.parseInt( splited.get(1) ), polyList );
					}
				}

				//Print
				else if ( splited.get(0).equalsIgnoreCase ("print") )
				{
					if ( isValidIndex ( splited.get(1) ) )
					{
						doPrint( Integer.parseInt( splited.get(1) ), polyList );
					}
				}

				//Evaluate
				else if ( splited.get(0).equalsIgnoreCase ("eval") )
				{
					if ( isValidIndex ( splited.get(1) ) )
					{
						doEval( Integer.parseInt( splited.get(1) ), polyList );
					}
				}

				else
				{
					System.out.println("ERROR: Illegal command.  Type 'help' for command options.");
				}
				break;

			case 3:  // copy, addIn

			//copy
				if ( splited.get(0).equalsIgnoreCase ("copy") )
				{
					if (isValidIndex (splited.get(1)) && isValidIndex (splited.get(2)))
					{
						doCopy (Integer.parseInt(splited.get(1)), Integer.parseInt(splited.get(2)), polyList);
					}
				}

				//addIn
				else if ( splited.get(0).equalsIgnoreCase ("addIn") )
				{
					if (isValidIndex (splited.get(1)) && isValidIndex (splited.get(2)))
					{
						doAddIn ( Integer.parseInt(splited.get(1)), Integer.parseInt(splited.get(2)), polyList );
					}
				}

				else
				{
					System.out.println("ERROR: Illegal command.  Type 'help' for command options.");
				}
				break;

			case 4:	// add, mult

				//add
				if ( splited.get(0).equalsIgnoreCase ("add") )
				{
					if ( isValidIndex (splited.get(1)) && isValidIndex (splited.get(2)) && isValidIndex (splited.get(3)) )
					{
						doAdd (Integer.parseInt(splited.get(1)), Integer.parseInt(splited.get(2)), Integer.parseInt(splited.get(3)), polyList);
					}
				}

				//mult
				else if ( splited.get(0).equalsIgnoreCase ("mult") )
				{
					if ( isValidIndex (splited.get(1)) && isValidIndex (splited.get(2)) && isValidIndex (splited.get(3)) )
					{
						doMult (Integer.parseInt(splited.get(1)), Integer.parseInt(splited.get(2)), Integer.parseInt(splited.get(3)), polyList);
					}
				}

				else
				{
					System.out.println("ERROR: Illegal command.  Type 'help' for command options.");
				}
				break;

			default:  // input number of words greater than 4, illegal command
				System.out.println("ERROR: Illegal command.  Type 'help' for command options.");
				break;

			}
			//in.close();
		}

	}

	// Helper methods

	//doCreate

	/**
	 * doCreate
	 * @param index index of the poly to operate on
	 * @param polyList array list in which poly0 to poly9 stores
	 */
	public static void doCreate(int index, ArrayList<Poly> polyList)
	{

		System.out.println("Enter a space-separated sequence of coeff-power pairs terminated by <nl>");
		Scanner in = new Scanner ( System.in );
		String input = in.nextLine(); //put user input to a string

		Scanner readInput = new Scanner ( input ); //scan the collected input string
		ArrayList <Term> termList = new ArrayList <Term> ();  // to collect right terms when scanning, then use this list to construct poly

		boolean goodData = true ;  //flag, true means data are legal by now
		while ( readInput.hasNext() && goodData)
		{  // once there is a bad datum, the loop ends

			Double coeff = 0.0 ;	 // to store coeff temporarily
			if ( readInput.hasNextDouble() )
			{		//double coeff
				coeff = readInput.nextDouble();
			}
			else	//coeff wrong
			{
				goodData = false;
				System.out.println("ERROR: wrong input type.  A term is a coefficient (double) \n       followed by an exponent (int).");  //prompt enter again?
					doCreate( index, polyList);
			}

			if ( goodData )
			{	// coeff was right
				if( readInput.hasNext() )
				{	// expon is not missing if this is the last pair
					if ( readInput.hasNextInt()  )
					{ 	//expon was right typt int
						int expon = readInput.nextInt() ;
						if ( expon < 0)
						{
							System.out.println("WARNING: negative exponent:"+ expon +". It is taken as the absolute value " );
							termList.add ( new Term ( coeff, Math.abs(expon) ) );  // absolute value of expon added
						}
						else
						{
							termList.add ( new Term ( coeff, expon ) );
						}
					}
					else
					{  		//coeff right, expon wrong
						goodData = false;
						System.out.println( "ERROR: wrong input type.  A term is a coefficient (double) \n       followed by an exponent (int)." );
						doCreate (index, polyList);
					}
				}
				else
				{  // expon missing
					System.out.println("WARNING: Missing exponent for the last pair, the last value entered was ignored");
				}
			}
		}

		if ( goodData)
		{
			polyList.set ( index, new Poly ( termList ) );
		}

	}


	//doPrint

	/**
	 * doPrint
	 * @param index index of the poly to operate on
	 * @param polyList array list in which poly0 to poly9 stores
	 */
	public static void doPrint ( int index, ArrayList<Poly> polyList)
	{
		System.out.println ( polyList.get( index ).toFormattedString() );
	}

	//doEval

	/**
	 * Prompt user to input a double number to evaluate.
	 * @param index index of the poly to operate on
	 * @param polyList array list in which poly0 to poly9 stores
	 */
	public static void doEval(int index, ArrayList<Poly> polyList)
	{
		System.out.println("Enter a floating point value for x:");
		Scanner in = new Scanner ( System.in);
		if ( in.hasNextDouble() )
		{
			System.out.println ( polyList.get( index ).eval( in.nextDouble() ) );
		}
		else
		{
			System.out.println("ERROR: Illegal command. Value to eval should be type double. Type 'help' for command options.");
		}
	}


	//doHelp

	/**
	 * Print help message for users
	 */
	public static void doHelp ()
	{
		System.out.println ( "Commands examples: create 0, create 1 create 2, ..., create 9" );
		System.out.println ( "                   print 0,  print 1, print 2, ...,  print 9" );
		System.out.println ( "                   eval 0,   eval 1,  eval 2, ...,   eval 9" );
		System.out.println ( "                   copy poly2 into poly1: copy 1 2  " );
		System.out.println ( "                   addIn " );
		System.out.println ( "                   add  " );
		System.out.println ( "                   mult  " );
		System.out.println ( "                   quit, help" );
		System.out.println ( "All commands are case insensitive." );
	}

	//doCopy

	/**
	 * copy source poly to destination poly.
	 * @param desti  index of destination poly.
	 * @param source  index of source poly.
	 * @param polyList	array list in which poly0 to poly9 stores
	 */
	public static void doCopy ( int desti, int source, ArrayList<Poly> polyList)
	{
		polyList.set(desti, polyList.get(source));
	}

	//doAddIn

	/**
	 * do AddIn, poly1 = poly1 + poly2
	 * @param index1	index of poly1.
	 * @param index2	index of poly2.
	 * @param polyList	array list in which poly0 to poly9 stores.
	 */
	public static void doAddIn ( int index1, int index2, ArrayList<Poly> polyList)
	{
		Poly poly1 =  polyList.get(index1);
		Poly poly2 =  polyList.get(index2);
		poly1.addIn(poly2);
	}

	//doAdd

	/**
	 * do Add, poly1 = poly2 + poly3
	 * @param index1	index of poly1
	 * @param index2	index of poly2
	 * @param index3	index of poly3
	 * @param polyList	array list in which poly0 to poly9 stores.
	 */
	public static void doAdd ( int index1, int index2, int index3, ArrayList<Poly> polyList)
	{
		Poly poly2 =  polyList.get(index2);
		Poly poly3 =  polyList.get(index3);
		polyList.set(index1, poly2.add(poly3));
	}

	//doMult

	/**
	 * do Mult, poly1 = poly2 * poly3
	 * @param index1	index of poly1
	 * @param index2	index of poly2
	 * @param index3	index of poly3
	 * @param polyList	array list in which poly0 to poly9 stores.
	 */
	public static void doMult ( int index1, int index2, int index3, ArrayList<Poly> polyList)
	{
		Poly poly2 =  polyList.get(index2);
		Poly poly3 =  polyList.get(index3);
		polyList.set(index1, poly2.mult(poly3) );
	}


	//numOfWords
	/**
	 * split the input string into words and add to a arraylist, return the size of the list
	 * @param string   the string to be split
	 * @param splited  arraylist of the splited words
	 * @return  the size of the arraylist
	 */
	public static int numOfWords( String string, ArrayList<String> splited )
	{

		Scanner in = new Scanner(string);
		while(in.hasNext())
		{
			splited.add ( in.next() );
		}
		return splited.size();
	}

	//isValidIndex
	/**
	 * Check whether the content of a string is a valid index, and print error messages when not valid
	 * @param str the target string
	 * @return true when it is a valid index
	 */
	public static boolean isValidIndex ( String str )
	{
		Scanner in = new Scanner (str);
		if ( in.hasNextInt() )
		{		// right index type
			int index = in.nextInt ();//index of poly, 0 to 9
			if ( index >= 0 && index <= 9)
			{
				return true;
			}
			else
			{ // not between 0~9
				System.out.println("ERROR: illegal index for a poly.  must be between 0 and 9, inclusive");
				return false;
			}
		}
		else
		{
			System.out.println("ERROR: Illegal command. Index should be type int. Type 'help' for command options.");
			return false;
		}
	}

}