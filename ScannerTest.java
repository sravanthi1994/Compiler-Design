package cop5556sp17;

import static cop5556sp17.Scanner.Kind.SEMI;
import static cop5556sp17.Scanner.Kind.PLUS;
import static cop5556sp17.Scanner.Kind.MINUS;
import static cop5556sp17.Scanner.Kind.OR;
import static cop5556sp17.Scanner.Kind.ARROW;
import static cop5556sp17.Scanner.Kind.GT;
import static cop5556sp17.Scanner.Kind.LT;
import static cop5556sp17.Scanner.Kind.GE;
import static cop5556sp17.Scanner.Kind.LE;
import static cop5556sp17.Scanner.Kind.EQUAL;
import static cop5556sp17.Scanner.Kind.NOTEQUAL;
import static cop5556sp17.Scanner.Kind.NOT;
import static cop5556sp17.Scanner.Kind.ASSIGN;
import static cop5556sp17.Scanner.Kind.TIMES;
import static cop5556sp17.Scanner.Kind.DIV;
import static cop5556sp17.Scanner.Kind.KW_IF;
import static cop5556sp17.Scanner.Kind.IDENT;
import static cop5556sp17.Scanner.Kind.BARARROW;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import cop5556sp17.Scanner.IllegalCharException;
import cop5556sp17.Scanner.IllegalNumberException;

public class ScannerTest {

	@Rule
    public ExpectedException thrown = ExpectedException.none();


	
	@Test
	public void testEmpty() throws IllegalCharException, IllegalNumberException {
		String input = "";
		Scanner scanner = new Scanner(input);
		scanner.scan();
	}

	@Test
	public void testSemiConcat() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = ";;;";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(SEMI, token.kind);
		assertEquals(0, token.pos);
		String text = SEMI.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		//get the next token and check its kind, position, and contents
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(SEMI, token1.kind);
		assertEquals(1, token1.pos);
		assertEquals(text.length(), token1.length);
		assertEquals(text, token1.getText());
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(SEMI, token2.kind);
		assertEquals(2, token2.pos);
		assertEquals(text.length(), token2.length);
		assertEquals(text, token2.getText());
		//check that the scanner has inserted an EOF token at the end
		Scanner.Token token3 = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF,token3.kind);
	}
	
	
	/**
	 * This test illustrates how to check that the Scanner detects errors properly. 
	 * In this test, the input contains an int literal with a value that exceeds the range of an int.
	 * The scanner should detect this and throw and IllegalNumberException.
	 * 
	 * @throws IllegalCharException
	 * @throws IllegalNumberException
	 */
	@Test
	public void testIntOverflowError() throws IllegalCharException, IllegalNumberException{
		String input = "99999999999999999";
		Scanner scanner = new Scanner(input);
		thrown.expect(IllegalNumberException.class);
		scanner.scan();		
	}
	@Test
	public void plus() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "+";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(Scanner.Kind.PLUS, token.kind);
		assertEquals(0, token.pos);
	}
	
	@Test
	public void bararrow() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "|->";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(BARARROW, token.kind);
		assertEquals(0, token.pos);
		String text = BARARROW.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
	}
	@Test
	public void greaterthan() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = ">=";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(GE, token.kind);
		assertEquals(0, token.pos);
		String text = GE.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
	}
	
	@Test
	public void lessthan() throws IllegalCharException, IllegalNumberException {
		String input = "<=";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Scanner.Token token = scanner.nextToken();
		assertEquals(LE, token.kind);
		assertEquals(0, token.pos);
		String text = LE.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
	}
	@Test
	public void assign() throws IllegalCharException, IllegalNumberException {
		String input = "<-";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Scanner.Token token = scanner.nextToken();
		assertEquals(ASSIGN, token.kind);
		assertEquals(0, token.pos);
		String text = ASSIGN.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
	}
	@Test
	public void JUSTONEEQUAL() throws IllegalCharException, IllegalNumberException{
		String input = "=";
		Scanner scanner = new Scanner(input);
		thrown.expect(IllegalCharException.class);
		scanner.scan();		
	}
	@Test
	public void MINUS() throws IllegalCharException, IllegalNumberException {
		String input = "-";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Scanner.Token token = scanner.nextToken();
		assertEquals(MINUS, token.kind);
		assertEquals(0, token.pos);
		String text = MINUS.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
	}
	@Test
	public void arrow() throws IllegalCharException, IllegalNumberException {
		String input = "->";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Scanner.Token token = scanner.nextToken();
		assertEquals(ARROW, token.kind);
		assertEquals(0, token.pos);
		String text = ARROW.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
	}
	@Test
	public void EQUALS() throws IllegalCharException, IllegalNumberException {
		String input = "==";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Scanner.Token token = scanner.nextToken();
		assertEquals(EQUAL, token.kind);
		assertEquals(0, token.pos);
		String text = EQUAL.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
	}
	@Test
	public void NOT() throws IllegalCharException, IllegalNumberException {
		String input = "!";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Scanner.Token token = scanner.nextToken();
		assertEquals(NOT, token.kind);
		assertEquals(0, token.pos);
		String text = NOT.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
	}
	@Test
	public void noTEQUAL() throws IllegalCharException, IllegalNumberException {
		String input = "!=";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Scanner.Token token = scanner.nextToken();
		assertEquals(NOTEQUAL, token.kind);
		assertEquals(0, token.pos);
		String text = NOTEQUAL.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
	}
	@Test
	public void MULTIPLE1() throws IllegalCharException, IllegalNumberException {
		//input string
		String input = "/*>=";
		//create and initialize the scanner
		Scanner scanner = new Scanner(input);
		scanner.scan();
		//get the first token and check its kind, position, and contents
		Scanner.Token token = scanner.nextToken();
		assertEquals(DIV, token.kind);
		assertEquals(0, token.pos);
		String text = DIV.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		//get the next token and check its kind, position, and contents
		Scanner.Token token1 = scanner.nextToken();
		assertEquals(TIMES, token1.kind);
		assertEquals(1, token1.pos);
		String text1 = TIMES.getText();
		assertEquals(text1.length(), token1.length);
		assertEquals(text1, token1.getText());
		Scanner.Token token2 = scanner.nextToken();
		assertEquals(GE, token2.kind);
		assertEquals(2, token2.pos);
		String text3 = GE.getText();
		assertEquals(text3.length(), token2.length);
		assertEquals(text3, token2.getText());
		//check that the scanner has inserted an EOF token at the end
		Scanner.Token token3 = scanner.nextToken();
		assertEquals(Scanner.Kind.EOF,token3.kind);
	}
	@Test
	public void IFF() throws IllegalCharException, IllegalNumberException {
		String input = "if";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Scanner.Token token = scanner.nextToken();
		assertEquals(KW_IF, token.kind);
		assertEquals(0, token.pos);
		String text = KW_IF.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
		
	}
	/*@Test
	public void digit() throws IllegalCharException, IllegalNumberException {
		String input = "265";
		Scanner scanner = new Scanner(input);
		scanner.scan();
		Scanner.Token token = scanner.nextToken();
		assertEquals(DIGIT, token.kind);
		assertEquals(0, token.pos);
		String text = DIGIT.getText();
		assertEquals(text.length(), token.length);
		assertEquals(text, token.getText());
	}*/

//TODO  more tests
	
}

