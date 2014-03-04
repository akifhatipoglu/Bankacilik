package Synchronization_Condition;

//Akif Hatipoðlu
public class TransferRunnable implements Runnable {

	public Bank bank;
	private int gonderen_hesap;
	private double max_miktar;
	private int gecikme = 1500;

	public TransferRunnable(Bank bank, int gonderen_hesap, double max_miktar) {
		this.bank = bank;
		this.gonderen_hesap = gonderen_hesap;
		this.max_miktar = max_miktar;
	}

	@Override
	public void run() {
		try {

			while (true) {
				int gidecek_hesap = (int) (bank.size() * Math.random());

				double miktar = max_miktar * Math.random();

				bank.transfer(gonderen_hesap, gidecek_hesap, miktar);

				Thread.sleep(gecikme);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
