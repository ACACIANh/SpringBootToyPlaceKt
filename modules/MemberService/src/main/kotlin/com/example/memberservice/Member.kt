package com.example.memberservice

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val nickname: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val memberStatus: MemberStatus = MemberStatus.ACTIVE,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

enum class MemberStatus {
    ACTIVE, INACTIVE, DELETED
}

@Entity
@Table(name = "member_auth")
class MemberAuth(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val authType: AuthType,

    @Column(nullable = true, unique = true)
    val providerId: String?,

    @Column(nullable = true)
    val password: String?
)

enum class AuthType {
    EMAIL, GOOGLE, KAKAO, APPLE
}

@Entity
@Table(name = "member_setting")
class MemberSetting(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @Column(nullable = false)
    val notificationsEnabled: Boolean = true,

    @Column(nullable = false)
    val darkModeEnabled: Boolean = false,

    @Column(nullable = false)
    val language: String = "en"
)

@Entity
@Table(name = "member_device")
class MemberDevice(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,

    @Column(nullable = false)
    val deviceType: DeviceType,

    @Column(nullable = false)
    val fcmToken: String
)

enum class DeviceType {
    IOS, ANDROID, WEB
}
