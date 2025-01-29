package mes;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import mes.Database.ProductDatabase;
import mes.Database.ProductionDatabase;
import mes.Database.RawMaterialDatabase;
import mes.Database.WareHouseDatabase;
import mes.model.WareHouse;
import mes.model.Product;
import mes.model.RawMaterial;

public class WorkOrders extends javax.swing.JFrame {

    public WorkOrders() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        loadTable();
        loadWarehouses();
    }

    private void createWorkOrder() {
        int selectedRow = products_rawMaterials_tbl.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir ürün seçin.");
            return;
        }

        String warehouseName = (String) warehouse_combox.getSelectedItem();
        String quantityText = quantity.getText();

        if (warehouseName == null || warehouseName.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun.");
            return;
        }

        int productionQuantity;
        try {
            productionQuantity = Integer.parseInt(quantityText);
            if (productionQuantity <= 0) {
                JOptionPane.showMessageDialog(this, "Üretim miktarı pozitif bir sayı olmalıdır.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Üretim miktarı geçerli bir sayı olmalıdır.");
            return;
        }

        try {
            ProductionDatabase productionDatabase = new ProductionDatabase();

            int productId = (int) products_rawMaterials_tbl.getValueAt(selectedRow, 0);

            Optional<Integer> warehouseIdOpt = productionDatabase.getWarehouseIdByName(warehouseName);

            int warehouseId = warehouseIdOpt.get();

            Optional<Integer> rawProductIdOpt = productionDatabase.getRawProductIdByProductId(productId);
            Integer rawProductId = rawProductIdOpt.orElse(null);

            boolean isCreated = productionDatabase.createWorkOrder(productId, warehouseId, rawProductId, productionQuantity);
            if (isCreated) {
                JOptionPane.showMessageDialog(this, "İş emri başarıyla oluşturuldu!");
            } else {
                JOptionPane.showMessageDialog(this, "İş emri oluşturulamadı.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "İş emri oluşturulurken bir hata oluştu: " + ex.getMessage());
        }
    }

    private void loadWarehouses() {
        try {
            WareHouseDatabase warehouseDatabase = new WareHouseDatabase();
            List<WareHouse> warehouses = warehouseDatabase.getAllWarehouses();

            warehouse_combox.removeAllItems();

            for (WareHouse warehouse : warehouses) {
                warehouse_combox.addItem(warehouse.getWarehouseName());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Depo isimleri yüklenirken hata oluştu: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        products_rawMaterials_tbl = new javax.swing.JTable();
        createWorkOrder_btn = new javax.swing.JButton();
        quantity = new javax.swing.JTextField();
        warehouse_combox = new javax.swing.JComboBox<>();
        warehouseName_lbl = new javax.swing.JLabel();
        productionAmount_lbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        products_rawMaterials_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(products_rawMaterials_tbl);

        createWorkOrder_btn.setText("Create Order");
        createWorkOrder_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createWorkOrder_btnActionPerformed(evt);
            }
        });

        warehouse_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        warehouseName_lbl.setText("Gönderilecek depo");

        productionAmount_lbl.setText("Üretim Miktarı");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1002, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(createWorkOrder_btn)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(warehouseName_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(productionAmount_lbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(warehouse_combox, 0, 108, Short.MAX_VALUE)
                            .addComponent(quantity))))
                .addGap(280, 280, 280))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(warehouse_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(warehouseName_lbl))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(productionAmount_lbl))
                .addGap(18, 18, 18)
                .addComponent(createWorkOrder_btn)
                .addContainerGap(214, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createWorkOrder_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createWorkOrder_btnActionPerformed
        createWorkOrder();
    }//GEN-LAST:event_createWorkOrder_btnActionPerformed

    private void loadTable() {
        try {
            ProductDatabase productDatabase = new ProductDatabase();
            RawMaterialDatabase rawMaterialDatabase = new RawMaterialDatabase();

            List<Product> products = productDatabase.getAllProducts();
            List<RawMaterial> rawMaterials = rawMaterialDatabase.getAllRawMaterials();

            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Product ID", "Name", "Gender", "Color", "Stock", "Category", "Raw Product", "Raw Product Stock"
            }, 0);

            for (Product product : products) {
                RawMaterial associatedRawMaterial = null;

                if (product.getRawProductId() != 0) {
                    for (RawMaterial rawMaterial : rawMaterials) {
                        if (rawMaterial.getRawProductId() == product.getRawProductId()) {
                            associatedRawMaterial = rawMaterial;
                            break;
                        }
                    }
                }

                model.addRow(new Object[]{
                    product.getProductId(),
                    product.getProductName(),
                    product.getProductGender(),
                    product.getProductColor(),
                    product.getProductStock(),
                    product.getProductCategory(),
                    associatedRawMaterial != null ? associatedRawMaterial.getRawProductName() : "N/A",
                    associatedRawMaterial != null ? associatedRawMaterial.getRawProductStock() : "N/A"
                });
            }

            products_rawMaterials_tbl.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Tablo yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WorkOrders().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createWorkOrder_btn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel productionAmount_lbl;
    private javax.swing.JTable products_rawMaterials_tbl;
    private javax.swing.JTextField quantity;
    private javax.swing.JLabel warehouseName_lbl;
    private javax.swing.JComboBox<String> warehouse_combox;
    // End of variables declaration//GEN-END:variables
}
