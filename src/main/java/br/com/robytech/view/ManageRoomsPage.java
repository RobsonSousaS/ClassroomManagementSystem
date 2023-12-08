package br.com.robytech.view;

import br.com.robytech.model.ClassRoomModel;
import br.com.robytech.model.enums.StatusEnum;
import br.com.robytech.model.enums.TypeClassEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ManageRoomsPage {

    public static void show(Stage primaryStage) {
        primaryStage.setTitle("Gerenciar Salas");

        VBox root = new VBox(20);
        root.setStyle("-fx-background-color: #98fb98;");
        root.setAlignment(Pos.CENTER);

        ObservableList<ClassRoomModel> classRooms = FXCollections.observableArrayList();

        TextField searchField = new TextField();
        searchField.setPromptText("Digite número da sala");

        ListView<ClassRoomModel> roomListView = new ListView<>(classRooms);
        roomListView.setCellFactory(param -> new ListCell<ClassRoomModel>() {
            private HBox buttonsBox;

            {
                Button updateButton = new Button("Atualizar");
                Button deleteButton = new Button("Deletar");

                updateButton.setOnAction(event -> {
                    ClassRoomModel item = getItem();
                    System.out.println("Atualizando sala: " + item);
                });

                deleteButton.setOnAction(event -> {
                    ClassRoomModel item = getItem();
                    System.out.println("Deletando sala: " + item);
                    classRooms.remove(item);
                });

                buttonsBox = new HBox(10, updateButton, deleteButton);
                buttonsBox.setAlignment(Pos.CENTER_LEFT);
            }

            @Override
            protected void updateItem(ClassRoomModel item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item.getIdString() + " - Tipo de sala: " + item.getTypeClassString() + " - Status: "
                            + item.getStatusString());
                    setGraphic(new VBox(5, buttonsBox));
                }
            }
        });

        Button searchButton = new Button("Pesquisar");
        searchButton.setOnAction(event -> {
            String searchTerm = searchField.getText().toLowerCase();
            ObservableList<ClassRoomModel> filteredList = FXCollections.observableArrayList();

            for (ClassRoomModel room : classRooms) {
                if (String.valueOf(room.getNumberClass()).contains(searchTerm)) {
                    filteredList.add(room);
                }
            }

            roomListView.setItems(filteredList);
        });

        Button addButton = new Button("Adicionar Sala");
        addButton.setOnAction(event -> showAddRoomDialog(classRooms));

        root.getChildren().addAll(searchField, searchButton, addButton, roomListView);

        Scene scene = new Scene(root, 1280, 720);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void showAddRoomDialog(ObservableList<ClassRoomModel> classRooms) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setTitle("Adicionar Sala");

        VBox dialogRoot = new VBox(20);
        dialogRoot.setAlignment(Pos.CENTER);

        TextField blockField = new TextField();
        blockField.setPromptText("Número do Bloco");

        TextField numberField = new TextField();
        numberField.setPromptText("Número da Sala");

        ComboBox<TypeClassEnum> typeComboBox = new ComboBox<>(
                FXCollections.observableArrayList(TypeClassEnum.values()));
        typeComboBox.setPromptText("Tipo de Sala");

        ComboBox<StatusEnum> statusComboBox = new ComboBox<>(FXCollections.observableArrayList(StatusEnum.values()));
        statusComboBox.setPromptText("Status");

        Button addButton = new Button("Adicionar");
        addButton.setOnAction(event -> {
            int block = Integer.parseInt(blockField.getText());
            int number = Integer.parseInt(numberField.getText());
            TypeClassEnum type = typeComboBox.getValue();
            StatusEnum status = statusComboBox.getValue();

            ClassRoomModel newRoom = new ClassRoomModel(block, number, type, status);
            classRooms.add(newRoom);

            dialogStage.close();
        });

        dialogRoot.getChildren().addAll(blockField, numberField, typeComboBox, statusComboBox,
                addButton);

        Scene dialogScene = new Scene(dialogRoot, 400, 300);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();
    }
}