package zProducerConsumer;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//Akif Hatipoðlu

public class UreticiTuketici {

	public static BankaHesap buffer = new BankaHesap();

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new UreticiGorevi(buffer));
		executor.execute(new TuketiciGorevi(buffer));
		executor.shutdown();
	}

}

class UreticiGorevi implements Runnable {
	BankaHesap buffer;

	public UreticiGorevi(BankaHesap buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			int i = 1;
			while (true) {
				System.out.println("Uretici ekledi: " + i);
				buffer.uret(i++);
				Thread.sleep((int) (Math.random() * 1000));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}

class TuketiciGorevi implements Runnable {
	BankaHesap buffer;

	public TuketiciGorevi(BankaHesap buffer) {
		// TODO Auto-generated constructor stub
		this.buffer = buffer;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			while (true) {
				System.out.println("\t\tTuketici aldý: " + buffer.tuket());
				Thread.sleep((int) (Math.random() * 1000));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

class BankaHesap {
	private static final int kapasite = 1;

	private LinkedList<Integer> kuyruk = new LinkedList<Integer>();

	private static Lock kilit = new ReentrantLock();

	private static Condition notEmpty = kilit.newCondition();

	private static Condition notFull = kilit.newCondition();

	public void uret(int deger) {
		kilit.lock();
		try {
			while (kuyruk.size() == kapasite) {
				System.out.println("wait for notFull condition");
				notFull.await();
			}
			kuyruk.offer(deger);
			notEmpty.signal();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			kilit.unlock();
		}

	}

	public int tuket() {
		int deger = 0;
		kilit.lock();
		try {
			while (kuyruk.isEmpty()) {
				System.out.println("\t\twait for notEmty condition");
				notEmpty.await();
			}
			deger = kuyruk.remove();
			notFull.signal();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			kilit.unlock();
			return deger;
		}
	}

}
