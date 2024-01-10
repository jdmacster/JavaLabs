import java.util.Set;
import java.util.HashSet;

public class Proposition {
	private Set<Set<Symbol>> andSet; // a set of sets, which holds all the sets seperated by ors. this set is seperated by ands
	
	/**
	 * constructor
	 */
	public Proposition() {
		andSet = new HashSet<>();
	}
	
	/**
	 * a method to add a subset to the andSet, which will be seperated by ors
	 * @param ors
	 */
	public void addSub(Set<Symbol> ors) {
		andSet.add(ors);
	}
	
	/**
	 * personal toString method to turn the proposition into CNF
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append("(");
		
		for (Set<Symbol> ors : andSet) { // for all the subsets of the andSet
			if (sb.length() > 1) { // if we are past the first subset, add an and in between
				sb.append(" ^ ");
			}
			
			sb.append("(");
			boolean first = true; // keeps track of whether or not this is the first clause
			for (Symbol s : ors) { // for all the symbols in the subset
				if (!first) { // if we are past the first subset, add an or
					sb.append(" V ");
				}
				
				sb.append(s);
				first = false;
			}
			
			sb.append(")");
		}
		
		sb.append(")");
		return sb.toString();
	}

	// ********************************* GETTERS AND SETTERS *************************
	public Set<Set<Symbol>> getAndSet() {
		return andSet;
	}

	public void setAndSet(Set<Set<Symbol>> andSet) {
		this.andSet = andSet;
	}
	
	
}
