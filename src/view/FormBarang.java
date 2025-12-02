package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Barang;
import util.NewHibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class FormBarang extends javax.swing.JFrame {

    public FormBarang() {
        initComponents();
        loadTable();
    }

    // ========================= LOAD DATA =============================
    private void loadTable() {
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        List<Barang> list = s.createQuery("FROM Barang").list();

        DefaultTableModel m = new DefaultTableModel(
                new String[]{"ID", "Kode", "Nama", "Harga", "Stok"}, 0);

        for (Barang b : list) {
            m.addRow(new Object[]{
                b.getId(),
                b.getKode_barang(),
                b.getNama_barang(),
                b.getHarga(),
                b.getStok()
            });
        }

        tableBarang.setModel(m);
        s.close();
    }

    // ========================= SAVE =============================
    private void saveData() {
        Session s = NewHibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Barang b = new Barang();
        b.setKode_barang(txtKode.getText());
        b.setNama_barang(txtNama.getText());
        b.setHarga(Integer.parseInt(txtHarga.getText()));
        b.setStok(Integer.parseInt(txtStok.getText()));

        s.save(b);
        t.commit();
        s.close();
        loadTable();

        JOptionPane.showMessageDialog(this, "Data berhasil disimpan!");
    }

    // ========================= UPDATE =============================
    private void updateData() {
        int row = tableBarang.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            return;
        }

        int id = Integer.parseInt(tableBarang.getValueAt(row, 0).toString());

        Session s = NewHibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Barang b = (Barang) s.get(Barang.class, id);
        b.setKode_barang(txtKode.getText());
        b.setNama_barang(txtNama.getText());
        b.setHarga(Integer.parseInt(txtHarga.getText()));
        b.setStok(Integer.parseInt(txtStok.getText()));

        s.update(b);
        t.commit();
        s.close();

        loadTable();
        JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
    }

    // ========================= DELETE =============================
    private void deleteData() {
        int row = tableBarang.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            return;
        }

        int id = Integer.parseInt(tableBarang.getValueAt(row, 0).toString());

        Session s = NewHibernateUtil.getSessionFactory().openSession();
        Transaction t = s.beginTransaction();

        Barang b = (Barang) s.get(Barang.class, id);
        s.delete(b);

        t.commit();
        s.close();

        loadTable();
        JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
    }

    // ========================= CETAK NOTA =============================
   private void cetakLaporan() {
    try {
        JasperReport jr = JasperCompileManager.compileReport("src/report/nota.jrxml");

        Map param = new HashMap<>();

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/kasir_db",
                "root",
                ""
        );

        JasperPrint jp = JasperFillManager.fillReport(jr, param, conn);
        JasperViewer.viewReport(jp, false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }




    // ========================= GUI COMPONENTS =============================
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableBarang = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtKode = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        txtStok = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Form Barang");

        tableBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {}
        ));
        jScrollPane1.setViewportView(tableBarang);

        jLabel1.setText("Kode Barang");
        jLabel2.setText("Nama Barang");
        jLabel3.setText("Harga");
        jLabel4.setText("Stok");

        btnSave.setText("Simpan");
        btnSave.addActionListener(evt -> saveData());

        btnUpdate.setText("Update");
        btnUpdate.addActionListener(evt -> updateData());

        btnDelete.setText("Delete");
        btnDelete.addActionListener(evt -> deleteData());

        btnCetak.setText("Cetak Laporan");
        btnCetak.addActionListener(evt -> cetakLaporan());

        // ============= Layout Auto-Generated NetBeans ===================
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtKode)
                            .addComponent(txtNama)
                            .addComponent(txtHarga)
                            .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 20, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtKode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtStok, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }

    // ========================= MAIN =============================
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new FormBarang().setVisible(true);
        });
    }

    // ========================= VARIABLES =============================
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnCetak;

    private javax.swing.JLabel jLabel1, jLabel2, jLabel3, jLabel4;
    private javax.swing.JScrollPane jScrollPane1;

    private javax.swing.JTable tableBarang;

    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtStok;
}
