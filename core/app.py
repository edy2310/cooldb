from flask import Flask, escape, request, Response
import database as db
import apptoken 

app = Flask(__name__)

@app.route("/core/verifydatabase/<name>/<token>", methods = ['GET'])
def verifydatabase(name, token):
    if apptoken.verify(token):
        if db.verify(name):
            return Response("Database already exist", status=302)
        else:
            return Response("Database doesn't exist")
    else:
        return Response("Invalid authorization", status=401)


@app.route('/core/createdatabase/<token>',  methods = ['POST'])
def createdatabase(token):
    if apptoken.verify(token):
        name = request.json.get('name')
        password = request.json.get('password')
        permission = request.json.get('permission')
        if db.create(name, password, permission):
            return Response("Database created", status=201)
        else:
            return Response("Error creating database", status=400)
    else:
        return Response("Invalid authorization", status=401)

@app.route('/core/deletedatabase/<name>/<token>', methods = ['DELETE'])
def deletedatabase(name, token):
    if apptoken.verify(token):
        if db.delete(name):
            return Response("Database deleted")
        else:
            return Response("Error deleting database", status=400)
    else:
        return Response("Invalid authorization", status=401)

@app.route('/core/updatepassword/<name>/<token>', methods = ['PUT'])
def updatepassword(name, token):
    if apptoken.verify(token):
        password = request.json.get('password')
        if(db.update(name, password)):
            return Response("Password updated")
        else:
            return Response("Unable to update password", status=400)
    else:
        return Response("Invalid authorization", status=401)

if __name__ == '__main__':
    app.run()