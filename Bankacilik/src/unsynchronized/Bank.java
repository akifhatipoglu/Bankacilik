package unsynchronized;
public class Bank {
	// Akif Hatipoðlu
	//Github Deneme11
	// accounts
	private final double[] hesap;

	// n=hesap no kaç hesap olacaði
	public Bank(int n, double baslangicbakiyesi) {

		hesap = new double[n];
		for (int i = 0; i < hesap.length; i++) {
			hesap[i] = baslangicbakiyesi;
		}
	}

	// kimden=>AMOUNT
	public void transfer(int kimden, int kime, double miktar) {
		if (hesap[kimden] < miktar) {
			return;
		}
		System.out.println(Thread.currentThread());

		hesap[kimden] -= miktar;

		System.out.println(miktar + " miktar para " + kimden + " den " + kime
				+ " gonderildi.");

		hesap[kime] += miktar;

		System.out.println("Toplam miktar: " + getToplamMiktar()
				+ " Gönderen kisinin bakiyesi: " + hesap[kimden]
				+ " Alan kisinin bakiyesi:" + hesap[kime]);
		System.out.println();
	}

	public double getToplamMiktar() {
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
