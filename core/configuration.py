import mysql.connector

config = mysql.connector.connect(
  host="localhost",
  port=3301,
  user="root",
  passwd="ThisIsMyPassword2310_core"
)

connection = config.cursor()