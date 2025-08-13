module dev.jolvera.finjav {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens dev.jolvera.finjav to javafx.fxml;
    exports dev.jolvera.finjav;
}