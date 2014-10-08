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
u3 = User("nfegard", "1242353424")
u4 = User("bitcoin", "2394352342")
u5 = User("implausible", "2354523454")
u6 = User("burgle", "09878907088")
db.session.add(u)
db.session.add(u2)
db.session.add(u3)
db.session.add(u4)
db.session.add(u5)
db.session.add(u6)
db.session.commit()
u.follow(u2)
u.follow(u3)
u.follow(u6)
u2.follow(u3)
u2.follow(u4)
u2.follow(u5)
u3.follow(u4)
u3.follow(u5)
u6.follow(u5)
db.session.commit()
kb = Kickback(user=u, start=datetime.now(), end=datetime.now() + timedelta(hours=24))
db.session.add(kb)
db.session.commit()
