package symtab;

public class BaseScope implements Scope  {

	@Override
	public String getScopeName() {
		return null;
	}

	@Override
	public Scope getEnclosingScope() {
		return null;
	}

	@Override
	public void define(Symbol symbol) {
		
	}

	@Override
	public Symbol resolve(String name) {
		return null;
	}

	@Override
	public Symbol resolveThisLevel(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
