from flask import Flask
from flask.ext.sqlalchemy import SQLAlchemy
from datetime import datetime, timedelta

app = Flask(__name__)
app.config.from_object('config')
db = SQLAlchemy(app)
db.create_all()

from routes import *
from models import *

u = User("stevex86", "6027520045")
u2 = User("sking", "4352293879")
db.session.add(u)
db.session.add(u2)
db.session.commit()
print u
print u2
kb = Kickback(user=u, start=datetime.now(), end=datetime.now() + timedelta(hours=24))
db.session.add(kb)
db.session.commit()
print kb
