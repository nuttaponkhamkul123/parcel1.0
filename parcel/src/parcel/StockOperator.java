package parcel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.Serializable;

import javax.swing.JOptionPane;

public class StockOperator implements Serializable{
	
	ArrayList<History> history;
	ArrayList<Parcel> parcels;
	public StockOperator() {
	    //Initiallize something
		history = new ArrayList<History>();
		parcels = new ArrayList<Parcel>();
	  }
	  
	  public void fetchHistory(String id) {
		  try {
			  this.history = new ArrayList<>();
			    FileInputStream fis = new FileInputStream("history.dat");
			    ObjectInputStream ois = new ObjectInputStream(fis);
			    history = (ArrayList<History>) ois.readObject();
			    ois.close();
		} catch (Exception e) {
			System.out.println("File not found");
		}
	     
	    StockPanel.isOpened = false;
	  }
	  
	  public void saveHistory(){
		  try {
			  
			  FileOutputStream fos = new FileOutputStream("./history.dat");
			  ObjectOutputStream oos = new ObjectOutputStream(fos);
			  oos.writeObject(history);
			  System.out.println("Saved");
			  oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		  	
	  }
	  public void updateHistory(Long id,Parcel parcel) {
		  Parcel temp = null;
		  for(Parcel h : this.parcels) {
			  if(h.getId() == id.toString()) {
				  h = parcel;
				  temp = h;
				  break;
			  }
		  }
		  saveHistory();
		  
	  }
	  public void removeHistory(Long id) {
		   history.remove(id);
		   saveHistory();
		 }
	  

	  public ArrayList<Parcel> getItems() {
		    return this.parcels;
		  }
		  
	  public ArrayList<History> getHistory() {
		    return this.history;
		  }

	  
//	  public static void main(String[] args) {
//		Parcel p1 = new Parcel("test 1 ", "123456", "lorteee", 5, "test");
//		StockOperator op = new StockOperator();
//		op.fetchHistory(p1.getId());
//		System.out.println(op.getHistory());
//	}
	
	
}
