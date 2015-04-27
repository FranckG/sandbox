/**
 * Copyright (c) THALES, 2014. All rights reserved.
 */
package com.thalesgroup.orchestra.security.credentials;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;

import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.modes.AEADBlockCipher;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.params.AEADParameters;
import org.bouncycastle.crypto.params.KeyParameter;

public class AESGCMCryptoService implements CryptoService {

    private final static int iterationCount = 200000;
    private final static int keySize = 256;
    private final static int macGCM = 128;
    private final static int nonceBitSize = 128;
    private final static int saltBitSize = 256;
    private final PKCS5S2ParametersGenerator pkcs5s2ParametersGenerator;
    private final SecureRandom secureRandom;
    private final byte[] masterPassword;

    AESGCMCryptoService(String masterPassword) {
        pkcs5s2ParametersGenerator = new PKCS5S2ParametersGenerator();
        secureRandom = new SecureRandom();
        this.masterPassword = PKCS5S2ParametersGenerator
                .PKCS5PasswordToUTF8Bytes(masterPassword.toCharArray());
    }

    @Override
    public byte[] decrypt(byte[] data) throws CryptoServiceException {
        try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
            byte[] salt = new byte[saltBitSize / 8];
            stream.read(salt);

            KeyParameter key = getKey(salt);

            byte[] nonce = new byte[nonceBitSize / 8];
            stream.read(nonce);

            byte[] cipherText = new byte[data.length - nonce.length
                    - salt.length];
            stream.read(cipherText);

            return process(cipherText, key, false, nonce);
        } catch (Exception e) {
            throw new CryptoServiceException("Decryption failed");
        }
    }

    @Override
    public byte[] encrypt(byte[] data) throws CryptoServiceException {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            byte[] salt = generateRandomBytes(saltBitSize);
            stream.write(salt);
            KeyParameter key = getKey(salt);

            byte[] nonce = generateRandomBytes(nonceBitSize);
            stream.write(nonce);

            byte[] cipherText = process(data, key, true, nonce);
            stream.write(cipherText);

            return stream.toByteArray();
        } catch (Exception e) {
            throw new CryptoServiceException("Encryption failed");
        }
    }

    private KeyParameter getKey(byte[] salt) {
        pkcs5s2ParametersGenerator.init(masterPassword, salt, iterationCount);
        return (KeyParameter) (pkcs5s2ParametersGenerator
                .generateDerivedParameters(keySize));
    }

    private byte[] generateRandomBytes(int sizeInBits) {
        byte[] bytes = new byte[sizeInBits / 8];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

    private byte[] process(byte[] input, KeyParameter key,
            boolean forEncryption, byte[] nonce) throws Exception {
        AESEngine engine = new AESEngine();
        AEADBlockCipher cipher = new GCMBlockCipher(engine);

        AEADParameters parameters = new AEADParameters(key, macGCM, nonce, null);
        cipher.init(forEncryption, parameters);

        byte[] cipherText = new byte[cipher.getOutputSize(input.length)];

        int outputLength = cipher.processBytes(input, 0, input.length,
                cipherText, 0);

        cipher.doFinal(cipherText, outputLength);

        return cipherText;
    }
}
