module com.sio.pi_zza {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens com.sio.pi_zza to javafx.fxml;
    exports com.sio.pi_zza;
    requires jakarta.ws.rs;
    requires json;
    requires jersey.client;
    requires jersey.hk2;
    requires jakarta.annotation;
    requires jakarta.activation;
    requires java.logging;
}