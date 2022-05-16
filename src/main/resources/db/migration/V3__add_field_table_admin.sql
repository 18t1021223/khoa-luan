alter table admin
add verify_token varchar(150);

alter table admin add unique (`verify_token`);