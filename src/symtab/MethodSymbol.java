package symtab;

import java.util.List;

import japa.parser.ast.body.Parameter;
import japa.parser.ast.type.Type;

public class MethodSymbol extends ScopedSymbol {
	
	private final List<Parameter> parameters;
	private Type returnType;

	public MethodSymbol(String name, Scope enclosingScope, List<Parameter> parameters, Type returnType) {
		super(name, enclosingScope);
		this.parameters = parameters;
		this.returnType = returnType;
	}

	public List<Parameter> getParameters(){
		return parameters;
	}
	
	public Type getReturnType(){
		return returnType;
	}
}
