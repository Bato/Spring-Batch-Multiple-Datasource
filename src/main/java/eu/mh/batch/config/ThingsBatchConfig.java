package eu.mh.batch.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;

import eu.mh.batch.listener.ImportThingJobCompletionNotificationListener;
import eu.mh.batch.model.Thing;
import eu.mh.batch.processor.ThingItemProcessor;

@Configuration
@EnableBatchProcessing(modular=true)
public class ThingsBatchConfig {
	
	private static final String SQL_1 = "INSERT INTO things (name, descriptor) VALUES (:name, :descriptor)";
	private static final String CSV = "data/sample-data-things.csv";
	
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
   
    @Autowired(required=true)
    @Qualifier("h2EmbeddedDataSource")
    public DataSource h2EmbeddedDataSource;
    
    @Bean
    public FlatFileItemReader<Thing> reader1() {
        FlatFileItemReader<Thing> reader = new FlatFileItemReader<Thing>();
        reader.setResource(new ClassPathResource(CSV));
        reader.setLineMapper(new DefaultLineMapper<Thing>() {{
            setLineTokenizer(new DelimitedLineTokenizer(";") {{
                setNames(new String[] { "name", "descriptor" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Thing>() {{
                setTargetType(Thing.class);
            }});
        }});
        return reader;
    }

    @Bean
    public ThingItemProcessor processor() {
        return new ThingItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Thing> writer() {
        JdbcBatchItemWriter<Thing> writer = new JdbcBatchItemWriter<Thing>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Thing>());
        writer.setSql(SQL_1);
        writer.setDataSource(h2EmbeddedDataSource);
        return writer;
    }

    @Bean
    public JobExecutionListener listener() {
        return new ImportThingJobCompletionNotificationListener(new JdbcTemplate(h2EmbeddedDataSource));
    }

    @Bean
    public Job importThingsJob() {
        return jobBuilderFactory.get("importThingsJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Thing, Thing> chunk(10)
                .reader(reader1())
                .processor(processor())
                .writer(writer())
                .build();
    }

}
