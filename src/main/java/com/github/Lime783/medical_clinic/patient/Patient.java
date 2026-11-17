package com.github.Lime783.medical_clinic.patient;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@ToString(exclude = {"password", "idCardNo"})
@EqualsAndHashCode(of = {"idCardNo", "firstName", "lastName"})
public class Patient {
    private final UUID id;
    private String email;
    private String password;
    private final String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthday;

    @Builder
    public Patient(String email, String password, String idCardNo, String firstName, String lastName, String phoneNumber, LocalDate birthday) {
        validateData(email, password, idCardNo, firstName, lastName, phoneNumber, birthday);
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.idCardNo = idCardNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
    }

    private void validateData(String email, String password, String idCardNo, String firstName, String lastName, String phoneNumber, LocalDate birthday) {
        validateEmail(email);
        validatePassword(password);
        validateIdCardNo(idCardNo);
        validateFirstName(firstName);
        validateLastName(lastName);
        validatePhoneNumber(phoneNumber);
        validateBirthday(birthday);
    }

    private void validateEmail(String newEmail) {
        if (newEmail.length() < 3) {
            throw new IllegalArgumentException("Email length should be at least 3 characters: " + newEmail);
        }
    }

    private void validatePassword(String newPassword) {
        if (newPassword.length() < 3) {
            throw new IllegalArgumentException("Password length should be at least 3 characters: " + newPassword);
        }
    }

    private void validateIdCardNo(String newIdCardNo) {
        if (newIdCardNo.length() < 3) {
            throw new IllegalArgumentException("ID card length should be at least 3 characters: " + newIdCardNo);
        }
    }

    private void validateFirstName(String newFirstName) {
        if (newFirstName.length() < 3) {
            throw new IllegalArgumentException("First name length should be at least 3 characters: " + newFirstName);
        }
    }

    private void validateLastName(String newLastName) {
        if (newLastName.length() < 3) {
            throw new IllegalArgumentException("Last name length should be at least 3 characters: " + newLastName);
        }
    }

    private void validatePhoneNumber(String newPhoneNumber) {
        if (newPhoneNumber.length() < 3) {
            throw new IllegalArgumentException("Phone number length should be at least 3 characters: " + newPhoneNumber);
        }
    }

    private void validateBirthday(LocalDate newBirthday) {
        if (newBirthday.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Birthday should not be after now: " + newBirthday);
        }
    }

    public void changeEmail(String newEmail) {
        validateEmail(newEmail);
        this.email = newEmail;
    }

    public void changePassword(String newPassword) {
        validatePassword(newPassword);
        this.password = newPassword;
    }

    public void changeFirstName(String newFirstName) {
        validateFirstName(newFirstName);
        this.firstName = newFirstName;
    }

    public void changeLastName(String newLastName) {
        validateLastName(newLastName);
        this.lastName = newLastName;
    }

    public void changePhoneNumber(String newPhoneNumber) {
        validatePhoneNumber(newPhoneNumber);
        this.phoneNumber = newPhoneNumber;
    }

    public void changeBirthday(LocalDate newBirthday) {
        validateBirthday(newBirthday);
        this.birthday = newBirthday;
    }
}
