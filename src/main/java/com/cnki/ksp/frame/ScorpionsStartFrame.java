package com.cnki.ksp.frame;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.cnki.ksp.core.AppContext;
import com.cnki.ksp.core.CrawlerController;
import com.cnki.ksp.core.KspObserver;

public class ScorpionsStartFrame extends JFrame {

	private static final long serialVersionUID = -3686716486219841278L;
	private static JTextArea jta_info = new JTextArea();
	private JTextField jtf_message = new JTextField();
	private JButton jbtn_close = new JButton("Close");
	private JButton jbtn_scorpinAction = new JButton("Action");
	private Timer sysTimer;
	
	private final String title = "KScorpion In Action V1.0.1";

	public ScorpionsStartFrame() {
		initFrame();
		initTimer();
	}

	public void initFrame() {
		this.setLayout(null);

		jbtn_scorpinAction.setBounds(10, 10, 120, 40);
		jbtn_scorpinAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jbtn_scorpinAction.setVisible(false);
				jbtn_close.setVisible(true);
				jtf_message.setText("KScorpion is running...");
				new Thread() {
					@Override
					public void run() {
						startAutohomeScorpion();
					}
				}.start();
			}
		});

		jbtn_close.setBounds(10, 10, 120, 40);
		jbtn_close.setVisible(false);
		jbtn_close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sysTimer.stop();
				System.exit(0);
			}

		});

		jtf_message.setBounds(140, 10, 650, 40);

		JScrollPane jsp_info = new JScrollPane(jta_info);
		jsp_info.setBounds(10, 60, 780, 500);

		Container con = this.getContentPane();
		con.add(jbtn_close);
		con.add(jbtn_scorpinAction);
		con.add(jtf_message);
		con.add(jsp_info);

		this.setSize(800, 600);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("OK, exit.");
				System.exit(0);
			}

		});
		this.setTitle(title);
		this.setVisible(true);
	}

	private void initTimer() {
		final long startTime = System.currentTimeMillis();
		sysTimer = new Timer(10000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				long currentTime = System.currentTimeMillis();
				long minutes = (currentTime - startTime) / 1000 / 60;
				jtf_message.setText("KScorpion is running for " + minutes + " minutes");
			}
		});
		sysTimer.start();
	}

	private void startAutohomeScorpion() {
		try {
			KspObserver observer = KspObserver.getIntance();
			observer.setHandler(jta_info);
			CrawlerController cc = AppContext.getBean("autohomeController", CrawlerController.class);
			cc.init(observer);
			cc.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ScorpionsStartFrame();
	}

}
