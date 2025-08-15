module dev.jolvera.finjav {
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

    opens dev.jolvera.finjav to javafx.fxml;
    exports dev.jolvera.finjav;
}