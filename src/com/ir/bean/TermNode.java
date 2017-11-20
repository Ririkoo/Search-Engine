package com.ir.bean;

import java.util.ArrayList;

import com.ir.indexstruct.TokenInfo;

public class TermNode implements Comparable<TermNode>{
    
    private String string;
    private ArrayList<TokenInfo> postingList;

    public TermNode(String string){
        this.string = string;
        this.postingList = new ArrayList<>();
    }
    
    public void addIndex(TokenInfo i){
    	postingList.add(i);
    }
    
    public ArrayList<TokenInfo> getIndexes() {
        return postingList;
    }

    public void setIndexes(ArrayList indexes) {
        this.postingList = indexes;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
    
    @Override
    public String toString(){
        String s = "";
        s += "Word " + string + ":\n";
        s += "Present in ";
        if(postingList.size() > 0){
            s += postingList.toString();
        }
        else{
            s += "no documents.";
        }
        return s;
    }

    @Override
    public int compareTo(TermNode w) {
        return w.getString().compareTo(this.string);
    }
    
}