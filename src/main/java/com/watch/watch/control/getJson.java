package com.watch.watch.control;


import com.watch.watch.domin.Device;

import com.watch.watch.domin.DeviceService;
import com.watch.watch.domin.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
public class getJson {

    @Autowired
    private JdbcTemplate jdbc;

    @PostMapping(value = "/getJson")
    @ResponseBody
    public Device getJson(@RequestBody Device device) {

        String sql = "insert into device(notifyType,requestId,deviceId," +
                "gatewayId,service) values (?,?,?,?,?) ;";
        jdbc.update(sql,device.getNotifyType(),device.getRequestId(),device.getDeviceId(),
                device.getGatewayId(),device.getService());

        DeviceService deviceService = device.getDeviceService();

        String sql1= "insert into deviceservice(serviceId,serviceType,eventTime," +
                "serviceInfo) values (?,?,?,?,?);";
        jdbc.update(sql1,deviceService.getServiceId(),deviceService.getServiceType(),
                deviceService.getEventTime(),deviceService.getServiceInfo());

        data data = deviceService.getData();
        String sql2 = "insert into data(brightness) values (?);";
        jdbc.update(sql2,data.getBrightness());

        return device;
    }
}
