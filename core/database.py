from configuration import connection

def verify(name):
    connection.execute("SHOW DATABASES")
    ans = connection.fetchall()
    for databaseName in ans:
        if databaseName[0] == name:
            return True
        else:
            continue
    return False
    

def create(name, password, permission):
    try:
        connection.execute("CREATE DATABASE " + name)
        connection.execute("CREATE USER '" + name + "'@'%' IDENTIFIED BY '" + password + "'")
        if permission == "RW":
            connection.execute("GRANT CREATE, DROP, SELECT, INSERT, UPDATE, DELETE, INDEX ON " + name + ".* TO '" + name + "'@'%'")
        else:
            connection.execute("GRANT SELECT, INDEX ON " + name + ".* TO '" + name + "'@'%'")
        connection.execute("FLUSH PRIVILEGES")
        return True
    except:
        return False


def delete(name):
    try:
        connection.execute("DROP DATABASE " + name)
        connection.execute("DROP USER '" + name + "'@'%'")
        return True
    except:
        return False

def update(name, password):
    try:
        connection.execute("ALTER USER '" + name + "'@'%' IDENTIFIED BY '" + password + "'")
        return True
    except:
        return False