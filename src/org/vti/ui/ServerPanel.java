package org.vti.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.vti.enumeration.Version;
import org.vti.service.ExploitService;
import org.vti.service.impl.Struts2_S016_ExploitServiceImpl;
import org.vti.service.impl.Struts2_S019_ExploitServiceImpl;
import org.vti.service.impl.Struts2_S032_ExploitServiceImpl;
import org.vti.service.impl.Struts2_S038_ExploitServiceImpl;
import org.vti.service.impl.Struts2_S09_ExploitServiceImpl;

public class ServerPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private JButton getJButton;
	
	private JTextPane textPane;
	
	private String host;
	
	private Version version;
	
	public ServerPanel(){
		setSize(600,460);
		
		JLabel infoJLabel = new JLabel("服务器信息");
		
		getJButton = new JButton("获取");
		getJButton.addActionListener(this);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		
		JScrollPane scrollPane=new JScrollPane(textPane);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(infoJLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 460, Short.MAX_VALUE)
							.addComponent(getJButton, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(infoJLabel)
						.addComponent(getJButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
					.addContainerGap())
		);
		setLayout(groupLayout);
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==getJButton) {
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
			textPane.setText("");
			if (host!=null) {
				textPane.setText("请稍候...");
				
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
				
				Map<String, String>properties=new LinkedHashMap<String,String>();
				properties.put("\u64cd\u4f5c\u7cfb\u7edf\uff1a", "os.name");
				properties.put("\u5f53\u524d\u7528\u6237\uff1a", "user.name");
				properties.put("\u5f53\u524d\u7528\u6237\u76ee\u5f55\uff1a", "user.home");
				properties.put("\u004a\u0052\u0045\u76ee\u5f55\uff1a", "java.home");
				
				String info=service.getRealPath(host)+"\r\n";
			
				for (Entry<String, String>entry:service.getServerInfo(host, properties).entrySet()) {
					info+=entry.getKey()+entry.getValue()+"\r\n";
				}
				
				textPane.setText(info);
			
			}else {
				JOptionPane.showMessageDialog(this, "请输入执行命令");
			}
		} catch (Exception exp) {
			textPane.setText(exp.getMessage());
		}
	}
	
	public void setReqestUrl(String host){
		this.host=host;
	}
	
	public void setVersion(Version version){
		this.version=version;
	}
	
}
