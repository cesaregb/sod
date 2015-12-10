package com.example.threads;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.example.service.URLReader;

/**
 * @author cesagonz
 * This class was only created for possible multy-threading implementation. 
 * The wiring with the services has to be done with sockets or other method, or maybe return a complex object with different htmls.  
 */
public class Executor {
	public static class HttpRquestCallable implements Callable<KeyValue> {
		private String url;
		public HttpRquestCallable(String url) {
			this.url = url;
		}
		public KeyValue call() {
			try{
				String result = URLReader.getURLContent(url);
				return new KeyValue(url, result);
			}catch(Exception e){
				return new KeyValue(url, "error");
			}
		}
	}
	
	public static void execute(String args[]) throws Exception {
		if (args.length == 0){
			System.out.println("Error. No url passed...");
		}else{
			ExecutorService pool = Executors.newFixedThreadPool(3);
			Set<Future<KeyValue>> set = new HashSet<Future<KeyValue>>();
			for (String url: args) {
				Callable<KeyValue> callable = new HttpRquestCallable(url);
				Future<KeyValue> future = pool.submit(callable);
				set.add(future);
			}
			
			for (Future<KeyValue> future : set) {
				KeyValue kv = future.get();
				if (kv.getValue().equals("errpr")){
					System.out.println("Error with url: " + kv.getKey());
				}else{
					System.out.println("HTML content from: " + kv.getKey());
				}
			}
			pool.shutdown();
		}
		}
}

class KeyValue{
	private String key; 
	private String value; 
	KeyValue (String key, String value){
		this.key = key;
		this.value = value;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}