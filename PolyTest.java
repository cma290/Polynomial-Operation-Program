// Name:Cheng Ma	
// USC loginid:cma290
// CS 455 PA3
// Fall 2013

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

public class PolyTest{

	public static void main(String[] args) {
		//p1
		ArrayList<Term> terms= new ArrayList<Term>(){{ 
			add( new Term(3,3)); 
			add( new Term(2,1)); 
			add( new Term(7,0)); 
			add( new Term(0,0));}};

			System.out.println(terms.size());

			//p2
			ArrayList<Term> terms2= new ArrayList<Term>(){{
				add( new Term(1,5)); 
				add( new Term(-3,3)); 
				add( new Term(5,0)); 
				add( new Term(0,0));}};

				Poly p1=new Poly( terms  );
				Poly p2=new Poly( terms2  );
				System.out.println( "p1= "+p1.toFormattedString());
				System.out.println( "p2= "+p2.toFormattedString());
											
				Poly p3 = p1.add(p2);
				System.out.println("p1.add(p2):" + p3.toFormattedString() );				
				
//				p1.addIn(p1);				
//				System.out.println("p1.addIn(p1);" + p1.toFormattedString() );				
//				
//				p1.addIn(p2);				
//				System.out.println("p1.addIn(p2) " + p1.toFormattedString() );
//				
//				Poly p4 = p1.mult(p2);
//				System.out.println("p1 * p2= " + p4.toFormattedString() );	

	}
}