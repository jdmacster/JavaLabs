import java.util.Objects;

public class Symbol {
	
	// character and description for the symbol with simple getters and setters
	private String symbol;
	private String descrip;
	
	public Symbol (String s, String d) {
		this.symbol = s;
		this.descrip = d;
	}
	
	public boolean isNot(Symbol s) {
		if (s.getSymbol().startsWith("~")) {
			return true;
		}
		else {
			return false;
		}
	}

	// ****************************** GETTERS AND SETTERS*************************
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDescrip() {
		return descrip;
	}

	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descrip, symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Symbol other = (Symbol) obj;
		return Objects.equals(descrip, other.descrip) && Objects.equals(symbol, other.symbol);
	}
	
	
	
}
