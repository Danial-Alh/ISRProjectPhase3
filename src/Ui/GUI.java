package Ui;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import sun.plugin.dom.core.*;
import sun.plugin.dom.core.Document;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Vector;
import  java.lang.Object;


/**
 * Created by mohammad on 3/9/16.
 */
public class GUI extends JFrame{
    JTextField queryField;
    Dimension screenSize;
    JButton open;
    JTextField fileField;
    JButton run;

    public void initFileChooser(){
        FileDialog fd = new FileDialog(this,"choose a file",FileDialog.LOAD);
        fd.setVisible(true);
        String str = fd.getDirectory()+fd.getFile();
        if(!str.equals("nullnull")){
            fileField.setText(fd.getDirectory()+fd.getFile());
        }
    }


    public GUI(){
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int width = (int)screenSize.getWidth();
        final int height = (int)screenSize.getHeight();
        this.setLocation(width/10,height/10);
        this.setSize(8*width/10,8*height/10);
        this.setLayout(null);

        queryField = new JTextField("Enter query");

        this.getContentPane().add(queryField);
        queryField.setSize(18*(int)this.getSize().width/20,2*(int)this.getHeight()/30);
        queryField.setLocation((int)this.getSize().width/20,(int)this.getHeight()/40);
        queryField.setVisible(true);

//        doc = new org.apache.lucene.document.Document();


        queryField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent focusEvent) {
                queryField.setText("");
            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                queryField.setText("Enter query");
            }
        });

//        fileField = new JTextField();
//        fileField.setSize(17*(int)this.getSize().width/20,2*(int)this.getHeight()/30);
//        fileField.setLocation((int)this.getSize().width/20,4*(int)this.getHeight()/40);
//        this.getContentPane().add(fileField);

//        open = new JButton("open");
//        open.setSize((int)this.getSize().width/20,2*(int)this.getHeight()/30);
//        open.setLocation(18*(int)this.getSize().width/20,4*(int)this.getHeight()/40);
//        this.getContentPane().add(open);
//        open.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent mouseEvent) {
//                initFileChooser();
//            }
//        });


        run = new JButton("RUN");
        run.setSize(200,(int)this.getHeight()/10);
        run.setLocation(4*(int)this.getSize().width/10,7*(int)this.getHeight()/40);
        this.getContentPane().add(run);
        run.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                String text = queryField.getText();
                Main.Main.process_user_query(text);
            }
        });
        this.setVisible(true);
    }
}
