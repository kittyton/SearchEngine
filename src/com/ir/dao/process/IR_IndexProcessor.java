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
	//取得需要创建索引的文件数组
	public void createIndex(String inputDir) throws CorruptIndexException, LockObtainFailedException, IOException{
	    
		File filesDir=new File(inputDir);
		//取得需要创建索引的文件数组
		File[] files=filesDir.listFiles();
		for(int i=0;i<files.length;i++){
			if(files[i].isDirectory()){   //如果是一个目录	
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
	//下面是为一个单独的文件创建索引
	public void createFileIndex(File file,IndexWriter writer,MMAnalyzer analyzer) throws CorruptIndexException, IOException{
		
//		N++;
//		if(N != 15)
//			return;
//		else N = 0;
		
		
		
		String fileName=file.getName();
		String[] news=new String[3];
		news=loadFile(file);
		//判断文件是否为txt类型
		if(fileName.substring(fileName.lastIndexOf('.')).equals(".txt")){
			//创建一个新的document
			Document doc=new Document();
			//为文件名创建field
			Field field=new Field("fileName",file.getName(),Field.Store.YES,
					Field.Index.TOKENIZED);
			doc.add(field);
			//为URL创建一个field
			field=new Field("url",news[0],Field.Store.YES,Field.Index.UN_TOKENIZED);
			doc.add(field);
			//为Title创建一个field
			field=new Field("title",news[1],Field.Store.YES,Field.Index.TOKENIZED, TermVector.YES);
			doc.add(field);
			//为Content创建一个field
			field=new Field("content",news[2],Field.Store.YES,Field.Index.TOKENIZED, TermVector.YES);
			doc.add(field);
			
			field = new Field("date",ExtractTime.extractTime(news[0]),Field.Store.YES,Field.Index.UN_TOKENIZED);
			doc.add(field);
			
			field = new Field("heat",ExtractTime.random(),Field.Store.YES,Field.Index.UN_TOKENIZED);
			doc.add(field);
			//把document加入到IndexWriter中
			writer.addDocument(doc);
			//下面输出分词的结果
		//	System.out.println(analyzer.segment(news[1], "|"));
		//	System.out.println(analyzer.segment(news[2], "|"));
		//	System.out.println(analyzer.contains("詹姆斯"));
		}
		}
	
	//从文件中读出url，标题以及内容
	public String[] loadFile(File file) throws IOException{
		String[] fileContents=new String[3];
		BufferedReader br=new BufferedReader(new FileReader(file));
		SourcePreprocess preProcess=new SourcePreprocess();
		StringBuffer sb=new StringBuffer();
		String url=br.readLine();//获取url
		String title=br.readLine();//获取新闻标题
		title=preProcess.replace(title, "&nbsp");
		title=preProcess.replace(title, "&quot");
		String contentLine=br.readLine();
		while(contentLine!=null){
			sb.append(contentLine);
			contentLine=br.readLine();
		}
		br.close();
		String content=sb.toString();//获取新闻内容	
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
		processer.createIndex(PROCESS_PATH);//要建立索引的目录
		processer.close();
		Date endTime=new Date();
		long timeOfSearch=(endTime.getTime()-beginTime.getTime())/1000;
		System.out.println("构建索引总耗时"+timeOfSearch+"s");

	}

}
