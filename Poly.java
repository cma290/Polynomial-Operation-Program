// Name:Cheng Ma	
// USC loginid:cma290
// CS 455 PA3
// Fall 2013


import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;


/**
   A polynomial. Polynomials can be added together, evaluated, and
   converted to a string form for printing.
 */
public class Poly 
{

	/**
       Creates the 0 polynomial
	 */	
	public Poly() 
	{
		this( new Term(0,0));
		//polyList = new LinkedList<Term>();
		assert isValidPoly();
	}

	/**
       Creates polynomial with single term given
	 */
	public Poly(Term term) 
	{
		polyList = new LinkedList<Term>();
		if( term.getCoeff() != 0 )
		{   //if C=0, it is the same as 0 constructor Poly()
			polyList.add(term);
		}
		assert isValidPoly();
	}
	/**
	 	Create a poly that is the copy of poly1
	 */
	public Poly(Poly poly1) 
	{ 
		polyList = new LinkedList<Term>();
		ListIterator<Term> iter = poly1.polyList.listIterator(); 
		while( iter.hasNext() )
		{
			polyList.add( iter.next() );
		}
		assert isValidPoly();
	}


	/**
       Creates polynomial from a (possibly-empty) ArrayList of terms.
       The terms in the ArrayList have no restrictions: can be in any
       order, have 0 terms, and/or duplicate exponents.
       The given ArrayList is not modified by the constructor.
	 */
	public Poly(ArrayList<Term> termList) 
	{

		Term[] tempArr = termList.toArray(new Term[termList.size()]);  // makes a copy into an array

		Arrays.sort(tempArr, new TermComparator());  // sorts in decreasing order by exponent

		//System.out.println("DEBUG: sorted array: " + Arrays.toString(tempArr));
		// you can remove this line later

		// you complete the implementation of this method . . .
		polyList = new LinkedList<Term>();		
		double tempCoeff = 0; // to store sum of coefficients when combing duplicate-expon terms
		boolean inSequence = false;

		if(termList.size() > 1)
		{
			// the index i is for tempArr use only. 
			// The only LinkdedList operation here is polyList.add(term), without index. 
			for(int i = 0; i < termList.size()-1; i++)
			{	//size-1 because we are compare item i and i+1
				if (tempArr[i].getExpon() == tempArr[i+1].getExpon())
				{
					if(!inSequence)
					{		//start of a sequence of same expon terms
						tempCoeff = tempArr[i].getCoeff() + tempArr[i+1].getCoeff();
						inSequence = true;
					}
					else
					{  				//in the middle of a sequence of same expon terms
						tempCoeff = tempCoeff + tempArr[i+1].getCoeff();
					}
					if(i == termList.size()-2 && tempCoeff != 0)
					{  //deal with the last item
						polyList.add(new Term(tempCoeff,tempArr[i].getExpon()) );
						inSequence = false;
					}
				}
				else
				{  //arr(i)!=arr(i+1) 
					if( !inSequence && tempArr[i].getCoeff()!=0)
					{	// item that is single (no duplicate expon)
						polyList.add( tempArr[i]);	
					}
					if( inSequence)
					{			//end of a sequence of same expon terms
						inSequence = false;	//reset to get ready for the next
						if( tempCoeff != 0)
						{
							polyList.add(new Term(tempCoeff,tempArr[i].getExpon()) );
						}			
					}
					if(i == termList.size()-2 && tempArr[i+1].getCoeff()!=0)
					{  //deal with the last item
						polyList.add( tempArr[i+1]);
						inSequence = false;
					}
				}
				//System.out.println("i="+i + polyList); //debug use
			}
		}
		else if(termList.size() ==1 && tempArr[0].getCoeff()!=0 )
		{
			polyList.add( tempArr[0]);
		}
		assert isValidPoly();   	

	}


	/**
       Returns the value of the poly at a given value of x.
	 */
	public double eval(double x) 
	{
		ListIterator<Term> iter = polyList.listIterator();		
		double sum = 0; // sum of terms
		while (iter.hasNext())
		{			
			Term term = iter.next();
			double termValue = term.getCoeff();  
			for( int i = 1; i <= term.getExpon(); i++)
			{
				termValue = termValue * x;  // value of a term
			}
			sum = sum + termValue ; // value of poly; 
		}
		return sum; 
	}


	/**
       Return a String version of the polynomial with the 
       following format, shown by exmaple:
       zero poly:   "0.0"
       1-term poly: "3.0x^2"
       4-term poly: "3.0x^5 + x^2 + 2.0x + 7.0"

       Poly is in a simplified form (only one term for any exponent),
       with no zero-coefficient terms, and terms are shown in
       decreasing order by exponent.
	 */
	public String toFormattedString()
	{		
		String string = "";
		if( polyList.size() == 0)
		{
			string = "0.0";
		}
		else
		{
			ListIterator<Term> iter = polyList.listIterator();
			int i=1; // count the position of iter;
			while( iter.hasNext())
			{
				Term term = iter.next();
				if( i == 1)
				{	// first term comes without "+"	
					string = string + stringOfTerm( term );			
				}
				else
				{	// terms after the first comes with "+"
					string = string + " + " + stringOfTerm( term ); 
				}
				i++;
			}
		}
		return string;        // dummy code.  just to get stub to compile
	}

	/**
 		Produce the string version of a given term
	 */
	public String stringOfTerm( Term term)
	{
		String string = "";
		if (term.getCoeff() == 1)
		{  // "1.0x" will be shown as "x"

			if( term.getExpon() == 0 )
			{
				string = term.getCoeff() + "";
			}
			if( term.getExpon() == 1 )
			{
				string = "x"; 
			}			
			if( term.getExpon() > 1 )
			{
				string = "x^" + term.getExpon();
			}			
		}

		else if (term.getCoeff() == -1 )
		{
			if( term.getExpon() == 0 )
			{
				string = term.getCoeff() + ""; // -1.0
			}
			if( term.getExpon() == 1 )
			{
				string = "-x"; 
			}
			if( term.getExpon() > 1 )
			{
				string = "-x^" + term.getExpon();
			}	
		}

		else
		{
			if( term.getExpon() == 0 )
			{
				string = term.getCoeff() + "";
			}
			if( term.getExpon() == 1 )
			{
				string = term.getCoeff() + "x"; 
			}
			if( term.getExpon() > 1 )
			{
				string = term.getCoeff() + "x^" + term.getExpon();
			}	
		}
		return string;
	}

	// **************************************************************
	//  PRIVATE METHOD(S)

	/**
       Returns true iff the poly data is in a valid state.
	 */
	private boolean isValidPoly() 
	{
		boolean valid = true;
		ListIterator<Term> iter = polyList.listIterator();

		if ( polyList.size() == 1)
		{  
			if (iter.next().getCoeff() == 0)
			{
				valid = false;
			}
		}

		if ( polyList.size() > 1 )
		{
			Term termLeft = iter.next(); // the left term when comparing two adjacent terms
			if (termLeft.getCoeff() == 0)
			{
				valid = false;
			}
			while ( iter.hasNext()) 
			{	
				Term termRight = iter.next();
				// expon should be in decreasing order, coeff should be non-zero;
				if ( ( termLeft.getExpon() < termRight.getExpon() ) || ( termRight.getCoeff() == 0 ))
				{
					valid = false;
				}
				termLeft = termRight; // the right term becomes the left term of the next comparison
			}
		}
		return valid;     // dummy code.  just to get stub to compile
	}

	/**
		Add poly2 to this poly. This poly gets changed to the result of the adding.
	 */
	public void addIn (Poly poly2)
	{

		ListIterator<Term> iter1 = polyList.listIterator(); 		
		ListIterator<Term> iter2 = poly2.polyList.listIterator();

		// pick a leftmost term each time in poly2 and insert into poly1 in an appropriate position
		// if poly2 ends first that's fine, because the result list is the modified poly1.
		// but if poly1 ends first we need to finish inserting the rest of poly2.		

		while ( iter2.hasNext() && iter1.hasNext() )
		{
			Term term1 = iter1.next();
			Term term2 = iter2.next();
			if( term2.getExpon() > term1.getExpon() )
			{
				iter1.previous();  // move to left of term1 because term2 needs to be added on the left of term1 
				iter1.add(term2);// after add, cant get .next
			}

			else if( term2.getExpon() == term1.getExpon() )
			{
				double sumCoeff = term2.getCoeff() + term1.getCoeff();

				if (sumCoeff == 0)
				{
					iter1.remove();
				}					

				else
				{
					Term sameExponTerm = new Term (sumCoeff, term1.getExpon() );
					iter1.set( sameExponTerm );
				}

				if ( iter2.hasNext())
				{	//if poly1 ends but poly2 doesn't, compare the rest term2s with the last of modified poly1 if poly1 is not empty now,
					if (iter1.hasPrevious())   
					{
						iter1.previous();
					}

				}
			}

			else if( term2.getExpon() < term1.getExpon() )
			{
				if( iter1.hasNext())
				{
					iter2.previous();  // move iter back to compare this same term2 with the next term in poly1
				}
				else
				{  
					iter1.add( term2 );
					if ( iter2.hasNext() )
					{ //poly1 ends but poly2 doesn't, compare the rest term2s with the last of modified poly1, 
						iter1.previous();			//and term2 afterward will be smaller and be added to the end of the modified poly1

					}
				}
			}
		}

		if ( polyList.size() == 0 )   // Poly1 may be empty at the beginning or after the above while loop
		{			
			while( iter2.hasNext() )
			{
				iter1.add( iter2.next() );
			}	//copy poly2 (or rest of poly2) to poly1
		}
		assert isValidPoly();
		assert poly2.isValidPoly();
	} 

	/**
	Add this poly and poly2 and return the result. This poly and poly2 keep unchanged.
	 */
	public Poly add (Poly poly2)
	{
		Poly result = new Poly (this); // copy poly itself into result
		result.addIn( poly2 );

		assert isValidPoly();
		assert poly2.isValidPoly();
		assert result.isValidPoly();
		return result;
	} 

	/**
	Return the product of this poly and poly2. This poly and poly2 keep unchanged.
	 */
	public Poly mult (Poly poly2)
	{

		Poly result = new Poly( );
		ListIterator<Term> iter1 = polyList.listIterator(); 

		while ( iter1.hasNext() )
		{

			Term term1 = iter1.next();
			Poly poly2Copy = new Poly( poly2 );
			ListIterator<Term> iter2 = poly2Copy.polyList.listIterator();

			while ( iter2.hasNext() )
			{  //term1 multi poly2
				Term term2 = iter2.next();			
				iter2.set( new Term ( term1.getCoeff() * term2.getCoeff(), term1.getExpon() + term2.getExpon() ) ); 
			}
			result.addIn( poly2Copy);			
		}
		assert isValidPoly();
		assert poly2.isValidPoly();
		assert result.isValidPoly();
		return result;

	} 

	// **************************************************************
	//  PRIVATE INSTANCE VARIABLE(S)
	private LinkedList<Term> polyList;
}


// *****************************************************************
// Helper class needed for call to Arrays.sort above  -- DO NOT CHANGE

// comparator to be used by sort in ArrayList to Poly constructor, above
class TermComparator implements Comparator<Term>
{

	// returns value < 0 if t1's exponent is > t2's exponent (i.e. t1 should come before t2),
	// value > 0 if t1's exponent is < t2's exponent (i.e., t1 should come after t2),
	// and 0 if their exponents are the same
	public int compare(Term t1, Term t2)
	{
		return t2.getExpon() - t1.getExpon();
	}
}