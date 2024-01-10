import static org.junit.jupiter.api.Assertions.*;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class KBTest {

	/**
	 * a test to see if the addSymbol method works
	 */
	@Test
	public void testAddSymbol() {
		KB kb = new KB();
		
		kb.addSymbol("t", "toothache");
		Symbol sym = kb.dict.get("t");
		
		assertNotNull(sym, "Symbol should be in the dictionary");
		assertEquals("toothache", sym.getDescrip(), "Symbol description should match");
	}
	
	/**
	 * a test to see if the addProp method works
	 */
	@Test
	public void testAddProp() {
		KB kb = new KB();
		
		Set<Symbol> prop = new HashSet<>();
		prop.add(new Symbol ("t", "toothache"));
		prop.add(new Symbol ("c", "cavity"));
	
		kb.addProp(prop);
		
		assertTrue(kb.getKb().contains(prop), "the prop should be added to the kb");
	}
	
	/**
	 * a test to see if the resolve function works
	 */
	@Test
	public void testResolve() {
		KB kb = new KB();
		
		Set<Symbol> clauseOne = new HashSet<>();
		clauseOne.add(new Symbol ("t", "toothache"));
		clauseOne.add(new Symbol ("c", "cavity"));
		
		Set<Symbol> clauseTwo = new HashSet<>();
		clauseTwo.add(new Symbol ("~c", "not cavity"));
		clauseTwo.add(new Symbol ("s", "sunny"));
		
		Set<Set<Symbol>> resolvents = kb.resolve(clauseOne, clauseTwo);
		
		assertEquals(1, resolvents.size(), "one resolvent should be made");
		
		Set<Symbol> expectedResolvent = new HashSet<>();
        expectedResolvent.add(new Symbol("t", "toothache"));
        expectedResolvent.add(new Symbol("s", "sunny"));
		
//		Set<Symbol> expectedResolvent = new HashSet<>();
//        expectedResolvent.add(new Symbol("c", "cavity"));
//        expectedResolvent.add(new Symbol("~c", "not cavity"));

        assertTrue(resolvents.contains(expectedResolvent), "Generated resolvent should match the expected resolvent");
		
	}
	
	/**
	 * a test to see if the entails method works
	 */
	
	@Test
	void testEntails() {
		KB kb = new KB();
		
		kb.addSymbol("t", "toothache");
		kb.addSymbol("c", "cavity");
		kb.addSymbol("s", "sunny");
		
		Set<Symbol> propOne = new HashSet<>();
		propOne.add(kb.dict.get("t"));
		propOne.add(kb.dict.get("c"));
		kb.addProp(propOne);
		
		Set<Symbol> propTwo = new HashSet<>();
		propTwo.add(kb.dict.get("s"));
		kb.addProp(propTwo);
		
		Set<Symbol> query = new HashSet<>();
		query.add(kb.dict.get("t"));
		
		boolean isEntailed = kb.entails(query);
		
		assertTrue(isEntailed, "Query is entailed by kb");
	}
	
	/**
	 * a test to see if the isOp method works and determines two symbols are evil twins
	 */
	@Test
	void testIsOp() {
		KB kb = new KB();
		
		Symbol one = new Symbol("t", "toothache");
		Symbol two = new Symbol("~t", "not toothache");
		
		boolean opposite = kb.isOp(one, two);
		
		assertTrue(opposite, "they should be opposite");
	}

}
