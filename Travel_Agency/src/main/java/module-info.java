module org.project.travel_agency {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires org.slf4j;

    exports org.project.travel_agency.Application;
    opens org.project.travel_agency.Application to javafx.fxml;

}