module com.esprit.wellbeing_final {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.esprit.wellbeing_final to javafx.fxml;
    exports com.esprit.wellbeing_final;

    
    exports com.esprit.wellbeing_final.controllers.admin;
    opens com.esprit.wellbeing_final.controllers.admin to javafx.fxml;
    exports com.esprit.wellbeing_final.controllers.employee;
    opens com.esprit.wellbeing_final.controllers.employee to javafx.fxml;
    exports com.esprit.wellbeing_final.controllers.auth;
    opens com.esprit.wellbeing_final.controllers.auth to javafx.fxml;
    exports com.esprit.wellbeing_final.controllers.coach;
    opens com.esprit.wellbeing_final.controllers.coach to javafx.fxml;

}