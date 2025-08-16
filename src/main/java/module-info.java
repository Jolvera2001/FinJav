module dev.jolvera.finjav {
    requires java.compiler;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires dagger;
    requires javax.inject;
    requires java.sql;
    requires org.jdbi.v3.core;
    requires org.jdbi.v3.sqlobject;
    requires jakarta.inject;
    requires static lombok;
    requires org.xerial.sqlitejdbc;
    requires jakarta.validation;

    opens dev.jolvera.finjav to javafx.fxml;
    exports dev.jolvera.finjav;
    exports dev.jolvera.finjav.controllers;
    opens dev.jolvera.finjav.controllers to javafx.fxml;
}