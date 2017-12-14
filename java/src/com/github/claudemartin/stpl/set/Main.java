package com.github.claudemartin.stpl.set;

import java.util.concurrent.atomic.LongAdder;

public class Main {
	public static void main(String... arg) {
//		System.out.println(Long.hashCode(-1) ^-1L);
//		System.out.println(Long.hashCode(0L) );
//		System.out.println(Long.hashCode(-1L) );
		
//		for (long i = -10L; i <= 10L; i++) {
//			//System.out.println(Long.hashCode(i) );
//			System.out.println(Long.valueOf(i).hashCode());
//		}
		
//		
		for (int x = 0; x < 10; x++) {
			ZFSet set = ZFSet.of(x);
			LongAdder i = new LongAdder();
			set.generateTokens(c -> {
				//System.out.print(c);
				i.increment();
			} , 0);
			System.out.print(x);
			System.out.print(' ');
			System.out.print(set);
			System.out.print(' ');
			System.out.println(i.sum());
		}
	}
}
