import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class KB {
	public Set<Set<Symbol>> kb;
	public Map<String, Symbol> dict;
	
	/**
	 * constructor for the knowledge base
	 */
	public KB() {
		kb = new HashSet<>();
		dict = new HashMap<>();
	}
	
	/**
	 * a method to add a symbol to the knowledge base
	 * @param s - a string symbol that will represent the symbol
	 * @param d - a string description for the symbol
	 */
	public void addSymbol(String s, String d) {
		Symbol sym = new Symbol(s, d);
		dict.put(s, sym);
	}
	
	/**
	 * a method to add a proposition to the knowledge base
	 * @param s - a set of symbols, which represents a proposition
	 */
	public void addProp(Set<Symbol> s) {
		kb.add(s);
	}
	
	/**
	 * a method to return a boolean whether or not the knowledge base contains
	 * a query
	 * @param query - what we are searching for in the kb
	 * @return
	 */
	public boolean entails(Set<Symbol> query) {
		Set<Set<Symbol>> tempKb = new HashSet<>(kb); // a temp set to not mess with the original
		tempKb.add(query);
		
		while(true) { // go until we go through all the clauses
			Set<Set<Symbol>> tempClauses = new HashSet<>();
			List<Set<Symbol>> kbList = new ArrayList<>(tempKb);
			
			for (int i = 0; i < kbList.size(); i++) { // for each pair in the kb
				for (int j = i + 1; j < kbList.size(); j++) {
					Set<Set<Symbol>> resolvents = resolve(kbList.get(i), kbList.get(j)); // use the resolve method to get the resolvents
					
					if (resolvents.isEmpty()) { // if it is ever empty, then the prop is included
						return true;
					}
					
					tempClauses.addAll(resolvents); // add the resolvents
				}
			}
			
			if (tempKb.containsAll(tempClauses)) { // if not, then the prop is not entailed
				return false;
			}
			
			tempKb.addAll(tempClauses); // add the clauses to the kb
		}
	}
	
	/**
	 * a method to resolve two propositions and return the potential resolvents
	 * @param one - first prop
	 * @param two - second prop
	 * @return
	 */
	public Set<Set<Symbol>> resolve(Set<Symbol> one, Set<Symbol> two) {
		Set<Set<Symbol>> resolvents = new HashSet<>(); // temp set to store resolvents
		for (Symbol symOne : one) { // for each symbol in the props
			for (Symbol symTwo : two) {
				if (isOp(symOne, symTwo)) { // if either symbol has a opposite to pair with using the is op method
					Set<Symbol> r = new HashSet<>(); // make a new temp hashset
					r.addAll(one); // add the pair
					r.addAll(two);
					r.remove(symOne); // remove the evil twins
					r.remove(symTwo);
					
					resolvents.add(r); // add to the resolvents
				}
			}
		}
		
		return resolvents;
	}
	
	/**
	 * a method to check if two symbols are evil twins and returns a boolean result
	 * @param one - the two symbols we are checking
	 * @param two
	 * @return
	 */
	public boolean isOp(Symbol one, Symbol two) {
		return one.getSymbol().equals("~" + two.getSymbol()) || two.getSymbol().equals("~" + one.getSymbol()); // im so sorry for this but it was the simplest way i could think to do it
	}
	
	/**
	 * a method to add a clause to the kb
	 * @param clause - a clause to add to the kb
	 */
	public void addClause(Set<String> clause) {
		Set<Symbol> clauses = new HashSet<>();
		for (String s : clause) {
			if (dict.containsKey(s)) {
				clauses.add(dict.get(s));
			}
			else {
				System.err.println("not found in dictionary: "+ s);
			}
		}
		
		kb.add(clauses);
	}
	
	/**
	 * a method to print the kb
	 */
	public void printKb() {
		for (Set<Symbol> clause : kb) { // for all the clauses in the kb
			System.out.print("{");
			boolean first = true; // keeps track if its the first symbol in the clause
			for (Symbol s : clause) { // for all the symbols in the clause
				if (!first) { // if its not the first, add an or
					System.out.print(" V ");
				}
				System.out.print(s);
				first = false;
			}
			
			System.out.println("}");
		}
	}

	// ******************************** GETTERS AND SETTERS **************************
	public Set<Set<Symbol>> getKb() {
		return kb;
	}

	public void setKb(Set<Set<Symbol>> kb) {
		this.kb = kb;
	}

	public Map<String, Symbol> getDict() {
		return dict;
	}

	public void setDict(Map<String, Symbol> dict) {
		this.dict = dict;
	}
	
	
	
	
}
