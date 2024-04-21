package com.example.Tuesday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.VaultResponse;

import java.util.Collections;

@Service
public class VaultOtpService {

    @Autowired
    private VaultTemplate vaultTemplate;

    public void storeOtp(String email, String otp) {
        vaultTemplate.write("otp/data/" + email, Collections.singletonMap("otp", otp));
    }

    public boolean verifyOtp(String email, String otp) {
        VaultResponse response = vaultTemplate.read("otp/data/" + email);
        if (response != null && response.getData() != null) {
            String storedOtp = (String) response.getData().get("otp");
            return storedOtp != null && storedOtp.equals(otp);
        }
        return false;
    }

    public void deleteOtp(String email) {
        vaultTemplate.delete("otp/data/" + email);
    }
}
