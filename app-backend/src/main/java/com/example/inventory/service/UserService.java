// service/UserService.java
package com.example.inventory.service;

import com.example.inventory.entity.User;
import com.example.inventory.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // コンストラクタインジェクション
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * ユーザー登録（または初期登録用）
     * 生のパスワードを受け取り、BCrypt でハッシュ化して保存する。
     */
    public User registerUser(String username, String rawPassword, String role) {
        // BCryptでハッシュ化
        String hashed = passwordEncoder.encode(rawPassword);

        // 便利コンストラクタで必須項目をまとめてセット
        User user = new User(username, hashed, role, true);
        return userRepository.save(user);
    }

    /**
     * ログイン認証。
     * - username でユーザーを検索
     * - PasswordEncoder.matches で生パスワードとハッシュを比較
     */
    public Optional<User> authenticate(String username, String rawPassword) {
        log.info("[AUTH] try username={}", username);

        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isEmpty()) {
            log.info("[AUTH] user not found: {}", username);
            return Optional.empty();
        }

        User user = userOpt.get();

        // enabled を見たいなら（いまは必須じゃないけどチェックに便利）
        log.info("[AUTH] found username={} enabled={} role={} hashPrefix={}",
                user.getUsername(),
                user.isEnabled(),
                user.getRole(),
                user.getPassword().substring(0, Math.min(10, user.getPassword().length()))
        );

        boolean ok = passwordEncoder.matches(rawPassword, user.getPassword());
        log.info("[AUTH] password matches? {}", ok);

        // enabled を認証条件にする場合はここも有効化
        // if (!user.isEnabled()) return Optional.empty();

        return ok ? userOpt : Optional.empty();
    }
}
