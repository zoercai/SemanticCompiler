package symtab;

import japa.parser.ast.expr.Expression;

public class VariableSymbol extends Symbol {

	private boolean isField;
	private Expression valueAssigned;

	public VariableSymbol(String name, Type type, Expression expression, boolean isField) {
		super(name, type);
		setValueAssigned(expression);
		this.isField = isField;
	}
	
	public Expression getValueAssigned(){
		return valueAssigned;
	}
	
	public void setValueAssigned(Expression expression){
		this.valueAssigned = expression;
	}
	
	public boolean isField(){
		return isField;
	}

}
