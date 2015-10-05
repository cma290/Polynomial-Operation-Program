/**
   One term of a polynomial: (coeff, expon)
   Exponents must be non-negative.

   This class is immutable.

 */
public class Term {

    /**
       Create the term 0x^0  (coeff and exponent are zero)
    */
    public Term() {
	this(0,0);
    }

    /**
       Creates term with given coefficient and exponent.
       PRE: anExpon >= 0
     */
    public Term(double aCoeff, int anExpon) {
	assert anExpon >= 0;  // only gets checked when assertions enabled

	coeff = aCoeff;
	expon = anExpon;
    }

    public double getCoeff() {
	return coeff;
    }

    public int getExpon() {
	return expon;
    }

    /**
       Return string version of object for debugging purposes.
    */
    public String toString() {
	return "Term[coeff=" + coeff + ",expon=" + expon + "]";
    }

    private final double coeff;  // coefficient
    private final int expon;     // exponent, must be >=0

}
