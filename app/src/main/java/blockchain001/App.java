/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package blockchain001;

import java.util.Random;
import java.util.Scanner;

public class App {
    private Scanner scanner;
    private Blockchain blockchain;

    public App() {
        this.scanner = new Scanner(System.in);
        this.blockchain = Blockchain.getInstance();
    }

    public void start() {
        boolean running = true;
        printWelcome();

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "0":
                    handleMockTransactions();
                    break;
                case "1":
                    handleTransaction();
                    break;
                case "2":
                    handlePrint();
                        break;
                case "3":
                    handleValidation();
                    break;
                case "4":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }
    
    private void handleMockTransactions(){
        final String[] names = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Hannah", "Ivy", "Jack"};
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            String sender = names[random.nextInt(names.length)];
            String receiver;

            do {
                receiver = names[random.nextInt(names.length)];
            } while (sender.equals(receiver));

            int amount = random.nextInt(5000);
            
            try {
                String message = blockchain.addTransaction(new Transaction(sender, receiver, amount));

                System.out.println(message);
                
            } catch (Exception e) {
                System.out.println("Recieved an erro: "+e.getMessage());
            }

        }

    }

    private void handlePrint(){
        System.out.println("Here is the blockchain:\n");
        System.out.println(blockchain);
    }


    private void handleTransaction(){
        System.out.print("Enter the reciever: ");
        String receiver = scanner.nextLine().trim();

        System.out.print("Enter your name(sender): ");
        String sender = scanner.nextLine().trim();

        try{
            System.out.print("Enter amount of money: ");
            int moneyAmount = Integer.parseUnsignedInt(scanner.nextLine().trim());
            Transaction transaction = new Transaction(sender, receiver, moneyAmount);
            String message = blockchain.addTransaction(transaction);

            System.out.println(message);

        }catch(NumberFormatException e){
             System.out.println("Error: Please enter valid numbers!");
        }catch(Exception e){
            System.out.println("Recieved an erro: "+e.getMessage());
        }
    }


    private void handleValidation(){

        if (blockchain.isChainValid()) {
            System.out.println("The blockchain is valid!");
            return;
        }
        
        System.out.println("Something is wrong! The chain did not pass the validation!");

    }

    private void printWelcome() {
        System.out.println("Welcome to the Simple Blockchain!");
        System.out.println("============================");
    }

    private void printMenu() {
        System.out.println("\nPlease select an option:");
        System.out.println("0. Create 10 mock transactions");
        System.out.println("1. Add transaction");
        System.out.println("2. Print the blockchain");
        System.out.println("3. Validate blockchain");
        System.out.println("4. Exit");
        System.out.print("> ");
    }

    public static void main(String[] args) {
        App app = new App();
        app.start();
    }
}