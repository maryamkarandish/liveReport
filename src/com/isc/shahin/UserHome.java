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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
public class UserHome extends  JFrame{
    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JLabel label;
    private JPanel contentPane;
    private JTable table;
    private String username;
    public UserHome(String username){
        this.username = username;
//    }
//    public void UserHome(String Username){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 2000, 1000);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("نام کاربری");
        lblNewLabel.setForeground(Color.BLACK);
        lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 38));
        lblNewLabel.setBounds(1200, 10, 273, 93);
        contentPane.add(lblNewLabel);

        textField = new JTextField(username);
        textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        textField.setBounds(900, 10, 281, 68);
        contentPane.add(textField);
        textField.setColumns(10);
        //textField.setBackground(Color.GRAY);
        textField.setEnabled(false);

//        passwordField = new JPasswordField();
//        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
//        passwordField.setBounds(481, 286, 281, 68);
//        contentPane.add(passwordField);

//        JLabel lblUsername = new JLabel("Username");
//        lblUsername.setBackground(Color.BLACK);
//        lblUsername.setForeground(Color.BLACK);
//        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
//        lblUsername.setBounds(250, 166, 193, 52);
//        contentPane.add(lblUsername);

//        JLabel lblPassword = new JLabel("Password");
//        lblPassword.setForeground(Color.BLACK);
//        lblPassword.setBackground(Color.CYAN);
//        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
//        lblPassword.setBounds(250, 286, 193, 52);
//        contentPane.add(lblPassword);

        btnNewButton = new JButton("search");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
        btnNewButton.setBounds(545, 392, 162, 73);
        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String userName = textField.getText();
                //DisplayData da = new DisplayData(username);
                //da.setTitle("Lists");
               // da.setVisible(true);

            }
        });

        contentPane.add(btnNewButton);

        label = new JLabel("");
        label.setBounds(0, 0, 1008, 562);
        contentPane.add(label);
    }


}
