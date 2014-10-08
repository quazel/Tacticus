from tacticus import app, db
from models import User
from flask import jsonify

session = db.session

@app.route("/")
def index():
    return "Hello World!"

@app.route("/user/<username>")
def get_user(username):
    user = session.query(User).filter_by(name=username).first()
    if user:
        return jsonify(user.serialize())
    else:
        return "", 404

@app.route("/users")
def get_all_users():
    users = session.query(User).order_by(User.id).all()
    if users:
        user_dict = {}
        for user in users:
            user_dict[user.id] = user.name
        return jsonify(user_dict)
    else:
        return "", 404