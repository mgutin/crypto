package crypto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
	static String fileName = "wallet.txt";
	
	static Gson gson = new GsonBuilder().create();
	
	public static void main(String[] args) throws URISyntaxException, ClientProtocolException, IOException{	
		Wallet myWallet = new Wallet();
		CoinManager.addCoinToWallet("BTC", 1.1165301f, myWallet);
		CoinManager.addCoinToWallet("ETH", 6.564297f, myWallet);
		CoinManager.addCoinToWallet("BCH", 2.1539f, myWallet);
		write(myWallet);
		System.out.println(myWallet);
		System.out.println();
		printProfitInfo(myWallet, 7500);
	}
	
	public static void printProfitInfo(Wallet wallet, float price){
		float newPrice = wallet.getValue();
		System.out.println("$"+(newPrice-price));
		int percent = (int)(100*newPrice/price)-100;
		System.out.println(percent+"%");
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
