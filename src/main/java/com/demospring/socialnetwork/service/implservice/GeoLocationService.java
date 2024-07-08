package com.demospring.socialnetwork.service.implservice;

import com.demospring.socialnetwork.dto.response.GeoResponseData;
import com.demospring.socialnetwork.service.iservice.IGeoLocationService;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GeoLocationService implements IGeoLocationService {


    @Override
    public GeoResponseData getLocationById(String clientIp) throws IOException, GeoIp2Exception {
        File database = new File("D:\\larion\\GeoLite2-City_20240705\\GeoLite2-City.mmdb");
        DatabaseReader dbReader = new DatabaseReader.Builder(database)
                .build();
        InetAddress ipAddress = InetAddress.getByName(clientIp);
        CityResponse response = dbReader.city(ipAddress);
        System.out.println(response.getLocation().getLongitude());
        System.out.println(response.getLocation().getLatitude());
        return GeoResponseData.builder()
                .longitude(response.getLocation().getLongitude())
                .latitude(response.getLocation().getLatitude())
                .build();
    }
}
