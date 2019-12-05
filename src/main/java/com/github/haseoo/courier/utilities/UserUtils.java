package com.github.haseoo.courier.utilities;

import com.github.haseoo.courier.enums.EmployeeType;
import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.exceptions.serviceexceptions.InvalidEmployeeInstance;
import com.github.haseoo.courier.models.*;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserUtils {
    private static Map<Class<?extends UserModel>, UserType> userTypeMap;
    static {
        userTypeMap = new HashMap<>();
        userTypeMap.put(CourierModel.class, UserType.COURIER);
        userTypeMap.put(LogisticianModel.class, UserType.LOGISTICIAN);
        userTypeMap.put(ClientIndividualModel.class, UserType.INDIVIDUAL_CLIENT);
        userTypeMap.put(ClientCompanyModel.class, UserType.COMPANY_CLIENT);
    }
    public static EmployeeType getEmployeeType(EmployeeModel employeeModel) {
        if (employeeModel instanceof CourierModel) {
            return  EmployeeType.COURIER;
        }
        if (employeeModel instanceof LogisticianModel) {
            return EmployeeType.LOGISTICIAN;
        }
        throw new InvalidEmployeeInstance();
    }

    public static UserType getUserType(UserModel userModel) {
        return userTypeMap.get(userModel.getClass());
    }
}
