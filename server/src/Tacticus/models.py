from Tacticus import db


class User(db.Model):
    __tablename__ = "user"
    id = db.Column(db.Integer, primary_key=True)
    name = db.Column(db.String(14), index=True)
    friends = db.relationship("User", backref="parent_user", lazy="dynamic")

    def __init__(self, name):
        self.name = name

    def __repr__(self):
        return "<User {0} {1}>".format(self.id, self.name)