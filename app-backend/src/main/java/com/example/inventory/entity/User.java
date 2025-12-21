package com.example.inventory.entity;

import jakarta.persistence.*;

/**
 * ログインユーザー情報を管理するエンティティ
 * 対応テーブル: users
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // BIGINT AUTO_INCREMENT
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;  // ログインID

    @Column(name = "password", nullable = false, length = 200)
    private String password;  // パスワード（将来はハッシュ値を格納）

    @Column(name = "role", nullable = false, length = 20)
    private String role;      // 権限: 'ADMIN' / 'USER' など

    @Column(name = "enabled", nullable = false)
    private boolean enabled;  // 有効フラグ（1=有効, 0=無効）

    // --- コンストラクタ ---

    // JPA 用のデフォルトコンストラクタ（必須）
    protected User() {
    }

    // 便利コンストラクタ
    public User(String username, String password, String role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    // --- getter / setter ---

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // --- 便利メソッド（任意）---

    /**
     * 管理者かどうかを判定するヘルパー
     */
    public boolean isAdmin() {
        return "ADMIN".equalsIgnoreCase(this.role);
    }
}
