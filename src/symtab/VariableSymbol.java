package symtab;

import japa.parser.ast.expr.Expression;

public class VariableSymbol extends Symbol {

	private boolean isField;
	private Expression valueAssigned;
	private int lineAssigned = -1;
	private int columnAssigned = -1;

	public VariableSymbol(String name, Type type, Expression expression, boolean isField, int beginLine, int beginColumn) {
		super(name, type);
		this.isField = isField;
		setValueAssigned(expression, beginLine, beginColumn);
	}
	
	public Expression getValueAssigned(){
		return valueAssigned;
	}
	
	public void setValueAssigned(Expression expression, int beginLine, int beginColumn){
		this.valueAssigned = expression;
		if((expression!=null || this.isField) && this.lineAssigned==-1){
			this.lineAssigned = beginLine;
			this.columnAssigned = beginColumn;
		}
	}
	
	public boolean isField(){
		return isField;
	}
	
	public int getLineAssigned(){
		return lineAssigned;
	}
	
	public int getColumnAssigned(){
		return columnAssigned;
	}

}
