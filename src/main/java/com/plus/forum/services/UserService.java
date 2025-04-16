package com.plus.forum.services;

import com.plus.forum.repositories.User;
import com.plus.forum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Value("${app.avatar.upload-directory:src/main/resources/public/images/user_avatars}")
    private String avatarUploadPath;

    @Value("${app.avatar.web-path:/images/user_avatars/}")
    private String avatarWebPath;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User createOAuthUser(String username, String email, String avatarUrl) {
        System.out.println("Creating user: " + username + ", " + email + ", " + avatarUrl);
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(null);
        user.setRoles(Set.of("ROLE_USER"));
        user.setAvatar(avatarUrl);
        return userRepository.save(user);
    }

    public void updateAvatar(User user, String avatarUrl) {
        user.setAvatar(avatarUrl);
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void updateAvatar(Long userId, MultipartFile avatarFile) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String contentType = avatarFile.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Only image files are allowed");
        }

        File uploadDirectory = new File(avatarUploadPath);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        String fileExtension = getFileExtension(avatarFile.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + fileExtension;

        Path filePath = Paths.get(avatarUploadPath, fileName);
        Files.copy(avatarFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        String oldAvatarPath = user.getAvatar();
        if (oldAvatarPath != null && oldAvatarPath.startsWith(avatarWebPath)) {
            try {
                String oldFileName = oldAvatarPath.substring(avatarWebPath.length());
                Path oldFilePath = Paths.get(avatarUploadPath, oldFileName);
                Files.deleteIfExists(oldFilePath);
            } catch (IOException e) {
                System.err.println("Failed to delete old avatar: " + e.getMessage());
            }
        }

        String avatarUrl = avatarWebPath + fileName;
        user.setAvatar(avatarUrl);

        userRepository.save(user);
    }

    private String getFileExtension(String filename) {
        if (filename == null) {
            return "";
        }
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex);
    }

    public void updateEmail(Long userId, String email) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(email);
        userRepository.save(user);
    }

    public void updatePassword(Long userId, String newPassword) {
        if (passwordEncoder == null) {
            throw new RuntimeException("PasswordEncoder not configured");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
