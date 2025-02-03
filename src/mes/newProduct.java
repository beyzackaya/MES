package mes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import mes.Database.DatabaseConnector;
import mes.Database.RawMaterialDatabase;
import mes.model.RawMaterial;
import java.util.ArrayList;
import mes.Database.ProductDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class newProduct extends javax.swing.JFrame {

    public newProduct() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        loadCategories();
        loadRawMaterial();
        loadGenders();

    }

    private void loadCategories() {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SHOW COLUMNS FROM products LIKE 'product_category';";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> categories = new ArrayList<>();

            if (rs.next()) {
                String enumType = rs.getString("Type");
                enumType = enumType.substring(enumType.indexOf('(') + 1, enumType.indexOf(')'));
                String[] enumValues = enumType.split(",");

                for (String value : enumValues) {
                    categories.add(value.replace("'", "").trim());
                }
            }

            categoryCombox.removeAllItems();

            for (String category : categories) {
                categoryCombox.addItem(category);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadGenders() {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SHOW COLUMNS FROM products LIKE 'product_gender';";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> categories = new ArrayList<>();

            if (rs.next()) {
                String enumType = rs.getString("Type");
                enumType = enumType.substring(enumType.indexOf('(') + 1, enumType.indexOf(')'));
                String[] enumValues = enumType.split(",");

                for (String value : enumValues) {
                    categories.add(value.replace("'", "").trim());
                }
            }

            productGender_combox.removeAllItems();

            for (String category : categories) {
                productGender_combox.addItem(category);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadRawMaterial() {
        try {
            RawMaterialDatabase rawMaterialDatabase = new RawMaterialDatabase();
            List<RawMaterial> rawMaterial = rawMaterialDatabase.getAllRawMaterials();

            Set<String> rawMaterialNames = new HashSet<>();

            for (RawMaterial p : rawMaterial) {
                if (p.getRawProductName() != null && !p.getRawProductName().isEmpty()) {
                    rawMaterialNames.add(p.getRawProductName());
                }
            }

            rawProduct_combox.removeAllItems();
            for (String rawMaterialName : rawMaterialNames) {
                rawProduct_combox.addItem(rawMaterialName);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Renkler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        productName_txt = new javax.swing.JTextField();
        productColor_txt = new javax.swing.JTextField();
        salePrice_txt = new javax.swing.JTextField();
        categoryCombox = new javax.swing.JComboBox<>();
        rawProduct_combox = new javax.swing.JComboBox<>();
        addProduct_btn = new javax.swing.JButton();
        addCategory_btn = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        requiredRawMaterial_txt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        productGender_combox = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Product Name");

        jLabel2.setText("Category");

        jLabel3.setText("Color");

        jLabel4.setText("Sale Price");

        jLabel6.setText("Raw Material ");

        categoryCombox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        categoryCombox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryComboxActionPerformed(evt);
            }
        });

        rawProduct_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        addProduct_btn.setText("Add Product");
        addProduct_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProduct_btnActionPerformed(evt);
            }
        });

        addCategory_btn.setText("+");
        addCategory_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCategory_btnActionPerformed(evt);
            }
        });

        jLabel7.setText("Required Raw Material");

        jLabel8.setText("Product Gender");

        productGender_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        productGender_combox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                productGender_comboxActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Helvetica Neue", 0, 24)); // NOI18N
        jLabel9.setText("Add Product");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(productName_txt)
                            .addComponent(productColor_txt)
                            .addComponent(categoryCombox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(addProduct_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(salePrice_txt)
                            .addComponent(rawProduct_combox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(productGender_combox, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(requiredRawMaterial_txt, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addCategory_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(132, 132, 132)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(80, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(38, 38, 38)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(productName_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(categoryCombox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addCategory_btn))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(productColor_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(productGender_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(salePrice_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(rawProduct_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(requiredRawMaterial_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addComponent(addProduct_btn)
                .addGap(135, 135, 135))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void categoryComboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryComboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoryComboxActionPerformed

    private void addCategory_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCategory_btnActionPerformed
        String newCategory = JOptionPane.showInputDialog(this, "Yeni Kategori Girin:", "Yeni Kategori Ekle", JOptionPane.PLAIN_MESSAGE);

        if (newCategory != null && !newCategory.trim().isEmpty()) {
            try {
                ProductDatabase productDatabase = new ProductDatabase();
                String existingCategories = productDatabase.getExistingEnumValues();

                Connection conn = DatabaseConnector.getConnection();
                String alterSql = "ALTER TABLE products MODIFY COLUMN product_category ENUM("
                        + existingCategories + ", '" + newCategory.trim() + "');";
                PreparedStatement stmt = conn.prepareStatement(alterSql);
                stmt.executeUpdate();
                stmt.close();
                conn.close();

                categoryCombox.addItem(newCategory.trim());

                // Başarı mesajı
                JOptionPane.showMessageDialog(this, "Kategori başarıyla eklendi!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Kategori eklenirken hata oluştu: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Kategori adı boş olamaz!");
        }
    }//GEN-LAST:event_addCategory_btnActionPerformed
    public int getProductIdByName(String productName) {
        int productId = -1;
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT rawproduct_id FROM raw_material WHERE rawproduct_name = ?")) {
            stmt.setString(1, productName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                productId = rs.getInt("rawproduct_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productId;
}    private void addProduct_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProduct_btnActionPerformed
            try {

                String productName = productName_txt.getText().trim();
                String productCategory = categoryCombox.getSelectedItem().toString().trim();
                String productColor = productColor_txt.getText().trim();
                double salePrice = Double.parseDouble(salePrice_txt.getText().trim());
                String productGender = productGender_combox.getSelectedItem().toString().trim();
                int requiredRawQuantity = Integer.parseInt(requiredRawMaterial_txt.getText().trim());
                String rawProductName = rawProduct_combox.getSelectedItem().toString();

                int rawProductId = getProductIdByName(rawProductName);
                if (rawProductId == -1) {
                    JOptionPane.showMessageDialog(null, "Hata: Seçilen ham madde bulunamadı!");
                    return;
                }

                ProductDatabase db = new ProductDatabase();
                Products productsColorProducts = new Products();
                String a = productsColorProducts.capitalizeFirstLetter(productColor.toLowerCase());
                boolean success = db.addProduct(productName, productGender, a, 0,
                        productCategory, salePrice, rawProductId, requiredRawQuantity);

                if (success) {
                    JOptionPane.showMessageDialog(null, "Ürün başarıyla eklendi!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Lütfen geçerli bir sayı girin!");
                e.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Bir hata oluştu: " + e.getMessage());
                e.printStackTrace();
            }

    }//GEN-LAST:event_addProduct_btnActionPerformed

    private void productGender_comboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_productGender_comboxActionPerformed
    }//GEN-LAST:event_productGender_comboxActionPerformed

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new newProduct().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCategory_btn;
    private javax.swing.JButton addProduct_btn;
    private javax.swing.JComboBox<String> categoryCombox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField productColor_txt;
    private javax.swing.JComboBox<String> productGender_combox;
    private javax.swing.JTextField productName_txt;
    private javax.swing.JComboBox<String> rawProduct_combox;
    private javax.swing.JTextField requiredRawMaterial_txt;
    private javax.swing.JTextField salePrice_txt;
    // End of variables declaration//GEN-END:variables
}
