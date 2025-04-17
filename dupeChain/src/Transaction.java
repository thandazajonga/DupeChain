import java.security.*;
import java.util.ArrayList;
public class Transaction {
    private String transactionId;
    public PublicKey senderPublicKey;
    public PublicKey receiverPublicKey;
    public float amount;
    public byte[] signature;

    private static int sequence = 0; // a rough count of how many transactions have been generated.

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    public Transaction(PublicKey senderPublicKey,PublicKey receiverPublicKey,float amount,ArrayList<TransactionInput> inputs){
        this.senderPublicKey=senderPublicKey;
        this.receiverPublicKey=receiverPublicKey;
        this.amount=amount;
        this.inputs=inputs;
    }
    //Signs all the data we dont wish to be tampered with.
    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(senderPublicKey) + StringUtil.getStringFromKey(receiverPublicKey) + Float.toString(amount)	;
        signature = StringUtil.applyECDSASig(privateKey,data);		
    }
    public boolean verifiySignature() {
        String data = StringUtil.getStringFromKey(senderPublicKey) + StringUtil.getStringFromKey(receiverPublicKey) + Float.toString(amount)	;
        return StringUtil.verifyECDSASig(senderPublicKey, data, signature);
    }
    // Calculates the Transaction Id using the Hashing
    /*private String calculateTransactionId(){
        sequence++;
        return StringUtil.useSha256(StringUtil.getStringFromKey(senderPublicKey) +
        StringUtil.getStringFromKey(receiverPublicKey) +
        Float.toString(value) + sequence);
    }*/


}
