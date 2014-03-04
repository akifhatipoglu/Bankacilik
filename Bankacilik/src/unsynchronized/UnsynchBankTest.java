package unsynchronized;
public class UnsynchBankTest {
	// Akif Hatipoðlu
	// Yarýþ Durumunun önlenemediði bir bankacýlýk uygulamasý

	// NACCOUNTS
	public static final int hesapsayisi = 100;
	// initial_ballance
	public static final double baslangis_bakiyesi = 1000;

	public static void main(String[] args) {
		Bank b = new Bank(hesapsayisi, baslangis_bakiyesi);

		for (int j = 0; j < hesapsayisi; j++) {
			TransferRunnable r = new TransferRunnable(b, j, baslangis_bakiyesi);
			Thread t = new Thread(r);
			t.start();
		}
	}

}
