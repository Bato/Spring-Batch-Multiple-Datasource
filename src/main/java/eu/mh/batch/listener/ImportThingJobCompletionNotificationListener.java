package eu.mh.batch.listener;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import eu.mh.batch.model.Thing;

public class ImportThingJobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(ImportThingJobCompletionNotificationListener.class);

	private static final String SQL_1 = "SELECT name, descriptor FROM things";

	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public ImportThingJobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			List<Thing> results = jdbcTemplate.query(SQL_1, new RowMapper<Thing>() {
				@Override
				public Thing mapRow(ResultSet rs, int row) throws SQLException {
					return new Thing(rs.getString(1), rs.getString(2));
				}
			});

			for (Thing person : results) {
				log.info("Found <" + person + "> in the database.");
			}

		}
	}
}
