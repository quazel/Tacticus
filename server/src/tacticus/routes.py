from tacticus import app, db
from models import User
from flask import jsonify

session = db.session

@app.route('/')
def index():
    return "Hello World!"

@app.route('/api/<username>')
def get_user(username):
    user = session.query(User).filter_by(name=username).first()
    if user:
        return jsonify(user.serialize())
    else:
        return "", 404