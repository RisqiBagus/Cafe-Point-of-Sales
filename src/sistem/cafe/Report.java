/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistem.cafe;



import javax.swing.table.DefaultTableModel;
import Koneksi.Db_Koneksi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.sql.Timestamp;


/**
 *
 * @author Risqi Bagus Palevi
 */
public class Report extends javax.swing.JFrame {

    private DefaultTableModel model,model2,model3,model4,model5;
    int hasil, jmlTransaksi, tahun;
    String fhasil, fjmlTransaksi, bulan, tgl;
    /**
     * Creates new form Report
     */
    public Report() {
        initComponents();
         
    ShowDefault();     

    }
    
     public void CallRekapHarian(){
         
        tgl = txtTgl.getText(); 
        model = new DefaultTableModel();
        tblReport.setModel(model);
        model.addColumn("Kode Transaksi");
        model.addColumn("Customer");
        model.addColumn("Menu");
        model.addColumn("Jumlah");
        model.addColumn("Total Bayar");
         
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        try{
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "Select detail_transaksi.kode_transaksi, detail_transaksi.cust, detail_transaksi.menu, detail_transaksi.jumlah, detail_transaksi.total_bayar FROM detail_transaksi INNER JOIN transaksi ON detail_transaksi.kode_transaksi=transaksi.kode_transaksi WHERE DATE(transaksi.tgl_transaksi)= '"+tgl+"'";
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next()){
                Object[] obj = new Object[5];
                obj[0] = res.getString("kode_transaksi");
                obj[1] = res.getString("cust");
                obj[2] = res.getString("menu");
                obj[3] = res.getString("jumlah");
                obj[4] = res.getString("total_bayar");
             
                model.addRow(obj);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }       

         try{
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "select SUM(detail_transaksi.total_bayar) FROM detail_transaksi INNER JOIN transaksi ON detail_transaksi.kode_transaksi=transaksi.kode_transaksi WHERE DATE(transaksi.tgl_transaksi)= '"+tgl+"'";
            PreparedStatement p = (PreparedStatement) Db_Koneksi.getKoneksi().prepareStatement(sql);
            
            ResultSet a = p.executeQuery(sql);    
            
            if (a.next()) {
                hasil = a.getInt(1);
                fhasil = Integer.toString(hasil);
                txtOmzet.setText("Rp "+fhasil);
            }
          
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }                    
        try{
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "select COUNT( DISTINCT detail_transaksi.kode_transaksi) FROM detail_transaksi INNER JOIN transaksi ON detail_transaksi.kode_transaksi=transaksi.kode_transaksi WHERE DATE(transaksi.tgl_transaksi)= '"+tgl+"'";
            PreparedStatement p = (PreparedStatement) Db_Koneksi.getKoneksi().prepareStatement(sql);            
            ResultSet a = p.executeQuery(sql);    
            
            if (a.next()) {
                jmlTransaksi = a.getInt(1);
                fjmlTransaksi = Integer.toString(jmlTransaksi);
                txtJmlTransaksi.setText(""+fjmlTransaksi);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }                 
    }
     
    public void CallRekapBulanan () {
        
        bulan = txtBulan.getText();
        tahun = Integer.parseInt(txtTahun.getText());
        
        model2 = new DefaultTableModel();
        tblReport.setModel(model2);
        model2.addColumn("Kode Transaksi");
        model2.addColumn("Customer");
        model2.addColumn("Menu");
        model2.addColumn("Jumlah");
        model2.addColumn("Total Bayar");
        
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        
        try{
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "Select detail_transaksi.kode_transaksi, detail_transaksi.cust, detail_transaksi.menu, detail_transaksi.jumlah, detail_transaksi.total_bayar FROM detail_transaksi INNER JOIN transaksi ON detail_transaksi.kode_transaksi=transaksi.kode_transaksi WHERE MONTHNAME(transaksi.tgl_transaksi)= '"+bulan+"' AND YEAR(transaksi.tgl_transaksi)= "+tahun+" ";
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next()){
                Object[] obj = new Object[5];
                obj[0] = res.getString("kode_transaksi");
                obj[1] = res.getString("cust");
                obj[2] = res.getString("menu");
                obj[3] = res.getString("jumlah");
                obj[4] = res.getString("total_bayar");
             
                model2.addRow(obj);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        
        try{
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "select SUM(detail_transaksi.total_bayar) FROM detail_transaksi INNER JOIN transaksi ON detail_transaksi.kode_transaksi=transaksi.kode_transaksi WHERE MONTHNAME(transaksi.tgl_transaksi)= '"+bulan+"' AND YEAR(transaksi.tgl_transaksi)= "+tahun+"";
            PreparedStatement p = (PreparedStatement) Db_Koneksi.getKoneksi().prepareStatement(sql);

            ResultSet a = p.executeQuery(sql);    
            
            if (a.next()) {
                hasil = a.getInt(1);
                fhasil = Integer.toString(hasil);
                txtOmzet.setText("Rp "+fhasil);
            }                      
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }   
                 
        try{
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "select COUNT( DISTINCT detail_transaksi.kode_transaksi) FROM detail_transaksi INNER JOIN transaksi ON detail_transaksi.kode_transaksi=transaksi.kode_transaksi WHERE MONTHNAME(transaksi.tgl_transaksi)= '"+bulan+"' AND YEAR(transaksi.tgl_transaksi)= "+tahun+"";
            PreparedStatement p = (PreparedStatement) Db_Koneksi.getKoneksi().prepareStatement(sql);
            
            ResultSet a = p.executeQuery(sql);    
            
            if (a.next()) {
                jmlTransaksi = a.getInt(1);
                fjmlTransaksi = Integer.toString(jmlTransaksi);
                txtJmlTransaksi.setText(""+fjmlTransaksi);
            }        
            
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }    
    }
    
    public void CallRekapTahunan () {
        
        tahun = Integer.parseInt(txtTahun.getText());
        model3 = new DefaultTableModel();
        tblReport.setModel(model3);
        model3.addColumn("Kode Transaksi");
        model3.addColumn("Customer");
        model3.addColumn("Menu");
        model3.addColumn("Jumlah");
        model3.addColumn("Total Bayar");
        
        model3.getDataVector().removeAllElements();
        model3.fireTableDataChanged();
        
        try{
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "Select detail_transaksi.kode_transaksi, detail_transaksi.cust, detail_transaksi.menu, detail_transaksi.jumlah, detail_transaksi.total_bayar FROM detail_transaksi INNER JOIN transaksi ON detail_transaksi.kode_transaksi=transaksi.kode_transaksi WHERE YEAR(transaksi.tgl_transaksi)= "+tahun+" ";
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next()){
                Object[] obj = new Object[5];
                obj[0] = res.getString("kode_transaksi");
                obj[1] = res.getString("cust");
                obj[2] = res.getString("menu");
                obj[3] = res.getString("jumlah");
                obj[4] = res.getString("total_bayar");
             
                model3.addRow(obj);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
        
        try{
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "select SUM(detail_transaksi.total_bayar) FROM detail_transaksi INNER JOIN transaksi ON detail_transaksi.kode_transaksi=transaksi.kode_transaksi WHERE YEAR(transaksi.tgl_transaksi)= "+tahun+"";
            PreparedStatement p = (PreparedStatement) Db_Koneksi.getKoneksi().prepareStatement(sql);
            
            ResultSet a = p.executeQuery(sql);    
            
            if (a.next()) {
                hasil = a.getInt(1);
                fhasil = Integer.toString(hasil);
                txtOmzet.setText("Rp "+fhasil);
            }                           
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }   
                 
        try{
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "select COUNT( DISTINCT detail_transaksi.kode_transaksi) FROM detail_transaksi INNER JOIN transaksi ON detail_transaksi.kode_transaksi=transaksi.kode_transaksi WHERE YEAR(transaksi.tgl_transaksi)= "+tahun+"";
            PreparedStatement p = (PreparedStatement) Db_Koneksi.getKoneksi().prepareStatement(sql);
            
            ResultSet a = p.executeQuery(sql);    
            
            if (a.next()) {
                jmlTransaksi = a.getInt(1);
                fjmlTransaksi = Integer.toString(jmlTransaksi);
                txtJmlTransaksi.setText(""+fjmlTransaksi);
            }        
            
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }  
    }
    
    public void ShowDefault () {
        
        model5 = new DefaultTableModel();
        tblReport.setModel(model5);
        model5.addColumn("Customer");
        model5.addColumn("Menu");
        model5.addColumn("Jumlah");
        model5.addColumn("Keterangan");
        model5.addColumn("Harga Menu");
        model5.addColumn("Total Bayar");
        model5.addColumn("Kode Transaksi");
        
        model5.getDataVector().removeAllElements();
        model5.fireTableDataChanged();
        
        try{
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "Select * from detail_transaksi";
            ResultSet res = stat.executeQuery(sql);
            
            while(res.next()){
                Object[] obj = new Object[7];
                obj[0] = res.getString("cust");
                obj[1] = res.getString("menu");
                obj[2] = res.getString("jumlah");
                obj[3] = res.getString("keterangan");
                obj[4] = res.getString("harga_menu");
                obj[5] = res.getString("total_bayar");
                obj[6] = res.getString("kode_transaksi");
             
                model5.addRow(obj);
            }
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }  
        
        countPriceDisplay();
        countJumlahTransaksi();
    }
    
    public void countPriceDisplay(){
          try{
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "select SUM(total_bayar) from detail_transaksi";
            PreparedStatement p = (PreparedStatement) Db_Koneksi.getKoneksi().prepareStatement(sql);
            
            ResultSet a = p.executeQuery(sql);    
            
            if (a.next()) {
                hasil = a.getInt(1);
                fhasil = Integer.toString(hasil);
                txtOmzet.setText("Rp "+fhasil);
            }                                    
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }              
      }
    
    public void countJumlahTransaksi(){
          try{
            Statement stat = (Statement) Db_Koneksi.getKoneksi().createStatement();
            String sql = "select COUNT( DISTINCT kode_transaksi) from detail_transaksi";
            PreparedStatement p = (PreparedStatement) Db_Koneksi.getKoneksi().prepareStatement(sql);
            
            ResultSet a = p.executeQuery(sql);    
            
            if (a.next()) {
                jmlTransaksi = a.getInt(1);
                fjmlTransaksi = Integer.toString(jmlTransaksi);
                txtJmlTransaksi.setText(""+fjmlTransaksi);
            }                        
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }              
      }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        rbTgl = new javax.swing.JRadioButton();
        rbBulan = new javax.swing.JRadioButton();
        rbTahun = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        txtTahun = new javax.swing.JTextField();
        txtTgl = new javax.swing.JTextField();
        txtBulan = new javax.swing.JTextField();
        txtJmlTransaksi = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtOmzet = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblReport = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(250, 100));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setText("Format Tanggal : YYYY-MM-DD");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, -1, 20));

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setText("RESET");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 110, 90, -1));

        rbTgl.setBackground(new java.awt.Color(255, 255, 255));
        rbTgl.setText("Tanggal");
        rbTgl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTglActionPerformed(evt);
            }
        });
        jPanel1.add(rbTgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, -1, -1));

        rbBulan.setBackground(new java.awt.Color(255, 255, 255));
        rbBulan.setText("Bulan");
        rbBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbBulanActionPerformed(evt);
            }
        });
        jPanel1.add(rbBulan, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 110, -1, -1));

        rbTahun.setBackground(new java.awt.Color(255, 255, 255));
        rbTahun.setText("Tahun");
        rbTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbTahunActionPerformed(evt);
            }
        });
        jPanel1.add(rbTahun, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 110, -1, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setText("CARI");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, 90, -1));

        txtTahun.setEnabled(false);
        jPanel1.add(txtTahun, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 110, 60, -1));

        txtTgl.setEnabled(false);
        jPanel1.add(txtTgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 60, 210, -1));

        txtBulan.setEnabled(false);
        jPanel1.add(txtBulan, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 110, 60, -1));

        txtJmlTransaksi.setEditable(false);
        txtJmlTransaksi.setBackground(new java.awt.Color(0, 0, 0));
        txtJmlTransaksi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtJmlTransaksi.setForeground(new java.awt.Color(153, 255, 204));
        txtJmlTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJmlTransaksiActionPerformed(evt);
            }
        });
        jPanel1.add(txtJmlTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 430, 140, 30));

        jLabel3.setText("TOTAL TRANSAKSI :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 440, -1, 20));

        jLabel4.setText("OMSET : ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 480, -1, 20));

        txtOmzet.setEditable(false);
        txtOmzet.setBackground(new java.awt.Color(0, 0, 0));
        txtOmzet.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtOmzet.setForeground(new java.awt.Color(153, 255, 204));
        jPanel1.add(txtOmzet, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 470, 140, 30));

        jLabel1.setFont(new java.awt.Font("Work Sans", 1, 36)); // NOI18N
        jLabel1.setText("REPORT BERKALA");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 43, -1, -1));

        tblReport.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblReport.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblReport);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 880, 270));

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setText("Dashboard");
        jButton2.setPreferredSize(new java.awt.Dimension(85, 25));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 40, 130, 30));

        logo.setFont(new java.awt.Font("Nunito Sans Black", 2, 48)); // NOI18N
        logo.setForeground(new java.awt.Color(0, 204, 204));
        logo.setText("SiPOZKu");
        jPanel1.add(logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon("E:\\Kuliah\\Aset Gambar Projek AKhir Pmvs\\bg new.png")); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        GoToDashboard();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtJmlTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJmlTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJmlTransaksiActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       
        if (rbTgl.isSelected() == true) {
           CallRekapHarian();
       } else if (rbBulan.isSelected() == true) {
           CallRekapBulanan();
       } else if (rbTahun.isSelected() == true) {
           CallRekapTahunan();
       } else {
           JOptionPane.showMessageDialog(null, "Pilih Tanggal/Bulan dan Tahun/Tahun!","Warning",JOptionPane.WARNING_MESSAGE);
       }       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void rbTglActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTglActionPerformed
        
        if (rbTgl.isSelected() == true){
            txtTgl.setEnabled(true);
        } else {        
            txtTgl.setEnabled(false);
            txtTgl.setText("");
        }
    }//GEN-LAST:event_rbTglActionPerformed

    private void rbBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbBulanActionPerformed
        
        if (rbBulan.isSelected() == true){
            txtBulan.setEnabled(true);
            txtTahun.setEnabled(true);            
            } else {               
               txtTahun.setEnabled(false);
               txtBulan.setEnabled(false);
               txtBulan.setText("");
               txtTahun.setText("");
            }
    }//GEN-LAST:event_rbBulanActionPerformed

    private void rbTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbTahunActionPerformed
        if (rbTahun.isSelected() == true){               
                txtTahun.setEnabled(true);
                } else {            
                txtTahun.setEnabled(false);
                txtTahun.setText("");
                }
    }//GEN-LAST:event_rbTahunActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        txtTgl.setText("");
        txtBulan.setText("");
        txtTahun.setText("");
        ShowDefault();
    }//GEN-LAST:event_jButton3ActionPerformed

    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Report.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Report().setVisible(true);
            }
        });
    }

    public void GoToDashboard(){
        this.setVisible(false);
        new Dashboard().setVisible(true);
    }
    
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel logo;
    private javax.swing.JRadioButton rbBulan;
    private javax.swing.JRadioButton rbTahun;
    private javax.swing.JRadioButton rbTgl;
    private javax.swing.JTable tblReport;
    private javax.swing.JTextField txtBulan;
    private javax.swing.JTextField txtJmlTransaksi;
    private javax.swing.JTextField txtOmzet;
    private javax.swing.JTextField txtTahun;
    private javax.swing.JTextField txtTgl;
    // End of variables declaration//GEN-END:variables
}
