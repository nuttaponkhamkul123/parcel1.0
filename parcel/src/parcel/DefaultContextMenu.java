package parcel;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;

public class DefaultContextMenu extends JPopupMenu {
  private Clipboard clipboard;
  
  private UndoManager undoManager;
  
  private JMenuItem cut;
  
  private JMenuItem copy;
  
  private JMenuItem paste;
  
  private JTextComponent textComponent;
  
  public DefaultContextMenu() {
    this.undoManager = new UndoManager();
    this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    addPopupMenuItems();
  }
  
  private void addPopupMenuItems() {
    add(new JSeparator());
    this.cut = new JMenuItem("ตัด");
    this.cut.setEnabled(false);
    this.cut.setAccelerator(KeyStroke.getKeyStroke(88, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    this.cut.addActionListener(event -> this.textComponent.cut());
    add(this.cut);
    this.copy = new JMenuItem("คัดลอก");
    this.copy.setEnabled(false);
    this.copy.setAccelerator(KeyStroke.getKeyStroke(67, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    this.copy.addActionListener(event -> this.textComponent.copy());
    add(this.copy);
    this.paste = new JMenuItem("วาง");
    this.paste.setEnabled(false);
    this.paste.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
    this.paste.addActionListener(event -> this.textComponent.paste());
    add(this.paste);
    this.cut.setFont(new Font("Angsana New", 0, 25));
    this.copy.setFont(new Font("Angsana New", 0, 25));
    this.paste.setFont(new Font("Angsana New", 0, 25));
  }
  
  private void addTo(JTextComponent textComponent) {
    textComponent.addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent pressedEvent) {
            if (pressedEvent.getKeyCode() == 90 && (
              pressedEvent.getModifiersEx() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0)
              if (DefaultContextMenu.this.undoManager.canUndo())
                DefaultContextMenu.this.undoManager.undo();  
            if (pressedEvent.getKeyCode() == 89 && (
              pressedEvent.getModifiersEx() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0)
              if (DefaultContextMenu.this.undoManager.canRedo())
                DefaultContextMenu.this.undoManager.redo();  
          }
        });
    textComponent.addMouseListener(new MouseAdapter() {
          public void mousePressed(MouseEvent releasedEvent) {
            DefaultContextMenu.this.handleContextMenu(releasedEvent);
          }
          
          public void mouseReleased(MouseEvent releasedEvent) {
            DefaultContextMenu.this.handleContextMenu(releasedEvent);
          }
        });
    textComponent.getDocument().addUndoableEditListener(event -> {
        
        });
  }
  
  private void handleContextMenu(MouseEvent releasedEvent) {
    if (releasedEvent.getButton() == 3)
      processClick(releasedEvent); 
  }
  
  private void processClick(MouseEvent event) {
    this.textComponent = (JTextComponent)event.getSource();
    this.textComponent.requestFocus();
    boolean enableUndo = this.undoManager.canUndo();
    boolean enableRedo = this.undoManager.canRedo();
    boolean enableCut = false;
    boolean enableCopy = false;
    boolean enablePaste = false;
    boolean enableDelete = false;
    boolean enableSelectAll = false;
    String selectedText = this.textComponent.getSelectedText();
    String text = this.textComponent.getText();
    if (text != null)
      if (text.length() > 0)
        enableSelectAll = true;  
    if (selectedText != null)
      if (selectedText.length() > 0) {
        enableCut = true;
        enableCopy = true;
        enableDelete = true;
      }  
    if (this.clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor) && this.textComponent.isEnabled())
      enablePaste = true; 
    this.cut.setEnabled(enableCut);
    this.copy.setEnabled(enableCopy);
    this.paste.setEnabled(enablePaste);
    show(this.textComponent, event.getX(), event.getY());
  }
  
  public static void addDefaultContextMenu(JTextComponent component) {
    DefaultContextMenu defaultContextMenu = new DefaultContextMenu();
    defaultContextMenu.addTo(component);
  }
}

