package back.vybz.notification_service.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfig {

    @Value("${firebase.config-path}")
    private Resource firebaseConfig;

    @PostConstruct
    public void init() {
        try {
            InputStream serviceAccount = firebaseConfig.getInputStream();
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase 초기화 성공");
            }
        } catch (Exception e) {
            throw new RuntimeException("Firebase 초기화 실패", e);
        }
    }

}
