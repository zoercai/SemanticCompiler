package symtab;

import java.util.LinkedList;
import java.util.List;

import japa.parser.ast.type.ClassOrInterfaceType;

public class ClassOrInterfaceSymbol extends ScopedSymbol implements Type {
	
	private boolean isInterface;
	private List<ClassOrInterfaceSymbol> extendsList = new LinkedList<ClassOrInterfaceSymbol>();
	private List<ClassOrInterfaceSymbol> implementsList = new LinkedList<ClassOrInterfaceSymbol>();
	
	public ClassOrInterfaceSymbol(String name, Scope enclosingScope, boolean isInterface) {
		super(name, enclosingScope);
		this.isInterface = isInterface;
	}

	public boolean isInterface() {
		return isInterface;
	}

	public List<ClassOrInterfaceSymbol> getExtendsList() {
		return extendsList;
	}
	
	public List<ClassOrInterfaceSymbol> getImplementsList() {
		return implementsList;
	}

	public void addExtend(ClassOrInterfaceSymbol parentSym) {
		this.extendsList.add(parentSym);
	}

	public void addImplement(ClassOrInterfaceSymbol parentSym) {
		this.implementsList.add(parentSym);
	}

	
	
}
