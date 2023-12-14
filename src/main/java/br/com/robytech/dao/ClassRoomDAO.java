package br.com.robytech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.robytech.model.ClassRoomModel;
import br.com.robytech.model.enums.StatusEnum;
import br.com.robytech.model.enums.TypeClassEnum;
import br.com.robytech.util.DatabaseUtil;

public class ClassRoomDAO {
    public List<ClassRoomModel> getAllClassRooms() {
        List<ClassRoomModel> classRooms = new ArrayList<>();
        String sql = "SELECT * FROM classroom";
        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ClassRoomModel classRoom = new ClassRoomModel(
                        resultSet.getString("idString"),
                        resultSet.getInt("block"),
                        resultSet.getInt("numberClass"),
                        TypeClassEnum.valueOf(resultSet.getString("typeClass")),
                        StatusEnum.valueOf(resultSet.getString("status")));

                classRoom.setIdString(resultSet.getString("idString"));

                classRooms.add(classRoom);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classRooms;
    }

    public void insertClassRoom(ClassRoomModel classRoom) {
        String sql = "INSERT INTO classroom (idString, block, numberClass, typeClass, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, classRoom.getIdString());
            preparedStatement.setInt(2, classRoom.getBlock());
            preparedStatement.setInt(3, classRoom.getNumberClass());
            preparedStatement.setString(4, classRoom.getTypeClass().toString());
            preparedStatement.setString(5, classRoom.getStatus().toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateClassRoom(ClassRoomModel classRoom) {
        String sql = "UPDATE classroom SET block = ?, numberClass = ?, typeClass = ?, status = ? WHERE idString = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, classRoom.getBlock());
            preparedStatement.setInt(2, classRoom.getNumberClass());
            preparedStatement.setString(3, classRoom.getTypeClass().toString());
            preparedStatement.setString(4, classRoom.getStatus().toString());
            preparedStatement.setString(5, classRoom.getIdString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteClassRoom(String idString) {
        String sql = "DELETE FROM classroom WHERE idString = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, idString);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
