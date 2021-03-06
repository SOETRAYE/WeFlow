package com.etoc.weflow.parallel;


import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelManager  {
	private static ParallelManager ins = null;

	private ExecutorService executor;
	private Set<Long> runningSet;

	private ParallelManager(){
		executor = Executors.newFixedThreadPool(8);
		runningSet = new HashSet<Long>();
	}
	
	synchronized public static ParallelManager getInstance(){
		if(ins==null){
			ins = new ParallelManager();
		}
		
		return ins;
	}
	
	
	public void submitTask(IYTask t){
		executor.submit(t);
	}

	synchronized public boolean hasEventRunning(long event_id) {
		// TODO Auto-generated method stub
		return runningSet.contains(event_id);
	}
	
	synchronized public boolean beginEventRunning(long event_id) {
		// TODO Auto-generated method stub
		return runningSet.add(event_id);
	}
	
	synchronized public void endEventRunning(long event_id) {
		// TODO Auto-generated method stub
		runningSet.remove(event_id);
	}

}
