package com.ir.indexstruct;

import java.util.ArrayList;

public class TokenInfo implements Comparable<TokenInfo>{
    private final int DOCID;
    private int WORDFREQ;
    private ArrayList<Integer> POSITION = new ArrayList<Integer>();

    /**
     *  @param The DOC's ID
     *  @param The Word frequency
     *  @param The POSITION in DOC
     */
    public TokenInfo(int doc ,int wordfreq,ArrayList<Integer> position){
        this.DOCID = doc;
        this.POSITION = position;
        this.WORDFREQ = wordfreq;
    }
    

    public ArrayList<Integer> getPosition() {
        return POSITION;
    }

    public int getDoc() {
        return DOCID;
    }
    
    public int getWordfreq(){
    	return WORDFREQ;
    }
    
    public void setWordfreq(int freq){
    	this.WORDFREQ=freq;
    }
    
    @Override
    public String toString(){
        String s = "";
        s += "(DocID:" + DOCID + ", TF:" + WORDFREQ  +", Pos:" + POSITION + ")";
        return s;
    }
    
	private boolean comparePositions(TokenInfo other) {
		ArrayList<Integer> thisPos = POSITION;
		ArrayList<Integer> otherPos = other.getPosition();
		if (thisPos.size() != otherPos.size())
			return false;
		for (int i = 0; i < otherPos.size(); i++) {
			if (otherPos.get(i) != thisPos.get(i))
				return false;
		}
		return true;
	}
	
    @Override
    public int compareTo(TokenInfo w) {
        if(this.DOCID>w.getDoc())
            return -1;
        else if(this.DOCID<w.getDoc())
            return 1;
        else
        	return 0;
        }
}
