package br.com.robytech.view;

import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuPage extends VBox {

    private ManageRoomsPage manageRoomsPage;
    private ManageDisciplinePage manageDisciplinePage;

    public MainMenuPage(Stage primaryStage) {
        setSpacing(20);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: #1cc6e8;");

        manageRoomsPage  = new ManageRoomsPage();
        manageDisciplinePage = new ManageDisciplinePage();

        Button manageRoomButton = new Button("Gerenciar Sala");
        Button manageDisciplineButton = new Button("Gerenciar Disciplina");
        Button viewAllocationsButton = new Button("Visualizar Alocações");

        manageRoomButton.setOnAction(event -> {manageRoomsPage.show(primaryStage); manageRoomsPage.updateListView();});
        manageDisciplineButton.setOnAction(event -> {manageDisciplinePage.show(primaryStage); manageDisciplinePage.updateListView();});
        viewAllocationsButton.setOnAction(event -> System.out.println("Ação: Visualizar Alocações"));

        String buttonStyle = "-fx-font-size: 18; -fx-min-width: 380; -fx-min-height: 60;";
        manageRoomButton.setStyle(buttonStyle);
        manageDisciplineButton.setStyle(buttonStyle);
        viewAllocationsButton.setStyle(buttonStyle);

        getChildren().addAll(manageRoomButton, manageDisciplineButton, viewAllocationsButton);
    }
}
