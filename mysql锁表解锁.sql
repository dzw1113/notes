SELECT COUNT(1) FROM km_member a WHERE a.`MB_LAST_LOGIN_TIME` > '2017-01-09 00:00:00';

SHOW OPEN TABLES WHERE In_use > 0;

-- lock table ¶ÁËø¶¨
UNLOCK TABLES;

-- Ð´Ëø¶¨
lock table ;

SELECT * FROM km_sys_sequence;


SHOW PROCESSLIST;

SELECT CONCAT('kill ',lock_trx_id,';') FROM INFORMATION_SCHEMA.INNODB_LOCKS; 


SELECT CONCAT('kill ',requesting_trx_id,';') FROM INFORMATION_SCHEMA.INNODB_LOCK_WAITS; 


SELECT CONCAT('KILL ',ID) FROM information_schema.`PROCESSLIST` WHERE STATE = 'updating';

KILL 12818321;

SHOW ENGINE INNODB STATUS;

SELECT CONCAT('kill ',lock_trx_id,';') FROM information_schema.INNODB_TRX WHERE trx_state = 'LOCK WAIT';

COMMIT;


SHOW STATUS LIKE '%lock%';



SELECT * FROM information_schema.`PROCESSLIST` 