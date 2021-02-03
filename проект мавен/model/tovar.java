package model;

public class tovar {
	   
	   private int id;
	public tovar(int id, String name, double price, int count, String description) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.count = count;
		this.description = description;
	}

	private String name;
	private double price;
	private int count;
	private String description;   
	   
	   
	    
	 
	    
	   public String getName() {
	       return name;
	   }
	 
	   public void setName(String name) {
	       this.name = name;
	   }
	 
	   public String getDescription() {
	       return description;
	   }
	 
	   public void setDescription(String description) {
	       this.description = description;
	   }
	 
	   public int getId() {
	       return id;
	   }
	 
	   public void setId(int id) {
	       this.id = id;
	   }
	   
	   public double getPrice() {
	       return price;
	   }
	 
	   public void setPrice(double price) {
	       this.price = price;
	   }
	   public int getCount() {
	       return count;
	   }
	 
	   public void setPrive(int count) {
	       this.count = count;
	   }
}
