package com.github.haseoo.courier.utilities;

import com.github.haseoo.courier.enums.ParcelStateType;
import com.github.haseoo.courier.exceptions.serviceexceptions.InvalidEmailAddress;
import com.github.haseoo.courier.servicedata.parcels.ParcelData;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import static com.github.haseoo.courier.utilities.Constants.EMAIL_REGEX_EXPRESSION;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class Utils {
    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static void validateEmailAddress(String emailAddress) {
        if (!emailAddress.matches(EMAIL_REGEX_EXPRESSION)) {
            throw new InvalidEmailAddress(emailAddress);
        }
    }

    public static boolean isParcelMoveable(ParcelData parcelData) {
        ParcelStateType currentState = parcelData.getCurrentState().getState();
        return currentState != ParcelStateType.ASSIGNED &&
                currentState != ParcelStateType.AT_COURIER &&
                !parcelData.getDateMoved();
    }

    public static LocalDate addWorkdays(LocalDate date, int workdays) {
        if (workdays < 1) {
            return date;
        }

        LocalDate result = date;
        int addedDays = 0;
        while (addedDays < workdays) {
            result = result.plusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY ||
                    result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++addedDays;
            }
        }

        return result;
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
