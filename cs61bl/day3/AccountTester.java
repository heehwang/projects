public class AccountTester {

    public static void main (String [ ] args) {
        Account mike;
        mike = new Account (1000);
        System.out.println (mike.balance ());
        mike.deposit (100);
        System.out.println (mike.balance ());
        mike.withdraw (200);
        System.out.println (mike.balance ());
        System.out.println("This should be false: " + mike.withdraw(-100));
        System.out.println("This should be false: " + mike.withdraw(1000));
        System.out.println("This should be true: " + mike.withdraw(100));
        
        Account stanley = new Account(10000);
        mike.merge(stanley);
        System.out.println("Mike's balance is:" + mike.balance());
        System.out.println("Stanley's balance is:" + stanley.balance());
        
        Account kathy = new Account(500);
        Account megan = new Account(100, kathy);
        
        System.out.println("withdrawing 50 should be true: "+megan.withdraw(50));
   
        System.out.println("Megan's balance:" + megan.balance() + " Kathy's balance" + kathy.balance());
        megan.deposit(50);
        
        System.out.println("withdrawing 200 should be true: "+megan.withdraw(200));
        System.out.println("Megan's balance:" + megan.balance() + " Kathy's balance" + kathy.balance());
        megan.deposit(100);
        kathy.deposit(100);
        
        System.out.println("withdrawing 700 should be false: "+megan.withdraw(700));
        System.out.println("Megan's balance:" + megan.balance() + " Kathy's balance: " + kathy.balance());
        
    }
}
