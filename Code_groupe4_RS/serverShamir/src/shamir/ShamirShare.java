package shamir;
import java.math.BigInteger;

import shamir.PVSSEngine;
import shamir.PublicInfo;
import shamir.PublishedShares;
import shamir.Share;

public class ShamirShare {

	static final int NUM_BITS = 192;
	
	private PVSSEngine engine;
	
	
	public ShamirShare() {
		super();
	}

	public ShamirShare(int nb_available, int nb_required) throws Exception {
		
		engine = PVSSEngine.getInstance(nb_available,nb_required,NUM_BITS);
	}
	
	/**
	 * shares a secret into nb_available
	 * @param secret the secret to share
	 * @param nb_available to share it for this number
	 * @param nb_required to decide which number do we need to recover the secret
	 * @return shamir shares of the secret
	 */
	public Share[] shamirShare(String secret) throws Exception
	{   
		int nb_available = engine.getPublicInfo().getN();
	
        PublicInfo info = engine.getPublicInfo();
        
        BigInteger[] secretKeys = engine.generateSecretKeys();
        
        BigInteger[] publicKeys = new BigInteger[nb_available];
        for(int i=0; i<nb_available; i++){
            publicKeys[i] = engine.generatePublicKey(secretKeys[i]);
        }
        
        
        PublishedShares publishedShares = engine.generalPublishShares(
        		secret.getBytes(), publicKeys);
        
        System.out.println(publishedShares.verify(info,publicKeys)?"valid shares":"invalid shares");
        
        Share[] shares = new Share[nb_available];
        
        for(int i=0; i<nb_available; i++) {
            shares[i] = publishedShares.getShare(i,secretKeys[i],info,publicKeys);
            
            String validity = shares[i].verify(info,publicKeys[i])?" valid":" invalid";
            
            System.out.println(shares[i]+validity);
        }

		return shares;
		
	}
	
	/**
	 * 
	 * @param parts shamir shares
	 * @return the recovered secret
	 */
	public String shamirRecover(Share[] parts) throws Exception
	{
		byte[] result = engine.generalCombineShares(parts);
		
		return new String(result);
		
	}

	public PVSSEngine getEngine() {
		return engine;
	}

	public void setEngine(PVSSEngine engine) {
		this.engine = engine;
	}
	
	
}
