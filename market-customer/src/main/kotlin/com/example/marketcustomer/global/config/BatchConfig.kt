package com.example.marketcustomer.global.config

import com.example.marketcustomer.domain.model.OrderEntity
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.scheduling.annotation.Scheduled
import java.time.OffsetDateTime
import javax.sql.DataSource

@EnableBatchProcessing
@Configuration
class BatchConfig(
    val jobBuilderFactory: JobBuilderFactory,
    val stepBuilderFactory: StepBuilderFactory,
    val dataSource: DataSource,
    val jobLauncher: JobLauncher,
) {
    companion object {
        const val ORDER_ITEM_READER = "ORDER_ITEM_READER"
        const val ORDER_ITEM_TO_CSV_JOB = "ORDER_ITEM_TO_CSV_JOB"
        const val ORDER_ITEM_TO_CSV_STEP = "ORDER_ITEM_TO_CSV_STEP"
        const val ORDER_ITEM_TO_CSV_WRITER = "ORDER_ITEM_TO_CSV_WRITER"
        const val chunkSize = 10
        const val FIND_ALL_ORDERS_QUERY = "SELECT id, name, price FROM tbl_order"
        const val CSV_FILE_NAME = "receipt.csv"
    }

    @Bean
    @StepScope
    fun itemReader(@Value("#{jobParameters[requestDate]}") requestDate: String?): JdbcCursorItemReader<OrderEntity> {
        return JdbcCursorItemReaderBuilder<OrderEntity>()
            .name(ORDER_ITEM_READER)
            .dataSource(dataSource)
            .sql(FIND_ALL_ORDERS_QUERY)
            .rowMapper { resultSet, _ ->
                OrderEntity(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price")
                )
            }
            .build()
    }

    @Bean
    @StepScope
    fun itemWriter(@Value("#{jobParameters[requestDate]}") requestDate: String?): FlatFileItemWriter<OrderEntity> {
        return FlatFileItemWriterBuilder<OrderEntity>()
            .name(ORDER_ITEM_TO_CSV_WRITER)
            .headerCallback {
                it.write("id,name,price")
            }
            .resource(FileSystemResource("src/main/resources/$CSV_FILE_NAME"))
            .delimited()
            .delimiter(",")
            .names("id", "name", "price")
            .build()
    }

    @Bean
    fun exportJob(): Job {
        return jobBuilderFactory
            .get(ORDER_ITEM_TO_CSV_JOB)
            .start(exportStep())
            .build()
    }

    @Bean
    fun exportStep(): Step {
        return stepBuilderFactory
            .get(ORDER_ITEM_TO_CSV_STEP)
            .chunk<OrderEntity, OrderEntity>(chunkSize)
            .reader(itemReader(null))
            .writer(itemWriter(null))
            .build()
    }

    @Scheduled(cron = "*/10 * * * * *")
    fun launchJob() {
        val jobParameterMap = mapOf("requestDate" to JobParameter(OffsetDateTime.now().toString()))
        jobLauncher.run(exportJob(), JobParameters(jobParameterMap))
    }
}
