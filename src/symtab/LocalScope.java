package symtab;

import java.util.HashMap;

import se701.A2SemanticsException;

public class LocalScope extends BaseScope {

	private HashMap<String, Symbol> symbols = new HashMap<String, Symbol>();
	protected Scope enclosingScope = null;
	
	public LocalScope(Scope currentScope) {
		this.enclosingScope = currentScope;
	}
	
	@Override
	public Scope getEnclosingScope() {
		return enclosingScope;
	}

	@Override
	public void define(Symbol symbol) {
		String name = symbol.getName();
		if (symbols.get(name) != null) {
			throw new A2SemanticsException("\"" + name + "\" is already defined in scope " + getScopeName());
		}
		symbols.put(name, symbol);
	}
	
	@Override
	public Symbol resolve(String name) {
		Symbol s = symbols.get(name);
		if (s != null) {
			return s;
		}

		if (enclosingScope != null) {
			return enclosingScope.resolve(name);
		}

		return null;
	}
	
	@Override
	public Symbol resolveThisLevel(String name){
		Symbol s = symbols.get(name);
		if (s != null) {
			return s;
		}
		return null;
	}
}
