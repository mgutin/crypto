package crypto;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class CryptoMain {
	static String fileName = "wallet.txt";
	
	static Gson gson = new GsonBuilder().create();
	
	public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException{	
		Wallet myWallet = new Wallet();
		CoinManager.addCoinToWallet("BTC", 1.1165301f, 4500, myWallet);
		CoinManager.addCoinToWallet("BTG", 1.1165301f, myWallet);
		CoinManager.addCoinToWallet("ETH", 6.564297f, 2000, myWallet);
		CoinManager.addCoinToWallet("BCH", 2.1539f, 1000, myWallet);
		System.out.println(myWallet);
		System.out.println();
		Wallet lastWallet = getLast();
		printProfitInfo(myWallet, lastWallet);
		write(myWallet);
		
	}
	
	public static void printProfitInfo(Wallet wallet, Wallet lastWallet){
		float price = 0;
		for(Coin coin : wallet.getCoins()){
			price+= coin.purchasePrice;
			System.out.print(coin.name + ": ");
			float coinPrice = coin.purchasePrice;
			float newPrice = coin.getValue();
			if(coin.purchasePrice != 0){
				System.out.print("$"+(newPrice-coinPrice) +"\t");
				int percent = (int)(100*newPrice/coinPrice)-100;
				System.out.println(percent+"%");
			}else{
				System.out.print("$" + newPrice +"\t");
				System.out.println("FREE");
			}
		}
		float newPrice = wallet.getValue();
		System.out.print("ALL: $"+(newPrice-price) + "\t");
		int percent = (int)(100*newPrice/price)-100;
		System.out.println(percent+"%"+"\n");
		System.out.println("Since Last: " + (wallet.getValue() - lastWallet.getValue()));
	}
	
	public static Wallet getLast() throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		List<String> lines = new ArrayList<String>();
		String l;
		while((l =reader.readLine()) != null){
			lines.add(l);
		}
		JSONObject json = new JSONObject(lines.get(lines.size()-1));
		reader.close();
		return gson.fromJson(json.getJSONArray("wallet").getString(0).toString(), Wallet.class);
	}
	
	public static void write(Wallet wallet) throws IOException {
	    String str = "";
	    JSONObject json = new JSONObject();
	   
	    json.append("wallet",  gson.toJson(wallet));
	    json.append("date", new Date());
	    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
	    writer.write(json.toString());
	    writer.newLine();
	    writer.close();
	}
	
}
