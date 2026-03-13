module Joc_del_pingu {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens Vistas to javafx.fxml;
    opens Modelos to javafx.graphics;
}