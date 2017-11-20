package com.ir.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.ir.indexstruct.PositionSearchStruct;
import com.ir.indexstruct.TokenInfo;

public class Search {
	public static Map<Integer, String> docFile;
	public static Hashtable<String,ArrayList<TokenInfo>> dictionary;
	public postingListAlgorithm listAlgo = new postingListAlgorithm();

	public Search(Hashtable<String,ArrayList<TokenInfo>> dictionary, Map<Integer, String> docFile) {
		Search.dictionary = dictionary;
		Search.docFile = docFile;
	}

	public List<Integer> NormalSearch(String keyword) {
		List<Integer> resList = new ArrayList<Integer>();
		ArrayList<TokenInfo> postingList= dictionary.get(keyword);
		if(postingList==null){
			return resList;
		}
		for(TokenInfo attribute : postingList) {
			  resList.add(attribute.getDoc());
		}
		Collections.sort(resList);
		return resList;
	}
	
	public ArrayList<TokenInfo> NormalPositionSearch(String keyword) {
		ArrayList<TokenInfo> resList = new ArrayList<TokenInfo>();
		ArrayList<TokenInfo> postingList= dictionary.get(keyword);
		if(postingList==null){
			return resList;
		}
//		Collections.sort(postingList);
		return postingList;
	}
	
	public List<Integer> NotNormalSearch(String keyword) {
		List<Integer> normalList= NormalSearch(keyword);
		List<Integer> resList = new ArrayList<Integer>();
		for (Integer i : docFile.keySet()) {
			if(!normalList.contains(i))
				resList.add(i);
		}
		return resList;
	}

	public List<Integer> AndSearch(String keyword1, String keyword2) {
		List<Integer> resList = new ArrayList<Integer>();
		List<Integer> firstList = NormalSearch(keyword1);
		List<Integer> secondList = NormalSearch(keyword2);
		resList = listAlgo.listIntersect(firstList, secondList);
		return resList;
	}
	
	public List<Integer> AndWithSkipSearch(String keyword1, String keyword2) {
		List<Integer> resList = new ArrayList<Integer>();
		List<Integer> firstList = NormalSearch(keyword1);
		List<Integer> secondList = NormalSearch(keyword2);
		resList = listAlgo.listIntersectSkip(firstList, secondList);
		return resList;
	}
	
	public ArrayList<PositionSearchStruct> AndPositionSearch(String keyword1, String keyword2 , int k) {
		ArrayList<PositionSearchStruct> resList = new ArrayList<PositionSearchStruct>();
		ArrayList<TokenInfo> firstList = NormalPositionSearch(keyword1);
		ArrayList<TokenInfo> secondList = NormalPositionSearch(keyword2);
		resList = listAlgo.postionalIntersect(firstList, secondList, k);
		return resList;
	}
	
	public List<Integer> OrSearch(String keyword1, String keyword2) {
		List<Integer> resList = new ArrayList<Integer>();
		List<Integer> firstList = NormalSearch(keyword1);
		List<Integer> secondList = NormalSearch(keyword2);
		resList = listAlgo.listUnion(firstList, secondList);
		return resList;
	}
	
	public List<Integer> AndNotSearch(String keyword1, String keyword2) {
		List<Integer> resList = new ArrayList<Integer>();
		List<Integer> firstList = NormalSearch(keyword1);
		List<Integer> secondList = NotNormalSearch(keyword2);
		System.out.println(firstList.toString());
		System.out.println(secondList.toString());
		resList = listAlgo.listIntersect(firstList, secondList);
		return resList;
	}
	
	public List<Integer> OrNotSearch(String keyword1, String keyword2) {
		List<Integer> resList = new ArrayList<Integer>();
		List<Integer> firstList = NormalSearch(keyword1);
		List<Integer> secondList = NotNormalSearch(keyword2);
		System.out.println(firstList.toString());
		System.out.println(secondList.toString());
		resList = listAlgo.listUnion(firstList, secondList);
		return resList;
	}
	
	public List<Integer> BiwordSearch(String keywordString) {
		String[] keyword= keywordString.split(" ");
		List<Integer> resList = new ArrayList<Integer>();
		resList=this.AndSearch(keyword[0], keyword[1]);
		return resList;
	}
	
	public List<Integer> NotBiwordSearch(String keyword) {
		List<Integer> normalList= BiwordSearch(keyword);
		List<Integer> resList = new ArrayList<Integer>();
		for (Integer i : docFile.keySet()) {
			if(!normalList.contains(i))
				resList.add(i);
		}
		return resList;
	}

	public List<Integer> AndBiwordSearch(String keyword1, String keyword2) {
		List<Integer> resList = new ArrayList<Integer>();
		List<Integer> firstList = BiwordSearch(keyword1);
		List<Integer> secondList = BiwordSearch(keyword2);
		resList = listAlgo.listIntersect(firstList, secondList);
		return resList;
	}
	
	
	public List<Integer> OrBiwordSearch(String keyword1, String keyword2) {
		List<Integer> resList = new ArrayList<Integer>();
		List<Integer> firstList = BiwordSearch(keyword1);
		List<Integer> secondList = BiwordSearch(keyword2);
		resList = listAlgo.listUnion(firstList, secondList);
		return resList;
	}
	
	public List<Integer> AndNotBiwordSearch(String keyword1, String keyword2) {
		List<Integer> resList = new ArrayList<Integer>();
		List<Integer> firstList = BiwordSearch(keyword1);
		List<Integer> secondList = NotBiwordSearch(keyword2);
		System.out.println(firstList.toString());
		System.out.println(secondList.toString());
		resList = listAlgo.listIntersect(firstList, secondList);
		return resList;
	}
	
	public List<Integer> OrNotBiwordSearch(String keyword1, String keyword2) {
		List<Integer> resList = new ArrayList<Integer>();
		List<Integer> firstList = BiwordSearch(keyword1);
		List<Integer> secondList = NotBiwordSearch(keyword2);
		System.out.println(firstList.toString());
		System.out.println(secondList.toString());
		resList = listAlgo.listUnion(firstList, secondList);
		return resList;
	}
	


}
