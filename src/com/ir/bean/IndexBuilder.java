package com.ir.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ir.indexstruct.TokenInfo;

import org.jsoup.examples.HtmlToPlainText;

public class IndexBuilder {
	
	public final static int APPEND_ONE_FREQ = 1;
	
	public static File[] CollectFiles(String filePath) {
		File file = new File(filePath);
		File[] fileList = file.listFiles();
		return fileList;
	}

	private static String ProcessFiles(String filetype, File file) throws Exception {
		String word = "";
		if (filetype.equals("pdf")) {
			PDDocument document = null;
			document = PDDocument.load(file);
			PDFTextStripper stripper = new PDFTextStripper();
			word = stripper.getText(document);
		} else if (filetype.equals("doc")) {
			InputStream iStream = new FileInputStream(file);
			WordExtractor ex = new WordExtractor(iStream);
			word = ex.getText();
			iStream.close();
		} else if (filetype.equals("xlsx") || filetype.equals("xls")) {
			boolean isOldFormat = false;
			InputStream iStream = null;
			if (filetype.equals("xls"))
				isOldFormat = true;
			Workbook workbook = null;
			if (isOldFormat) {
				iStream = new FileInputStream(file);
				workbook = new HSSFWorkbook(iStream);
			} else {
				workbook = new XSSFWorkbook(file);
			}
			Sheet sheet = workbook.getSheetAt(0);
			int rowStart = sheet.getFirstRowNum();
			int rowEnd = sheet.getLastRowNum();
			for (int i = rowStart; i <= rowEnd; i++) {
				Row row = sheet.getRow(i);
				int colNum = row.getPhysicalNumberOfCells();
				for (int j = 0; j < colNum; j++) {
					word = word + row.getCell(j).toString() + " ";
				}
			}
			if (isOldFormat)
				iStream.close();
			workbook.close();
		} else if (filetype.equals("html")){
			Document doc = Jsoup.parse(file,"UTF-8");
			word = new HtmlToPlainText().getPlainText(doc);
		}
		return word;
	}
	
	private String ProcessURL(String eachURL) throws IOException {
		String plaintext = "";
		Document doc = Jsoup.connect(eachURL).get();
		Elements ptext = doc.select("p");
		for (Element p : ptext) {
			plaintext =plaintext+"\n"+p.text();
		}
		return plaintext;
	}
	
	
	public void CreateDictionary(String filePath, Hashtable<String,ArrayList<TokenInfo>> dic,
			Map<Integer, String> docFile) throws Exception {
		Integer docIDcounter = 0;
		File[] filelist = CollectFiles(filePath);
		for (File eachFile : filelist) {
			++docIDcounter;
			docFile.put(docIDcounter, eachFile.toString());
			String fileName = eachFile.getName();
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			BufferedReader reader = null;
			// Process Different Type
			if (suffix.equals("txt"))
				reader = new BufferedReader(new FileReader(eachFile));
			else
				reader = new BufferedReader(new StringReader(ProcessFiles(suffix, eachFile)));
			String line = "";
			int pos_num=0;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.trim().split("[\\p{Punct}\\s]+");
				for (String token : tokens) {
					pos_num=pos_num+1;
					token = token.toLowerCase();
					// Checking if word is in dictionary
					if (!dic.containsKey(token)) {
						ArrayList<TokenInfo> newset = new ArrayList<TokenInfo>();
						ArrayList<Integer> poslist =  new ArrayList<Integer>();
						poslist.add(pos_num);
						newset.add(new TokenInfo(docIDcounter,1,poslist));
						dic.put(token, newset);
					} else {
						ArrayList<TokenInfo> tmpNode = dic.get(token);
						UpdateTokenInfo(tmpNode,pos_num,docIDcounter,APPEND_ONE_FREQ);
						dic.put(token, tmpNode);
					}
				}
			}
			reader.close();
		}
	}
	

	public void CreateURLDictionary(List<String> URLCollection, Hashtable<String,ArrayList<TokenInfo>> dic,
			Map<Integer, String> docFile) throws Exception {
		Integer docIDcounter = 0;
		for (String eachURL : URLCollection) {
			++docIDcounter;
			docFile.put(docIDcounter, eachURL);
			BufferedReader reader = null;
			reader = new BufferedReader(new StringReader(ProcessURL(eachURL)));
			String line = "";
			int pos_num=0;
			while ((line = reader.readLine()) != null) {
				String[] tokens = line.trim().split("[\\p{Punct}\\s]+");
				for (String token : tokens) {
					pos_num+=1;
					token = token.toLowerCase();
					// Checking if word is in dictionary
					if (!dic.containsKey(token)) {
						ArrayList<TokenInfo> newset = new ArrayList<TokenInfo>();
						ArrayList<Integer> poslist =  new ArrayList<Integer>();
						poslist.add(pos_num);
						newset.add(new TokenInfo(docIDcounter,1,poslist));
						dic.put(token, newset);
					} else {
						ArrayList<TokenInfo> tmpNode = dic.get(token);
						UpdateTokenInfo(tmpNode,pos_num,docIDcounter,APPEND_ONE_FREQ);
						dic.put(token, tmpNode);
					}
				}
			}
			reader.close();
		}
	}
	
	private void UpdateTokenInfo(ArrayList<TokenInfo> tmpNode, int line_num, int docid, int appendFreq) {
		boolean isNewDoc=true;
		for(TokenInfo info : tmpNode){
			if(info.getDoc()==docid){
				isNewDoc=false;
				info.setWordfreq(info.getWordfreq()+appendFreq);
				info.getPosition().add(line_num);
			}
		}
		if(isNewDoc){
			ArrayList<Integer> poslist =  new ArrayList<Integer>();
			poslist.add(line_num);
			tmpNode.add(new TokenInfo(docid,1,poslist));
		}
	}
}
