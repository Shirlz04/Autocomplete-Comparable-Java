import java.util.Arrays;
import java.util.Comparator;

// A data type that provides autocomplete functionality for a given set of 
// string and weights, using Term and BinarySearchDeluxe. 
public class Autocomplete {
    private Term[] terms;

    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) { throw new NullPointerException(); }
        this.terms = new Term[terms.length];
        for (int a = 0; a < terms.length; a++) {
            this.terms[a] = terms[a];
        }
        Arrays.sort(this.terms);
        
    }

    // All terms that start with the given prefix, in descending order of
    // weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) { throw new NullPointerException(); }   
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        Term term = new Term(prefix);
        Term[] temp = new Term[numberOfMatches(prefix)];
        int lo = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int hi = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int b = 0;
        for (int a = lo; a <= hi; a++) {
            int cmp = prefixOrder.compare(term, terms[a]);
            if (cmp == 0) { temp[b++] = terms[a]; }
        }
        Arrays.sort(temp, Term.byReverseWeightOrder());
        return temp;
    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) { throw new NullPointerException(); }
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        Term term = new Term(prefix);
        int lo = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int hi = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = 0;
        for (int a = lo; a <= hi; a++) {
            int cmp = prefixOrder.compare(term, terms[a]);
            if (cmp == 0) { count++; }
            //StdOut.println(count); 
        }
        return count;
        
        
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong(); 
            in.readChar(); 
            String query = in.readLine(); 
            terms[i] = new Term(query.trim(), weight); 
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }
    }
}
