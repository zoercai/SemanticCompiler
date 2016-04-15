package symtab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import japa.parser.ast.body.Parameter;
import japa.parser.ast.expr.AnnotationExpr;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.type.Type;

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
		define(new BuiltInTypeSymbol("null"));
		
		define(new ClassOrInterfaceSymbol("String", this, false));
		
		ClassOrInterfaceSymbol mapSymbol = new ClassOrInterfaceSymbol("Map", this, true);
		define(mapSymbol);
		
		ClassOrInterfaceSymbol hashMapSymbol = new ClassOrInterfaceSymbol("HashMap", this, false);
		hashMapSymbol.addImplement(mapSymbol);
		define(hashMapSymbol);

		define(new ClassOrInterfaceSymbol("int[]", this, false));
		define(new ClassOrInterfaceSymbol("Entry<Integer, Integer>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<Long, Long>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<Float, Float>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<Double, Double>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<Boolean, Boolean>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<Character, Character>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<String, String>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<Integer, String>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<Float, String>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<Character, String>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<Boolean, String>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<String, Integer>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<String, Long>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<String, Character>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<String, Boolean>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<Integer, Integer>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<Character, Integer>", this, true));
		define(new ClassOrInterfaceSymbol("Entry<Character, Integer>", this, true));

		List<Type> typeArgs = null;
		japa.parser.ast.type.Type stringType = new ClassOrInterfaceType(0, 0, null, "String", typeArgs);

		define(new MethodSymbol("entrySet", this, null, null));
		define(new MethodSymbol("getKey", this, null, stringType));
		define(new MethodSymbol("getValue", this, null, stringType));
		
		// System.out.println hard code...
		define(new ClassOrInterfaceSymbol("System", this, false));
		ClassOrInterfaceSymbol printStreamSymbol = new ClassOrInterfaceSymbol("PrintStream", this, false);
		define(printStreamSymbol);
		define(new VariableSymbol("out", printStreamSymbol, null, true, -1, -1));
		List<Parameter> parameters = new LinkedList<Parameter>(); 
		List<AnnotationExpr> annotations = null;
		parameters.add(new Parameter(0, 0, 0, annotations, (japa.parser.ast.type.Type) stringType, true, null));
		define(new MethodSymbol("println", this, parameters, null));
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
