module com.sio.pi_zza {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sio.pi_zza to javafx.fxml;
    exports com.sio.pi_zza;
}
