package parcel;

import java.io.Serializable;

public class History implements Serializable{
	  private String itemID;
	  private static final long serialVersionUID = 1L;
	  private String date;
	  
	  private String document;
	  
	  private int quantity;
	  
	  private int status;
	  
	  private int remaining;
	  
	  private long hisID;
	  
	  private String picker;
	  
	  private String note;
	  
	  public History(String itemID, String date, String document, int quantity, int status, int remaining, long hisID, String picker, String note) {
	    this.itemID = itemID;
	    this.date = date;
	    this.document = document;
	    this.quantity = quantity;
	    this.status = status;
	    this.remaining = remaining;
	    this.hisID = hisID;
	    this.note = note;
	    this.picker = picker;
	  }
	  
	  public String getPicker() {
	    return this.picker;
	  }
	  
	  public void setPicker(String picker) {
	    this.picker = picker;
	  }
	  
	  public String getNote() {
	    return this.note;
	  }
	  
	  public void setNote(String note) {
	    this.note = note;
	  }
	  
	  public String getItemID() {
	    return this.itemID;
	  }
	  
	  public void setItemID(String itemID) {
	    this.itemID = itemID;
	  }
	  
	  public String getDate() {
	    return this.date;
	  }
	  
	  public void setDate(String date) {
	    this.date = date;
	  }
	  
	  public String getDocument() {
	    return this.document;
	  }
	  
	  public void setDocument(String document) {
	    this.document = document;
	  }
	  
	  public int getQuantity() {
	    return this.quantity;
	  }
	  
	  public void setQuantity(int quantity) {
	    this.quantity = quantity;
	  }
	  
	  public int getStatus() {
	    return this.status;
	  }
	  
	  public void setStatus(int status) {
	    this.status = status;
	  }
	  
	  public int getRemaining() {
	    return this.remaining;
	  }
	  
	  public void setRemaining(int remaining) {
	    this.remaining = remaining;
	  }
	  
	  public long getHisID() {
	    return this.hisID;
	  }
	  
	  public void setHisID(long hisID) {
	    this.hisID = hisID;
	  }
	}

