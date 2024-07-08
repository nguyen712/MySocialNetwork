package com.demospring.socialnetwork.service.iservice;

import com.demospring.socialnetwork.dto.response.GeoResponseData;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.UnknownHostException;

@Service
public interface IGeoLocationService {
    GeoResponseData getLocationById(String clientIp) throws IOException, GeoIp2Exception;
}
