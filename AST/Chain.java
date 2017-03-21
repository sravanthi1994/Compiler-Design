package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;


public abstract class Chain extends Statement {
	
	public Chain(Token firstToken) {
		super(firstToken);
	}

	public Type.TypeName typename;

	public Type.TypeName getTypeName(){
		return typename;
	}

	public void setTypeName(Type.TypeName typename){
		this.typename = typename;
	}

}
