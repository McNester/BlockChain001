package blockchain001;

import java.util.ArrayList;

public class MerkleTree {

    private ArrayList<ArrayList<String>> merkleTree;

    public MerkleTree(ArrayList<String> initialHashes) {

        merkleTree = new ArrayList<>();

        if (initialHashes == null || initialHashes.isEmpty()) {
            return;
        }
        
        generateMerkleTree(initialHashes);
    }


    private void generateMerkleTree(ArrayList<String> hashLevel){


        merkleTree.add(hashLevel);

        if (hashLevel.size() == 1) {
            return;
        }

        ensureEven(hashLevel);

        ArrayList<String> nextHashLevel = new ArrayList<>();
        for (int i = 0; i < hashLevel.size(); i+=2) {
            String combinedHash = Hasher.generateHash( hashLevel.get(i) + hashLevel.get(i+1) );
            nextHashLevel.add(combinedHash);
        }

        generateMerkleTree(nextHashLevel);
    }


    private void ensureEven(ArrayList<String> hashLevel){

        if (hashLevel.size() % 2 == 0) {
            return;
        }

        hashLevel.add(hashLevel.get(hashLevel.size()-1));

    }


    public String getMerkleRoot(){
        return merkleTree.get(merkleTree.size()-1).get(0);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Merkle Tree Levels:\n");

        for (int i = 0; i < merkleTree.size(); i++) {
            sb.append(String.format("L%d: %s\n", i, 
                        String.join(" \n| ", merkleTree.get(i))));
        }

        return sb.toString();
    }

}
