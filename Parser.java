package cop5556sp17;

import cop5556sp17.Scanner.Kind;
import static cop5556sp17.Scanner.Kind.*;

import java.util.ArrayList;

import cop5556sp17.Scanner.Token;
import cop5556sp17.AST.ASTNode;
import cop5556sp17.AST.AssignmentStatement;
import cop5556sp17.AST.BinaryChain;
import cop5556sp17.AST.BinaryExpression;
import cop5556sp17.AST.Block;
import cop5556sp17.AST.BooleanLitExpression;
import cop5556sp17.AST.Chain;
import cop5556sp17.AST.ChainElem;
import cop5556sp17.AST.ConstantExpression;
import cop5556sp17.AST.Dec;
import cop5556sp17.AST.Expression;
import cop5556sp17.AST.FilterOpChain;
import cop5556sp17.AST.FrameOpChain;
import cop5556sp17.AST.IdentChain;
import cop5556sp17.AST.IdentExpression;
import cop5556sp17.AST.IdentLValue;
import cop5556sp17.AST.IfStatement;
import cop5556sp17.AST.ImageOpChain;
import cop5556sp17.AST.IntLitExpression;
import cop5556sp17.AST.ParamDec;
import cop5556sp17.AST.Program;
import cop5556sp17.AST.SleepStatement;
import cop5556sp17.AST.Statement;
import cop5556sp17.AST.Tuple;
import cop5556sp17.AST.WhileStatement;


public class Parser {

    @SuppressWarnings("serial")
    public static class SyntaxException extends Exception {
        public SyntaxException(String message) {
            super(message);
        }
    }

    Scanner scanner;
    Token t;

    Parser(Scanner scanner) {
        this.scanner = scanner;
        t = scanner.nextToken();
    }


    Program parse() throws SyntaxException {
    	Program pr;
        pr = program();
        matchEOF();
        return pr;
    }

    Expression expression() throws SyntaxException {
    	Token firstToken = t;
    	Expression e0=null,e1=null;
    	Token operator = null;
        try {
            e0 = term();
            while (t.isKind(LT) || t.isKind(LE) || t.isKind(GT) || t.isKind(GE) || t.isKind(EQUAL) || t.isKind(NOTEQUAL)) {
            	operator = t;
                consume();
                e1 = term();
                e0 = new BinaryExpression(firstToken,e0,operator,e1);
            }
            
        }
        catch (SyntaxException e){
            throw new SyntaxException("illegal character "+e.getMessage());
        }
        return e0;
    }

    Expression term() throws SyntaxException {
    	Token firstToken = t;
    	Expression e0=null,e1=null;
    	Token operator = null;
        try {
            e0 = elem();
            while (t.isKind(PLUS) || t.isKind(MINUS) || t.isKind(OR)) {
            	operator = t;
            	consume();
                e1 = elem();
                e0 = new BinaryExpression(firstToken,e0,operator,e1);
            }
            
        }
        catch (SyntaxException e){
            throw new SyntaxException("illegal character "+e.getMessage());
            }
        return e0;
        }



    Expression elem() throws SyntaxException {
    	Token firstToken = t;
    	Expression e0=null,e1=null;
    	Token operator = null;
        try {
            e0 = factor();
            while (t.isKind(TIMES) || t.isKind(DIV) || t.isKind(AND) || t.isKind(MOD)) {
            	operator = t;
                consume();
                e1 = factor();
                e0 = new BinaryExpression(firstToken,e0,operator,e1);
            }
           
        }
        catch (SyntaxException e){
            throw new SyntaxException("illegal character "+ e.getMessage());
        }
        return e0;
    }

    Expression factor() throws SyntaxException {
    	Token firstToken =t;
    	Expression e0 = null;
        Kind kind = t.kind;
        switch (kind) {
            case IDENT: {
            	e0 = new IdentExpression(firstToken);
                consume();
            }
            break;
            case INT_LIT: {
            	e0 = new IntLitExpression(firstToken);
                consume();
            }
            break;
            case KW_TRUE:
            case KW_FALSE: {
            	e0 = new BooleanLitExpression(firstToken);
                consume();
            }
            break;
            case KW_SCREENWIDTH:
            case KW_SCREENHEIGHT: {
            	e0 = new ConstantExpression(firstToken);
                consume();
            }
            break;
            case LPAREN: {
                consume();
                e0 = expression();
                match(RPAREN);
            }
            break;
            default:
                //you will want to provide a more useful error message
                throw new SyntaxException("illegal factor");
        }
        return e0;
    }


    Block block() throws SyntaxException {
        Dec d = null; Statement s0 = null; Block b0 = null;
        ArrayList<Dec> arrdec = new ArrayList<Dec>(); ArrayList<Statement> arrstat = new ArrayList<Statement>();
        Token firstToken  = t;
        if(t.isKind(LBRACE)){
            consume();
            while(t.isKind(KW_INTEGER) || t.isKind(KW_BOOLEAN) || t.isKind(KW_IMAGE) || t.isKind(KW_FRAME) || t.isKind(OP_SLEEP) || t.isKind(KW_WHILE) || t.isKind(KW_IF) || t.isKind(IDENT)|| t.isKind(OP_BLUR) || t.isKind(OP_CONVOLVE) || t.isKind(OP_GRAY) || t.isKind(KW_SHOW) ||
                    t.isKind(KW_HIDE) || t.isKind(KW_MOVE) || t.isKind(KW_XLOC) || t.isKind(KW_YLOC) || t.isKind(OP_WIDTH)
                    || t.isKind(OP_HEIGHT) || t.isKind(KW_SCALE)){
            if(t.isKind(KW_INTEGER) || t.isKind(KW_BOOLEAN) || t.isKind(KW_IMAGE) || t.isKind(KW_FRAME)){
                d = dec();
                arrdec.add(d);
            }
            if(t.isKind(OP_SLEEP) || t.isKind(KW_WHILE) || t.isKind(KW_IF) || t.isKind(IDENT)|| t.isKind(OP_BLUR) || t.isKind(OP_CONVOLVE) || t.isKind(OP_GRAY) || t.isKind(KW_SHOW) ||
                    t.isKind(KW_HIDE) || t.isKind(KW_MOVE) || t.isKind(KW_XLOC) || t.isKind(KW_YLOC) || t.isKind(OP_WIDTH)
                    || t.isKind(OP_HEIGHT) || t.isKind(KW_SCALE)){
                s0 = statement();
                arrstat.add(s0);
            }}
            match(RBRACE);
            b0 = new Block(firstToken, arrdec, arrstat);
        }
        else{
            throw new SyntaxException("Left Brace is expected");
        }
        return b0;
    }

    Program program() throws SyntaxException {
    	ParamDec pd = null, pd1 = null; Block b0 = null;
    	Program pr = null;
    	ArrayList<ParamDec> arrpd = new ArrayList<ParamDec>();
    	Token firstToken = t;
        match(IDENT);
        if(t.isKind(KW_URL) || t.isKind(KW_FILE) || t.isKind(KW_INTEGER) || t.isKind(KW_BOOLEAN)){
            pd = paramDec();
            arrpd.add(pd);
            while(t.isKind(COMMA)){
                consume();
                pd1 = paramDec();
                arrpd.add(pd1);
            }
            b0 = block();
            pr = new Program(firstToken, arrpd, b0);
        }
        else if(t.isKind(LBRACE)){
            b0 = block();
            pr = new Program(firstToken, arrpd, b0);
        }
        else{
            throw new SyntaxException("illegal token after identifier");
        }
        return pr;
    }

    ParamDec paramDec() throws SyntaxException {
    	Token firstToken  = t;
         ParamDec pd = null;
                if (t.isKind(KW_URL) || t.isKind(KW_FILE) || t.isKind(KW_INTEGER) || t.isKind(KW_BOOLEAN)) {
                    consume();
                    Token op = t;
                    pd = new ParamDec(firstToken, op);
                    match(IDENT);
                    
                }

        else{
            throw new SyntaxException("expected keyword not found");
        }
				return pd;

    }

    Dec dec() throws SyntaxException {
        Token firstToken  = t, secondToken = null;
        Dec d = null;
            if (t.isKind(KW_IMAGE) || t.isKind(KW_FRAME) || t.isKind(KW_INTEGER) || t.isKind(KW_BOOLEAN)) {
                consume();
                secondToken = t;
                match(IDENT);
                d = new Dec(firstToken, secondToken);
            }

        else{
            throw new SyntaxException("expected keyword not found");
        }
            return d;
    }

    Statement statement() throws SyntaxException {
        Kind kind = t.kind;
        Expression e0; Statement s0 = null; Block b0 = null;
        ChainElem ch = null, ch1 = null;
        Chain chain = null;
        Token firstToken = t;
        switch (kind){
            case OP_SLEEP:{
            	firstToken = t;
                consume();
                e0 = expression();
                match(SEMI);
                s0 = new SleepStatement(firstToken, e0);
            }
            break;
            case KW_WHILE:{
            	firstToken = t;
                consume();
                if(t.isKind(LPAREN)){
                    consume();
                    e0 = expression();
                    match(RPAREN);
                    b0 = block();
                    s0 = new WhileStatement(firstToken, e0, b0);
                }
                else{
                    throw new SyntaxException("Left brace is expected");
                }
            }
            break;
            case KW_IF:{
            	firstToken = t;
                consume();
                if(t.isKind(LPAREN)){
                    consume();
                    e0 = expression();
                    match(RPAREN);
                    b0 = block();
                    s0 = new IfStatement(firstToken, e0, b0);
                }
                else{
                    throw new SyntaxException("Left brace is expected");
                }
            }
            break;
            case IDENT:{
            	IdentLValue ilv = new IdentLValue(firstToken);
            	IdentChain idch = new IdentChain(firstToken);
                Token t1 = scanner.peek();
                firstToken = t;
                consume();
                if(t1.isKind(ASSIGN)){
                    consume();
                    e0 = expression();
                    match(SEMI);
                    s0 = new AssignmentStatement(firstToken, ilv, e0);
                    
                }
                else if(t1.isKind(ARROW) || t1.isKind(BARARROW) ){
                	Token tok1 = t1;
                    consume();
                    ch = chainElem();
                    chain = new BinaryChain(firstToken, idch, tok1, ch);
                    while (t.isKind(ARROW) || t.isKind(BARARROW)){
                    	Token tok2 = t;
                        consume();
                        ch1 = chainElem();
                        chain = new BinaryChain(firstToken, chain, tok2, ch1);
                    }
                    s0 = chain;
                    match(SEMI);
                }
                else
                    throw new  SyntaxException("illegal character found after identifier");
            }
            break;
            case OP_GRAY:
            case OP_CONVOLVE:
            case OP_BLUR:
            case KW_SHOW:
            case KW_HIDE:
            case KW_MOVE:
            case KW_XLOC:
            case KW_YLOC:
            case OP_WIDTH:
            case OP_HEIGHT:
            case KW_SCALE:{
                s0 = chain();
                match(SEMI);
            }
            break;
            default: throw new SyntaxException("Not a statement");

        }
        return s0;
    }

    Chain chain() throws SyntaxException {
    	ChainElem ch0 = null, ch1 = null, ch2 = null;
    	Chain chain1 = null;
    	Token firstToken = t;
        try {
            chain1 = chainElem();
            if (t.isKind(ARROW) || t.isKind(BARARROW)) {
            	Token operator =t;
            	consume();
            	ch1 = chainElem();
                chain1 = new BinaryChain(firstToken, chain1, operator, ch1);
                
                while (t.isKind(ARROW) || t.isKind(BARARROW)) {
                	operator=t;
                	consume();
                    ch2 = chainElem();
                    chain1 = new BinaryChain(firstToken, chain1, operator, ch2);
//                    consume();
                    
                }
            }
            else {
                throw new SyntaxException("expecting arrow or bararrow");
            }
            return chain1;
        }
        catch(SyntaxException e){
            throw new SyntaxException("illegal character "+e.getMessage());


    }}

    ChainElem chainElem() throws SyntaxException {
        Kind kind = t.kind;
        Tuple tuple = null;
        Token firstToken = t;
        ChainElem ch = null;
        switch(kind){
            case IDENT:{
                consume();
                ch = new IdentChain(firstToken);
            }break;
            case OP_BLUR:
            case OP_CONVOLVE:
            case OP_GRAY:{
                consume();
                tuple = arg();
                ch = new FilterOpChain(firstToken , tuple);
            }
            break;
            case KW_SHOW:
            case KW_HIDE:
            case KW_MOVE:
            case KW_XLOC:
            case KW_YLOC:{
                consume();
                tuple = arg();
                ch = new FrameOpChain(firstToken, tuple);
            }
            break;
            case OP_HEIGHT:
            case OP_WIDTH:
            case KW_SCALE:{
                consume();
                tuple = arg();
                ch = new ImageOpChain(firstToken, tuple);
            }
            break;
            default:throw new SyntaxException("illegal character : expecting an identifier or filter operator or image operator" +
                    " or frame operator ");

        }
        return ch;
    }

    Tuple arg() throws SyntaxException {
        Tuple tuple = null;
        Expression e0 = null,e1 = null;
        ArrayList<Expression> arr = new ArrayList<Expression>();
        
        Token firstToken = t;
        tuple = new Tuple(firstToken, arr);
        try{
        if(t.isKind(LPAREN)){
            consume();
            e0 = expression();
            arr.add(e0);
            while(t.isKind(COMMA)){
                consume();
                e1 = expression();
                arr.add(e1);
            }
            match(RPAREN);
            tuple = new Tuple(firstToken, arr);
        }
        }
        catch( SyntaxException e){
            throw new SyntaxException("illegal character "+ e.getMessage());
        }
        return tuple;
    }

    private Token matchEOF() throws SyntaxException {
        if (t.isKind(EOF)) {
            return t;
        }
        throw new SyntaxException("expected EOF");
    }


    private Token match(Kind kind) throws SyntaxException {
        if (t.isKind(kind)) {
            return consume();
        }
        throw new SyntaxException("saw " + t.kind + "expected " + kind);
    }

    private Token match(Kind... kinds) throws SyntaxException {
        StringBuilder sb = new StringBuilder("");
        for(Kind kind:kinds){
            sb.append(kind+",");
            if(t.isKind(kind)){
                return consume();
            }
        }
        throw new SyntaxException("encountered "+t.kind+" expected one of "+sb.toString());
    }

    private Token consume() throws SyntaxException {
        Token tmp = t;
        t = scanner.nextToken();
        return tmp;
    }

}
