package com.github.haseoo.courier.utilities;

import com.github.haseoo.courier.enums.EmployeeType;
import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.InvalidPeselException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.InvalidPeselFormatException;
import com.github.haseoo.courier.exceptions.serviceexceptions.userexceptions.employees.InvalidEmployeeInstanceException;
import com.github.haseoo.courier.models.*;
import com.github.haseoo.courier.security.UserRole;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static com.github.haseoo.courier.enums.UserType.ADMIN;
import static com.github.haseoo.courier.utilities.Constants.*;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserUtils {
    private static Map<Class<? extends UserModel>, UserType> userTypeMap;
    private static Map<Class<? extends UserModel>, UserRole> userRoleMap = new HashMap<>();

    static {
        userTypeMap = new HashMap<>();
        userTypeMap.put(CourierModel.class, UserType.COURIER);
        userTypeMap.put(LogisticianModel.class, UserType.LOGISTICIAN);
        userTypeMap.put(ClientIndividualModel.class, UserType.INDIVIDUAL_CLIENT);
        userTypeMap.put(ClientCompanyModel.class, UserType.COMPANY_CLIENT);
        userRoleMap.put(ClientIndividualModel.class, UserRole.CLIENT);
        userRoleMap.put(ClientCompanyModel.class, UserRole.CLIENT);
        userRoleMap.put(CourierModel.class, UserRole.COURIER);
        userRoleMap.put(LogisticianModel.class, UserRole.LOGISTICIAN);
    }

    public static EmployeeType getEmployeeType(EmployeeModel employeeModel) {
        if (employeeModel instanceof CourierModel) {
            return EmployeeType.COURIER;
        }
        if (employeeModel instanceof LogisticianModel) {
            return EmployeeType.LOGISTICIAN;
        }
        throw new InvalidEmployeeInstanceException();
    }

    public static UserRole getUserRole(UserModel userModel) {
        if (userModel.getUserName().equals(ADMIN_USERNAME)) {
            return UserRole.ADMIN;
        }
        return userRoleMap.get(userModel.getClass());
    }

    public static UserType getUserType(UserModel userModel) {
        if (userModel.getUserName().equals(ADMIN_USERNAME)) {
            return ADMIN;
        }
        return userTypeMap.get(userModel.getClass());
    }

    public static boolean tryPesel(String pesel) {
        if (validatePeselString(pesel)) {
            return false;
        }
        return isChecksumValid(pesel);
    }

    public static void validatePesel(String pesel) {
        if (validatePeselString(pesel)) {
            throw new InvalidPeselFormatException();
        }
        if (!isChecksumValid(pesel)) {
            throw new InvalidPeselException();
        }
    }

    private static int getCheckDigit(String pesel) {
        return Integer.parseInt(pesel.substring(pesel.length() - 1));
    }

    private static Integer calculateChecksum(String pesel) {
        int checksum = 0;
        for (int i = 0; i < pesel.length() - 1; i++) {
            checksum += Integer.parseInt(pesel.substring(i, i + 1)) * peselWeights.get(i);
        }
        return checksum;
    }

    private static boolean validatePeselString(String pesel) {
        return pesel.matches(PESEL_REGEX) || pesel.length() != PESEL_LENGTH;
    }

    private static boolean isChecksumValid(String pesel) {
        int checksum = calculateChecksum(pesel);
        int checkDigit = getCheckDigit(pesel);
        checksum %= TEN_CUT;
        checksum = TEN_CUT - checksum;
        checksum %= TEN_CUT;
        return checksum == checkDigit;
    }
}
