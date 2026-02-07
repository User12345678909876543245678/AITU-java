package database;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    //INSERT - (name, age, email, preferred_size, points, customer_type, join_date, vip_level)
    public boolean insertRegularCustomer(RegularCustomer regularCustomer) {
        String sql = "INSERT INTO customer (name, age, email, preferred_size, points, customer_type, join_date, vip_level) VALUES (?, ?, ?, ?, ?, 'Regular', ?, NULL)";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, regularCustomer.getName());
            statement.setInt(2, regularCustomer.getAge());
            statement.setString(3, regularCustomer.getEmail());
            statement.setString(4, regularCustomer.getPreferredSize());
            statement.setInt(5, regularCustomer.getPoints());
            statement.setString(6, regularCustomer.getJoinDate());

            int rowsInserted = statement.executeUpdate();
            statement.close();
            if (rowsInserted > 0) {
                System.out.println("✅ Regular customer inserted: " + regularCustomer.getName());
                return true;
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }
    public boolean insertVIPCustomer(VIPCustomer VIP) {
        String sql = "INSERT INTO customer (name, age, email, preferred_size, points, customer_type, join_date, vip_level) VALUES (?, ?, ?, ?, ?, 'VIP', NULL, ?)";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;
        try {

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, VIP.getName());
            statement.setInt(2, VIP.getAge());
            statement.setString(3, VIP.getEmail());
            statement.setString(4, VIP.getPreferredSize());
            statement.setInt(5, VIP.getPoints());
            statement.setString(6, VIP.getVipLevel());

            int rowsInserted = statement.executeUpdate();
            statement.close();
            if (rowsInserted > 0) {
                System.out.println("✅ VIP customer inserted: " + VIP.getName());
                return true;
            }
        } catch (
                SQLException e) {
            e.printStackTrace();
        }
        finally {
            DatabaseConnection.closeConnection(connection);
        }
        return false;
    }

//SELECT
    public List<Customer> getAllCustomer() {
        List<Customer> customerList = new ArrayList<>();
        String sql = "SELECT * FROM customer ORDER BY customer_id";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return customerList;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                if (customer != null) customerList.add(customer);
            }
            resultSet.close();
            statement.close();
            System.out.println("✅ Retrieved " + customerList.size() + " customer from database");
        } catch (SQLException e) {
            System.out.println("❌ Select all customer failed!");
            e.printStackTrace();
        }
        finally {
            DatabaseConnection.closeConnection(connection);
        }
        return customerList;
    }
    public Customer getCustomerById(int customerId) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);

                resultSet.close();
                statement.close();

                if (customer != null) {
                    System.out.println("✅ Found customer with ID: " + customerId);
                }

                return customer;
            }

            System.out.println("⚠️ No customer found with ID: " + customerId);

            resultSet.close();
            statement.close();

        } catch (SQLException e) {
            System.out.println("❌ Select by ID failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }
        return null;
    }
    public List<RegularCustomer> getAllRegularCustomers() {
        List<RegularCustomer> regularCustomers = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE customer_type = 'Regular' ORDER BY customer_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return regularCustomers;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                if (customer instanceof RegularCustomer) {
                    regularCustomers.add((RegularCustomer) customer);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Retrieved " + regularCustomers.size() + " Regular Customers");

        } catch (SQLException e) {
            System.out.println("❌ Select Regular Customers failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return regularCustomers;
    }
    public List<VIPCustomer> getAllVIPCustomers() {
        List<VIPCustomer> VIP = new ArrayList<>();
        String sql = "SELECT * FROM customer WHERE customer_type = 'VIP' ORDER BY customer_id";
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return VIP;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                if (customer instanceof VIPCustomer) {
                    VIP.add((VIPCustomer) customer);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Retrieved " + VIP.size() + " VIP customers");

        } catch (SQLException e) {
            System.out.println("❌ Select VIP customers failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return VIP;
    }

    //UPDATE
    public boolean updateRegularCustomer(RegularCustomer regularCustomer) {
        String sql = "UPDATE customer SET name = ?, age = ?, email = ?, preferred_size = ?,points = ?,join_date = ?" +
                " WHERE customer_id = ? AND customer_type = 'Regular'";
//name, age, email, preferred_size, points, customer_type, join_date, vip_level
        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, regularCustomer.getName());
            statement.setInt(2, regularCustomer.getAge());
            statement.setString(3, regularCustomer.getEmail());
            statement.setString(4, regularCustomer.getPreferredSize());
            statement.setInt(5, regularCustomer.getPoints());
            statement.setString(6, regularCustomer.getJoinDate());
            statement.setInt(7,regularCustomer.getCustomerId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated > 0) {
                System.out.println("✅ Regular Customer updated: " + regularCustomer.getName());
                return true;
            } else {
                System.out.println("⚠️ No Regular Customer found with ID: " + regularCustomer.getCustomerId());
            }

        } catch (SQLException e) {
            System.out.println("❌ Update Regular Customer failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }
    public boolean updateVIPCustomer(VIPCustomer VIP) {
        String sql = "UPDATE customer SET name = ?, age = ?, email = ?, preferred_size = ?,points = ?, vip_level = ?" +
                " WHERE customer_id = ? AND customer_type = 'VIP'";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, VIP.getName());
            statement.setInt(2, VIP.getAge());
            statement.setString(3, VIP.getEmail());
            statement.setString(4, VIP.getPreferredSize());
            statement.setInt(5, VIP.getPoints());
            statement.setString(6, VIP.getVipLevel());
            statement.setInt(7,VIP.getCustomerId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated > 0) {
                System.out.println("✅ VIP updated: " + VIP.getName());
                return true;
            } else {
                System.out.println("⚠️ No VIP found with ID: " + VIP.getCustomerId());
            }

        } catch (SQLException e) {
            System.out.println("❌ Update VIP failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

//DELETE
    public boolean deleteCustomer(int customerId) {
        String sql = "DELETE FROM customer WHERE customer_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);

            int rowsDeleted = statement.executeUpdate();
            statement.close();

            if (rowsDeleted > 0) {
                System.out.println("✅ customer deleted (ID: " + customerId + ")");
                return true;
            } else {
                System.out.println("⚠️ No customer found with ID: " + customerId);
            }

        } catch (SQLException e) {
            System.out.println("❌ Delete failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

//SEARCH
    public List<Customer> SearchbyName(String name) {
        List<Customer> customerList = new ArrayList<>();

        String sql = "SELECT * FROM customer WHERE name ILIKE ? ORDER BY name";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return customerList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");  // % = wildcard

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                if (customer != null) {
                    customerList.add(customer);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Found " + customerList.size() + " customer matching '" + name + "'");

        } catch (SQLException e) {
            System.out.println("❌ Search by name failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return customerList;
    }
    public List<Customer> SearchBySizeRange(String minSize, String maxSize) {
        List<Customer> customerList = new ArrayList<>();

        String sql = "SELECT * FROM customer WHERE preferred_size BETWEEN ? AND ? ORDER BY preferred_size DESC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return customerList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, minSize);
            statement.setString(2, maxSize);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                if (customer != null) {
                    customerList.add(customer);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Found " + customerList.size() + " customer in Size range " +
                    minSize + " - " + maxSize);

        } catch (SQLException e) {
            System.out.println("❌ Search by Size failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return customerList;
    }
    public List<Customer> SearchByMinSize(String minSize) {
        List<Customer> customerList = new ArrayList<>();

        String sql = "SELECT * FROM customer WHERE preferred_size >= ? ORDER BY preferred_size DESC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return customerList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, minSize);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = extractCustomerFromResultSet(resultSet);
                if (customer != null) {
                    customerList.add(customer);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Found " + customerList.size() + " customer's preferred size >= " + minSize);

        } catch (SQLException e) {
            System.out.println("❌ Search by min size failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return customerList;
    }
    private Customer extractCustomerFromResultSet(ResultSet resultSet) throws SQLException {
        //name = ?, age = ?, email = ?, preferred_size = ?,points = ?
        int customerId = resultSet.getInt("customer_id");
        String name = resultSet.getString("name");
        int age = resultSet.getInt("age");
        String email = resultSet.getString("email");
        String preferred_size = resultSet.getString("preferred_size");
        int points = resultSet.getInt("points");
        String customerType = resultSet.getString("customer_type");

        Customer customer = null;

        if ("Regular".equals(customerType)) {
            String joindate = resultSet.getString("join_date");
            customer = new RegularCustomer(customerId, name, age, email, preferred_size, points, joindate);

        } else if ("VIP".equals(customerType)) {
            String viplevel = resultSet.getString("vip_level");
            customer = new VIPCustomer(customerId, name, age, email, preferred_size, points, viplevel);
        }

        return customer;
    }
    public void displayAllCustomer() {
        List<Customer> customerList = getAllCustomer();

        System.out.println("\n========================================");
        System.out.println("   ALL Customer FROM DATABASE");
        System.out.println("========================================");

        if (customerList.isEmpty()) {
            System.out.println("No Customer members in database.");
        } else {
            for (int i = 0; i < customerList.size(); i++) {
                Customer s = customerList.get(i);
                System.out.print((i + 1) + ". ");
                System.out.print("[" + s.getCustomerType() + "] ");
                System.out.println(s.toString());
            }
        }

        System.out.println("========================================\n");
    }
    public void demonstratePolymorphism() {
        List<Customer> customerList = getAllCustomer();

        System.out.println("\n========================================");
        System.out.println("  POLYMORPHISM: customer from Database");
        System.out.println("========================================");

        if (customerList.isEmpty()) {
            System.out.println("No customer to demonstrate.");
        } else {
            for (Customer s : customerList) {
                System.out.println(s.getCustomerType()); // Polymorphic call!
            }
        }

        System.out.println("========================================\n");
    }
}
