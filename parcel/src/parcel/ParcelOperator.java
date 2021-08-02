package parcel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ParcelOperator {
	ArrayList<Parcel> parcels;
	public ParcelOperator() {
	    //Initiallize something
		parcels = new ArrayList<Parcel>();
		fetchParcel();
	  }
	  
	  public void fetchParcel() {
		  try {
			  this.parcels = new ArrayList<>();
			    FileInputStream fis = new FileInputStream("parcel.dat");
			    ObjectInputStream ois = new ObjectInputStream(fis);
			    parcels = (ArrayList<Parcel>) ois.readObject();
			    ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	     
	    StockPanel.isOpened = false;
	  }
	  
	  public void saveParcel(){
		  try {
			  System.out.println("Saving Parcel");
			  FileOutputStream fos = new FileOutputStream("./parcel.dat");
			  ObjectOutputStream oos = new ObjectOutputStream(fos);
			  oos.writeObject(parcels);
			  System.out.println("Parcel Saved");
			  oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		  	
	  }
	  public void updateParcel(Parcel p) {
		  System.out.println("---------Updating Parcel-----------");
		  System.out.println("p : " + p.getId());
		  System.out.println("Parcel Array Size : " + parcels.size());
		  
		  for(int i = 0 ; i < parcels.size(); i++) {
			  if(parcels.get(i).getId().equals(p.getId())) {
				  parcels.get(i).removeParcel((int) (parcels.get(i).getQuantity() - p.getQuantity()));
			  }
		  }

		  
		  
	  }

	  public ArrayList<Parcel> getItems() {
		    return this.parcels;
	  }
	  public void setItems(ArrayList<Parcel> parcel) {
		  parcels = parcel;  
	  }
		  
}
