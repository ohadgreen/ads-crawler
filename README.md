# ads-crawler
Home assignment for Infolinks. Crawl and analyse ads.txt for given sites
Assumptions:
1. There is a MySql database up with 3 tables (see tables_ddl.sql file)
2. If the sites table is not populated, it will try to insert records from sites_list.csv file in resources directory

It will then run the crawler process for each of the sites, in parallel according to number of threads defined in application.properties
For each url it will:
1. If the url/ads.txt not available it will update sites table with the HTTP error message
2. If the file available, it will insert all valid lines into sites_ads table

After crawl completion, we can analyse the results using queries in analysis_queries.sql file