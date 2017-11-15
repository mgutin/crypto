package crypto;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class CoinManager {

	static CloseableHttpClient client = HttpClients.createDefault();
	static URIBuilder builder = new URIBuilder();

	
	
	public static Map<String, Float> prices = new HashMap<String, Float>();
	
	public static Coin makeCoin(String name, float amount, float purchasePrice) throws ClientProtocolException, URISyntaxException, IOException{
		if(!prices.keySet().contains(name)){
			prices.put(name, (float)getPriceForCoin(name));
		}
		return new Coin(name, amount, prices.get(name), purchasePrice);
	}
	
	public static Coin makeCoin(String name, float amount) throws ClientProtocolException, URISyntaxException, IOException{
		if(!prices.keySet().contains(name)){
			prices.put(name, (float)getPriceForCoin(name));
		}
		return new Coin(name, amount, prices.get(name));
	}
	
	public static void addCoinToWallet(String coinName, float amount, Wallet wallet) throws ClientProtocolException, URISyntaxException, IOException{
		wallet.addCoin(CoinManager.makeCoin(coinName, amount));
	}
	public static void addCoinToWallet(String coinName, float amount, float purchasePrice,  Wallet wallet) throws ClientProtocolException, URISyntaxException, IOException{
		wallet.addCoin(CoinManager.makeCoin(coinName, amount, purchasePrice));
	}
	
	private static float getPriceForCoin(String coinName) throws URISyntaxException, ClientProtocolException, IOException{
		builder.setHost("min-api.cryptocompare.com/data/price");
		builder.setScheme("https");
		builder.setParameter("tsyms", "USD");	
		builder.setParameter("fsym", coinName);
		HttpGet request = new HttpGet(builder.build());	
		CloseableHttpResponse response = client.execute(request);
	    String str = "";
		try {
			HttpEntity entity = response.getEntity();
		    InputStream responseStream = entity.getContent();
		    int c;
		    while((c = responseStream.read()) != -1){
		    	str += (char)c;
		    }
		    str = str.split(":")[1];
		    str = str.substring(0, str.length()-1);
		    EntityUtils.consume(entity);
		} finally {
		    response.close();
		}
		if(str == ""){
		    	return 0;
		}
		return Float.parseFloat(str);
	}
}
