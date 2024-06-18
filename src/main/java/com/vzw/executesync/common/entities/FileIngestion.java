package com.vzw.executesync.common.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "om_file_ingestion_status")
public class FileIngestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iid")
    private Integer id;

    @Column(name = "technology")
    private String technology;

    @Column(name = "vendor")
    private String vendor;

    @Column(name = "region")
    private String region;

    @Column(name = "market_id")
    private String marketId;

    @Column(name = "ems_host_name")
    private String emsHostName;

    @Column(name = "raw_file_name")
    private String rawFileName;

    @Column(name = "s3_bucket_path")
    private String s3BucketPath;

    @Column(name = "execsync_kafka_topic_name")
    private String execsyncKafkaTopicName;

    @Column(name = "execsync_start_datetime")
    private LocalDateTime execsyncStartDatetime;

    @Column(name = "execsync_end_datetime")
    private LocalDateTime execsyncEndDatetime;

    @Column(name = "parser_kafka_topic_name")
    private String parserKafkaTopicName;

    @Column(name = "parser_start_datetime")
    private LocalDateTime parserStartDatetime;

    @Column(name = "parser_end_datetime")
    private LocalDateTime parserEndDatetime;

    @Column(name = "parser_output_record_count")
    private Integer parserOutputRecordCount;

    @Column(name = "status")
    private String status;

    @Column(name = "error_log", columnDefinition = "TEXT")
    private String errorLog;

    @Column(name = "date_modified")
    private LocalDateTime dateModified;
}
