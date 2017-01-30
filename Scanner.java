package cop5556sp17;


import java.util.ArrayList;
import java.util.HashMap;

public class Scanner {
	/**
	 * Kind enum
	 */
	
	
	public static enum Kind {
		IDENT(""), INT_LIT(""), KW_INTEGER("integer"), KW_BOOLEAN("boolean"), 
		KW_IMAGE("image"), KW_URL("url"), KW_FILE("file"), KW_FRAME("frame"), 
		KW_WHILE("while"), KW_IF("if"), KW_TRUE("true"), KW_FALSE("false"), 
		SEMI(";"), COMMA(","), LPAREN("("), RPAREN(")"), LBRACE("{"), 
		RBRACE("}"), ARROW("->"), BARARROW("|->"), OR("|"), AND("&"), 
		EQUAL("=="), NOTEQUAL("!="), LT("<"), GT(">"), LE("<="), GE(">="), 
		PLUS("+"), MINUS("-"), TIMES("*"), DIV("/"), MOD("%"), NOT("!"), 
		ASSIGN("<-"), OP_BLUR("blur"), OP_GRAY("gray"), OP_CONVOLVE("convolve"), 
		KW_SCREENHEIGHT("screenheight"), KW_SCREENWIDTH("screenwidth"), 
		OP_WIDTH("width"), OP_HEIGHT("height"), KW_XLOC("xloc"), KW_YLOC("yloc"), 
		KW_HIDE("hide"), KW_SHOW("show"), KW_MOVE("move"), OP_SLEEP("sleep"), 
		KW_SCALE("scale"), EOF("eof");

		Kind(String text) {
			this.text = text;
		}

		final String text;

		String getText() {
			return text;
		}
	}
	
		
	
/**
 * Thrown by Scanner when an illegal character is encountered
 */
	@SuppressWarnings("serial")
	public static class IllegalCharException extends Exception {
		public IllegalCharException(String message) {
			super(message);
		}
	}
	
	/**
	 * Thrown by Scanner when an int literal is not a value that can be represented by an int.
	 */
	@SuppressWarnings("serial")
	public static class IllegalNumberException extends Exception {
	public IllegalNumberException(String message){
		super(message);
		}
	}
	
	
	

	/**
	 * Holds the line and position in the line of a token.
	 */
	static class LinePos {
		public final int line;
		public final int posInLine;

		public LinePos(int line, int posInLine) {
			super();
			this.line = line;
			this.posInLine = posInLine;
		}

		@Override
		public String toString() {
			return "LinePos [line=" + line + ", posInLine=" + posInLine + "]";
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			LinePos linePos = (LinePos) o;

			if (line != linePos.line) return false;
			return posInLine == linePos.posInLine;
		}

		@Override
		public int hashCode() {
			int result = line;
			result = 31 * result + posInLine;
			return result;
		}
	}
	
	HashMap<String, Kind> hm = new HashMap<String, Kind>();
	
	private void puts(){
		hm.put("integer", Kind.KW_INTEGER);
		hm.put("width", Kind.OP_WIDTH);
		hm.put("height", Kind.OP_HEIGHT);
		hm.put("boolean", Kind.KW_BOOLEAN);
		hm.put("image", Kind.KW_IMAGE);
		hm.put("url", Kind.KW_URL);
		hm.put("frame", Kind.KW_FRAME);
		hm.put("file", Kind.KW_FILE);
		hm.put("while", Kind.KW_WHILE);
		hm.put("if", Kind.KW_IF);
		hm.put("sleep", Kind.OP_SLEEP);
		hm.put("screenheight", Kind.KW_SCREENHEIGHT);
		hm.put("screenwidth", Kind.KW_SCREENWIDTH);
		hm.put("gray", Kind.OP_GRAY);
		hm.put("convolve", Kind.OP_CONVOLVE);
		hm.put("blur", Kind.OP_BLUR);
		hm.put("scale", Kind.KW_SCALE);
		hm.put("xloc", Kind.KW_XLOC);
		hm.put("yloc", Kind.KW_YLOC);
		hm.put("hide", Kind.KW_HIDE);
		hm.put("show", Kind.KW_SHOW);
		hm.put("move", Kind.KW_MOVE);
		hm.put("true", Kind.KW_TRUE);
		hm.put("false", Kind.KW_FALSE);
	}
	
	
	
	

	

	public class Token {
		public final Kind kind;
		public final int pos;  //position in input array
		public final int length;  
		
		
		//returns the text of this Token
		public  String getText() {
			//TODO IMPLEMENT THIS
			String s1 = chars.substring(pos, pos+length);
			return s1;
		}
		
		//returns a LinePos object representing the line and column of this Token
		LinePos getLinePos(){
			//TODO IMPLEMENT THIS
			LinePos p = null;
			int index=0;
			for(int i=0; i<linearr.size(); i++){
				if(pos>=linearr.get(i)){
					index=i;}
				else{
					break;
				}
			}

			p = new LinePos(index,pos-linearr.get(index));

			return p;

		}

		Token(Kind kind, int pos, int length) {
			this.kind = kind;
			this.pos = pos;
			this.length = length;
		}

		/** 
		 * Precondition:  kind = Kind.INT_LIT,  the text can be represented with a Java int.
		 * Note that the validity of the input should have been checked when the Token was created.
		 * So the exception should never be thrown.
		 * 
		 * @return  int value of this token, which should represent an INT_LIT
		 * @throws NumberFormatException
		 */
		public int intVal() throws NumberFormatException{
			//TODO IMPLEMENT THIS
			String s = getText();
			int foo = Integer.parseInt(s);
			
			return foo;
		}
		
	}



	

	 


	Scanner(String chars) {
		this.chars = chars;
		tokens = new ArrayList<Token>();
		line=0;
		linearr = new ArrayList<Integer>();
		linearr.add(0);
		puts();

	}
	
	
	public enum State{
		START, DIGITS, GOTEQUAL, IDENTPART,GOTLESS, GOTGREATER, GOTNOT, GOTMINUS, GOTOR, COMMENT 
	}


	
	/**
	 * Initializes Scanner object by traversing chars and adding tokens to tokens list.
	 * 
	 * @return this scanner
	 * @throws IllegalCharException
	 * @throws IllegalNumberException
	 */
	public Scanner scan() throws IllegalCharException, IllegalNumberException {
		int pos = 0; 
		//TODO IMPLEMENT THIS!!!!
		int len = chars.length();
		
		
		
		
		State state = State.START;
		int startPos = 0;
		int ch;

		while(pos<=len){
			if(pos<len){
				ch = chars.charAt(pos);

			}
			else
				ch = -1;


			switch(state){
			case START: {
				if(ch !='\n' && Character.isWhitespace(ch)){
					pos++;
				}

				else {
					if (pos < len) {
						ch = chars.charAt(pos);
					} else
						ch = -1;

					startPos = pos;
					switch (ch) {
						case -1: {
							tokens.add(new Token(Kind.EOF, pos, 0));
							pos++;
						}
						break;
						case '0': {
							tokens.add(new Token(Kind.INT_LIT, startPos, 1));
							pos++;
						}
						break;
						case '+': {
							tokens.add(new Token(Kind.PLUS, startPos, 1));
							pos++;
						}break;
						case '\n': {
							 line++;
							 linearr.add(pos+1);
							pos++;
						}
						break;
						case '*': {
							tokens.add(new Token(Kind.TIMES, startPos, 1));
							pos++;
						}
						break;
						case '=': {
							state = State.GOTEQUAL;
							pos++;
						}
						break;
						case '(': {
							tokens.add(new Token(Kind.LPAREN, startPos, 1));
							pos++;
						}
						break;
						case ')': {
							tokens.add(new Token(Kind.RPAREN, startPos, 1));
							pos++;
						}
						break;
						case '{': {
							tokens.add(new Token(Kind.LBRACE, startPos, 1));
							pos++;
						}
						break;
						case '}': {
							tokens.add(new Token(Kind.RBRACE, startPos, 1));
							pos++;
						}
						break;
						case ';': {
							tokens.add(new Token(Kind.SEMI, startPos, 1));
							pos++;
						}
						break;
						case ',': {
							tokens.add(new Token(Kind.COMMA, startPos, 1));
							pos++;
						}
						break;
						case '%': {
							tokens.add(new Token(Kind.MOD, startPos, 1));
							pos++;
						}
						break;
						case '/': {
							state = State.COMMENT;
						}
						break;
						case '&': {
							tokens.add(new Token(Kind.AND, startPos, 1));
							pos++;
						}
						break;
						case '!': {
							state = State.GOTNOT;
							pos++;
						}
						break;
						case '-': {
							state = State.GOTMINUS;
							pos++;
						}
						break;
						case '<': {
							state = State.GOTLESS;
							pos++;
						}
						break;
						case '>': {
							state = State.GOTGREATER;
							pos++;
						}
						break;
						case '|': {
							state = State.GOTOR;
							pos++;
						}
						break;
						default: {
							if (ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5' || ch == '6' || ch == '7' || ch == '8' || ch == '9') {
								state = State.DIGITS;
								pos++;
							} else if (Character.isJavaIdentifierStart(ch)) {
								state = State.IDENTPART;
								pos++;
							} else {
								throw new IllegalCharException("illegal char" + ch + "at pos" + pos);
							}
						}


					}
				}
			}break;
			
			case GOTEQUAL:{
				if(ch == '='){
					tokens.add(new Token(Kind.EQUAL, startPos, 2));
					pos++;
					state = State.START;
				}
				else{throw new IllegalCharException("illegal char"+ch+"at pos"+pos);}
			}break;
			
			case GOTGREATER:{
				if(ch == '='){
					tokens.add(new Token(Kind.GE, startPos, 2));
					pos++;
					state = State.START;
				}
				
				else{
					tokens.add(new Token(Kind.GT, startPos, 1));
					state = State.START;}
			}break;
			
			case GOTNOT:{
				if(ch == '='){
					tokens.add(new Token(Kind.NOTEQUAL, startPos, 2));
					pos++;
					state = State.START;
				}
				else{
					tokens.add(new Token(Kind.NOT, startPos, 1));
				    state = State.START;}
			}break;
			
			case GOTMINUS:{
				if(ch == '>'){
					tokens.add(new Token(Kind.ARROW, startPos, 2));
					pos++;
					state = State.START;
				}				
				else{
					tokens.add(new Token(Kind.MINUS, startPos, 1));
					state = State.START;}
			}break;
			
			case GOTLESS:{
				if(ch == '-'){
					tokens.add(new Token(Kind.ASSIGN, startPos, 2));
					pos++;
					state = State.START;
				}
				else if(ch== '='){
					tokens.add(new Token(Kind.LE, startPos, 2));
					pos++;
					state = State.START;
				}
				else{tokens.add(new Token(Kind.LT, startPos, 1));
				state = State.START;
				}
				
			}break;
			
			case GOTOR:{
				if(ch == '-'){
					pos++;
					char ch1 = chars.charAt(pos);
					if(ch1 == '>'){
						tokens.add(new Token(Kind.BARARROW, startPos, 3));
						pos++;
						state = State.START;
					}
					else{
						
						pos--;
						tokens.add(new Token(Kind.OR, startPos, 1));
						state = State.START;
						
					}
				}
				
				else{tokens.add(new Token(Kind.OR, startPos, 1));
				state = State.START;}
			}break;
			
			case DIGITS:{
				if(Character.isDigit(ch)){
					pos++;
				}
				else{
					Token t = new Token(Kind.INT_LIT,startPos, pos-startPos);
					try{t.intVal();}
					catch(NumberFormatException e){throw new IllegalNumberException(e.getMessage());}
					tokens.add(new Token(Kind.INT_LIT,startPos, pos-startPos));
					state = State.START;
				}
			}break;
			
			case IDENTPART:{
				if(Character.isJavaIdentifierPart(ch) || ch == '$' || ch=='_'){
					pos++;
				}
				else{
					 String s = chars.substring(startPos, pos);
					if(hm.containsKey(s))
						{
						tokens.add(new Token(hm.get(s),startPos, pos-startPos));
						}
					else
					{tokens.add(new Token(Kind.IDENT,startPos, pos-startPos));}
					state = State.START;
				}
			}break;
			
			case COMMENT:{
				if(ch == -1){
					tokens.add(new Token(Kind.EOF, pos, 0)); pos++;
				}
				else{ if((pos<chars.length()-1) && chars.charAt(pos+1)=='*'){
					pos++;
					while(pos<chars.length()){
						if(chars.charAt(pos) == '\n'){
							 line++;linearr.add(pos+1); pos++;
						}
						else if(chars.charAt(pos) != '*'){
							pos++;
						}
						else{
							if((pos<chars.length()-1) && chars.charAt(pos+1)=='/'){
								pos=pos+2;
								state = State.START;
								break;
							}
							else pos++;
						}
					}
					//while(chars.substring(pos, pos+2)!= "*/"){
						//a=pos+2;
						//if(a<len){
						//if(ch == '\n' || ch == '\r'){
							//line++;
							//linearr.add(pos-1);
						//}
						//pos++;
						
					//}
					//else{System.out.println("comments not closed");break;}}
					//pos=pos+2;
				}
				else{
					tokens.add(new Token(Kind.DIV, startPos, 1));
					pos++;
					state = State.START;
				}}
					
			}break;
			
			
			
			}
				
		}
		
		//tokens.add(new Token(Kind.EOF,pos,0));
		return this;  
	}
	



	final ArrayList<Token> tokens;
	final String chars;
	int tokenNum;
	int line;
	final ArrayList<Integer> linearr;

	/*
	 * Return the next token in the token list and update the state so that
	 * the next call will return the Token..  
	 */
	public Token nextToken() {
		if (tokenNum >= tokens.size())
			return null;
		return tokens.get(tokenNum++);
	}
	
	/*
	 * Return the next token in the token list without updating the state.
	 * (So the following call to next will return the same token.)
	 */
	public Token peek(){
		if (tokenNum >= tokens.size())
			return null;
		return tokens.get(tokenNum);		
	}

	

	/**
	 * Returns a LinePos object containing the line and position in line of the 
	 * given token.  
	 * 
	 * Line numbers start counting at 0
	 * 
	 * @param t
	 * @return
	 */
	public LinePos getLinePos(Token t) {
		//TODO IMPLEMENT THIS
		return t.getLinePos();
	}


}

