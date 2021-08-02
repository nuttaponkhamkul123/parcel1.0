package parcel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import net.miginfocom.swing.MigLayout;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class DetailPanel extends JFrame {
  private static final long serialVersionUID = 1L;
  
  private JPanel tfPanel;
  
  private JPanel tbPanel;
  
  private JTable table;
  
  private JScrollPane scrollPane;
  
  private boolean isFound = false;
  
  private Parcel selectedParcel = null;
  
  private DefaultTableModel model;
  
  private ArrayList<History> history;
  
  private Connector c;
  
  private StockOperator so; 
  
  private History temp_his;
  
  private int temp_quantity = 0;
  
  private final Font ANGSANA25 = new Font("Angsana New", 0, 25);
  
  private final Font ANGSANA25_BOLD = new Font("Angsana New", 1, 25);
  
  private ParcelOperator po;
  
  public DetailPanel(Parcel parcel) {
    UIManager.put("OptionPane.messageFont", new Font("Angsana New", 0, 20));
    so = new StockOperator();
    po = new ParcelOperator();
    this.tfPanel = new JPanel();
    this.tbPanel = new JPanel();
    so.fetchHistory(parcel.getId());
    this.history = so.getHistory();
    renderItemUi(parcel);
    if (!this.isFound)
      JOptionPane.showMessageDialog(null, "Not Found"); 
  }
  
  private void putItemInTable() {
    this.model.setRowCount(0);
    for (History index : this.history) {
      if (index.getStatus() == 1) {
        Object[] temp = { index.getDate(), index.getDocument(), Integer.valueOf(index.getQuantity()), Integer.valueOf(index.getRemaining()), "-", Integer.valueOf(index.getQuantity() + index.getRemaining()), index.getPicker(), index.getNote(), Long.valueOf(index.getHisID()) };
        this.model.addRow(temp);
        continue;
      } 
      if (index.getStatus() == 0) {
        Object[] temp = { index.getDate(), index.getDocument(), Integer.valueOf(index.getQuantity()), "-", Integer.valueOf(index.getRemaining()), Integer.valueOf(index.getQuantity() - index.getRemaining()), index.getPicker(), index.getNote(), Long.valueOf(index.getHisID()) };
        this.model.addRow(temp);
      } 
    } 
  }
  
  private void renderItemUi(final Parcel i) {
    this.tfPanel.setLayout((LayoutManager)new MigLayout("ins"));
    JLabel[] tfLabels = new JLabel[4];
    JTextField[] fields = new JTextField[4];
    tfLabels[0] = new JLabel("รหัสพัสดุ");
    tfLabels[1] = new JLabel("หน่วยนับ");
    tfLabels[2] = new JLabel("ชื่อพัสดุ(ทางการ)");
    tfLabels[3] = new JLabel("ชื่อพัสดุ(ไม่เป็นทางการ)");
    fields[0] = new JTextField();
    fields[1] = new JTextField();
    fields[2] = new JTextField();
    fields[3] = new JTextField();
    byte b;
    int j;
    JLabel[] arrayOfJLabel1;
    for (j = (arrayOfJLabel1 = tfLabels).length, b = 0; b < j; ) {
      JLabel l = arrayOfJLabel1[b];
      l.setFont(this.ANGSANA25);
      b++;
    } 
    JTextField[] arrayOfJTextField1;
    for (j = (arrayOfJTextField1 = fields).length, b = 0; b < j; ) {
      JTextField f = arrayOfJTextField1[b];
      f.setFont(this.ANGSANA25);
      
      b++;
    } 
    fields[0].setText(i.getId());
    fields[1].setText(i.getMeasure());
    fields[2].setText(i.getPublicName());
    fields[3].setText(i.getName());
    JButton addItembtn = new JButton("รับ-จ่ายพัสดุ");
    JButton exportExcelbtn = new JButton("ส่งออกไฟล์ Excel");
    addItembtn.setFont(this.ANGSANA25_BOLD);
    exportExcelbtn.setFont(this.ANGSANA25_BOLD);
    this.tfPanel.add(tfLabels[0]);
    this.tfPanel.add(fields[0], "w 100%");
    this.tfPanel.add(tfLabels[1]);
    this.tfPanel.add(fields[1], "w 100%,wrap");
    this.tfPanel.add(tfLabels[2]);
    this.tfPanel.add(fields[2], "w 100%,");
    this.tfPanel.add(tfLabels[3]);
    this.tfPanel.add(fields[3], "w 100%,wrap");
    this.tfPanel.add(addItembtn);
    this.tfPanel.add(exportExcelbtn);
    this.model = new DefaultTableModel() {
        private static final long serialVersionUID = 7152761192162720292L;
        
        public boolean isCellEditable(int row, int column) {
          return false;
        }
      };
    this.table = new JTable(this.model);
    JTableHeader header = this.table.getTableHeader();
    this.table.setFont(new Font("Angsana New", 0, 20));
    header.setFont(this.ANGSANA25_BOLD);
    JPanel downPanel = new JPanel();
    downPanel.setLayout((LayoutManager)new MigLayout("", "[][][]push[]", ""));
    JButton rmHisBtn = new JButton("ลบ");
    rmHisBtn.setFont(this.ANGSANA25_BOLD);
    downPanel.add(rmHisBtn, "span 10");
    this.model = (DefaultTableModel)this.table.getModel();
    this.model.addColumn("ว/ด/ป");
    this.model.addColumn("เอกสารรับ-ส่ง");
    this.model.addColumn("จำนวนที่มี");
    this.model.addColumn("รับ");
    this.model.addColumn("จ่าย");
    this.model.addColumn("คงเหลือ");
    this.model.addColumn("ชื่อผู้เบิก");
    this.model.addColumn("หมายเหตุ");
    this.model.addColumn("id");
    putItemInTable();
    TableColumnModel tcm = this.table.getColumnModel();
    tcm.removeColumn(tcm.getColumn(8));
    this.scrollPane = new JScrollPane(this.table);
    this.tbPanel.add(this.scrollPane);
    this.tbPanel.setLayout(new GridLayout());
    this.tbPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    add(this.tfPanel, "North");
    add(this.tbPanel, "Center");
    add(downPanel, "South");
    setResizable(true);
    setEnabled(true);
    setVisible(true);
    pack();
    setLocationRelativeTo((Component)null);
    setDefaultCloseOperation(2);
    this.isFound = true;
    this.selectedParcel = i;
    rmHisBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            int choice = JOptionPane.showConfirmDialog(null, "ต้องการจะลบหรือไม่?");
            if (choice == 0) {
              Object selectedID = DetailPanel.this.table.getModel().getValueAt(DetailPanel.this.table.getSelectedRow(), 8);
              System.out.println(DetailPanel.this.table.getModel().getValueAt(DetailPanel.this.table.getSelectedRow(), 8));
              DetailPanel.this.so.removeHistory((Long)selectedID);
              DetailPanel.this.so.fetchHistory(DetailPanel.this.selectedParcel.getId());
              DetailPanel.this.history = DetailPanel.this.so.getHistory();
              DetailPanel.this.putItemInTable();
            } 
          }
        });
    exportExcelbtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent arg0) {
            try {
              ExcelOperator ex = new ExcelOperator(DetailPanel.this.history, DetailPanel.this.selectedParcel);
              ex.operate();
            } catch (InvalidFormatException|java.io.IOException e) {
              e.printStackTrace();
            } 
          }
        });
    addItembtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            final JFrame dialog = new JFrame();
            dialog.setPreferredSize(new Dimension(300, 500));
            JPanel panel1 = new JPanel();
            panel1.setLayout(new GridLayout());
            JPanel radioPanel = new JPanel();
            JPanel tfPanel = new JPanel();
            tfPanel.setLayout((LayoutManager)new MigLayout());
            tfPanel.setAlignmentX(0.5F);
            radioPanel.setBorder(new TitledBorder("ประเภท"));
            ((TitledBorder)radioPanel.getBorder()).setTitleFont(DetailPanel.this.ANGSANA25_BOLD);
            final JRadioButton wdRb = new JRadioButton("จ่าย");
            final JRadioButton dpRb = new JRadioButton("รับ");
            panel1.setFont(new Font("Angsana New", 0, 25));
            JLabel maxLabel = new JLabel("จำนวนที่มี");
            JLabel currentLabel = new JLabel("จำนวน");
            final JTextField maximumQuantity = new JTextField();
           
            maximumQuantity.setText((new StringBuilder(String.valueOf(i.getQuantity()))).toString());
            maximumQuantity.setEditable(false);
            final JTextField currentQuantity = new JTextField();
            
            JLabel noteLabel = new JLabel("หมายเหตุ");
            noteLabel.setFont(new Font("Angsana New", 1, 18));
            final JTextArea note = new JTextArea();
            maximumQuantity.setPreferredSize(new Dimension(180, 25));
            currentQuantity.setPreferredSize(new Dimension(180, 25));
            note.setPreferredSize(new Dimension(250, 100));
            JLabel spinnerLabel = new JLabel("วัน/เดือน/ปี");
            final JTextField timeSpinner = new JTextField();
            
            timeSpinner.setPreferredSize(new Dimension(250, 25));
            JLabel documentLabel = new JLabel("เอกสารรับ-จ่าย");
            final JTextField documentTextField = new JTextField();
            
            documentTextField.setPreferredSize(new Dimension(120, 25));
            JLabel pickerLabel = new JLabel("ผู้เบิก");
            final JTextField pickerTextField = new JTextField("");
            
            pickerTextField.setPreferredSize(new Dimension(120, 25));
            JButton submitBtn = new JButton("ยืนยัน");
            submitBtn.addActionListener(new ActionListener() {
                  public void actionPerformed(ActionEvent e) {
                    if (wdRb.isSelected()) {
                      int tempQuantity = Integer.parseInt(maximumQuantity.getText());
                      try {
                        temp_quantity = Integer.parseInt(currentQuantity.getText());
                        if (temp_quantity > tempQuantity) {
                          JOptionPane.showMessageDialog(null, "เลขพัสดุไม่ถูกต้อง กรุณากรอกตัวเลขใหม่");
                        } else {
                          selectedParcel.removeParcel(Integer.parseInt(currentQuantity.getText()));
                          System.out.println("Removed : Current Parcel : " + selectedParcel.getQuantity());
                          Object[] temp = { timeSpinner.getText(), documentTextField.getText(), Integer.valueOf(tempQuantity), "-", currentQuantity.getText(), Long.valueOf(selectedParcel.getQuantity()), pickerTextField.getText(), note.getText() };
                          temp_his = new History(selectedParcel.getId(), timeSpinner.getText(), documentTextField.getText(), tempQuantity, 0, temp_quantity, (history.size() + 1), pickerTextField.getText(), note.getText());
                          history.add(temp_his);
                          maximumQuantity.setText((new StringBuilder(String.valueOf(selectedParcel.getQuantity()))).toString());
                          currentQuantity.setText("");
                          save();
                          model.addRow(temp);
                        } 
                      } catch (NumberFormatException eb) {
                        JOptionPane.showMessageDialog(null, "จำนวนที่กรอกไม่ใช่ตัวเลข กรุณากรอกใหม่");
                      } 
                    } else if (dpRb.isSelected()) {
                      try {
                        temp_quantity = Integer.parseInt(currentQuantity.getText());
                        int tempQuantity = Integer.parseInt(maximumQuantity.getText());
                        selectedParcel.addParcel(Integer.parseInt(currentQuantity.getText()));
                        System.out.println("Added : Current Parcel : " + selectedParcel.getQuantity());
                        Object[] temp = { timeSpinner.getText(), documentTextField.getText(), maximumQuantity.getText(), currentQuantity.getText(), "-", Long.valueOf(selectedParcel.getQuantity()), pickerTextField.getText(), note.getText() };
                        temp_his = new History(selectedParcel.getId(), timeSpinner.getText(), documentTextField.getText(), tempQuantity, 1, temp_quantity, history.size() + 1, pickerTextField.getText(), note.getText());
                        System.out.println(selectedParcel.getQuantity());
                        history.add(temp_his);
                        maximumQuantity.setText((new StringBuilder(String.valueOf(selectedParcel.getQuantity()))).toString());
                        currentQuantity.setText("");
                        save();
                        model.addRow(temp);
                      } catch (NumberFormatException ea) {
                        JOptionPane.showMessageDialog(null, "จำนวนที่กรอกไม่ใช่ตัวเลข กรุณากรอกใหม่");
                      } 
                    } else {
                      JOptionPane.showMessageDialog(null, "Please select some of action");
                    } 
                    dialog.dispose();
                  }
                });
            spinnerLabel.setFont(DetailPanel.this.ANGSANA25_BOLD);
            timeSpinner.setFont(DetailPanel.this.ANGSANA25_BOLD);
            wdRb.setFont(DetailPanel.this.ANGSANA25_BOLD);
            dpRb.setFont(DetailPanel.this.ANGSANA25_BOLD);
            submitBtn.setFont(DetailPanel.this.ANGSANA25_BOLD);
            maxLabel.setFont(DetailPanel.this.ANGSANA25_BOLD);
            maximumQuantity.setFont(DetailPanel.this.ANGSANA25_BOLD);
            currentLabel.setFont(DetailPanel.this.ANGSANA25_BOLD);
            currentQuantity.setFont(DetailPanel.this.ANGSANA25_BOLD);
            documentLabel.setFont(DetailPanel.this.ANGSANA25_BOLD);
            documentTextField.setFont(DetailPanel.this.ANGSANA25_BOLD);
            pickerLabel.setFont(DetailPanel.this.ANGSANA25_BOLD);
            pickerTextField.setFont(DetailPanel.this.ANGSANA25_BOLD);
            note.setFont(DetailPanel.this.ANGSANA25_BOLD);
            tfPanel.add(spinnerLabel);
            tfPanel.add(timeSpinner, "wrap");
            tfPanel.add(maxLabel);
            tfPanel.add(maximumQuantity, "wrap");
            tfPanel.add(currentLabel);
            tfPanel.add(currentQuantity, "growx, wrap");
            tfPanel.add(documentLabel);
            tfPanel.add(documentTextField, "growx, wrap");
            tfPanel.add(pickerLabel);
            tfPanel.add(pickerTextField, "growx, wrap");
            tfPanel.add(noteLabel, "h 40px,wrap");
            tfPanel.add(note, "span,w 300px");
            ButtonGroup bg = new ButtonGroup();
            bg.add(wdRb);
            bg.add(dpRb);
            radioPanel.add(wdRb);
            radioPanel.add(dpRb);
            panel1.add(radioPanel);
            dialog.add(panel1, "North");
            dialog.add(tfPanel, "Center");
            dialog.add(submitBtn, "South");
            dialog.pack();
            dialog.setLocationRelativeTo((Component)null);
            dialog.setTitle("Add Item");
            dialog.setVisible(true);
            dialog.setResizable(true);
          }
        });
  }
  
  private void save() {
	
	this.so.updateHistory(Long.parseLong(this.temp_his.getItemID()), this.selectedParcel);
	
	this.po.updateParcel(this.selectedParcel);
	this.po.saveParcel();
    this.temp_his = null;
    this.temp_quantity = 0;
  }
}

