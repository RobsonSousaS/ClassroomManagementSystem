package br.com.robytech.view;

import br.com.robytech.model.DisciplineModel;
import br.com.robytech.model.enums.DaysWeekEnum;
import br.com.robytech.model.enums.HoraryEnum;
import br.com.robytech.model.enums.TurnEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManageDisciplinePage {

    private static ObservableList<DisciplineModel> disciplines;
    private static ListView<DisciplineModel> disciplineListView;

    public void show(Stage primaryStage) {
        primaryStage.setTitle("Gerenciar Disciplinas");

        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #1cc6e8;");
        root.setAlignment(Pos.CENTER);

        disciplines = FXCollections.observableArrayList();

        TextField searchField = new TextField();
        searchField.setPromptText("Digite o código ou nome da disciplina");

        disciplineListView = new ListView<>(FXCollections.observableArrayList());
        disciplineListView.setCellFactory(param -> new ListCell<DisciplineModel>() {
            private HBox buttonsBox;

            {
                Button updateButton = new Button("Atualizar");
                Button deleteButton = new Button("Deletar");

                updateButton.setOnAction(event -> {
                    DisciplineModel item = getItem();
                    showEditDisciplineDialog(item);
                });

                deleteButton.setOnAction(event -> {
                    DisciplineModel item = getItem();
                    disciplines.remove(item);
                    updateListView(disciplineListView); 
                });

                buttonsBox = new HBox(10, updateButton, deleteButton);
                buttonsBox.setAlignment(Pos.CENTER_LEFT);
            }

            @Override
            protected void updateItem(DisciplineModel item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getCodDiscipline() + " - Nome: " + item.getNomeDiscipline() + " - Professor: "
                            + item.getTeacher() + " - Carga: "
                            + item.getWeeklyWorkload() + "Hrs" + " - Horario: " + item.getHorary() + " - Turno: "
                            + item.getTurn()
                            + " - Dia da Semana: " + item.getDay());
                    setGraphic(new VBox(5, buttonsBox));
                }
            }
        });

        Button searchButton = new Button("Pesquisar");
        searchButton.setOnAction(event -> {
            String searchTerm = searchField.getText().toLowerCase();
            ObservableList<DisciplineModel> filteredList = FXCollections.observableArrayList();

            for (DisciplineModel discipline : disciplines) {
                if (discipline.getCodDiscipline().toLowerCase().contains(searchTerm)
                        || discipline.getNomeDiscipline().toLowerCase().contains(searchTerm)) {
                    filteredList.add(discipline);
                }
            }

            disciplineListView.setItems(filteredList);
        });

        Button addButton = new Button("Adicionar Disciplina");
        addButton.setOnAction(event -> showAddDisciplineDialog());

        root.getChildren().addAll(searchField, searchButton, addButton, disciplineListView);

        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddDisciplineDialog() {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle("Adicionar Disciplina");

        VBox dialogRoot = new VBox(20);
        dialogRoot.setAlignment(Pos.CENTER);

        TextField nomeDisciplineField = new TextField();
        nomeDisciplineField.setPromptText("Nome da Disciplina");

        TextField courseField = new TextField();
        courseField.setPromptText("Curso");

        TextField weeklyWorkloadField = new TextField();
        weeklyWorkloadField.setPromptText("Carga Horária Semanal");

        TextField teacherField = new TextField();
        teacherField.setPromptText("Professor");

        ComboBox<TurnEnum> turnComboBox = new ComboBox<>(
                FXCollections.observableArrayList(TurnEnum.values()));
        turnComboBox.setPromptText("Turno");

        ComboBox<DaysWeekEnum> dayComboBox = new ComboBox<>(
                FXCollections.observableArrayList(DaysWeekEnum.values()));
        dayComboBox.setPromptText("Dia da Semana");

        ComboBox<HoraryEnum> horaryComboBox = new ComboBox<>(
                FXCollections.observableArrayList(HoraryEnum.values()));
        horaryComboBox.setPromptText("Horário");

        Button addButton = new Button("Adicionar");
        addButton.setOnAction(event -> {
            String nomeDiscipline = nomeDisciplineField.getText();
            String course = courseField.getText();
            int weeklyWorkload = Integer.parseInt(weeklyWorkloadField.getText());
            String teacher = teacherField.getText();
            TurnEnum turn = turnComboBox.getValue();
            DaysWeekEnum day = dayComboBox.getValue();
            HoraryEnum horary = horaryComboBox.getValue();

            DisciplineModel newDiscipline = new DisciplineModel(nomeDiscipline, course, weeklyWorkload,
                    teacher, turn, day, horary, null);
            disciplines.add(newDiscipline);
            updateListView(disciplines);

            dialogStage.close();
        });

        dialogRoot.getChildren().addAll(nomeDisciplineField, courseField, weeklyWorkloadField,
                teacherField, turnComboBox, dayComboBox, horaryComboBox, addButton);

        Scene dialogScene = new Scene(dialogRoot, 500, 500);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }

    private void showEditDisciplineDialog(DisciplineModel disciplineToEdit) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle("Editar Disciplina");

        VBox dialogRoot = new VBox(20);
        dialogRoot.setAlignment(Pos.CENTER);

        TextField nomeDisciplineField = new TextField();
        nomeDisciplineField.setPromptText("Nome da Disciplina");
        nomeDisciplineField.setText(disciplineToEdit.getNomeDiscipline());

        TextField courseField = new TextField();
        courseField.setPromptText("Curso");
        courseField.setText(disciplineToEdit.getCourse());

        TextField weeklyWorkloadField = new TextField();
        weeklyWorkloadField.setPromptText("Carga Horária Semanal");
        weeklyWorkloadField.setText(String.valueOf(disciplineToEdit.getWeeklyWorkload()));

        TextField teacherField = new TextField();
        teacherField.setPromptText("Professor");
        teacherField.setText(disciplineToEdit.getTeacher());

        ComboBox<TurnEnum> turnComboBox = new ComboBox<>(
                FXCollections.observableArrayList(TurnEnum.values()));
        turnComboBox.setPromptText("Turno");
        turnComboBox.setValue(disciplineToEdit.getTurn());

        ComboBox<DaysWeekEnum> dayComboBox = new ComboBox<>(
                FXCollections.observableArrayList(DaysWeekEnum.values()));
        dayComboBox.setPromptText("Dia da Semana");
        dayComboBox.setValue(disciplineToEdit.getDay());

        ComboBox<HoraryEnum> horaryComboBox = new ComboBox<>(
                FXCollections.observableArrayList(HoraryEnum.values()));
        horaryComboBox.setPromptText("Horário");
        horaryComboBox.setValue(disciplineToEdit.getHorary());

        Button editButton = new Button("Editar");
        editButton.setOnAction(event -> {

            String nomeDiscipline = nomeDisciplineField.getText();
            String course = courseField.getText();
            int weeklyWorkload = Integer.parseInt(weeklyWorkloadField.getText());
            String teacher = teacherField.getText();
            TurnEnum turn = turnComboBox.getValue();
            DaysWeekEnum day = dayComboBox.getValue();
            HoraryEnum horary = horaryComboBox.getValue();

            DisciplineModel updatedDiscipline = new DisciplineModel(
                    nomeDiscipline, course, weeklyWorkload, teacher, turn, day, horary, null);
            int index = disciplines.indexOf(disciplineToEdit);
            disciplines.set(index, updatedDiscipline);
            updateListView(disciplines);

            dialogStage.close();
        });

        Button cancelButton = new Button("Cancelar");
        cancelButton.setOnAction(event -> dialogStage.close());

        dialogRoot.getChildren().addAll(nomeDisciplineField, courseField, weeklyWorkloadField,
                teacherField, turnComboBox, dayComboBox, horaryComboBox, editButton, cancelButton);

        Scene dialogScene = new Scene(dialogRoot, 400, 400);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }

    private void updateListView(ObservableList<DisciplineModel> updatedList) {
        disciplineListView.setItems(updatedList);
    }
}
