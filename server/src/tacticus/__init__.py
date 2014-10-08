from flask import Flask
from flask.ext.sqlalchemy import SQLAlchemy

app = Flask(__name__)
app.config.from_object('config')
db = SQLAlchemy(app)

from routes import *
from models import *

u = User("stevex86", "6027520045")
u2 = User("sking", "4352293879")
db.session.add(u)
db.session.add(u2)
db.session.commit()