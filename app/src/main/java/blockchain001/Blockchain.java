package blockchain001;

import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private final List<Block> chain;
    private Block miningBlock;

    private Blockchain() {
        this.chain = new ArrayList<>();
        String prevHash = addGenesisBlock();
        miningBlock = new Block(prevHash);
    }

    private static class SingletonHandler {
        private static final Blockchain INSTANCE = new Blockchain();
    }

    private String addGenesisBlock() {
        Block genesisBlock = new Block("0");
        genesisBlock.addTransaction(new Transaction("mainnet", "pool", 3000));
        genesisBlock.finilizeBlock();
        chain.add(genesisBlock);
        return genesisBlock.getBlockHash();
    }

    public static Blockchain getInstance() {
        return SingletonHandler.INSTANCE;
    }

    private String addBlock(Block newBlock) throws Exception {

        newBlock.finilizeBlock();

        if (!isChainValid()) {
            throw new Exception("Block is corrupted, could not add the block into chain.");
        }

        mineBlock(newBlock);

        return "Block is successfully mined!";

    }

    private void mineBlock(Block newBlock){
        chain.add(newBlock);
    }

    public boolean isChainValid(){

        if (chain.isEmpty()) {
            return false;
        }

        
        for (int i = 0; i < chain.size(); i++) {
            Block currBlock = chain.get(i);
            String previousHash = (i == 0) ? "0" : chain.get(i - 1).getBlockHash();

            if (!currBlock.isBlockValid(previousHash)) {
                return false;
            }
        }

        return true;

    }


    public String addTransaction(Transaction newTrans)throws Exception{
        miningBlock.addTransaction(newTrans);

        if (miningBlock.getTransactions().size() == 10) {
            String message = addBlock(miningBlock);
            String prevHash = miningBlock.getBlockHash();
            miningBlock = new Block(prevHash);
            return message;
        }

        return "Transaction added to a block!";

    }



    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        chain.forEach(block ->{
            builder.append("\n");
            builder.append(block.toString());

        });

        return builder.toString();
    }

}
