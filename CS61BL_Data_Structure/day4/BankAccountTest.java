import junit.framework.TestCase;


public class BankAccountTest extends TestCase {

	public void testInit() {
		BankAccount b = new BankAccount(1000);
		assertTrue (b.balance() == 1000);
	}
	public void testInvalidArgs(){
		BankAccount b = new BankAccount(-1000);
		assertTrue (b.balance() <=0);
	}
	public void testOverdraft(){
		BankAccount b = new BankAccount(1000);
		b.withdraw(2000);
		assertTrue (b.balance()>=0);
	}
	public void testDeposit(){
		BankAccount b = new BankAccount(1000);
		b.deposit(100);
		assertTrue(b.balance()==1100);
	}
	public void testWithdraw(){
		BankAccount b = new BankAccount(1000);
		b.withdraw(100);
		assertTrue(b.balance() == 900);
	}
}

