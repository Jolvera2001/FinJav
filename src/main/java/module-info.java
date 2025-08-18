module dev.jolvera.finjav {
    requires java.compiler;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires org.jdbi.v3.core;
    requires org.jdbi.v3.sqlobject;
    requires org.xerial.sqlitejdbc;
    requires jakarta.validation;
    requires jbcrypt;

    exports dev.jolvera.finjav;
    exports dev.jolvera.finjav.controllers;
    opens dev.jolvera.finjav to javafx.fxml;
    opens dev.jolvera.finjav.controllers to javafx.fxml;
}