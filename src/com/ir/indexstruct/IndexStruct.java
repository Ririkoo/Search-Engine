package com.ir.indexstruct;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class IndexStruct {
	public static Map<Integer, String> docFile;
	public static Hashtable<String,ArrayList<TokenInfo>> dictionary ;
	
	public IndexStruct() {
		IndexStruct.docFile = new TreeMap<Integer, String>();
		IndexStruct.dictionary = new Hashtable<String,ArrayList<TokenInfo>>();
	}
	
	public IndexStruct(Map<Integer, String> docFile,
			Hashtable<String,ArrayList<TokenInfo>> dictionary) {
		IndexStruct.docFile=docFile;
		IndexStruct.dictionary=dictionary;
	}
	
	public Map<Integer, String> getDocFile() {
		return docFile;
	}

	public void setDocFile(Map<Integer, String> docFile) {
		IndexStruct.docFile = docFile;
	}

	public Hashtable<String,ArrayList<TokenInfo>> getDictionary() {
		return dictionary;
	}

	public void setDictionary(Hashtable<String,ArrayList<TokenInfo>> dictionary) {
		IndexStruct.dictionary = dictionary;
	}
	
}
