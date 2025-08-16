package dev.jolvera.finjav.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;

public class MainPageController {
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
