module Joc_del_pingu {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
	requires javafx.graphics;

	requires javafx.media;


    opens Vistas to javafx.fxml;
    opens Modelos to javafx.graphics;
}