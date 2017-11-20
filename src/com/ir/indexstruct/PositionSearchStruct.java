package com.ir.indexstruct;

import java.util.ArrayList;

public class PositionSearchStruct implements Comparable<PositionSearchStruct>{
    private final int DOCID;
    private int POS1;
    private int POS2;


    public PositionSearchStruct(int doc ,int pos1,int pos2){
        this.DOCID = doc;
        this.POS1 = pos1;
        this.POS2 = pos2;
    }
    

    public int getPOS1() {
		return POS1;
	}


	public void setPOS1(int pOS1) {
		POS1 = pOS1;
	}


	public int getPOS2() {
		return POS2;
	}


	public void setPOS2(int pOS2) {
		POS2 = pOS2;
	}


	public int getDoc() {
		return DOCID;
	}


	@Override
    public String toString(){
        String s = "";
        s += "(DocID:" + DOCID + ", Pos1:" + POS1  +", Pos2:" + POS2 + ")";
        return s;
    }
    
	
    @Override
    public int compareTo(PositionSearchStruct w) {
        if(this.DOCID>w.getDoc())
            return -1;
        else if(this.DOCID<w.getDoc())
            return 1;
        else
        	return 0;
        }
}
