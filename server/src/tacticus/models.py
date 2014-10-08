from sqlalchemy import Table, Column, Integer, String, ForeignKey, DateTime
from sqlalchemy.orm import relationship, backref
from sqlalchemy.ext.declarative import declarative_base
from tacticus import db

Base = declarative_base()

user_to_user = Table("user_to_user", Base.metadata,
                     Column("follower_id", Integer, ForeignKey("user.id"), primary_key=True),
                     Column("followed_id", Integer, ForeignKey("user.id"), primary_key=True))


class User(Base):
    __tablename__ = "user"
    id = Column(Integer, primary_key=True, autoincrement=True)
    name = Column(String(12))
    phone_number = Column(String(14))
    followed = relationship("User",
                            secondary="user_to_user",
                            primaryjoin=id==user_to_user.c.follower_id,
                            secondaryjoin=id==user_to_user.c.followed_id,
                            backref="followers",
                            lazy="dynamic")
    kickbacks = relationship("Kickback", backref="user", lazy="dynamic")

    def __init__(self, name, phone_number):
        self.name = name
        self.phone_number = phone_number

    def __repr__(self):
        return "<User {0} {1}>".format(self.id, self.name)

    def follow(self, user):
        if not self.is_following(user):
            self.followed.append(user)
            user.followed.append(self)
            return self

    def unfollow(self, user):
        if self.is_following(user):
            self.followed.remove(user)
            user.followed.remove(self)
            return self

    def is_following(self, user):
        return self.followed.filter(user_to_user.c.followed_id == user.id).count() > 0

    def serialize(self):
        return {
            "name": self.name,
            "phone_number": self.phone_number,
            "kickbacks": [k.serialize() for k in self.kickbacks.all()],
            "friends": [f.name for f in self.followed]
        }


class Kickback(Base):
    __tablename__ = "kickback"
    id = Column(Integer, primary_key=True, autoincrement=True)
    user_id = Column(Integer, ForeignKey("user.id"))
    start = Column(DateTime(timezone=True))
    end = Column(DateTime(timezone=True))

    def __init__(self, user, start, end):
        self.user_id = user.id
        self.start = start
        self.end = end

    def __repr__(self):
        return "<Kickback {0} From {1} Until {2}>".format(self.id, self.start, self.end)

    def serialize(self):
        return {
            "from": self.start,
            "until": self.end
        }

Base.metadata.create_all(db.engine)