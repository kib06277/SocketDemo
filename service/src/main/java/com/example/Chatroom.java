package com.example;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class Chatroom extends JFrame implements Server.OnServiceListener, ActionListener {
    private JLabel clientLabel;
    private JList clientList;
    private JLabel historyLabel;
    private JScrollPane jScrollPane;
    private JTextArea historyContentLabel;
    private JTextField messageText;
    private JButton sendButton;
    private Server server;
    private StringBuffer buffers;


    public Chatroom() {
        buffers = new StringBuffer();
        clientLabel = new JLabel("Customer");
        clientLabel.setBounds(0, 0, 100, 30);
        clientList = new JList<>();
        clientList.setBounds(0, 30, 100, 270);
        historyLabel = new JLabel("ChatHistory");
        historyLabel.setBounds(100, 0, 500, 30);

        historyContentLabel = new JTextArea();
        jScrollPane = new JScrollPane(historyContentLabel);
        jScrollPane.setBounds(100, 30, 500, 230);
        //分别设置水平和垂直滚动条自动出现
        jScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        historyContentLabel.setEditable(false);

        messageText = new JTextField();
        messageText.setBounds(100, 270, 440, 30);
        sendButton = new JButton("send");
        sendButton.setBounds(540, 270, 60, 30);


        sendButton.addActionListener(this);
        this.setLayout(null);

        add(clientLabel);
        add(clientList);
        add(historyLabel);
        add(jScrollPane);
        add(messageText);
        add(sendButton);

        //设置窗体
        this.setTitle("Chat Class");//窗体标签
        this.setSize(600, 330);//窗体大小
        this.setLocationRelativeTo(null);//在屏幕中间显示(居中显示)
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//退出关闭JFrame
        this.setVisible(true);//显示窗体
        this.setResizable(false);

        server = new Server();
        server.setOnServiceListener(this);
        server.start();
    }

    @Override
    public void onClientChanged(List<Client> clients) {
        // TODO Auto-generated method stub
        clientList.setListData(clients.toArray());

    }


    @Override
    public void onNewMessage(String message, Client client) {
        // TODO Auto-generated method stub
        buffers.append(client.getSocket().getInetAddress().toString() + "\n");
        buffers.append(message + "\n");
        historyContentLabel.setText(buffers.toString());
        historyContentLabel.setCaretPosition(historyContentLabel.getDocument().getLength());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == sendButton) {
            server.sendMessage("服務器#" + messageText.getText());
            buffers.append("服務器" + "\n");
            buffers.append(messageText.getText() + "\n");
            historyContentLabel.setText(buffers.toString());
        }
    }
}
