package symtab;

import java.util.LinkedList;
import java.util.List;

import japa.parser.ast.type.ClassOrInterfaceType;

public class ClassOrInterfaceSymbol extends ScopedSymbol implements Type {
	
	private boolean isInterface;
	private ClassOrInterfaceSymbol extendParent;
	private List<ClassOrInterfaceSymbol> implementsList = new LinkedList<ClassOrInterfaceSymbol>();
	
	public ClassOrInterfaceSymbol(String name, Scope enclosingScope, boolean isInterface) {
		super(name, enclosingScope);
		this.isInterface = isInterface;
	}

	public boolean isInterface() {
		return isInterface;
	}

	public ClassOrInterfaceSymbol getExtendsList() {
		return extendParent;
	}
	
	public List<ClassOrInterfaceSymbol> getImplementsList() {
		return implementsList;
	}

	public void addExtend(ClassOrInterfaceSymbol parentSym) {
		this.extendParent = parentSym;
	}

	public void addImplement(ClassOrInterfaceSymbol parentSym) {
		this.implementsList.add(parentSym);
	}

	public Symbol resolveMember(String name){
		Symbol s = symbols.get(name);
		if(s!=null)
			return s;
		
		if(extendParent!=null){
			return resolveMember(extendParent.toString());
		}
		return null;
	}
	
	
}
