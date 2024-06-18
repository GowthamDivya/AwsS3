package com.vzw.executesync.service;

import com.vzw.executesync.common.entities.ExecsyncConfig;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ExecsyncConfigDummyDataGenerator {

    public static List<ExecsyncConfig> generateDummyData(int numberOfRecords) {
        List<ExecsyncConfig> dummyData = new ArrayList<>();

        for (int i = 1; i <= numberOfRecords; i++) {
            ExecsyncConfig config = new ExecsyncConfig();
            config.setId(i);
            config.setEmsName("EMS-" + i);
            config.setRocRegion("Region-" + i);
            config.setMarketId("Market-" + i);
            config.setEmsType("Type-" + i);
            config.setEmsHostName("ems-host-" + i);
            config.setEmsIpAddressV4("192.168.0." + i);
            config.setEmsIpAddressV6("::" + i);
            config.setEmsRawfilePath("/path/to/rawfile-" + i);
            config.setEmsLsExpression("ls_expression_" + i);
            config.setEmsLoginId("login-" + i);
            config.setEmsEncryptedPassword("password-" + i);
            config.setPgServerHostName("pg-server-" + i);
            config.setPgServerIpAddressV4("10.0.0." + i);
            config.setScpThreadCount(5 + i);
            config.setS3BucketPath("s3://bucket/path-" + i);
            config.setS3AccessKey("access-key-" + i);
            config.setS3SecretKey("secret-key-" + i);
            config.setKafkaTopicName("topic-" + i);
            config.setKafkaHostName("kafka-host-" + i);
            config.setKafkaPortNo("9092");
            config.setKafkaSslFileName("ssl-file-" + i);
            config.setIsActive('Y');
            config.setDateModified(new Timestamp(System.currentTimeMillis()));

            dummyData.add(config);
        }

        return dummyData;
    }

    public static void main(String[] args) {
        List<ExecsyncConfig> dummyConfigs = generateDummyData(10);

        // Print out the generated dummy data
        for (ExecsyncConfig config : dummyConfigs) {
            System.out.println(config);
        }
    }
}
