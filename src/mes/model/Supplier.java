
package mes.model;

import java.util.List;


public class Supplier {
    private int supplier_id;
    private String address;
    private String contact_person;
    private String Email;
    private int phone_number;
    private String supplier_name;

    public Supplier() {
    }

    public int getSupplierId() {
        return supplier_id;
    }

    public void setSupplierId(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSupplierAddress() {
        return address;
    }

    public void setSupplierAddress(String address) {
        this.address = address;
    }

    public String getSupplierContactPerson() {
        return contact_person;
    }

    public void setSupplierContactPerson(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getSupplierEmail() {
        return Email;
    }

    public void setSupplierEmail(String Email) {
        this.Email = Email;
    }

    public int getSupplierPhoneNumber() {
        return phone_number;
    }

    public void setSupplierPhoneNumber(int phone_number) {
        this.phone_number = phone_number;
    }

    public String getSupplierName() {
        return supplier_name;
    }

    public void setSupplierName(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public void add(List<Supplier> suppliers) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
}
