package com.isc.shahin;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DisplayData extends JFrame implements ActionListener {
    JFrame frame1;
    JLabel l0, l1, l2,lCompanyName,lSandboxUsername;
    JComboBox c1;
    JTextField t1,tCompanyName,tSandboxUserName;
    JButton b1;
    Connection con;
    ResultSet rs, rs1;
    Statement st, st1;
    PreparedStatement pst;
    String ids;
    static JTable table;
    String from;

    private String userName;
    private String companyName;
    private String sandboxUname;

    String[] columnNames = {"نام کاربری", "رمز عبور", "شناسه حقوقی", "لینک بازگشت"};

    DisplayData(String username, String sandboxUname, String companyName){

        this.userName = username;
        this.companyName = companyName;
        this.sandboxUname = sandboxUname;

        l0 = new JLabel("Fatching Applications Information");
        l0.setForeground(Color.red);
        l0.setFont(new Font("Serif", Font.BOLD, 20));

        l1 = new JLabel("Select Username");
        l2 = new JLabel("Select application name");
        t1 = new JTextField(this.userName);
        b1 = new JButton("submit");

        lCompanyName = new JLabel("Company Name");
        lSandboxUsername = new JLabel("SandBox UserName");
        tCompanyName = new JTextField(this.companyName);
        tSandboxUserName = new JTextField(this.sandboxUname);

        l0.setBounds(400, 5, 350, 40);
        l1.setBounds(800, 80, 150, 20);
        l2.setBounds(800, 110, 150, 20);
        b1.setBounds(800, 150, 150, 20);
        t1.setBounds(600, 80, 150, 20);
        lCompanyName.setBounds(300, 80, 150, 20);
        lSandboxUsername.setBounds(300, 110, 150, 20);
        tCompanyName.setBounds(100, 80, 150, 20);
        tSandboxUserName.setBounds(100, 110, 150, 20);
        t1.setBounds(600, 80, 150, 20);

        b1.addActionListener(this);

        setTitle("Fetching Applications Info From DataBase");

        setLayout(null);

        setVisible(true);

        setSize(1000, 500);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(l0);
        add(l1);;
        add(l2);
        add(t1);
        add(b1);
        add(lCompanyName);
        add(lSandboxUsername);
        add(tCompanyName);
        add(tSandboxUserName);
        //add(frame1);

        try {

            con = DriverManager.getConnection("jdbc:postgresql://192.168.165.57:5432/obh", "testuser", "123");
            st = con.createStatement();
            PreparedStatement st = (PreparedStatement) con.prepareStatement("Select application_name from obh.application where client_id=? ");
            st.setString(1,this.userName);

            rs = st.executeQuery();

            Vector v = new Vector();

            while (rs.next()) {
                ids = rs.getString(1);
                v.add(ids);
            }

            c1 = new JComboBox(v);
            c1.setBounds(600, 110, 150, 20);
            add(c1);

            st.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == b1) {
            frame1 = new JFrame("Database Search Result");
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.setLayout(new BorderLayout());

            DefaultTableModel model = new DefaultTableModel();

            model.setColumnIdentifiers(columnNames);

            table = new JTable();
            table.setModel(model);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            table.setFillsViewportHeight(true);

            JScrollPane scroll = new JScrollPane(table);

            scroll.setHorizontalScrollBarPolicy(
                    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scroll.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

            //from = "3A6KZnZmZG"; //(String) c1.getSelectedItem();
            from = (String) c1.getSelectedItem();

            String uname = "";
            String email = "";
            String pass = "";
            String cou = "";

            try {

//                pst = con.prepareStatement("select * from obh.application where client_Id='" + from + "'");
                pst = con.prepareStatement("select * from obh.application where application_name=?");
                pst.setString(1,from);
                System.out.println(pst);

                ResultSet rs = pst.executeQuery();

                int i = 0;
                while (rs.next()) {

                    uname = rs.getString("client_id");
                    email = rs.getString("client_secret");
                    pass = rs.getString("client_user_name");
                    cou = rs.getString("redirect_uri");

                    model.addRow(new Object[]{uname, email, pass, cou});

                    i++;
                }

                if (i < 1) {
                    JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
                }

                System.out.println(i + " Records Found");

            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            }


           frame1.add(scroll);
           frame1.setVisible(true);
           frame1.setSize(400, 300);
        }



    }

}

