package com.github.haseoo.courier.services.adapters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.haseoo.courier.exceptions.serviceexceptions.parcelsexceptions.InvalidPostalCode;
import com.github.haseoo.courier.servicedata.places.PolishPostalCodeApiData;
import com.github.haseoo.courier.services.ports.PostalCodeHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.github.haseoo.courier.utilities.Constants.*;

@Service
@RequiredArgsConstructor
public class PolishPostalCodeHelperImpl implements PostalCodeHelper {
    @Value("${app.postalCode.provider.urlFormat}")
    private String providerApiUrlFormat;

    @Override
    public boolean isPostalCodeInCity(String postalCode, String city) throws IOException {
        URL url = getApiUrl(postalCode);
        HttpURLConnection con = prepareConnection(url);
        if (readApiResponse(con, (stringStream -> stringStream.allMatch(city::equalsIgnoreCase)))) {
            return true;
        }
        con.disconnect();
        return false;
    }

    @Override
    public void validatePostalCode(String city, String postalCode) {
        URL url;
        HttpURLConnection con;
        try {
            url = getApiUrl(postalCode);
            con = prepareConnection(url);
        } catch (IOException e) {
            //if service is not working it is not possible to validate postal code
            return;
        }
        try {
            if (!readApiResponse(con, stringStream -> stringStream.anyMatch(city::equalsIgnoreCase))) {
                throw new InvalidPostalCode(postalCode);
            }
        } catch (UnknownHostException e) {
            //if service is not working it is not possible to validate postal code
        } catch (IOException e) {
            //if there were an server exception it means postal code is not valid
            throw new InvalidPostalCode(postalCode);
        }
    }

    private boolean readApiResponse(HttpURLConnection con,
                                    Predicate<Stream<String>> check) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            PolishPostalCodeApiData[] data = objectMapper.readValue(content.toString().getBytes(), PolishPostalCodeApiData[].class);
            if (check.test(Arrays.stream(data).map(PolishPostalCodeApiData::getLocality))) {
                return true;
            }
        } catch (Exception e) {
            //404 means postal code not exists, other errors passes this test
            return con.getResponseCode() != 404;
        }
        return false;
    }

    private HttpURLConnection prepareConnection(URL url) throws IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(POSTAL_CODE_SERVICE_HTTP_METHOD_NAME);
        con.setRequestProperty(POSTAL_CODE_SERVICE_HTTP_METHOD_HEADER_FILED_NAME,
                POSTAL_CODE_SERVICE_HTTP_METHOD_MEDIA_TYPE);
        return con;
    }

    private URL getApiUrl(String postalCode) throws MalformedURLException {
        return new URL(String.format(providerApiUrlFormat, postalCode));
    }
}
