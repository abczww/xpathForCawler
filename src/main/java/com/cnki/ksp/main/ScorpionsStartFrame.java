package com.cnki.ksp.main;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.cnki.ksp.controller.KSPController;
import com.cnki.ksp.core.AppContext;
import com.cnki.ksp.core.CrawlerController;
import com.cnki.ksp.core.StyleChangedException;
import com.cnki.ksp.core.Version;
import com.cnki.ksp.core.observer.KspObserver;
import com.cnki.ksp.core.observer.ObserverFactory;

/**
 *
 * The main frame of the KSP, it's the entrance of the program.
 *
 */
public class ScorpionsStartFrame extends JFrame {

	private static final long serialVersionUID = -3686716486219841278L;
	private static JTextArea jta_info = new JTextArea();
	private JTextField jtf_topic = new JTextField();
	private JTextField jtf_message = new JTextField();
	private JButton jbtn_close = new JButton("Close");
	private JButton jbtn_scorpinAction = new JButton("Action");
	private Timer sysTimer;

	private final String title = "KScorpion In Action " + Version.LATEST_VERSION;

	public ScorpionsStartFrame() {
		initFrame();
		initTimer();
	}

	public void initFrame() {
		this.setLayout(null);

		jbtn_scorpinAction.setBounds(50, 10, 120, 35);
		jbtn_scorpinAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jbtn_scorpinAction.setEnabled(false);
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

		jbtn_close.setBounds(180, 10, 120, 35);
		jbtn_close.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				sysTimer.stop();
				System.exit(0);
			}

		});

		JLabel jll_info = new JLabel("Info");
		jll_info.setBounds(10, 50, 100, 30);

		jtf_message.setBounds(50, 50, 650, 30);

		JLabel jll_topic = new JLabel("Topic");
		jll_topic.setBounds(10, 90, 100, 30);

		jtf_topic = new JTextField();
		jtf_topic.setBounds(50, 90, 650, 30);

		JScrollPane jsp_info = new JScrollPane(jta_info);
		jsp_info.setBounds(10, 130, 780, 430);

		Container con = this.getContentPane();
		con.add(jbtn_close);
		con.add(jbtn_scorpinAction);
		con.add(jll_info);
		con.add(jtf_message);
		con.add(jll_topic);
		con.add(jtf_topic);
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
			KspObserver observer = (KspObserver)ObserverFactory.getObserverByName(ObserverFactory.KSP_FRAME_OBSERVER);
			observer.setHandler(jta_info);
			KSPController tsp = AppContext.getBean(KSPController.class);
			for (CrawlerController cc : tsp.getCrawlers()) {
				cc.init(observer);
				jtf_topic.setText(cc.getKspName() + " is getting articles from " + cc.getTopic());
				cc.run();
			}
		} catch (StyleChangedException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ScorpionsStartFrame();
	}

}
