package parcel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import net.miginfocom.swing.MigLayout;

public class StockPanel extends JFrame {
  private static final long serialVersionUID = 1400543192516649977L;
  
  JTable table;
  
  
  public static String[] selectedParcel = new String[2];
  
  private ArrayList<Parcel> parcels;
  
  private Connector c;
  
  private ParcelOperator po;
  
  public static boolean isOpened = false;
  
  private DefaultTableModel model;
  
  private JButton selectBtn;
  
  public StockPanel() {
    UIManager.put("OptionPane.messageFont", new Font("Angsana New", 0, 20));
    this.po = new ParcelOperator();
    this.parcels = po.getItems();
    setLayout(new BorderLayout(20, 10));
    JLabel l1 = new JLabel("");
    l1.setBorder(BorderFactory.createEmptyBorder(15, 40, 0, 20));
    l1.setFont(new Font("Angsana New", 1, 25));
    add(l1, "North");
    this.model = new DefaultTableModel() {
        public boolean isCellEditable(int row, int column) {
          return false;
        }
      };
    
    this.model.addColumn("รหัสพัสดุ");
    this.model.addColumn("ชื่อพัสดุ");
    this.model.addColumn("คงเหลือ");
    JPanel gridButtonPanel = new JPanel();
    gridButtonPanel.setLayout((LayoutManager)new MigLayout());
    this.selectBtn = new JButton("เลือก");
    JButton addParcelBtn = new JButton("เพิ่มพัสดุ");
    final JButton rmParcelBtn = new JButton("ลบพัสดุ");
    addParcelBtn.setFont(new Font("Angsana New", 1, 25));
    this.selectBtn.setFont(new Font("Angsana New", 1, 25));
    rmParcelBtn.setFont(new Font("Angsana New", 1, 25));
    this.selectBtn.setEnabled(false);
    rmParcelBtn.setEnabled(false);
    this.selectBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            Object selectedID = StockPanel.this.table.getValueAt(StockPanel.this.table.getSelectedRow(), 0);
            Object selectedName = StockPanel.this.table.getValueAt(StockPanel.this.table.getSelectedRow(), 1);
            StockPanel.selectedParcel[0] = (String)selectedID;
            StockPanel.selectedParcel[1] = (String)selectedName;
            System.out.println("Selected ID : " + StockPanel.selectedParcel[1] + "\nSelected Name : " + StockPanel.selectedParcel[0]);
            StockPanel.this.table.clearSelection();
            StockPanel.this.selectBtn.setEnabled(false);
            StockPanel.this.callNextFrame(StockPanel.selectedParcel[1], StockPanel.selectedParcel[0]);
          }
        });
    addParcelBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            final JFrame dialog = new JFrame();
            dialog.setLayout((LayoutManager)new MigLayout());
            JPanel panel1 = new JPanel();
            panel1.setLayout(new GridLayout(0, 1));
            panel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 5, 20));
            JLabel[] labels = new JLabel[5];
            final JTextField[] textField = new JTextField[5];
            labels[0] = new JLabel("ชื่อพัสดุ(ไม่เป็นทางการ)");
            textField[0] = new JTextField();
            labels[1] = new JLabel("รหัสพัสดุ");
            textField[1] = new JTextField();
            labels[2] = new JLabel("ชื่อพัสดุ(เป็นทางการ)");
            textField[2] = new JTextField();
            labels[3] = new JLabel("จำนวน");
            textField[3] = new JTextField();
            labels[4] = new JLabel("หน่วยวัด");
            textField[4] = new JTextField();
            JButton submitBtn = new JButton("ยืนยัน");
            submitBtn.setFont(new Font("Angsana New", 0, 20));
            byte b;
            int j;
            JTextField[] arrayOfJTextField1;
            for (j = (arrayOfJTextField1 = textField).length, b = 0; b < j; ) {
              JTextField f = arrayOfJTextField1[b];
              f.setFont(new Font("Angsana New", 1, 25));
              f.setPreferredSize(new Dimension(180, 25));
              
              b++;
            } 
            JLabel[] arrayOfJLabel1;
            for (j = (arrayOfJLabel1 = labels).length, b = 0; b < j; ) {
              JLabel l = arrayOfJLabel1[b];
              l.setFont(new Font("Angsana New", 1, 25));
              l.setPreferredSize(new Dimension(80, 25));
              b++;
            } 
            for (int i = 0; i < 5; i++) {
              JPanel temp = new JPanel();
              temp.setLayout((LayoutManager)new MigLayout());
              temp.add(labels[i], "pushx");
              temp.add(textField[i]);
              panel1.add(temp);
            } 
            panel1.add(submitBtn);
            dialog.add(panel1);
            submitBtn.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                    try {
                      String toSendID = textField[1].getText();
                      String toSendItemName = textField[0].getText();
                      String toSendPItemName = textField[2].getText();
                      int toSendQuantity = Integer.parseInt(textField[3].getText());
                      String toSendMeasure = textField[4].getText();
                      ///insert stock?
                      //String command = "INSERT INTO `items`(`itemID`, `itemName`, `itemPName`, `quantity`,`measure`) VALUES ('" + toSendID + "','" + toSendItemName + "','" + toSendPItemName + "'," + toSendQuantity + ",'" + toSendMeasure + "')";
                      
                        String[] object = { toSendID, toSendItemName, (new StringBuilder(String.valueOf(toSendQuantity))).toString() };
                        parcels.add(new Parcel(toSendItemName, toSendID, toSendPItemName, toSendQuantity, toSendMeasure));
                        po.setItems(parcels);
                        po.saveParcel();
                 
                        model.addRow((Object[])object);
                        dialog.dispose();
                      
                    } catch (NumberFormatException e1) {
                      JOptionPane.showMessageDialog(null, "จำนวนไม่ใช่ตัวเลข กรุณากรอกใหม่");
                    } 
                  }
                });
            dialog.pack();
            dialog.setLocationRelativeTo((Component)null);
            dialog.setTitle("Add Item");
            dialog.setVisible(true);
            dialog.setResizable(true);
          }
        });
    this.table = new JTable(this.model);
    JTableHeader header = this.table.getTableHeader();
    header.setFont(new Font("Angsana New", 0, 20));
    this.table.setFont(new Font("Angsana New", 0, 20));
    this.table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
          public void valueChanged(ListSelectionEvent arg0) {
            StockPanel.this.selectBtn.setEnabled(true);
            rmParcelBtn.setEnabled(true);
          }
        });
    rmParcelBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            Object selectedID = StockPanel.this.table.getValueAt(StockPanel.this.table.getSelectedRow(), 0);
            Object selectedName = StockPanel.this.table.getValueAt(StockPanel.this.table.getSelectedRow(), 1);
            StockPanel.selectedParcel[0] = (String)selectedID;
            StockPanel.selectedParcel[1] = (String)selectedName;
            int isConfirm = JOptionPane.showConfirmDialog(null, "ID : " + StockPanel.selectedParcel[1] + "\n: " + StockPanel.selectedParcel[0] + "?");
            System.out.println("Deleting ID : " + StockPanel.selectedParcel[1] + "\nSelected Name : " + StockPanel.selectedParcel[0]);
            if (isConfirm == 0) {
//              StockPanel.this.c.removeParcel(StockPanel.selectedParcel[0]);
            	po.getItems().remove(parcels.indexOf(StockPanel.selectedParcel[0]));
            	
              for (Parcel c : StockPanel.this.parcels) {
                if (selectedID == c.getId() && selectedName == c.getName()) {
                  StockPanel.this.parcels.remove(c);
                  break;
                } 
              } 
              StockPanel.this.model.removeRow(StockPanel.this.table.getSelectedRow());
            } 
            StockPanel.this.selectBtn.setEnabled(false);
            rmParcelBtn.setEnabled(false);
          }
        });
    for (int i = 0; i < loadItems().size(); i++) {
      String[] temp = { ((Parcel)this.parcels.get(i)).getId(), ((Parcel)this.parcels.get(i)).getName(), (new StringBuilder(String.valueOf(((Parcel)this.parcels.get(i)).getQuantity()))).toString() };
      this.model.addRow((Object[])temp);
    } 
    JScrollPane scrollPane = new JScrollPane(this.table);
    JPanel tablePanel = new JPanel();
    tablePanel.setLayout(new GridLayout());
    tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
    tablePanel.add(scrollPane);
    gridButtonPanel.add(this.selectBtn, "pushx ");
    gridButtonPanel.add(addParcelBtn, "align right");
    gridButtonPanel.add(rmParcelBtn, "wrap");
    add(tablePanel, "Center");
    add(gridButtonPanel, "South");
    addWindowListener(new WindowListener() {
          public void windowOpened(WindowEvent arg0) {}
          
          public void windowIconified(WindowEvent arg0) {}
          
          public void windowDeiconified(WindowEvent arg0) {}
          
          public void windowDeactivated(WindowEvent arg0) {}
          
          public void windowClosing(WindowEvent arg0) {
           
            System.out.println("Exiting");
          }
          
          public void windowClosed(WindowEvent arg0) {}
          
          public void windowActivated(WindowEvent arg0) {}
        });
   
    setResizable(true);
    setEnabled(true);
    setVisible(true);
    pack();
    setLocationRelativeTo((Component)null);
    setDefaultCloseOperation(3);
  }
  
  private ArrayList<Parcel> loadItems() {
    return po.getItems();
  }
  
  private void callNextFrame(String name, String id) {
    for (Parcel p : this.parcels) {
      if (name == p.getName() && id == p.getId()) {
        isOpened = true;
        DetailPanel panel = new DetailPanel(p);
        panel.addWindowListener(new WindowListener() {
              public void windowOpened(WindowEvent e) {}
              
              public void windowIconified(WindowEvent e) {}
              
              public void windowDeiconified(WindowEvent e) {}
              
              public void windowDeactivated(WindowEvent e) {}
              
              public void windowClosing(WindowEvent e) {
                System.out.println("Exit history");
                StockPanel.this.model.setRowCount(0);
                for (int i = 0; i < StockPanel.this.loadItems().size(); i++) {
                  String[] temp = { ((Parcel)StockPanel.this.parcels.get(i)).getId(), ((Parcel)StockPanel.this.parcels.get(i)).getName(), (new StringBuilder(String.valueOf(((Parcel)StockPanel.this.parcels.get(i)).getQuantity()))).toString() };
                  StockPanel.this.model.addRow((Object[])temp);
                } 
                StockPanel.this.selectBtn.setEnabled(false);
                StockPanel.this.table.clearSelection();
              }
              
              public void windowClosed(WindowEvent e) {}
              
              public void windowActivated(WindowEvent e) {}
            });
        break;
      } 
    } 
  }
}

