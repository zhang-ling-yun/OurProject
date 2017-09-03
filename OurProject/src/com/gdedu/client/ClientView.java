package com.gdedu.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.gdedu.model.NeedMessage;
import com.gdedu.util.ReceiveDataUtil;
import java.awt.Choice;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JPasswordField;
import java.awt.Color;

/**
 *
 * 项目名称：OurProject 类名称：ClientView 
 * 类描述：这是客户端的可视化界面
 * 创建人：rain 创建时间：2017年8月25日 下午9:36:13
 * 修改人：ASUS 修改时间：2017年8月25日 下午9:36:13 修改备注：
 * @version
 */
public class ClientView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField dbSID;
	private JTextField username;
	private JTextField fileText;
	private Choice language;
	private Choice db;
	private Choice port;
	private JPasswordField password;
	private JTextField hostname;
	private JTextField packageName;

	/**
	 * Create the frame.
	 */
	public ClientView() {
		setTitle("\u4EE3\u7801\u751F\u6210\u5668");
		setForeground(Color.CYAN);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 547, 552);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		language = new Choice();
		language.setFont(new Font("Dialog", Font.PLAIN, 13));
		language.setBounds(297, 79, 116, 25);
		language.add("java");
		language.add("C#");

		JLabel languageLabel = new JLabel("\u8BF7\u9009\u62E9\u8981\u751F\u6210\u7684\u8BED\u8A00:");
		languageLabel.setBounds(112, 83, 191, 21);
		languageLabel.setFont(new Font("宋体", Font.PLAIN, 13));

		JLabel dbLabel = new JLabel("\u8BF7\u9009\u62E9\u6570\u636E\u5E93\u7C7B\u578B:");
		dbLabel.setBounds(112, 125, 170, 21);
		dbLabel.setFont(new Font("宋体", Font.PLAIN, 13));

		db = new Choice();
		db.setFont(new Font("Dialog", Font.PLAIN, 13));
		db.setBounds(297, 121, 116, 25);
		db.add("ORACLE");
		db.add("MYSQL");
		db.add("SQL SERVER");

		JLabel dbSIDLabel = new JLabel("\u6570\u636E\u5E93\u540D\u79F0(SID):");
		dbSIDLabel.setBounds(112, 169, 170, 21);
		dbSIDLabel.setFont(new Font("宋体", Font.PLAIN, 13));

		dbSID = new JTextField();
		dbSID.setFont(new Font("宋体", Font.PLAIN, 13));
		dbSID.setBounds(296, 168, 117, 21);
		dbSID.setColumns(10);

		JLabel usernameLabel = new JLabel("\u7528\u6237\u540D:");
		usernameLabel.setBounds(112, 214, 54, 15);
		usernameLabel.setFont(new Font("宋体", Font.PLAIN, 13));

		username = new JTextField();
		username.setFont(new Font("宋体", Font.PLAIN, 13));
		username.setBounds(297, 208, 116, 21);
		username.setColumns(10);

		JLabel passwordLabel = new JLabel("\u5BC6\u7801:");
		passwordLabel.setBounds(112, 258, 70, 15);
		passwordLabel.setFont(new Font("宋体", Font.PLAIN, 13));

		JLabel fileLabel = new JLabel("\u751F\u6210\u6587\u4EF6\u76EE\u5F55:");
		fileLabel.setBounds(112, 392, 104, 21);
		fileLabel.setFont(new Font("宋体", Font.PLAIN, 13));

		fileText = new JTextField();
		fileText.setFont(new Font("宋体", Font.PLAIN, 13));
		fileText.setBounds(226, 391, 162, 21);
		fileText.setColumns(10);

		JButton submit = new JButton("\u751F\u6210\u6587\u4EF6");
		submit.setFont(new Font("宋体", Font.PLAIN, 13));
		submit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				try 
				(
					Socket socket = new Socket("127.0.0.1", 30000);
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				)		
				{		
					NeedMessage needMessage = NeedMessage.newInstance();
					needMessage.setPackagename(packageName.getText());
					needMessage.setLanguage(language.getSelectedItem());
					needMessage.setDb(db.getSelectedItem());
					needMessage.setDbSID(dbSID.getText());
					needMessage.setUsername(username.getText());
					needMessage.setPassword(new String(password.getPassword()));
					needMessage.setHostname(hostname.getText());
					needMessage.setPort(port.getSelectedItem());
					oos.writeObject(needMessage);
					
					int fileNum=ReceiveDataUtil.generateFiles(packageName.getText(), fileText.getText(), br);
					JOptionPane.showMessageDialog(null, "文件生成成功!共生成" + fileNum + "份文件");										
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		submit.setBounds(226, 446, 116, 33);
		contentPane.setLayout(null);
		contentPane.add(language);
		contentPane.add(languageLabel);
		contentPane.add(dbLabel);
		contentPane.add(db);
		contentPane.add(dbSIDLabel);
		contentPane.add(dbSID);
		contentPane.add(usernameLabel);
		contentPane.add(username);
		contentPane.add(passwordLabel);
		contentPane.add(fileLabel);
		contentPane.add(fileText);
		contentPane.add(submit);

		JButton browseButton = new JButton("\u6D4F\u89C8");
		browseButton.setFont(new Font("宋体", Font.PLAIN, 13));
		browseButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogTitle("请选择文件");
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(chooser);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File dir = chooser.getSelectedFile();
					fileText.setText(dir.getAbsolutePath());
				}
			}
		});
		browseButton.setBounds(402, 391, 93, 23);
		contentPane.add(browseButton);

		password = new JPasswordField();
		password.setFont(new Font("宋体", Font.PLAIN, 13));
		password.setBounds(297, 252, 116, 21);
		contentPane.add(password);

		JLabel hostnameLabel = new JLabel("\u4E3B\u673A\u540D:");
		hostnameLabel.setFont(new Font("宋体", Font.PLAIN, 13));
		hostnameLabel.setBounds(112, 299, 54, 21);
		contentPane.add(hostnameLabel);

		hostname = new JTextField();
		hostname.setFont(new Font("宋体", Font.PLAIN, 13));
		hostname.setText("127.0.0.1");
		hostname.setBounds(297, 298, 116, 21);
		contentPane.add(hostname);
		hostname.setColumns(10);

		JLabel portLabel = new JLabel("\u7AEF\u53E3\u53F7:");
		portLabel.setFont(new Font("宋体", Font.PLAIN, 13));
		portLabel.setBounds(112, 344, 54, 21);
		contentPane.add(portLabel);

		port = new Choice();
		port.setFont(new Font("Dialog", Font.PLAIN, 13));
		port.setBounds(297, 340, 116, 25);
		port.add("1521");
		port.add("3306");
		port.add("1433");
		contentPane.add(port);

		JLabel packageNameLabel = new JLabel(
				"\u751F\u6210\u7684\u5305\u540D\u524D\u7F00\u6216\u547D\u540D\u7A7A\u95F4:");
		packageNameLabel.setFont(new Font("宋体", Font.PLAIN, 13));
		packageNameLabel.setBounds(113, 35, 169, 15);
		contentPane.add(packageNameLabel);

		packageName = new JTextField();
		packageName.setFont(new Font("宋体", Font.PLAIN, 13));
		packageName.setBounds(297, 32, 116, 21);
		contentPane.add(packageName);
		packageName.setColumns(10);
	}
}
