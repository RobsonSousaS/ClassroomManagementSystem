package br.com.robytech.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import br.com.robytech.model.enums.StatusEnum;
import br.com.robytech.model.enums.TypeClassEnum;

public class ClassRoomModel implements Serializable {
    private String idString;
    private int block;
    private int numberClass;
    private TypeClassEnum typeClass = TypeClassEnum.SALADEAULA;
    private StatusEnum status = StatusEnum.DISPONIVEL;
    private List<DisciplineModel> disciplines;

    public ClassRoomModel(int block, int numberClass, TypeClassEnum typeClass, StatusEnum status) {
        this.block = block;
        this.numberClass = numberClass;
        this.typeClass = typeClass;
        this.status = status;
    }

    public String getIdString() {
        return idString = getIdStringTemplate();
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getNumberClass() {
        return numberClass;
    }

    public void setNumberClass(int numberClass) {
        this.numberClass = numberClass;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public TypeClassEnum getTypeClass() {
        return typeClass;
    }

    public void setTypeClass(TypeClassEnum typeClass) {
        this.typeClass = typeClass;
    }

    public String getTypeClassString() {
        if (getTypeClass().equals(TypeClassEnum.SALADEAULA)) {
            return "Sala de Aula";
        } else if (getTypeClass().equals(TypeClassEnum.LABORATORIO)) {
            return "Laboratorio";
        } else {
            return null;
        }
    }

    public String getStatusString() {
        if (getStatus().equals(StatusEnum.DISPONIVEL)) {
            return "Disponível";
        } else if (getStatus().equals(StatusEnum.OCUPADO)) {
            return "Ocupado";
        } else if (getStatus().equals(StatusEnum.RESERVADO)) {
            return "Reservado";
        } else {
            return null;
        }
    }

    public String getIdStringTemplate() {
        if (getNumberClass() < 10) {
            return "B" + getBlock() + "S0" + getNumberClass();
        } else {
            return "B" + getBlock() + "S" + getNumberClass();
        }
    }

    public List<DisciplineModel> getDisciplines() {
        return disciplines;
    }

    public void setDisciplines(List<DisciplineModel> disciplines) {
        this.disciplines = disciplines;
    }

    private static final long serialVersionUID = 1L;

    private static final String CLASS_ROOMS_FILE = "classRooms.dat";

    private static List<ClassRoomModel> classRooms = new ArrayList<>();

    public static void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CLASS_ROOMS_FILE))) {
            oos.writeObject(classRooms);
            System.out.println("Lista de salas salva com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<ClassRoomModel> loadFromFile() {
        List<ClassRoomModel> classRooms = new ArrayList<>();
        if (Files.exists(Paths.get(CLASS_ROOMS_FILE))) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CLASS_ROOMS_FILE))) {
                classRooms = (List<ClassRoomModel>) ois.readObject();
                System.out.println("Lista de salas carregada com sucesso!");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Erro ao carregar arquivo: " + e.getMessage());
            }
        }

        return classRooms;
    }

}
