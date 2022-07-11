#! /bin/bash

echo "Please enter root user MySQL password"
echo "Note: password will be hidden when typing"
read -s rootpasswd

# Prepare variables
TABLE="questions"
SQL_EXISTS=$(printf 'SHOW TABLES LIKE "%s"' "$TABLE")
SQL_IS_EMPTY=$(printf 'SELECT 1 FROM %s LIMIT 1' "$TABLE")

# Credentials
USERNAME=wojadmin
PASSWORD=wojadmin
DATABASE=wheelofjeopardy

RESULT_VARIABLE="$(mysql -uroot -p${rootpasswd} -sse "SELECT EXISTS(SELECT 1 FROM mysql.user WHERE user = '${USERNAME}')")"

if [ "$RESULT_VARIABLE" = 0 ];
then
	echo "User does not exist"
	exit 1;
else
	if [[ $(mysql -u${USERNAME} -p ${PASSWORD} -e "$SQL_EXISTS" $DATABASE) ]]
	then
		echo "Table exists.."
		echo "Loading data..."
		
		if [[ $(mysql -u${USERNAME} -p ${PASSWORD} -e "$SQL_IS_EMPTY" $DATABASE) ]]
		then
			mysql --local-infile=1 -u${USERNAME} -p${PASSWORD} -e  "USE ${DATABASE}; LOAD DATA LOCAL INFILE './jeopardy.csv' INTO TABLE ${TABLE} FIELDS TERMINATED BY ',' ENCLOSED BY '\"' LINES TERMINATED BY '\n' IGNORE 1 ROWS;"
		else
			echo "Table is not empty"
			exit 1
		fi
	else
		echo "Database or table does not exist"
		exit 1
	fi
fi
