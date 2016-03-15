package com.ir.dao.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import jeasy.analysis.MMAnalyzer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.TermVector;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.LockObtainFailedException;

import com.ir.dao.preprocess.SourcePreprocess;

public class IR_IndexProcessor {

	//private ;
	private IndexWriter writer;
	private MMAnalyzer analyzer;
	private int N;
	public IR_IndexProcessor(String indexStorePath) throws CorruptIndexException, LockObtainFailedException, IOException{
		analyzer=new MMAnalyzer();
		writer=new IndexWriter(indexStorePath,analyzer,true);
		writer.setUseCompoundFile(false);
		N = 0;
	}
	//ȡ����Ҫ�����������ļ�����
	public void createIndex(String inputDir) throws CorruptIndexException, LockObtainFailedException, IOException{
	    
		File filesDir=new File(inputDir);
		//ȡ����Ҫ�����������ļ�����
		File[] files=filesDir.listFiles();
		for(int i=0;i<files.length;i++){
			if(files[i].isDirectory()){   //�����һ��Ŀ¼	
				String dir=files[i].getPath();
				createIndex(dir);
			}
			else{
			createFileIndex(files[i],writer,analyzer);
			}
		
		}
		
		writer.flush();
	}
	public void close() throws CorruptIndexException, IOException {
		writer.close();
	}
	//������Ϊһ���������ļ���������
	public void createFileIndex(File file,IndexWriter writer,MMAnalyzer analyzer) throws CorruptIndexException, IOException{
		
//		N++;
//		if(N != 15)
//			return;
//		else N = 0;
		
		
		
		String fileName=file.getName();
		String[] news=new String[3];
		news=loadFile(file);
		//�ж��ļ��Ƿ�Ϊtxt����
		if(fileName.substring(fileName.lastIndexOf('.')).equals(".txt")){
			//����һ���µ�document
			Document doc=new Document();
			//Ϊ�ļ�������field
			Field field=new Field("fileName",file.getName(),Field.Store.YES,
					Field.Index.TOKENIZED);
			doc.add(field);
			//ΪURL����һ��field
			field=new Field("url",news[0],Field.Store.YES,Field.Index.UN_TOKENIZED);
			doc.add(field);
			//ΪTitle����һ��field
			field=new Field("title",news[1],Field.Store.YES,Field.Index.TOKENIZED, TermVector.YES);
			doc.add(field);
			//ΪContent����һ��field
			field=new Field("content",news[2],Field.Store.YES,Field.Index.TOKENIZED, TermVector.YES);
			doc.add(field);
			
			field = new Field("date",ExtractTime.extractTime(news[0]),Field.Store.YES,Field.Index.UN_TOKENIZED);
			doc.add(field);
			
			field = new Field("heat",ExtractTime.random(),Field.Store.YES,Field.Index.UN_TOKENIZED);
			doc.add(field);
			//��document���뵽IndexWriter��
			writer.addDocument(doc);
			//��������ִʵĽ��
		//	System.out.println(analyzer.segment(news[1], "|"));
		//	System.out.println(analyzer.segment(news[2], "|"));
		//	System.out.println(analyzer.contains("ղķ˹"));
		}
		}
	
	//���ļ��ж���url�������Լ�����
	public String[] loadFile(File file) throws IOException{
		String[] fileContents=new String[3];
		BufferedReader br=new BufferedReader(new FileReader(file));
		SourcePreprocess preProcess=new SourcePreprocess();
		StringBuffer sb=new StringBuffer();
		String url=br.readLine();//��ȡurl
		String title=br.readLine();//��ȡ���ű���
		title=preProcess.replace(title, "&nbsp");
		title=preProcess.replace(title, "&quot");
		String contentLine=br.readLine();
		while(contentLine!=null){
			sb.append(contentLine);
			contentLine=br.readLine();
		}
		br.close();
		String content=sb.toString();//��ȡ��������	
		fileContents[0]=url;
		fileContents[1]=title;
		fileContents[2]=content;
		
		return fileContents;
		
	}
	public static void main(String[] args) throws CorruptIndexException, LockObtainFailedException, IOException {
		// TODO Auto-generated method stub
//		PathProperties.init("/path.properties");
//		Properties prop = new Properties();
//		prop.load(Object.class.getResourceAsStream("C:\\path.properties"));
//		System.out.println(prop.getProperty("INDEX_STORE_PATH"));
		
		String INDEX_STORE_PATH="G:\\tinyindex\\";
		String PROCESS_PATH="G:\\docs\\";

		Date beginTime=new Date();
		IR_IndexProcessor processer=new IR_IndexProcessor(INDEX_STORE_PATH);
		processer.createIndex(PROCESS_PATH);//Ҫ����������Ŀ¼
		processer.close();
		Date endTime=new Date();
		long timeOfSearch=(endTime.getTime()-beginTime.getTime())/1000;
		System.out.println("���������ܺ�ʱ"+timeOfSearch+"s");

	}

}
