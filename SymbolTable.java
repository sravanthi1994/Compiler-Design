package cop5556sp17;



import cop5556sp17.AST.Dec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class SymbolTable {

	public class SymbolTableEntry{
		int scope;
		Dec dec;
	}

	public SymbolTable() {
		//TODO:  IMPLEMENT THIS
		enterScope();
	}
	
	int curr_scope, next_scope = 0;
	Stack stack = new Stack();
	HashMap<String,ArrayList<SymbolTableEntry>> map = new HashMap<String, ArrayList<SymbolTableEntry>>();
	//TODO  add fields

	/** 
	 * to be called when block entered
	 */
	public void enterScope(){
		curr_scope = next_scope++;
		stack.push(curr_scope);
	}
	
	
	/**
	 * leaves scope
	 */
	public void leaveScope(){
		stack.pop();
		curr_scope = (Integer) stack.peek();


	}
	
	public boolean insert(String ident, Dec dec){
		SymbolTableEntry ste = new SymbolTableEntry();
		ste.scope = curr_scope;
		ste.dec = dec;
		if(!map.containsKey(ident)){
			ArrayList<SymbolTableEntry> arrlist = new ArrayList<SymbolTableEntry>();
			arrlist.add(ste);
			map.put(ident,arrlist);
		}
		else{
			ArrayList<SymbolTableEntry> temparr = map.get(ident);
			for(SymbolTableEntry st : temparr){
				if(st.scope == curr_scope){
					return false;
				}
			}
			map.get(ident).add(0,ste);
			map.put(ident, map.get(ident));
		}
		return true;
	}
	
	public Dec lookup(String ident){
		if(map.containsKey(ident)){
			ArrayList<SymbolTableEntry> temparr = map.get(ident);
			for(SymbolTableEntry st : temparr){
				if(st.scope <= curr_scope){
					return st.dec;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		//TODO:  IMPLEMENT THIS
		StringBuilder sb = new StringBuilder();
		for(String str : map.keySet()){
			sb.append(str);
			for(SymbolTableEntry st : map.get(str)){
				sb.append(st.scope);
				sb.append(st.dec);
			}
			sb.append(" ");
		}
		return sb.toString();
	}
	
	


}
