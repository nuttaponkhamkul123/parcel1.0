package parcel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Connector {
  private ArrayList<Parcel> parcels = new ArrayList<>();
  
  private ArrayList<History> history = new ArrayList<>();
  
  private Connection conn;
  
  private Statement stat;
  
  public Connector() {
    try {
      this.conn = DriverManager.getConnection("jdbc:mysql://localhost/parcel?characterEncoding=UTF-8", "root", "");
      Statement stat = this.conn.createStatement();
      ResultSet rs = stat.executeQuery("select * from items;");
      while (rs.next())
        this.parcels.add(new Parcel(rs.getString("itemName"), rs.getString("itemID"), rs.getString("itemPName"), Long.parseLong(rs.getString("quantity")), rs.getString("measure"))); 
      rs.close();
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "Cannot Connect to database");
    } 
  }
  
  public void close() {
    try {
      if (this.conn != null)
        this.conn.close(); 
    } catch (SQLException e) {
      e.printStackTrace();
    } 
  }
  
  public void fetchHistory(String id) {
    this.history = new ArrayList<>();
    try {
      this.conn = DriverManager.getConnection("jdbc:mysql://localhost/parcel?characterEncoding=UTF-8", "root", "");
      this.stat = this.conn.createStatement();
      ResultSet rs = this.stat.executeQuery("select * from history where itemID = '" + id + "'");
      while (rs.next())
        this.history.add(new History(rs.getString("itemID"), rs.getString("date"), rs.getString("document"), Integer.parseInt(rs.getString("quantity")), Integer.parseInt(rs.getString("status")), Integer.parseInt(rs.getString("remaining")), Integer.parseInt(rs.getString("hisID")), rs.getString("picker"), rs.getString("note"))); 
    } catch (SQLException e) {
      JOptionPane.showMessageDialog(null, "Cannot Connect to database");
    } 
    StockPanel.isOpened = false;
  }
  
  public boolean execute(String command) {
    boolean isComplete = false;
    try {
      this.stat = this.conn.createStatement();
      this.stat.executeUpdate(command);
      isComplete = true;
    } catch (SQLException e1) {
      System.out.println(e1);
      JOptionPane.showMessageDialog(null, "Query Error");
      isComplete = false;
    } 
    return isComplete;
  }
  
  public ArrayList<Parcel> getItems() {
    return this.parcels;
  }
  
  public ArrayList<History> getHistory() {
    return this.history;
  }
  
  public void removeParcel(String id) {
    String historyClear = "DELETE FROM `history` WHERE `history`.`itemID` = \"" + id + "\"";
    String itemDelete = "DELETE FROM `items` WHERE `items`.`itemID` = '" + id + "'";
    boolean histoClear = execute(historyClear);
    boolean iteDelete = execute(itemDelete);
    if (!histoClear && !iteDelete)
      JOptionPane.showMessageDialog(null, "Error"); 
  }
  
  public void removeHistory(Long id) {
    execute("DELETE FROM `history` WHERE `history`.`hisID` = " + id);
    execute("set @autoid :=0;");
    execute("update history set hisID = @autoid := (@autoid+1);");
    execute("alter table history Auto_Increment = 1;");
  }
}

