ALTER table appointments add column canceled tinyint not null default 0;
ALTER table appointments add column canceled_reason varchar(100);
ALTER table appointments add column canceled_at datetime;