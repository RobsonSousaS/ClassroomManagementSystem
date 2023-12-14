package br.com.robytech.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.robytech.model.ClassRoomModel;
import br.com.robytech.model.DisciplineModel;
import br.com.robytech.model.enums.DaysWeekEnum;
import br.com.robytech.model.enums.HoraryEnum;
import br.com.robytech.model.enums.StatusEnum;
import br.com.robytech.model.enums.TurnEnum;
import br.com.robytech.model.enums.TypeClassEnum;
import br.com.robytech.util.DatabaseUtil;

public class DisciplineDAO {

    private int nextDisciplineId = 0;

    public List<DisciplineModel> getAllDisciplines() {
        List<DisciplineModel> disciplines = new ArrayList<>();
        String sql = "SELECT * FROM discipline";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                DisciplineModel discipline = new DisciplineModel(
                        resultSet.getString("nameDiscipline"),
                        resultSet.getString("course"),
                        resultSet.getInt("weeklyWorkload"),
                        resultSet.getString("teacher"),
                        TurnEnum.valueOf(resultSet.getString("turn")),
                        DaysWeekEnum.valueOf(resultSet.getString("day")),
                        HoraryEnum.valueOf(resultSet.getString("horary"))

                );
                discipline.setCodDiscipline(resultSet.getString("codDiscipline"));
                disciplines.add(discipline);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return disciplines;
    }

    public DisciplineModel getDisciplineByCode(String codDiscipline) {
        String sql = "SELECT * FROM discipline WHERE codDiscipline = ?";
        DisciplineModel discipline = null;

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, codDiscipline);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    discipline = new DisciplineModel(
                            resultSet.getString("nameDiscipline"),
                            resultSet.getString("course"),
                            resultSet.getInt("weeklyWorkload"),
                            resultSet.getString("teacher"),
                            TurnEnum.valueOf(resultSet.getString("turn")),
                            DaysWeekEnum.valueOf(resultSet.getString("day")),
                            HoraryEnum.valueOf(resultSet.getString("horary")));

                    discipline.setCodDiscipline(resultSet.getString("codDiscipline"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discipline;
    }

    public void insertDiscipline(DisciplineModel discipline) {
        String sql = "INSERT INTO discipline (nameDiscipline, codDiscipline, course, weeklyWorkload, teacher, turn, day, horary) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql,
                        PreparedStatement.RETURN_GENERATED_KEYS)) {

            discipline.setCodDiscipline(generateNextDisciplineId());
            preparedStatement.setString(1, discipline.getNameDiscipline());
            preparedStatement.setString(2, discipline.getCodDiscipline());
            preparedStatement.setString(3, discipline.getCourse());
            preparedStatement.setInt(4, discipline.getWeeklyWorkload());
            preparedStatement.setString(5, discipline.getTeacher());
            preparedStatement.setString(6, discipline.getTurn().toString());
            preparedStatement.setString(7, discipline.getDay().toString());
            preparedStatement.setString(8, discipline.getHorary().toString());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    discipline.setCodDiscipline(generatedKeys.getString(1));
                }
            }
            updateClassRoomWithDiscipline(discipline.getCodDiscipline(), discipline.getClassRoom());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateClassRoomWithDiscipline(String codDiscipline, ClassRoomModel classRoom) {
        String sql = "UPDATE classroom SET Discipline_codDiscipline = ? WHERE idString = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, codDiscipline);
            preparedStatement.setString(2, classRoom.getIdString());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDiscipline(DisciplineModel discipline) {
        String sql = "UPDATE discipline SET nameDiscipline = ?, course = ?, weeklyWorkload = ?, teacher = ?, turn = ?, day = ?, horary = ? WHERE codDiscipline = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, discipline.getNameDiscipline());
            preparedStatement.setString(2, discipline.getCourse());
            preparedStatement.setInt(3, discipline.getWeeklyWorkload());
            preparedStatement.setString(4, discipline.getTeacher());
            preparedStatement.setString(5, discipline.getTurn().toString());
            preparedStatement.setString(6, discipline.getDay().toString());
            preparedStatement.setString(7, discipline.getHorary().toString());
            preparedStatement.setString(8, discipline.getCodDiscipline());

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    discipline.setCodDiscipline(generatedKeys.getString(1));
                }
            }
            updateClassRoomWithDiscipline(discipline.getCodDiscipline(), discipline.getClassRoom());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDiscipline(String codDiscipline) {
        String sql = "DELETE FROM discipline WHERE codDiscipline = ?";

        try (Connection connection = DatabaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, codDiscipline);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public String generateNextDisciplineId() {
        String nextId = "D" + nextDisciplineId;
        nextDisciplineId += 1;
        return nextId;
    }
}
