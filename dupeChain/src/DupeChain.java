import java.util.ArrayList;
//import org.bouncycastle.*;
import com.google.gson.GsonBuilder;

public class DupeChain {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty =5;
    public static void main(String[] args) throws Exception {
		//add our blocks to the blockchain ArrayList:
		blockchain.add(new Block("Hi im the first block", "0"));	
		System.out.println("Trying to Mine block 1... ");
		blockchain.get(0).mineBlock(difficulty);

		blockchain.add(new Block("Hello im the second block",blockchain.get(blockchain.size()-1).hash)); 
		System.out.println("Trying to Mine block 2... ");
		blockchain.get(1).mineBlock(difficulty);

		blockchain.add(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
		System.out.println("Trying to Mine block 3... ");
		blockchain.get(2).mineBlock(difficulty);
		
		String blockChainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println(blockChainJson);
    }

	public boolean isChainValid(){
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0"
		for (int i = 1; i < blockchain.size(); i++) {
			Block currBlock= blockchain.get(i);
			Block prevBlock= blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if (!currBlock.hash.equals(currBlock.calculateHash())) {
				System.out.println("Current hash not identical");
				return false;
			} 
			//compare prev hash and reg prev hash:
			if (!prevBlock.hash.equals(currBlock.prevHash)) {
				System.out.println("Previous hash not identical");
				return false;
			}
			// check if the block is mined 
			if (!target.equals(currBlock.hash.substring(0,difficulty))) {
				System.out.println("The block has not been mined ");
				return false;
			}
		}
		return true;
	}
}
