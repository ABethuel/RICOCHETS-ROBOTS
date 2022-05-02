module com.example.ricochetsrobots {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.ricochetrobots.systems to javafx.fxml;
    exports com.ricochetrobots.systems;
}