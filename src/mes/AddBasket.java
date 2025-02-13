package mes;

import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mes.Database.ProductDatabase;
import mes.Database.WarehouseDatabase;
import mes.Database.WarehouseStockDatabase;
import mes.model.Basket;
import mes.model.OrderProducts;

public class AddBasket extends javax.swing.JFrame {

    private int selectedProductId;
    private String selectedProductName;
    CreateOrder createOrder = new CreateOrder();

    public AddBasket() {
    }

    public AddBasket(int productId, String productName) {
        this.selectedProductId = productId;
        this.selectedProductName = productName;
        initComponents();
        loadProductWarehouses();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    }

    private void loadProductWarehouses() {
        WarehouseStockDatabase warehouseDb = new WarehouseStockDatabase();
        List<Map<String, Object>> warehouses = warehouseDb.getWarehousesForProduct(selectedProductId);

        DefaultTableModel model = new DefaultTableModel(new String[]{
            "Depo Adı", "Stok Miktarı"
        }, 0);

        for (Map<String, Object> row : warehouses) {
            model.addRow(new Object[]{
                row.get("warehouse_name"),
                row.get("quantity_in_stock")
            });
        }

        warehouseStocks_tbl.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        warehouseStocks_tbl = new javax.swing.JTable();
        quantity_txt = new javax.swing.JTextField();
        addBasket_btn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        warehouseStocks_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(warehouseStocks_tbl);

        addBasket_btn.setText("Add Basket");
        addBasket_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBasket_btnActionPerformed(evt);
            }
        });

        jLabel1.setText("Quantity");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel2.setText("Add Basket");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(429, 429, 429)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addBasket_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(88, 88, 88)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(quantity_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(quantity_txt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addBasket_btn)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBasket_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBasket_btnActionPerformed
       int selectedRow = warehouseStocks_tbl.getSelectedRow();

    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Lütfen bir depo seçin!");
        return;
    }

    double orderPrice = new ProductDatabase().getProductPriceById(selectedProductId);
    String quantityText = quantity_txt.getText().trim();

    if (quantityText.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Lütfen miktar giriniz!");
        return;
    }

    int quantity;
    try {
        quantity = Integer.parseInt(quantityText);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Geçerli bir sayı giriniz!");
        return;
    }

    WarehouseDatabase warehouseDb = new WarehouseDatabase();
    String warehouseName = warehouseStocks_tbl.getValueAt(selectedRow, 0).toString();
    int warehouseId = warehouseDb.getWarehouseIdByName(warehouseName);
    int stock = Integer.parseInt(warehouseStocks_tbl.getValueAt(selectedRow, 1).toString());

    // Stoktaki miktarı kontrol et
    if (quantity > stock) {
        JOptionPane.showMessageDialog(this, "Stoktaki miktardan fazla ürün ekleyemezsiniz!");
        return;
    }

    // Sepette bu ürün var mı kontrol et
    OrderProducts existingProduct = Basket.getInstance().getProductById(selectedProductId, warehouseId);
    
    if (existingProduct != null) {
        // Ürün zaten sepette varsa, miktarını arttır
        int newQuantity = existingProduct.getQuantity() + quantity;
        
        // Stoktaki miktarı aşmamak için yeni miktarı kontrol et
        if (newQuantity > stock) {
            JOptionPane.showMessageDialog(this, "Stoktaki miktardan fazla ürün sepete eklenemez!");
            return;
        }

        existingProduct.setQuantity(newQuantity); // Mevcut miktarı güncelle
    } else {
        // Ürün sepette yoksa, yeni ürün ekle
        OrderProducts selectedProduct = new OrderProducts(selectedProductId, selectedProductName, orderPrice, quantity, warehouseId);
        Basket.getInstance().addProduct(selectedProduct);
    }

    // Sepeti güncelle
    createOrder.updateBasketTable();
    JOptionPane.showMessageDialog(this, "Ürün sepete eklendi!");

    }//GEN-LAST:event_addBasket_btnActionPerformed
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddBasket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddBasket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddBasket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddBasket.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductsOrders().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBasket_btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField quantity_txt;
    private javax.swing.JTable warehouseStocks_tbl;
    // End of variables declaration//GEN-END:variables
}
