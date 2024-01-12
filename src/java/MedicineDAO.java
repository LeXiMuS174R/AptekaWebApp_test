// MedicineDAO.java

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {

    private Connection connection;

//    public void setConnection(Connection connection) {
//        this.connection = connection;
//    }

    public MedicineDAO(Connection connection) {
        this.connection = connection;
    }
    
    // Метод для получения списка лекарств из базы данных
    public List<Medicine> getAllMedicines() {
        List<Medicine> medicines = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicine"); ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int quantity = resultSet.getInt("quantity");

                Medicine medicine = new Medicine(id, name, quantity);
                medicines.add(medicine);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Обработайте исключение согласно вашей логике
        }

        return medicines;
    }

    public Medicine getMedicineById(int id) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM medicine WHERE id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int quantity = resultSet.getInt("quantity");

                    return new Medicine(id, name, quantity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обработайте исключение согласно вашей логике
        }
        return null; // Возвращаем null, если не найдено лекарство с указанным id
    }

    // Добавление нового лекарства в базу данных
    public void addMedicine(Medicine medicine) {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO medicine (name, quantity) VALUES (?, ?)")) {

            statement.setString(1, medicine.getName());
            statement.setInt(2, medicine.getQuantity());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Обработайте исключение согласно вашей логике
        }
    }

    // Обновление информации о лекарстве в базе данных
    public void updateMedicine(Medicine medicine) {
        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE medicine SET name=?, quantity=? WHERE id=?")) {

            statement.setString(1, medicine.getName());
            statement.setInt(2, medicine.getQuantity());
            statement.setInt(3, medicine.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); // Обработайте исключение согласно вашей логике
        }
    }
}