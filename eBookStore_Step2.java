package Java2_08;


import java.sql.*;
import java.util.Scanner;

public class eBookStore_Step2 {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("1. SelectCustomer \n2. InsertCustomer \n3. DeleteCustomerById \n4. op100Customer");
        int action = scanner.nextInt();
        switch (action) {
            case 1:
                SelectCustomer();
                break;
            case 2:
                InsertCustomer();
                break;
            case 3:
                DeleteCustomerById();
                break;
            case 4:
                Top100Customer();
                break;
        }
    }
        public static void SelectCustomer(){
            try (
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/ebookshop?" +
                                    "&serverTimezone=UTC" +
                                    "&useSSL=false" +
                                    "&allowPublicKeyRetrieval=true",
                            "root",
                            "");
                    Statement stmt = conn.createStatement();
            ) {
                String sqlSelect = "select * from customers";
                System.out.println("The SQL statement is: " + sqlSelect + "\n");
                ResultSet rset = stmt.executeQuery(sqlSelect);
                int count = 0;
                while (rset.next()) {
                    System.out.println(rset.getInt("ID") + ", "
                            + rset.getString("customerName") + ", "
                            + rset.getString("gender") + ", "
                            + rset.getString("customerAddress") + ", "
                            + rset.getString("dateOfBirth") + ", "
                            + rset.getInt("customerPhone") + ", "
                            + rset.getString("registrationDate") + ", "
                            + rset.getString("email"));
                    count++;
                }
                System.out.println("Total number of record are: " + count);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        public static void InsertCustomer () {
            try (
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/ebookshop?" +
                                    "&serverTimezone=UTC" +
                                    "&useSSL=false" +
                                    "&allowPublicKeyRetrieval=true",
                            "root",
                            "");
                    Statement stmt = conn.createStatement();
            ) {
                customer customer = new customer();
                System.out.println("Enter id: ");
                int id = scanner.nextInt();
                customer.setCustomerId(id);
                System.out.println("Enter name: ");
                String name = scanner.next();
                customer.setName(name);
                System.out.println("Enter gender: ");
                String gender = scanner.nextLine();
                gender = scanner.nextLine();
                customer.setGender(gender);
                System.out.println("Enter address: ");
                String address = scanner.nextLine();
                customer.setAddress(address);
                System.out.println("Enter phone: ");
                int phone = scanner.nextInt();
                customer.setPhone(phone);
                System.out.println("Enter date of birth: ");
                String date = scanner.next();
                customer.setDateofbirth(date);
                System.out.println("Enter registration date: ");
                String regisdate = scanner.next();
                customer.setRegistrationdate(regisdate);
                System.out.println("Enter level: ");
                int level = scanner.nextInt();
                customer.setLevel(level);
                System.out.println("Enter email: ");
                String email = scanner.next();
                customer.setEmail(email);
                String sqlInsert = "insert into customers values" + "('" + customer.getName() + "','" + customer.getGender()
                        + "', '" + customer.getAddress() + "', " + customer.getPhone() + ", '" + customer.getDateofbirth() + "', '"
                        + customer.getRegistrationdate() + "'," + customer.getCustomerId() + ",'" + customer.getEmail() + "'," + level + ")";
                System.out.println("The SQL statement is: " + sqlInsert);
                int countInsert = stmt.executeUpdate(sqlInsert);
                System.out.println(countInsert + " records inserted.");
                System.out.println("The value is: ");
                System.out.println(customer.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        public static void DeleteCustomerById () {
            try (
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/ebookshop?" +
                                    "&serverTimezone=UTC" +
                                    "&useSSL=false" +
                                    "&allowPublicKeyRetrieval=true",
                            "root",
                            "");
                    Statement stmt = conn.createStatement();
            ) {
                System.out.println("Enter id customer want to delete: ");
                int number = scanner.nextInt();
                String sqlSelect = "select * from orderproduct where customerID = " + number;
                ResultSet rset = stmt.executeQuery(sqlSelect);
                int count = 0;
                while (rset.next()) {
                    rset.getInt("customerID");
                    count++;
                }
                if (count < 1) {
                    String sqlDelete = "delete from customers where ID =" + number + "";
                    System.out.println("The SQL statement is: " + sqlDelete);
                    int countDelete = stmt.executeUpdate(sqlDelete);
                    System.out.println(countDelete + " records deleted.");
                    System.out.println("Delete completed");
                } else {
                    System.out.println("Can't delete\nThis id = " + number + " have order now!!!!");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        public static void UpdateCustomerById () {
            try (
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/ebookshop?" +
                                    "&serverTimezone=UTC" +
                                    "&useSSL=false" +
                                    "&allowPublicKeyRetrieval=true",
                            "root",
                            "");
                    Statement stmt = conn.createStatement();
            ) {
                customer customer = new customer();
                System.out.println("Enter id: ");
                int id = scanner.nextInt();
                customer.setCustomerId(id);
                System.out.println("Enter phone: ");
                int phone = scanner.nextInt();
                customer.setPhone(phone);
                System.out.println("Enter level: ");
                int level = scanner.nextInt();
                customer.setLevel(level);
                System.out.println("Enter email: ");
                String email = scanner.next();
                customer.setEmail(email);
                String sqlInsert = "update customers set customerPhone = " + customer.getPhone() + " ,email = '" + customer.getEmail() + "', level = " + customer.getLevel();
                System.out.println("The SQL statement is: " + sqlInsert);
                int countInsert = stmt.executeUpdate(sqlInsert);
                System.out.println(countInsert + " records inserted.");
                System.out.println("The value is: ");
                System.out.println(customer.toString());
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        public static void Top100Customer () {
            try (
                    Connection conn = DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/ebookshop?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                            "root", "");
                    Statement stmt = conn.createStatement();
            ) {
                String strSelect = "select customerID,customerName, sum(orderPrice) as sumP from customers " +
                        "join orderproduct o on customers.ID = o.customerID " +
                        "join orderdetail o2 on o.orderID = o2.orderID " +
                        "group by customerID, customerName " +
                        "order by sumP DESC limit 100";
                System.out.println("The stetament SQL is : " + strSelect + "\n");
                ResultSet rset = stmt.executeQuery(strSelect);

                System.out.println("The records select are: ");
                int rowCount = 0;
                while (rset.next()) {
                    int id = rset.getInt("customerID");
                    String name = rset.getString("customerName");
                    int price = rset.getInt("sumP");
                    System.out.println("STT " + (rowCount + 1) + "-" + id + ", " + name + ", " + price + "\n");
                    rowCount++;
                }
                System.out.println("Total number of records = " + rowCount);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


