package com.cnki.ksp.check;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.cnki.ksp.controller.autohome.AutohomeBBSController;
import com.cnki.ksp.controller.autohome.AutohomeEvaluateController;
import com.cnki.ksp.core.AppContext;
import com.cnki.ksp.core.CrawlerController;
import com.cnki.ksp.core.StyleChangedException;
import com.cnki.ksp.core.XPathUtilTools;
import com.cnki.ksp.helper.JSoupConnectionHelper;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;

public class ArticleHelperFrame extends JFrame {

    private static final long serialVersionUID = -5180414036371694421L;
    JTextField jt_url = new JTextField();
    JTextField jt_title = new JTextField();
    JTextField jt_author = new JTextField();
    JTextField jt_date = new JTextField();
    JTextField jt_content = new JTextField();
    JTextField jt_forward = new JTextField();
    JButton jbtn_analysize = new JButton("Analysize");

    JTextArea jta_content = new JTextArea();
    Container con = this.getContentPane();
    Font font = new Font("宋体", Font.BOLD, 12);

    public ArticleHelperFrame() {
        this.setLayout(null);

        JLabel jl_title = new JLabel("XPath Helper", SwingUtilities.CENTER);
        jl_title.setFont(new Font("宋体", Font.BOLD, 16));
        jl_title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jl_title.setBounds(0, 0, 200, 30);

        JLabel jl_url = new JLabel("URL：", SwingUtilities.RIGHT);
        JLabel jl_titleh = new JLabel("Title：", SwingUtilities.RIGHT);
        JLabel jl_author = new JLabel("Author:", SwingUtilities.RIGHT);
        JLabel jl_date = new JLabel("Date:", SwingUtilities.RIGHT);
        JLabel jl_content = new JLabel("Content:", SwingUtilities.RIGHT);
        JLabel jl_forward = new JLabel("Forward", SwingUtilities.RIGHT);

        jl_content.setFont(font);
        jl_content.setBounds(60, 300, 60, 20);

        jta_content.setFont(font);
        JScrollPane jspane = new JScrollPane(jta_content);
        jspane.setBounds(120, 300, 660, 200);

        con.add(jl_title);
        addToContainer(jl_url, LEFT);
        addToContainer(jt_url, RIGHT);
        addButtonToContainer(jbtn_analysize, RIGHT);
        addToContainer(jl_titleh, LEFT);
        addToContainer(jt_title, RIGHT);
        addToContainer(jl_author, LEFT);
        addToContainer(jt_author, RIGHT);
        addToContainer(jl_date, LEFT);
        addToContainer(jt_date, RIGHT);
        addToContainer(jl_content, LEFT);
        addToContainer(jt_content, RIGHT);
        addToContainer(jl_forward, LEFT);
        addToContainer(jt_forward, RIGHT);

        con.add(jl_content);
        con.add(jspane);

        // loadDefaultValues();
        initXPath2();

        this.setVisible(true);
        this.setSize(800, 600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        jbtn_analysize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // saveDefaultValues();
                analysize();
            }
        });

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                jta_content.setText("");
                // saveDefaultValues();
                System.exit(0);
            }

        });
    }

    private void initXPath() {
        AutohomeBBSController coller = AppContext.getBean("autohomeController_bj40", AutohomeBBSController.class);
        Properties prop = coller.getControllerProperties();

        jt_title.setText(String.valueOf(prop.getProperty("xTitle")));
        jt_author.setText(String.valueOf(prop.getProperty("xAuthor")));
        jt_date.setText(String.valueOf(prop.getProperty("xDate")));
        jt_content.setText(String.valueOf(prop.getProperty("xContent")));
        jt_forward.setText(String.valueOf(prop.getProperty("xForward")));
        jt_url.setText(String.valueOf(prop.getProperty("entranceUrl")));

    }
    
    private void initXPath2() {
    	AutohomeEvaluateController coller = (AutohomeEvaluateController)AppContext.getBean("autohomeController_bj20_evalute", AutohomeEvaluateController.class);
        Properties prop = coller.getControllerProperties();

        jt_title.setText(String.valueOf(prop.getProperty("xTitle")));
        jt_author.setText(String.valueOf(prop.getProperty("xAuthor")));
        jt_date.setText(String.valueOf(prop.getProperty("xDate")));
        jt_content.setText(String.valueOf(prop.getProperty("xContent")));
        jt_forward.setText(String.valueOf(prop.getProperty("xForward")));
        jt_url.setText(String.valueOf(prop.getProperty("entranceUrl")));

    }

    private void analysize() {
        System.out.println("analysize...");
        String url = jt_url.getText();
        JXDocument xdoc;
        try {
            xdoc = JSoupConnectionHelper.getXDocumentFromUrl(url, 5000);
            System.out.println(xdoc);
            String title = getContentFromXdoc(xdoc, jt_title.getText());
            String author = getContentFromXdoc(xdoc, jt_author.getText());
            String date = getContentFromXdoc(xdoc, jt_date.getText());
            //String content = xpathTools.getContentByXPath(jt_content.getText());
            String forward = getContentFromXdoc(xdoc, jt_forward.getText());

            jta_content.append("Title: " + title + "\n");
            jta_content.append("Author: " + author + "\n");
            jta_content.append("Date: " + date + "\n");
            jta_content.append("Forward: " + forward + "\n");
            //jta_content.append("Content: " + content + "\n");

        } catch (Exception e) {
        	e.printStackTrace();
            this.jta_content.append(e.getMessage());
        }

    }
    
    private String getContentFromXdoc(JXDocument xdoc, String xPath){
    	XPathUtilTools xpathTools = new XPathUtilTools(xdoc);
    	String content = "Style Changed! Please change the related xPat.h";
        try {
        	content = xpathTools.getContentByXPath(xPath);
		} catch (XpathSyntaxErrorException e) {
			e.printStackTrace();
		} catch (StyleChangedException e) {
			e.printStackTrace();
		}
        
        return content;
    }

    private final int RIGHT = 1;
    private final int LEFT = 0;
    int left_x = 60;
    int left_width = 60;
    int left_height = 22;

    int right_x = 120;
    int right_width = 500;
    int right_height = 22;

    int y_position = 40;

    private void addToContainer(Component comp, int flag) {
        comp.setFont(font);
        if (LEFT == flag) {
            comp.setBounds(left_x, y_position, left_width, left_height);
        } else {
            comp.setBounds(right_x, y_position, right_width, right_height);
            y_position = y_position + 30;
        }
        con.add(comp);
    }

    private void addButtonToContainer(Component comp, int flag) {
        comp.setFont(font);
        if (LEFT == flag) {
            comp.setBounds(left_x, y_position, 120, 40);
        } else {
            comp.setBounds(right_x, y_position, 120, 40);
            y_position = y_position + 50;
        }
        con.add(comp);
    }

    public void loadDefaultValues() {
        Properties prop = new Properties();
        try {
            // InputStream in = new BufferedInputStream(new
            // FileInputStream("xpathheper//xpath_helper.properties"));
            InputStream in = new FileInputStream(propFilePath);
            prop.load(in);
            jt_url.setText(prop.getProperty("url"));
            jt_title.setText(prop.getProperty("xTitle"));
            jt_author.setText(prop.getProperty("xAuthor"));
            jt_date.setText(prop.getProperty("xDate"));
            jt_content.setText(prop.getProperty("xContent"));
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefaultValues() {
        try {
            FileOutputStream fos = new FileOutputStream(propFilePath);
            Properties prop = new Properties();
            prop.setProperty("url", jt_url.getText());
            prop.setProperty("xTitle", jt_title.getText());
            prop.setProperty("xAuthor", jt_author.getText());
            prop.setProperty("xDate", jt_date.getText());
            prop.setProperty("xContent", jt_content.getText());
            prop.store(fos, "");
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ArticleHelperFrame();
    }

    String propFilePath = this.getClass().getClassLoader().getResource("xpathhelper/xpath_helper.properties").getPath();
}
