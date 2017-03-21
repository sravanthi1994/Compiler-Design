package cop5556sp17;

import static cop5556sp17.Scanner.Kind.PLUS;
import static org.junit.Assert.assertEquals;

import cop5556sp17.AST.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556sp17.Parser.SyntaxException;
import cop5556sp17.Scanner.IllegalCharException;
import cop5556sp17.Scanner.IllegalNumberException;
import cop5556sp17.Scanner.Kind;

import java.util.List;

public class ASTTest {

	static final boolean doPrint = true;
	static void show(Object s){
		if(doPrint){System.out.println(s);}
	}
	

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testFactor0() throws IllegalCharException, IllegalNumberException, SyntaxException {
		String input = "abc";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode ast = parser.expression();
		assertEquals(IdentExpression.class, ast.getClass());
	}

	@Test
	public void testFactor1() throws IllegalCharException, IllegalNumberException, SyntaxException {
		String input = "123";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode ast = parser.expression();
		assertEquals(IntLitExpression.class, ast.getClass());
	}



	@Test
	public void testBinaryExpr0() throws IllegalCharException, IllegalNumberException, SyntaxException {
		String input = "1+abc";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode ast = parser.expression();
		assertEquals(BinaryExpression.class, ast.getClass());
		BinaryExpression be = (BinaryExpression) ast;
		assertEquals(IntLitExpression.class, be.getE0().getClass());
		assertEquals(IdentExpression.class, be.getE1().getClass());
		assertEquals(PLUS, be.getOp().kind);
	}
	
	@Test
	public void testDec() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "frame gif";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode ast = parser.dec();
		assertEquals(Dec.class, ast.getClass());
		Dec dec = (Dec) ast;
		assertEquals(Kind.KW_FRAME, dec.getType().kind);
		assertEquals(Kind.IDENT, dec.getIdent().kind);		
	}
	
	@Test
	public void testChainElem() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "sravz";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode ast = parser.chainElem();
		assertEquals(IdentChain.class, ast.getClass());
	}
	
	@Test
	public void testChainElem_2() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "convolve (123, sravz)";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode ast = parser.chainElem();
		assertEquals(FilterOpChain.class, ast.getClass());
		FilterOpChain flop_obj = (FilterOpChain) ast;
		assertEquals(Tuple.class, flop_obj.getArg().getClass());
		Tuple tup = (Tuple) flop_obj.getArg();
		assertEquals("(", tup.getFirstToken().getText());
		
	}
	
	@Test
	public void test_blk() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "{ frame sravz image abc boolean me}";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode ast = parser.block();
		assertEquals(Block.class, ast.getClass());
		
		//printing and checking
		//System.out.println(ast.toString());
		
		
	}
	
	@Test
	public void test_while() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "while (11>2) { frame sravz image abc boolean me}";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode ast = parser.statement();
		assertEquals(WhileStatement.class, ast.getClass());
		WhileStatement  ws = (WhileStatement) ast;
		
		assertEquals(BinaryExpression.class, ws.getE().getClass());
		assertEquals(Block.class, ws.getB().getClass());
		
		
	}

	@Test
	public void test_assign() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = " var <- 789 ;";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		//thrown.expect(Parser.SyntaxException.class);

		Statement st = parser.statement();
		System.out.print(st.getFirstToken().getText());
		assertEquals(st.getFirstToken().getText() , "var");
	}

	
	
	@Test
	public void test_if() throws IllegalCharException, IllegalNumberException, SyntaxException{
		String input = "if (11>2) { frame sravz image abc boolean me}";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Parser parser = new Parser(scanner);
		ASTNode ast = parser.statement();
		assertEquals(IfStatement.class, ast.getClass());
		IfStatement  ifs = (IfStatement) ast;
		
		assertEquals(BinaryExpression.class, ifs.getE().getClass());
		assertEquals(Block.class, ifs.getB().getClass());
		
		
	}


	

	
	


}
