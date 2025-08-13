package dev.jolvera.finjav;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField searchField;

    @FXML
    private ListView<String> sidebarList;

    @FXML
    private void Initialize() {
        sidebarList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    private void addTransaction() {
        System.out.println("addTransaction pressed");
    }

    public void createProjection() {
        System.out.println("createProjection pressed");
    }

    public void refreshTransactions() {
        System.out.println("refreshTransactions pressed");
    }
}
