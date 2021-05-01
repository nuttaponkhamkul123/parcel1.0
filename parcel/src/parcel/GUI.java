package parcel;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {
  JFrame frame;
  
  JPanel panel;
  
  StockPanel p;
  
  public GUI() {
    if (StockPanel.selectedParcel[0] == null) {
      initial();
    } else {
      System.out.println("Exited");
    } 
  }
  
  private void initial() {
    this.p = new StockPanel();
  }
  
  public static void main(String[] args) {
    Runnable runnable = new Runnable() {
        public void run() {
          GUI gui = new GUI();
        }
      };
    Thread thread = new Thread(runnable);
    thread.start();
  }
}

