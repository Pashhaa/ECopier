module com.example.ecopier {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.commons.io;
    requires poi.ooxml;
    requires poi;


    opens com.example.ecopier to javafx.fxml;
    exports com.example.ecopier;
}