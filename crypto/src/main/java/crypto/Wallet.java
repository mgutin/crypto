package crypto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Wallet {
	Map<String, Coin> wallet = new HashMap<String, Coin>();
	
	public Wallet(){
		
	}
	
	public Collection<Coin> getCoins(){
		return wallet.values();
	}
	
	public Wallet(Coin... coins){
		for(Coin coin : coins)
		{
			wallet.put(coin.name, coin);
		}
	}
	
	public void addCoin(Coin... coins){
		for(Coin coin : coins)
		{
			wallet.put(coin.name, coin);
		}
	}
	
	public float getValue(){
		float value = 0;
		for(Coin coin : wallet.values()){
			value += coin.getValue();
		}
		return value;
	}
	
	public String toString(){
		String output = "";
		for(Coin coin : wallet.values()){
			output+=coin.name + ": " + coin.getValue() +  "\n";
		}
		output+="ALL: " + getValue();
		return output; 
	}
}
