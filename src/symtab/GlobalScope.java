package symtab;

import java.util.HashMap;

public class GlobalScope extends BaseScope {
	
	private HashMap<String, Symbol> symbols = new HashMap<String, Symbol>();
	
	public GlobalScope(){
		define(new BuiltInTypeSymbol("byte"));
		define(new BuiltInTypeSymbol("short"));
		define(new BuiltInTypeSymbol("int"));
		define(new BuiltInTypeSymbol("long"));
		define(new BuiltInTypeSymbol("float"));
		define(new BuiltInTypeSymbol("double"));
		define(new BuiltInTypeSymbol("boolean"));
		define(new BuiltInTypeSymbol("char"));
		
		define(new ClassOrInterfaceSymbol("String", this, false));
//		define(new ClassSymbol("HashMap"));
//		
//		define(new ClassSymbol("System"));
//		define(new ClassSymbol("out"));
		
		//define(new MethodSymbol("foo", (symtab.Type)resolve("String")));
	}
	
	public String getScopeName() {
		return "Global Scope";
	}

	public void define(Symbol symbol) {
		symbols.put(symbol.getName(), symbol);
	}

	public Symbol resolve(String name) {
		return symbols.get(name);
	}
}
