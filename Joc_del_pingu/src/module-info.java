module Joc_del_pingu {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens Vistas to javafx.fxml;
    opens Modelos to javafx.graphics;
}