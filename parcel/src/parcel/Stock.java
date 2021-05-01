package parcel;

import java.util.ArrayList;

public class Stock {
  private ArrayList<Parcel> items;
  
  public void addStock(Parcel item) {
    this.items.add(item);
  }
}