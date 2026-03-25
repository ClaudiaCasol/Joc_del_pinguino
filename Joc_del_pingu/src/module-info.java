module Joc_del_pingu {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.media;
<<<<<<< Updated upstream
	requires javafx.graphics;
=======
	requires javafx.media;
>>>>>>> Stashed changes

    opens Vistas to javafx.fxml;
    opens Modelos to javafx.graphics;
}