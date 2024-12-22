package blockchain001;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class Block {

    private long timestamp;
    private String prevHash;
    private List<Transaction> transactions;
    private MerkleTree merkleTree;
    private String blockHash;
    private String merkleRoot;



    public Block(String prevHash) {
        this.prevHash = prevHash;
        this.transactions = new ArrayList<>();
        this.timestamp = System.currentTimeMillis();
    }

    public void addTransaction(Transaction newTrans){
        transactions.add(newTrans);
    }

    public String getTimestamp() {
        Date date = new Date(timestamp);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);
    }

	public String getPrevHash() {
		return prevHash;
	}

    public String getBlockHash(){
        return blockHash;
    }

	public List<Transaction> getTransactions() {
		return transactions;
	}

    private String generateMerkleRoot(){
        if (merkleTree == null) {
            return "no transactions";
        }
        return merkleTree.getMerkleRoot();
    }

    private String generateHash() {
        return Hasher.generateHash(this.prevHash+String.valueOf(this.timestamp)+this.merkleRoot);
    }

    private void generateMerkleTree(){
        ArrayList<String> initialHashes = new ArrayList<>();

        transactions.forEach(t -> {initialHashes.add(t.generateHash());});

        this.merkleTree = new MerkleTree(initialHashes);
    }



    public void finilizeBlock(){
        generateMerkleTree();
        this.merkleRoot = generateMerkleRoot();
        this.blockHash = generateHash();
    }


    public boolean isBlockValid(String previousHash){
        return isBlockHashValid() && isPrevHashValid(previousHash) && isMerkleRootValid();
    }

    private boolean isMerkleRootValid(){
        return this.merkleRoot.equals(generateMerkleRoot());
    }

    private boolean isBlockHashValid(){
        return this.blockHash.equals(generateHash());
    }

    private boolean isPrevHashValid(String previousHash){
        return this.prevHash.equals(previousHash);
    }


    @Override
    public String toString() {
        StringBuilder baseBlockInfo = new StringBuilder( 
            "\nHash: "+blockHash+"\n"+
            "Previous hash: "+prevHash + "\n" +
            "Timestamp: "+getTimestamp() +"\n" +
            "Merkel root: " +merkleRoot+"\n" +
            merkleTree.toString()+
            "Transactions: ");
            
        transactions.forEach(t -> {
            baseBlockInfo.append("\n");
            baseBlockInfo.append(t.toString());
        });

        return baseBlockInfo.toString();

    }

    
}
