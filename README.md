Use of multiple DataSources in Spring Batch
==============================================

How to java-configure separate datasources for spring batch data and business data? 
Should I even do it?

Is it possible to use 2 databases simultaneously, 
the standard one for data processing and keep the embedded HSQLDB (or any other database) 
only for the framework?
 
I am trying to configure a couple of datasources within Spring Batch. 
On startup, Spring Batch is throwing the following exception:
    To use the default BatchConfigurer the context must contain no more thanone DataSource, found 2
    