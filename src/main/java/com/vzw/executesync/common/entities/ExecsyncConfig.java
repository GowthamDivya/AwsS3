package com.vzw.executesync.common.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Table(name = "execsync_config")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ExecsyncConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iid")
    private Integer id;

    @Column(name = "ems_name")
    private String emsName;

    @Column(name = "roc_region")
    private String rocRegion;

    @Column(name = "market_id")
    private String marketId;

    @Column(name = "ems_type")
    private String emsType;

    @Column(name = "ems_host_name")
    private String emsHostName;

    @Column(name = "ems_ip_address_v4")
    private String emsIpAddressV4;

    @Column(name = "ems_ip_address_v6")
    private String emsIpAddressV6;

    @Column(name = "ems_rawfile_path")
    private String emsRawfilePath;

    @Column(name = "ems_ls_expression")
    private String emsLsExpression;

    @Column(name = "ems_login_id")
    private String emsLoginId;

    @Column(name = "ems_encrypted_password")
    private String emsEncryptedPassword;

    @Column(name = "pgserver_host_name")
    private String pgServerHostName;

    @Column(name = "pgserver_ip_address_v4")
    private String pgServerIpAddressV4;

    @Column(name = "scp_thread_count")
    private int scpThreadCount;

    @Column(name = "s3_bucket_path")
    private String s3BucketPath;

    @Column(name = "s3_access_key")
    private String s3AccessKey;

    @Column(name = "s3_secret_key")
    private String s3SecretKey;

    @Column(name = "kafka_topic_name")
    private String kafkaTopicName;

    @Column(name = "kafka_host_name")
    private String kafkaHostName;

    @Column(name = "kafka_port_no")
    private String kafkaPortNo;

    @Column(name = "kafka_ssl_file_name")
    private String kafkaSslFileName;

    @Column(name = "is_active")
    private char isActive;

    @Column(name = "date_modified")
    private Timestamp dateModified;

}
