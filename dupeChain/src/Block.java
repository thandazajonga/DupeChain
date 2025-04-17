import java.util.Date;
public class Block 
{
    private String data; // data will be a simple message
    public String prevHash;
    public String hash; // digital sign
    private long timeStamp; //keeps record of when a transaction happened
    private int nonce;
    public Block(String data,String prevHash)
    {
        this.data=data;
        this.prevHash=prevHash;
        this.timeStamp= new Date().getTime();
        this.hash= calculateHash();
    }
    public String calculateHash()
    {
        return StringUtil.useSha256(data+prevHash+Long.toString(timeStamp)+ Integer.toString(nonce));
    }

    public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
}
