package parcel;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class Parcel implements Serializable{
  private String name;
  private static final long serialVersionUID = 1L;
  private String id;
  
  private String publicName;
  
  private String pattern = "dd/mm/yyyy";
  
  private String measure = "none";
  
  private long quantity;
  
  SimpleDateFormat simpleDateFormat = new SimpleDateFormat(this.pattern);
  
  public Parcel(String name, String id, String publicName, long quantity, String measure) {
    this.name = name;
    this.id = id;
    this.publicName = publicName;
    this.quantity = quantity;
    this.measure = measure;
  }
  
  public void addParcel(int i) {
    this.quantity += i;
    System.out.println(this.quantity);
  }
  
  public void removeParcel(int i) {
    if (this.quantity >= i)
      this.quantity -= i; 
    System.out.println(this.quantity);
  }
  
  public long getQuantity() {
    return this.quantity;
  }
  
  public String getName() {
    return this.name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public String getId() {
    return this.id;
  }
  
  public void setId(String id) {
    this.id = id;
  }
  
  public String getPublicName() {
    return this.publicName;
  }
  
  public void setPublicName(String publicName) {
    this.publicName = publicName;
  }
  
  public String getPattern() {
    return this.pattern;
  }
  public void setQuantity(Long amount) {
	  this.quantity = amount;
  }
  
  public void setPattern(String pattern) {
    this.pattern = pattern;
  }
  
  public String getMeasure() {
    return this.measure;
  }
  
  public void setMeasure(String measure) {
    this.measure = measure;
  }
}

