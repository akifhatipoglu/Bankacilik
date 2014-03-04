package Synchronization_synchronized;

//Akif Hatipo�lu

public class Bank {
	// accounts
	private final double[] hesap;

	// n=hesap no ka� hesap olaca�i
	public Bank(int n, double baslangicbakiyesi) {

		hesap = new double[n];
		for (int i = 0; i < hesap.length; i++) {
			hesap[i] = baslangicbakiyesi;
		}
	}

	// kimden=>AMOUNT
	// synchronized ile mutual exclusion yap�l�r.
	public synchronized void transfer(int kimden, int kime, double miktar)
			throws InterruptedException {

		// bakiyemiz istenen miktardan az ise bu thread uyutulur(wait
		// ile).Yeterli bakiye elde edildi�i takdire notify ile uyand�r�l�r.
		while (hesap[kimden] < miktar)
			wait();

		System.out.println(Thread.currentThread());

		hesap[kimden] -= miktar;

		System.out.println(miktar + " miktar para " + kimden + " den " + kime
				+ " gonderildi.");

		hesap[kime] += miktar;

		System.out.println("Toplam miktar: " + getToplamMiktar()
				+ " G�nderen kisinin bakiyesi: " + hesap[kimden]
				+ " Alan kisinin bakiyesi:" + hesap[kime]);
		System.out.println();
		notifyAll();
	}

	public synchronized double getToplamMiktar() {
		double toplam = 0;
		for (double a : hesap) {
			toplam += a;
		}
		return toplam;
	}

	public int size() {

		return hesap.length;

	}

}
