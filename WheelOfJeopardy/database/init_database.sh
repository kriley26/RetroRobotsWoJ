#! /bin/bash

echo "Please enter root user MySQL password"
echo "Note: password will be hidden when typing"
read -s rootpasswd

# Prepare variables
TABLE="question"
SQL_EXISTS=$(printf 'SHOW TABLES LIKE "%s"' "$TABLE")
SQL_IS_EMPTY=$(printf 'SELECT 1 FROM %s LIMIT 1' "$TABLE")

# Credentials
USERNAME=wojadmin
PASSWORD=wojadmin
DATABASE=wheelofjeopardy

echo "Checking if user ${USERNAME}"
RESULT_VARIABLE="$(mysql -uroot -p${rootpasswd} -sse "SELECT EXISTS(SELECT 1 FROM mysql.user WHERE user = '${USERNAME}')")"

if [ "$RESULT_VARIABLE" = 1 ];
then
	echo "User ${USERNAME} exists"
else
	echo "Creating user..."
	mysql -uroot -p${rootpasswd} -e "CREATE USER ${USERNAME}@localhost IDENTIFIED BY '${PASSWORD}';"
	echo "User successfully created"
	
	echo "Granting ALL privileges on ${DATABASE} to ${USERNAME}"
	mysql -uroot -p${rootpasswd} -e "CREATE DATABASE ${DATABASE};"
	mysql -uroot -p${rootpasswd} -e "GRANT ALL PRIVILEGES ON ${DATABASE}.* TO '${USERNAME}'@'localhost';"
	mysql -uroot -p${rootpasswd} -e "FLUSH PRIVILEGES;"
	echo "Done!"
fi

echo "Checking if table ${TABLE} exists.... "

# Check if table exists
if [[ $(mysql -u${USERNAME} -p${PASSWORD} -e "$SQL_EXISTS" $DATABASE) ]]
then 
	echo "Table exists ..."
	
	# Check if table has records
	if [[ $(mysql -u${USERNAME} -p${PASSWORD} -e "$SQL_IS_EMPTY" $DATABASE) ]]
	then
		echo "Table has records..."
	else
		echo "Tale is empty..."
	fi
else
	echo "Table not exists...."
	mysql -u${USERNAME} -p${PASSWORD} -e "SHOW DATABASES;"
	mysql -u${USERNAME} -p${PASSWORD} -e "USE ${DATABASE}; CREATE TABLE ${TABLE} (id int NOT NULL AUTO_INCREMENT, show_number int, air_date varchar(255), round varchar(255), category varchar(255), value varchar(255), question text(1000), answer varchar(255), PRIMARY KEY(id));"
fi
