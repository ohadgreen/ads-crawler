-- get all sites that have specific ads.txt line in master list
select s.*
from sites_ads_small sa inner join sites s on s.site_id = sa.site_id
inner join master_ads ma on
	ma.exchange_domain = sa.exchange_domain and
    ma.seller_account = sa.seller_account and
    ma.payment_type = sa.payment_type and
    ma.tag_id = sa.tag_id
where
	ma.exchange_domain = 'adtech.com' and
    ma.seller_account = 11409 and
    s.crawl_error is null;

-- get all missing lines in master for given site
select ma.*
from master_ads ma
where not exists (
	select * from sites_ads_small sa
    where sa.site_id = 5777 and
    ma.exchange_domain = sa.exchange_domain and
    ma.seller_account = sa.seller_account and
    ma.payment_type = sa.payment_type and
    ma.tag_id = sa.tag_id
);

-- get all sites without ads.txt
select * from sites s where s.crawl_error like '%404%';

-- get ads.txt status for given site
select s.*,
(select count(1) from sites_ads_small where site_id = s.site_id) as ads_count
from sites s
