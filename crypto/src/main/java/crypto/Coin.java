package crypto;

public class Coin {

	String name;
	float amount;
	float price;
	
	public Coin(String name, float amount, float price){
		this.name = name;
		this.amount = amount;
		this.price = price;
	}
	
	public float getValue(){
		int hold = (int)(100*amount*price);
		return ((float)hold)/100f;
	}
}
