package com.example.Tuesday;

import org.springframework.web.bind.annotation.*;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;


@RestController
public class EncryptionController {

    @PostMapping("/encrypt")
    public EncryptedMessage encryptMessage(@RequestBody String message) throws Exception {
        PrivateKey privateKey = getPrivateKey("private_key_pkcs8.pem");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        String encryptedMessage = Base64.getEncoder().encodeToString(encryptedBytes);

        String publicKey = new String(Files.readAllBytes(Paths.get("C:/Users/rbih2/public_key.pem")));

        return new EncryptedMessage(publicKey, encryptedMessage);
    }

    private PrivateKey getPrivateKey(String filename) throws Exception {
        String key = new String(Files.readAllBytes(Paths.get("C:/Users/rbih2/"+filename)), StandardCharsets.UTF_8);

        // Ensure only the encoded key is processed by removing headers, footers, and whitespace
        String privateKeyPEM = key
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");  // Remove all whitespace including newlines and tabs

        byte[] decoded = Base64.getDecoder().decode(privateKeyPEM);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    static class EncryptedMessage {
        private String publicKey;
        private String encryptedMessage;

        public EncryptedMessage(String publicKey, String encryptedMessage) {
            this.publicKey = publicKey;
            this.encryptedMessage = encryptedMessage;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getEncryptedMessage() {
            return encryptedMessage;
        }

        public void setEncryptedMessage(String encryptedMessage) {
            this.encryptedMessage = encryptedMessage;
        }
    }
}
