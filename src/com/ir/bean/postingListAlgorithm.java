package com.ir.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ir.indexstruct.PositionSearchStruct;
import com.ir.indexstruct.TokenInfo;


public class postingListAlgorithm {

	public postingListAlgorithm() {

	}

	public List<Integer> listIntersect(List<Integer> firList, List<Integer> secList) {
		List<Integer> resList = new ArrayList<Integer>();
		int p1 = 0, p2 = 0;
		while (p1 < firList.size() && p2 < secList.size()) {
			if (firList.get(p1) == secList.get(p2)) {
				resList.add(firList.get(p1));
				p1++;
				p2++;
			} else if (firList.get(p1) < secList.get(p2))
				p1++;
			else
				p2++;
		}
		return resList;
	}

	public List<Integer> listUnion(List<Integer> firList, List<Integer> secList) {
		List<Integer> resList = new ArrayList<Integer>();
		if (firList == null || firList.size() == 0) {
			resList = secList;
		} else if (secList == null || secList.size() == 0) {
			resList = firList;
		} else {
			for (int i = 0; i < firList.size(); i++) {
				boolean isExist = false;
				for (int j = 0; j < secList.size(); j++) {
					if (firList.get(i).equals(secList.get(j))) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					resList.add(firList.get(i));
				}
			}

			for (int k = 0; k < secList.size(); k++) {
				resList.add(secList.get(k));
			}
		}
		return resList;
	}

	public List<Integer> listIntersectSkip(List<Integer> firList, List<Integer> secList) {
		List<Integer> resList = new ArrayList<Integer>();
		int alen = firList.size();
		int blen = secList.size();
		int max_len = alen > blen ? alen : blen;
		int p1 = 0, p2 = 0;
		int skipPointNum = (int) Math.floor(Math.sqrt(max_len)) + 1;
		// System.out.println(skipPointNum+" "+max_len+" "+alen+" "+blen+" ");
		while (p1 < alen && p2 < blen) {
			if (firList.get(p1) == secList.get(p2)) {
				resList.add(firList.get(p1));
				p1++;
				p2++;
			} else if (firList.get(p1) < secList.get(p2)) {
				if ((p1 + skipPointNum) < alen && firList.get(p2 + skipPointNum) <= secList.get(p2))
					p1 = p1 + skipPointNum;
				else
					p1++;

			} else {
				if ((p2 + skipPointNum) < blen && firList.get(p1) >= secList.get(p2 + skipPointNum))
					p2 = p2 + skipPointNum;
				else
					p2++;
			}
		}
		return resList;
	}
	
	public ArrayList<PositionSearchStruct> postionalIntersect(ArrayList<TokenInfo> p1, ArrayList<TokenInfo> p2,
			int k) {
		ArrayList<PositionSearchStruct> answer = new ArrayList<PositionSearchStruct>();
		for (int i = 0, j = 0; i < p1.size() && j < p2.size();) {
			int p1ID = p1.get(i).getDoc();
			int p2ID = p2.get(j).getDoc();
			if (p1ID == p2ID) {
//				System.out.println("In Same Doc:"+p1ID+" k:"+k);
				List<Integer> positions = new ArrayList<Integer>();
				List<Integer> pp1 = p1.get(i).getPosition();
				List<Integer> pp2 = p2.get(j).getPosition();
//				System.out.println("key1 pos:"+pp1.toString());
//				System.out.println("key2 pos:"+pp2.toString());
				for (int ii = 0, jj = 0; ii < pp1.size() && jj < pp2.size();) {
					int positionPP1 = pp1.get(ii);
					int positionPP2 = pp2.get(jj);
					if (Math.abs(positionPP1 - positionPP2) <= k) {
//						System.out.println("pos < k :"+positionPP1+"-"+positionPP2);
						positions.add(positionPP2);
						jj++;
					} else if (positionPP2 > positionPP1) {
						break;
					}
					while (!positions.isEmpty() && Math.abs(positions.get(0) - positionPP1) > k){
						positions.remove(0);
					}
					for(int each=0;each<positions.size();each++){
						int ps=positions.get(each);
						PositionSearchStruct e = new PositionSearchStruct(p1ID,positionPP1,ps);
						answer.add(e);
					}
					ii++;
				}
				i++;
				j++;
			} else if (p1ID < p2ID) {
				i++;
			} else {
				j++;
			}
		}
		return answer;
	}

	public List<Integer> listMergebyHashSet(List<Integer> firList, List<Integer> secList) {
		Set<Integer> set = new HashSet<Integer>(firList);
		set.addAll(secList);
		return new ArrayList<Integer>(set);
	}

}
