package symtab;

public class CompilationUnitSymbol extends ScopedSymbol {

	public CompilationUnitSymbol() {
		super("Compilation Unit", null);
		
		define(new BuiltInTypeSymbol("byte"));
		define(new BuiltInTypeSymbol("short"));
		define(new BuiltInTypeSymbol("int"));
		define(new BuiltInTypeSymbol("long"));
		define(new BuiltInTypeSymbol("float"));
		define(new BuiltInTypeSymbol("double"));
		define(new BuiltInTypeSymbol("boolean"));
		define(new BuiltInTypeSymbol("char"));
		define(new BuiltInTypeSymbol("void"));
		
		define(new ClassOrInterfaceSymbol("String", this, false));
	}
}
