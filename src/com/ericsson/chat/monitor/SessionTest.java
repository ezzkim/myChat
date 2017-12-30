package com.ericsson.chat.monitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class SessionTest {

	private static void show(Collection<String> cl) {
		Iterator<String> it = cl.iterator();
		while(it.hasNext()) {
			System.out.println("element : " + it.next());
		}
	}
	
	public static void main(String[] args) {
		List<String> chaters = new ArrayList<String>();
		
		System.out.println(args.length);
		
		chaters.add("zhangsan");
		chaters.add("lisi");
		chaters.add("wangwu");
		
		show(chaters);
		System.out.println("-------------");
		chaters.remove("lisi");
		show(chaters);
	}

}
