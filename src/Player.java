import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

public class Player {
    public static int MAX = 52;
    public Card[] cards = new Card[MAX];
    private int cash = 0;
    private int card_count = 0;
    private int bet = 10;
    private String name;
    private File save_file;
    
    public Player(String _name){
 	save_file = new File("../data/save.txt");
	try{
	    String line = null;	     	    
	    BufferedReader reader = new BufferedReader(new FileReader(save_file));
	    if((line = reader.readLine()) != null){
		cash = Integer.parseInt(decrypt(line));
	    }
	    reader.close();
	}catch(Exception ex){
	    ex.printStackTrace();	    
	}

	if(cash <= 0){
	    try{
		cash = 100;
	    }catch(Exception ex){
		ex.printStackTrace();	    
	    }
	}
	name = _name;
	
    }

    public static String key()
    {
        return "kan_enckey_SGYR@45%^&Ctazsdf#%)";
    }

    public static Key getKey() throws Exception {
        return (key().length() == 24) ? getKey2(key()) : getKey1(key());
    }

    public static Key getKey1(String keyValue) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(keyValue.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        Key key = keyFactory.generateSecret(desKeySpec);
        return key;
    }

    public static Key getKey2(String keyValue) throws Exception {
        DESedeKeySpec desKeySpec = new DESedeKeySpec(keyValue.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        Key key = keyFactory.generateSecret(desKeySpec);
        return key;
    }

    public static String encrypt(String ID) throws Exception {
        if (ID == null || ID.length() == 0)
            return "";

        String instance = (key().length() == 24) ? "DESede/ECB/PKCS5Padding" : "DES/ECB/PKCS5Padding";
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, getKey());
        String amalgam = ID;

        byte[] inputBytes1 = amalgam.getBytes("UTF8");
        byte[] outputBytes1 = cipher.doFinal(inputBytes1);
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        String outputStr1 = encoder.encode(outputBytes1);
        return outputStr1;
    }

    public static String decrypt(String codedID) throws Exception {
        if (codedID == null || codedID.length() == 0)
            return "";

        String instance = (key().length() == 24) ? "DESede/ECB/PKCS5Padding" : "DES/ECB/PKCS5Padding";
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance(instance);
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, getKey());
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();

        byte[] inputBytes1 = decoder.decodeBuffer(codedID);
        byte[] outputBytes2 = cipher.doFinal(inputBytes1);

        String strResult = new String(outputBytes2, "UTF8");
        return strResult;
    }
    
    public int cardCount(){
	return card_count;
    }

    public Card receiveCard(Card card){
	cards[card_count++] = card;
	return card;
    }

    public void reset(){
	card_count = 0;
    }

    public int getCash(){
	return cash;
    }

    public void setCash(int _cash){
	try{
	    BufferedWriter writer = new BufferedWriter(new FileWriter(save_file));
	    writer.write(encrypt(_cash + ""));	        
	    writer.close();	    
	}catch(Exception ex){
	    ex.printStackTrace();	    
	}

	cash = _cash;
    }

    public void setBet(int _bet){
	bet = _bet;
    }

    public int getBet(){
	return bet;
    }
    
    public int getValue(){
	int result = 0;
	boolean hasAce = false;
	for(int i = 0; i < card_count; i++){ // Adding Cards value and get player score
	    result = result + cards[i].getValue();
	    if(cards[i].isAce()){
		hasAce = true;
	    }
	}
	if(hasAce && (result <= 11)){ // Ace check
	    result = result + 10;
	}
	return result;
    }
}
