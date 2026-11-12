package com.github.Lime783.medical_clinic.patient;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
@ToString(exclude = {"password", "idCardNo"})
@EqualsAndHashCode(of = {"idCardNo", "firstName", "lastName"})
public class Patient {
    private final UUID id;
    private String email;

    @NonNull
    private String password;

    private String idCardNo;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    private String phoneNumber;

    @NonNull
    private LocalDate birthday;

    public void changeEmail(String newEmail) {
        if (newEmail.length() < 3) {
            throw new IllegalArgumentException("Email length should be at least 3 characters: " + newEmail);
        }
        this.email = newEmail;
    }

    public void changePassword(String newPassword) {
        if (newPassword.length() < 3) {
            throw new IllegalArgumentException("Password length should be at least 3 characters: " + newPassword);
        }
        this.password = newPassword;
    }

    public void changeIdCardNo(String newIdCardNo) {
        if (newIdCardNo.length() < 3) {
            throw new IllegalArgumentException("ID card length should be at least 3 characters: " + newIdCardNo);
        }
        this.idCardNo = newIdCardNo;
    }

    public void changeFirstName(String newFirstName) {
        if (newFirstName.length() < 3) {
            throw new IllegalArgumentException("First name length should be at least 3 characters: " + newFirstName);
        }
        this.firstName = newFirstName;
    }

    public void changeLastName(String newLastName) {
        if (newLastName.length() < 3) {
            throw new IllegalArgumentException("Last name length should be at least 3 characters: " + newLastName);
        }
        this.lastName = newLastName;
    }

    public void changePhoneNumber(String newPhoneNumber) {
        if (newPhoneNumber.length() < 3) {
            throw new IllegalArgumentException("Phone number length should be at least 3 characters: " + newPhoneNumber);
        }
        this.phoneNumber = newPhoneNumber;
    }

    public void changeBirthday(LocalDate newBirthday) {
        if (newBirthday.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birthday should not be after now: " + newBirthday);
        }
        this.birthday = newBirthday;
    }
}
