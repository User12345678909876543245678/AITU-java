package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public void insert(ClothingItem item) {
        String sql = "INSERT INTO clothing_item (name, size, price, brand) VALUES (?, ?, ?, ?)";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, item.getName());
            ps.setString(2, item.getSize());
            ps.setDouble(3, item.getPrice());
            ps.setString(4, item.getBrand());

            ps.executeUpdate();
            System.out.println("Item inserted into DB.");

        } catch (SQLException e) {
            System.out.println("Insert failed: " + e.getMessage());
        }
    }

    public ArrayList<ClothingItem> getAll() {
        ArrayList<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT item_id, name, size, price, brand FROM clothing_item ORDER BY item_id";

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ClothingItem item = new ClothingItem(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getString("size"),
                        rs.getDouble("price"),
                        rs.getString("brand")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Select failed: " + e.getMessage());
        }
        return items;
    }
}


/**
 * StaffDAO - Week 8 Enhanced
 * Complete CRUD operations + Advanced Search
 * - CREATE (INSERT) ✓
 * - READ (SELECT) ✓
 * - UPDATE ✓ NEW!
 * - DELETE ✓ NEW!
 * - SEARCH by name ✓ NEW!
 * - SEARCH by salary range ✓ NEW!
 */
public class StaffDAO {

    // ========================================
    // CREATE - INSERT OPERATIONS (Week 7)
    // ========================================

    /**
     * INSERT Chef into database
     */
    public boolean insertChef(Chef chef) {
        String sql = "INSERT INTO staff (name, salary, experience_years, staff_type, specialization, tables_served) " +
                "VALUES (?, ?, ?, 'CHEF', ?, NULL)";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, chef.getName());
            statement.setDouble(2, chef.getSalary());
            statement.setInt(3, chef.getExperienceYears());
            statement.setString(4, chef.getSpecialization());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            if (rowsInserted > 0) {
                System.out.println("✅ Chef inserted: " + chef.getName());
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Insert Chef failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    /**
     * INSERT Waiter into database
     */
    public boolean insertWaiter(Waiter waiter) {
        String sql = "INSERT INTO staff (name, salary, experience_years, staff_type, specialization, tables_served) " +
                "VALUES (?, ?, ?, 'WAITER', NULL, ?)";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, waiter.getName());
            statement.setDouble(2, waiter.getSalary());
            statement.setInt(3, waiter.getExperienceYears());
            statement.setInt(4, waiter.getTablesServed());

            int rowsInserted = statement.executeUpdate();
            statement.close();

            if (rowsInserted > 0) {
                System.out.println("✅ Waiter inserted: " + waiter.getName());
                return true;
            }

        } catch (SQLException e) {
            System.out.println("❌ Insert Waiter failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    // ========================================
    // READ - SELECT OPERATIONS (Week 7)
    // ========================================

    /**
     * SELECT ALL staff members
     * @return List of Staff (Chef and Waiter objects)
     */
    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        String sql = "SELECT * FROM staff ORDER BY staff_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Retrieved " + staffList.size() + " staff from database");

        } catch (SQLException e) {
            System.out.println("❌ Select all staff failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    /**
     * SELECT staff by ID
     */
    public Staff getStaffById(int staffId) {
        String sql = "SELECT * FROM staff WHERE staff_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return null;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);

                resultSet.close();
                statement.close();

                if (staff != null) {
                    System.out.println("✅ Found staff with ID: " + staffId);
                }

                return staff;
            }

            System.out.println("⚠️ No staff found with ID: " + staffId);

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

    /**
     * SELECT all Chefs
     */
    public List<Chef> getAllChefs() {
        List<Chef> chefs = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE staff_type = 'CHEF' ORDER BY staff_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return chefs;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff instanceof Chef) {
                    chefs.add((Chef) staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Retrieved " + chefs.size() + " chefs");

        } catch (SQLException e) {
            System.out.println("❌ Select chefs failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return chefs;
    }

    /**
     * SELECT all Waiters
     */
    public List<Waiter> getAllWaiters() {
        List<Waiter> waiters = new ArrayList<>();
        String sql = "SELECT * FROM staff WHERE staff_type = 'WAITER' ORDER BY staff_id";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return waiters;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff instanceof Waiter) {
                    waiters.add((Waiter) staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Retrieved " + waiters.size() + " waiters");

        } catch (SQLException e) {
            System.out.println("❌ Select waiters failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return waiters;
    }

    // ========================================
    // WEEK 8: UPDATE OPERATION
    // ========================================

    /**
     * UPDATE Chef in database
     * @param chef Chef object with updated data
     * @return true if successful
     */

    // CHEF from DB
    // CHEF set change
    // update chef
    public boolean updateChef(Chef chef) {
        String sql = "UPDATE staff SET name = ?, salary = ?, experience_years = ?, specialization = ? " +
                "WHERE staff_id = ? AND staff_type = 'CHEF'";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, chef.getName());
            statement.setDouble(2, chef.getSalary());
            statement.setInt(3, chef.getExperienceYears());
            statement.setString(4, chef.getSpecialization());
            statement.setInt(5, chef.getStaffId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated > 0) {
                System.out.println("✅ Chef updated: " + chef.getName());
                return true;
            } else {
                System.out.println("⚠️ No chef found with ID: " + chef.getStaffId());
            }

        } catch (SQLException e) {
            System.out.println("❌ Update Chef failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    /**
     * UPDATE Waiter in database
     * @param waiter Waiter object with updated data
     * @return true if successful
     */
    public boolean updateWaiter(Waiter waiter) {
        String sql = "UPDATE staff SET name = ?, salary = ?, experience_years = ?, tables_served = ? " +
                "WHERE staff_id = ? AND staff_type = 'WAITER'";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, waiter.getName());
            statement.setDouble(2, waiter.getSalary());
            statement.setInt(3, waiter.getExperienceYears());
            statement.setInt(4, waiter.getTablesServed());
            statement.setInt(5, waiter.getStaffId());

            int rowsUpdated = statement.executeUpdate();
            statement.close();

            if (rowsUpdated > 0) {
                System.out.println("✅ Waiter updated: " + waiter.getName());
                return true;
            } else {
                System.out.println("⚠️ No waiter found with ID: " + waiter.getStaffId());
            }

        } catch (SQLException e) {
            System.out.println("❌ Update Waiter failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    // ========================================
    // WEEK 8: DELETE OPERATION
    // ========================================

    /**
     * DELETE staff by ID
     * @param staffId ID of staff to delete
     * @return true if successful
     */
    public boolean deleteStaff(int staffId) {
        String sql = "DELETE FROM staff WHERE staff_id = ?";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return false;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, staffId);

            int rowsDeleted = statement.executeUpdate();
            statement.close();

            if (rowsDeleted > 0) {
                System.out.println("✅ Staff deleted (ID: " + staffId + ")");
                return true;
            } else {
                System.out.println("⚠️ No staff found with ID: " + staffId);
            }

        } catch (SQLException e) {
            System.out.println("❌ Delete failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return false;
    }

    // ========================================
    // WEEK 8: SEARCH BY NAME
    // ========================================

    /**
     * SEARCH staff by name (partial match, case-insensitive)
     * Example: searchByName("mur") finds "Murat", "Murray", etc.
     * @param name Name or partial name to search
     * @return List of matching staff
     */
    public List<Staff> searchByName(String name) {
        List<Staff> staffList = new ArrayList<>();

        // ILIKE for case-insensitive search, % for partial match
        String sql = "SELECT * FROM staff WHERE name ILIKE ? ORDER BY name";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");  // % = wildcard

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Found " + staffList.size() + " staff matching '" + name + "'");

        } catch (SQLException e) {
            System.out.println("❌ Search by name failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    // ========================================
    // WEEK 8: SEARCH BY SALARY RANGE
    // ========================================

    /**
     * SEARCH staff by salary range
     * @param minSalary Minimum salary (inclusive)
     * @param maxSalary Maximum salary (inclusive)
     * @return List of staff in salary range
     */
    public List<Staff> searchBySalaryRange(double minSalary, double maxSalary) {
        List<Staff> staffList = new ArrayList<>();

        String sql = "SELECT * FROM staff WHERE salary BETWEEN ? AND ? ORDER BY salary DESC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, minSalary);
            statement.setDouble(2, maxSalary);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Found " + staffList.size() + " staff in salary range " +
                    minSalary + " - " + maxSalary);

        } catch (SQLException e) {
            System.out.println("❌ Search by salary failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    /**
     * SEARCH staff with minimum salary
     * @param minSalary Minimum salary
     * @return List of staff earning at least minSalary
     */
    public List<Staff> searchByMinSalary(double minSalary) {
        List<Staff> staffList = new ArrayList<>();

        String sql = "SELECT * FROM staff WHERE salary >= ? ORDER BY salary DESC";

        Connection connection = DatabaseConnection.getConnection();
        if (connection == null) return staffList;

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDouble(1, minSalary);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Staff staff = extractStaffFromResultSet(resultSet);
                if (staff != null) {
                    staffList.add(staff);
                }
            }

            resultSet.close();
            statement.close();

            System.out.println("✅ Found " + staffList.size() + " staff earning >= " + minSalary);

        } catch (SQLException e) {
            System.out.println("❌ Search by min salary failed!");
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(connection);
        }

        return staffList;
    }

    // ========================================
    // HELPER METHOD
    // ========================================

    /**
     * Extract Staff object from ResultSet
     * Creates Chef or Waiter based on staff_type
     */
    private Staff extractStaffFromResultSet(ResultSet resultSet) throws SQLException {
        int staffId = resultSet.getInt("staff_id");
        String name = resultSet.getString("name");
        double salary = resultSet.getDouble("salary");
        int experienceYears = resultSet.getInt("experience_years");
        String staffType = resultSet.getString("staff_type");

        Staff staff = null;

        if ("CHEF".equals(staffType)) {
            String specialization = resultSet.getString("specialization");
            staff = new Chef(staffId, name, salary, experienceYears, specialization);

        } else if ("WAITER".equals(staffType)) {
            int tablesServed = resultSet.getInt("tables_served");
            staff = new Waiter(staffId, name, salary, experienceYears, tablesServed);
        }

        return staff;
    }

    // ========================================
    // DISPLAY METHODS
    // ========================================

    /**
     * Display all staff in console
     */
    public void displayAllStaff() {
        List<Staff> staffList = getAllStaff();

        System.out.println("\n========================================");
        System.out.println("   ALL STAFF FROM DATABASE");
        System.out.println("========================================");

        if (staffList.isEmpty()) {
            System.out.println("No staff members in database.");
        } else {
            for (int i = 0; i < staffList.size(); i++) {
                Staff s = staffList.get(i);
                System.out.print((i + 1) + ". ");
                System.out.print("[" + s.getRole() + "] ");
                System.out.println(s.toString());
            }
        }

        System.out.println("========================================\n");
    }

    /**
     * Demonstrate polymorphism with database data
     */
    public void demonstratePolymorphism() {
        List<Staff> staffList = getAllStaff();

        System.out.println("\n========================================");
        System.out.println("  POLYMORPHISM: Staff from Database");
        System.out.println("========================================");

        if (staffList.isEmpty()) {
            System.out.println("No staff to demonstrate.");
        } else {
            for (Staff s : staffList) {
                s.work();  // Polymorphic call!
            }
        }

        System.out.println("========================================\n");
    }
}
