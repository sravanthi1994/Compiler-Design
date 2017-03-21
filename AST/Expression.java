package cop5556sp17.AST;

import cop5556sp17.Scanner.Token;
import cop5556sp17.AST.Type.*;

public abstract class Expression extends ASTNode {
	
	public TypeName typename;
	
	public TypeName getTypeName(){
		return typename;
	}
	
	public void setTypeName(TypeName typename){
		this.typename = typename;
	}
	protected Expression(Token firstToken) {
		super(firstToken);
	}

	@Override
	abstract public Object visit(ASTVisitor v, Object arg) throws Exception;

}
