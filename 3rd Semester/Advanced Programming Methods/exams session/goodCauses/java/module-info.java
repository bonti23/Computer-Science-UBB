module ubb.scs.map.faptebune {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires org.jgrapht.core;
    requires jdk.jfr;
    requires java.desktop;

    opens ubb.scs.map.faptebune.domain to javafx.base;
    opens ubb.scs.map.faptebune.controller to javafx.fxml;
    exports ubb.scs.map.faptebune;
}
