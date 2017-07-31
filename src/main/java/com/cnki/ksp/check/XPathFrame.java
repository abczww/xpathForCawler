package com.cnki.ksp.check;

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
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;

public class XPathFrame extends JFrame {

	private static final long serialVersionUID = -5180414036371694421L;
	JTextField jt_url = new JTextField();
	JTextField jt_xpath = new JTextField();
	JTextArea jta_content = new JTextArea();

	public XPathFrame() {
		Container con = this.getContentPane();
		this.setLayout(null);

		JLabel jl_title = new JLabel("XPath Helper", SwingUtilities.CENTER);
		Font font = new Font("宋体", Font.BOLD, 16);
		jl_title.setFont(font);
		jl_title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		jl_title.setBounds(0, 0, 200, 30);

		font = new Font("宋体", Font.PLAIN, 12);

		JLabel jl_url = new JLabel("URL：", SwingUtilities.RIGHT);
		jl_url.setFont(font);
		jl_url.setBounds(60, 40, 60, 20);

		jt_url.setFont(font);
		jt_url.setBounds(120, 40, 500, 20);

		JLabel jl_xpath = new JLabel("XPath：", SwingUtilities.RIGHT);
		jl_xpath.setFont(font);
		jl_xpath.setBounds(60, 70, 60, 20);

		jt_xpath.setFont(font);
		jt_xpath.setBounds(120, 70, 500, 20);
		jt_xpath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveDefaultValues();
				Document doc;
				try {
					doc = Jsoup.connect(jt_url.getText())
							.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0")
							.get();
					JXDocument xdoc = new JXDocument(doc);
					String xpath = jt_xpath.getText();
					List<Object> rs = xdoc.sel(xpath);
					String reValue = null;
					for (Object o : rs) {
						if (o instanceof Element) {
							Element ele = (Element) o;
							reValue = ele.toString();
						} else {
							reValue = null == o ? "" : o.toString();
						}
					}

					jta_content.setText(reValue==null?"null":reValue);
				} catch (IOException e1) {
					e1.printStackTrace();
					jta_content.setText(e1.getMessage());
				} catch (XpathSyntaxErrorException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					jta_content.setText(e1.getMessage());
				}
			}
		});

		JLabel jl_content = new JLabel("Content: ", SwingUtilities.RIGHT);
		jl_content.setFont(font);
		jl_content.setBounds(60, 100, 60, 20);

		jta_content.setFont(font);
		JScrollPane jspane = new JScrollPane(jta_content);
		jspane.setBounds(120, 100, 660, 400);

		con.add(jl_title);
		con.add(jl_url);
		con.add(jt_url);
		con.add(jl_xpath);
		con.add(jt_xpath);
		con.add(jl_content);
		con.add(jspane);

		loadDefaultValues();
		this.setVisible(true);
		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("OK");
				saveDefaultValues();
				System.exit(0);
			}

		});
	}

	public void loadDefaultValues() {
		Properties prop = new Properties();
		try {
			// InputStream in = new BufferedInputStream(new
			// FileInputStream("xpathheper//xpath_helper.properties"));
			InputStream in = new FileInputStream(propFilePath);
			prop.load(in);
			jt_url.setText(prop.getProperty("url"));
			jt_xpath.setText(prop.getProperty("xpath"));
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveDefaultValues() {
		try {
			FileOutputStream fos = new FileOutputStream(propFilePath);
			Properties prop = new Properties();
			prop.setProperty("url", jt_url.getText());
			prop.setProperty("xpath", jt_xpath.getText());
			prop.store(fos, "");
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new XPathFrame();
	}

	String propFilePath = this.getClass().getClassLoader().getResource("xpathhelper/xpath_helper.properties").getPath();
}
