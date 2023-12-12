package br.com.robytech.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import br.com.robytech.model.enums.DaysWeekEnum;
import br.com.robytech.model.enums.HoraryEnum;
import br.com.robytech.model.enums.TurnEnum;

public class DisciplineModel implements Serializable {
    private String nomeDiscipline;
    private String codDiscipline;
    private String course;
    private int weeklyWorkload;
    private String teacher;
    private TurnEnum turn = TurnEnum.M;
    private DaysWeekEnum day = DaysWeekEnum.SEGUNDA;
    private HoraryEnum horary = HoraryEnum.AB;
    private ClassRoomModel classRoom;

    public DisciplineModel(String nomeDiscipline, String course, int weeklyWorkload,
            String teacher, TurnEnum turn, DaysWeekEnum day, HoraryEnum horary, ClassRoomModel classRoom) {
        this.nomeDiscipline = nomeDiscipline;
        this.course = course;
        this.weeklyWorkload = weeklyWorkload;
        this.teacher = teacher;
        this.turn = turn;
        this.day = day;
        this.horary = horary;
        this.classRoom = classRoom;
    }

    public String getNomeDiscipline() {
        return nomeDiscipline;
    }

    public void setNomeDiscipline(String nomeDiscipline) {
        this.nomeDiscipline = nomeDiscipline;
    }

    public String getCodDiscipline() {
        return codDiscipline;
    }

    public void setCodDiscipline(String codDiscipline) {
        this.codDiscipline = codDiscipline;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getWeeklyWorkload() {
        return weeklyWorkload;
    }

    public void setWeeklyWorkload(int weeklyWorkload) {
        this.weeklyWorkload = weeklyWorkload;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public TurnEnum getTurn() {
        return turn;
    }

    public void setTurn(TurnEnum turn) {
        this.turn = turn;
    }

    public DaysWeekEnum getDay() {
        return day;
    }

    public void setDay(DaysWeekEnum day) {
        this.day = day;
    }

    public HoraryEnum getHorary() {
        return horary;
    }

    public void setHorary(HoraryEnum horary) {
        this.horary = horary;
    }

    public ClassRoomModel getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoomModel classRoom) {
        this.classRoom = classRoom;
    }

    private static final String FILE_NAME = "discipline.dat";

    public static void saveToFile(List<DisciplineModel> disciplines) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(disciplines);
            System.out.println("Discinpline salva com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<DisciplineModel> loadFromFile() {
        List<DisciplineModel> disciplines = new ArrayList<>();
        if (Files.exists(Paths.get(FILE_NAME))) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                disciplines = (List<DisciplineModel>) ois.readObject();
                System.out.println("Disciplina carregada com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return disciplines;
    }

}
