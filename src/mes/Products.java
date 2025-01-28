package mes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mes.Database.ProductDatabase;
import mes.model.Product;

public class Products extends javax.swing.JFrame {

    public Products() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        loadTable();
        loadCategories();
        loadGenders();
        loadColor();
    }

    private void loadCategories() {
        try {
            ProductDatabase productDatabase = new ProductDatabase();
            List<Product> products = productDatabase.getAllProducts();

            Set<String> categories = new HashSet<>();

            for (Product p : products) {
                if (p.getProductCategory() != null && !p.getProductCategory().isEmpty()) {
                    categories.add(p.getProductCategory());
                }
            }

            category_combox.removeAllItems();
            for (String category : categories) {
                category_combox.addItem(category);
            }
            category_combox.removeAllItems();
            for (String category : categories) {
                category_combox.addItem(category);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Renkler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    private void loadGenders() {

        try {
            ProductDatabase productDatabase = new ProductDatabase();
            List<Product> products = productDatabase.getAllProducts();

            Set<String> genders = new HashSet<>();

            for (Product p : products) {
                if (p.getProductGender() != null && !p.getProductGender().isEmpty()) {
                    genders.add(p.getProductGender());
                }
            }

            color_combox.removeAllItems();
            for (String gender : genders) {
                color_combox.addItem(gender);
            }
            gender_combox.removeAllItems();
            for (String gender : genders) {
                gender_combox.addItem(gender);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Renkler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    private void loadColor() {
        try {
            ProductDatabase productDatabase = new ProductDatabase();
            List<Product> products = productDatabase.getAllProducts();

            Set<String> colors = new HashSet<>();

            for (Product p : products) {
                if (p.getProductColor() != null && !p.getProductColor().isEmpty()) {
                    colors.add(p.getProductColor());
                }
            }

            color_combox.removeAllItems();
            for (String color : colors) {
                color_combox.addItem(color);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Renkler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    private void filter() {
        String productName = productname_txt.getText().toLowerCase();
        String productCategory = category_combox.getSelectedItem() != null ? category_combox.getSelectedItem().toString().toLowerCase() : "";
        String productColor = color_combox.getSelectedItem() != null ? color_combox.getSelectedItem().toString().toLowerCase() : "";
        String productGender = gender_combox.getSelectedItem() != null ? gender_combox.getSelectedItem().toString().toLowerCase() : "";
        String productStockStatus = stock_combox.getSelectedItem() != null ? stock_combox.getSelectedItem().toString() : "";

        try {
            ProductDatabase productDatabase = new ProductDatabase();
            List<Product> allProducts = productDatabase.getAllProducts();

            List<Product> filteredProducts = new ArrayList<>();
            for (Product p : allProducts) {
                boolean matches = true;

                if (!productName.isEmpty() && !p.getProductName().toLowerCase().contains(productName)) {
                    matches = false;
                }

                if (!productCategory.isEmpty() && !p.getProductCategory().toLowerCase().equals(productCategory)) {
                    matches = false;
                }

                if (!productColor.isEmpty() && !p.getProductColor().toLowerCase().equals(productColor)) {
                    matches = false;
                }

                if (!productGender.isEmpty() && !p.getProductGender().toLowerCase().equals(productGender)) {
                    matches = false;
                }

                if (!productStockStatus.isEmpty()) {
                    switch (productStockStatus) {
                        case "In Stock":
                            if (p.getProductStock() <= 0) {
                                matches = false;
                            }
                            break;
                        case "Out of Stock":
                            if (p.getProductStock() > 0) {
                                matches = false;
                            }
                            break;
                        case "Low Stock":
                            if (p.getProductStock() > 3) {
                                matches = false;
                            }
                            break;
                    }
                }

                if (matches) {
                    filteredProducts.add(p);
                }
            }

            DefaultTableModel model = (DefaultTableModel) products_tbl.getModel();
            model.setRowCount(0);

            for (Product p : filteredProducts) {
                model.addRow(new Object[]{
                    p.getProductId(),
                    p.getProductName(),
                    p.getProductGender(),
                    p.getProductColor(),
                    p.getProductStock(),
                    p.getProductCategory(),
                    p.getProductPrice()
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ürünler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        popupMenu1 = new java.awt.PopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        products_tbl = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        clearFilters_combox = new javax.swing.JButton();
        filter_btn = new javax.swing.JButton();
        color_combox = new javax.swing.JComboBox<>();
        stock_combox = new javax.swing.JComboBox<>();
        category_combox = new javax.swing.JComboBox<>();
        gender_combox = new javax.swing.JComboBox<>();
        productname_txt = new javax.swing.JTextField();

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        popupMenu1.setLabel("popupMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        products_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(products_tbl);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        clearFilters_combox.setText("Clear Filters");
        clearFilters_combox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearFilters_comboxActionPerformed(evt);
            }
        });

        filter_btn.setText("apply");
        filter_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filter_btnActionPerformed(evt);
            }
        });

        color_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        stock_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ","Low Stock", "In Stock", "Out of Stock"}));

        category_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Products", "Raw Material" }));

        gender_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clearFilters_combox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(filter_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(stock_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(color_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(category_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gender_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(productname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(gender_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(category_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(color_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(stock_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(productname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filter_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clearFilters_combox)
                .addGap(27, 27, 27))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void filter_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filter_btnActionPerformed
        filter();

    }//GEN-LAST:event_filter_btnActionPerformed

    private void clearFilters_comboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFilters_comboxActionPerformed
        loadTable();
    }//GEN-LAST:event_clearFilters_comboxActionPerformed
    private void loadTable() {
        try {
            ProductDatabase productDatabase = new ProductDatabase();
            List<Product> products = productDatabase.getAllProducts();

            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Product ID", "Name", "Gender", "Color", "Stock", "Category", "Price"
            }, 0);

            for (Product p : products) {
                model.addRow(new Object[]{
                    p.getProductId(),
                    p.getProductName(),
                    p.getProductGender(),
                    p.getProductColor(),
                    p.getProductStock(),
                    p.getProductCategory(),
                    p.getProductPrice()
                });
            }

            products_tbl.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading table: " + ex.getMessage());
        }
    }

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Products().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> category_combox;
    private javax.swing.JButton clearFilters_combox;
    private javax.swing.JComboBox<String> color_combox;
    private javax.swing.JButton filter_btn;
    private javax.swing.JComboBox<String> gender_combox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private java.awt.PopupMenu popupMenu1;
    private javax.swing.JTextField productname_txt;
    private javax.swing.JTable products_tbl;
    private javax.swing.JComboBox<String> stock_combox;
    // End of variables declaration//GEN-END:variables
}
