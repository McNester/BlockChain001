package blockchain001;

public class Transaction {

    private String sender;

    private String receiver;

    private int moneyAmount;

    public Transaction(String sender, String receiver, int moneyAmount){
        this.sender = sender;
        this.receiver = receiver;
        this.moneyAmount = moneyAmount;
    }

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public int getMoneyAmount() {
		return moneyAmount;
	}

	public void setMoneyAmount(int moneyAmount) {
		this.moneyAmount = moneyAmount;
	}


    public String generateHash(){
        return Hasher.generateHash(sender+receiver+moneyAmount);
    }


    @Override
    public String toString() {
        return "\n\tHash: "+generateHash()+"\n"+"\tReceiver: "+receiver + "\n" + "\tSender: "+sender +"\n" +"\tMoney amount: " +moneyAmount ;
    }

    
}
