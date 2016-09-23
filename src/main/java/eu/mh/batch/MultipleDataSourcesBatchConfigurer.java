package eu.mh.batch;


import javax.sql.DataSource;

import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@SuppressWarnings("unused")
@Component
public class MultipleDataSourcesBatchConfigurer extends BasicBatchConfigurer {

	@Autowired(required=true)
	protected MultipleDataSourcesBatchConfigurer(BatchProperties properties, @Qualifier("hsqlEmbeddedDataSource")  DataSource dataSource) {
		super(properties, dataSource);
	}

	@Autowired(required=true)
	@Qualifier("hsqlEmbeddedDataSource")
    private DataSource hsqlEmbeddedDataSource;
	
//	private PlatformTransactionManager transactionManager;
	private JobRepository jobRepository;

//	@Override
//	public JobRepository getJobRepository() {
//		if (jobRepository == null) {
//			try {
//				this.jobRepository =  new MapJobRepositoryFactoryBean(getTransactionManager()).getJobRepository();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return this.jobRepository;
//	}

    
	@Override
	public JobRepository getJobRepository() {
		if (jobRepository == null) {
		        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
		        // use the autowired data source
		        factory.setDataSource(hsqlEmbeddedDataSource);
		        factory.setTransactionManager(getTransactionManager());
		        try {
					factory.afterPropertiesSet();
			        jobRepository = factory.getObject();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return this.jobRepository;
	}

//	@Override
//	public PlatformTransactionManager getTransactionManager() {
//		if (this.transactionManager == null) {
//			this.transactionManager = new ResourcelessTransactionManager();
//		}
//		return this.transactionManager;	
//	}

}
