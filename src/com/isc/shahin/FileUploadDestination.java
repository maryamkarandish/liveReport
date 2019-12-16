package com.isc.shahin;
import com.isc.shahin.entities.AccountTotal;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public class FileUploadDestination extends JFrame {
    private JTable table;
    ArrayList<AccountTotal> accountTotals = new ArrayList<AccountTotal>();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                FileUploadDestination form = new FileUploadDestination();
                form.setVisible(true);

            }
        });
    }
    public FileUploadDestination(){


        // Create Form Frame
        setSize(668, 345);
        setLocation(500, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        // Label Result
        final JLabel lblResult = new JLabel("Result", JLabel.CENTER);
        lblResult.setBounds(150, 22, 370, 14);
        getContentPane().add(lblResult);

        // Table
        table = new JTable();
        getContentPane().add(table);

        // Table Model
        final DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.addColumn("CustomerID");
        model.addColumn("Name");
        model.addColumn("Email");
        model.addColumn("CountryCode");
        model.addColumn("Budget");
        model.addColumn("Used");

        // ScrollPane
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(84, 98, 506, 79);
        getContentPane().add(scroll);

        // Create Button Open JFileChooser
        JButton btnButton = new JButton("Open File Chooser");
        btnButton.setBounds(268, 47, 135, 23);
        btnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileopen = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter(
                        "Text/CSV file", "txt", "csv");
                fileopen.addChoosableFileFilter(filter);
                int ret = fileopen.showDialog(null, "Choose file");
                if (ret == JFileChooser.APPROVE_OPTION) {
                // Read Text file
                    File file = fileopen.getSelectedFile();
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        int row = 0;
                        while ((line = br.readLine()) != null) {
                            String[] arr = line.split(",");
                            AccountTotal accountTotalrow = new AccountTotal();
                            accountTotalrow = parseColumnValue(arr);
                            model.addRow(new Object[0]);
                            model.setValueAt(accountTotalrow.getId(), row, 0);
                            model.setValueAt(accountTotalrow.getBank(), row, 1);
                            model.setValueAt(accountTotalrow.getAccountOwnerName(), row, 2);
                            model.setValueAt(accountTotalrow.getCustomerNo(), row, 3);
                            model.setValueAt(accountTotalrow.getCustomerType(), row, 4);
                            model.setValueAt(accountTotalrow.getNationalCode(), row, 5);
                            row++;
                            accountTotals.add(accountTotalrow);
                         }
                        br.close();
                    } catch (IOException ex) {
                        // TODO Auto-generated catch block
                        ex.printStackTrace();
                    }
                    lblResult.setText(fileopen.getSelectedFile().toString());
                }
            }
        });
        getContentPane().add(btnButton);
        // Button Save
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SaveData(accountTotals); // save Data
            }
        });
        btnSave.setBounds(292, 228, 89, 23);
        getContentPane().add(btnSave);

    }

    private void SaveData(ArrayList<AccountTotal> accountTotals)
    {
        Connection connect = null;
        Statement s = null;
        try {
            connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ShahinLive", "postgres", "123");
            s = connect.createStatement();

            for(int i = 0; i<table.getRowCount();i++)
            {
                // SQL Insert
                String sql = "INSERT INTO obh_account "
                        + "(id,bank,customer_number,account_owner_name,account_type,branch,national_code,enabled,is_surgecharge_acc,customer_type, trustee_client) "
                        + "VALUES ('" + accountTotals.get(i).getId() + "','"
                        + accountTotals.get(i).getBank() + "','"
                        + accountTotals.get(i).getCustomerNo() + "'" + ",'"
                        + accountTotals.get(i).getAccountOwnerName() + "','"
                        + accountTotals.get(i).getAccountType() + "','"
                        + accountTotals.get(i).getBranch() + "','"
                        + accountTotals.get(i).getNationalCode() + "','"
                        + accountTotals.get(i).isEnabled() + "','"
                        + accountTotals.get(i).isSurgeChargeAcc() + "','"
                        + accountTotals.get(i).getCustomerType() + "','"
                        + accountTotals.get(i).getTrusteeClient() + "') ";
                System.out.println(sql.toUpperCase());
                s.execute(sql.toUpperCase());
            }
            JOptionPane.showMessageDialog(null,
                    "Import Data Successfully");
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, ex.getMessage());
            ex.printStackTrace();
        }
        try {
            if (s != null) {
                s.close();
                connect.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private AccountTotal parseColumnValue(String[] arr){
        ArrayList<AccountTotal> accountTotals = new ArrayList<AccountTotal>();
        String[] result = arr[0].split("\\|");
        AccountTotal accountTotalMap = new AccountTotal();
        try{
            accountTotalMap.setId(Integer.valueOf(result[0]));
            accountTotalMap.setBank(result[1]);
            accountTotalMap.setCustomerNo(result[2]);
            accountTotalMap.setAccountOwnerName(result[3]);
            accountTotalMap.setAccountType(result[4]);
            accountTotalMap.setBranch(result[5]);
            accountTotalMap.setNationalCode(result[6]);
            accountTotalMap.setCreationTime(result[7]); //todo
            accountTotalMap.setEnabled(result[8].equals("1")?true:false);
            accountTotalMap.setSurgeChargeAcc(result[9].equals("1")?true:false);
            accountTotalMap.setCustomerType(result[10].equals("01")?"individual":"organization");
            accountTotalMap.setTrusteeClient(result[11]);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return  accountTotalMap;
    }

}
