package br.com.robytech.view;
import javafx.stage.Stage;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainMenuPage extends VBox {

    public MainMenuPage(Stage primaryStage) {
        setSpacing(20);
        setAlignment(Pos.CENTER);

        Button manageRoomButton = new Button("Gerenciar Sala");
        Button manageDisciplineButton = new Button("Gerenciar Disciplina");
        Button viewAllocationsButton = new Button("Visualizar Alocações");

        manageRoomButton.setOnAction(event -> manageRoomButton.setOnAction(e -> {ManageRoomsPage.launch();;}));
        manageDisciplineButton.setOnAction(event -> System.out.println("Ação: Gerenciar Disciplina"));
        viewAllocationsButton.setOnAction(event -> System.out.println("Ação: Visualizar Alocações"));

        String buttonStyle = "-fx-font-size: 18; -fx-min-width: 380; -fx-min-height: 60;";
        manageRoomButton.setStyle(buttonStyle);
        manageDisciplineButton.setStyle(buttonStyle);
        viewAllocationsButton.setStyle(buttonStyle);

        getChildren().addAll(manageRoomButton, manageDisciplineButton, viewAllocationsButton);
    }
}
