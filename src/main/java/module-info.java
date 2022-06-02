module com.example.ricochetsrobots {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.media;

    opens com.ricochetrobots.systems to javafx.fxml;
    exports com.ricochetrobots.systems;
}