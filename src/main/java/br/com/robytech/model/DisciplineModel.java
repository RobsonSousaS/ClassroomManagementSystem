package br.com.robytech.model;

import br.com.robytech.model.enums.DaysWeekEnum;
import br.com.robytech.model.enums.HoraryEnum;
import br.com.robytech.model.enums.TurnEnum;

public class DisciplineModel {
    private String nomeDiscipline;
    private String codDiscipline;
    private String course;
    private int weeklyWorkload;
    private String teacher;
    private TurnEnum turn = TurnEnum.M;
    private DaysWeekEnum day = DaysWeekEnum.SEGUNDA;
    private HoraryEnum horary = HoraryEnum.AB;
    private ClassRoomModel classRoom;

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

}
