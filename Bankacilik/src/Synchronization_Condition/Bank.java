package Synchronization_Condition;

//Akif Hatipo�lu

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
	// accounts
	private final double[] hesap;

	private Lock bankLock;

	private Condition Yeterli_Fon;

	// n=hesap no ka� hesap olaca�i
	public Bank(int n, double baslangicbakiyesi) {

		hesap = new double[n];
		for (int i = 0; i < hesap.length; i++) {
			hesap[i] = baslangicbakiyesi;
		}

		bankLock = new ReentrantLock();

		Yeterli_Fon = bankLock.newCondition();

	}

	// kimden=>AMOUNT
	public void transfer(int kimden, int kime, double miktar)
			throws InterruptedException {
		bankLock.lock();
		// banka ba�ka thread ula�mas�n diye giri�i kapat�yor(mutual exclusion)
		try {
			// bakiyemiz istenen miktardan az ise bu thread
			// uyutulur(await).Yeterli bakiye elde edildi�i takdire signal ile
			// uyand�r�l�r.
			while (hesap[kimden] < miktar)
				Yeterli_Fon.await();

			System.out.println(Thread.currentThread());

			hesap[kimden] -= miktar;

			System.out.println(miktar + " miktar para " + kimden + " den "
					+ kime + " gonderildi.");

			hesap[kime] += miktar;

			System.out.println("Toplam miktar: " + getToplamMiktar()
					+ " G�nderen kisinin bakiyesi: " + hesap[kimden]
					+ " Alan kisinin bakiyesi:" + hesap[kime]);
			System.out.println();
			Yeterli_Fon.signalAll();

		} finally {
			// banka di�er threadlerin de girebilmesi i�in kiliti a��yor
			bankLock.unlock();

		}

	}

	public double getToplamMiktar() {
		bankLock.lock();
		try {
			double toplam = 0;
			for (double a : hesap) {
				toplam += a;
			}
			return toplam;
		} finally {
			bankLock.unlock();
		}
	}

	public int size() {

		return hesap.length;

	}

}
