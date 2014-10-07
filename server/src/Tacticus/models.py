from sqlalchemy import Table, Column, Integer, String, ForeignKey
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
    name = Column(String)

    followed = relationship("User",
                            secondary="user_to_user",
                            primaryjoin=id==user_to_user.c.follower_id,
                            secondaryjoin=id==user_to_user.c.followed_id,
                            backref="followers",
                            lazy="dynamic")

    def __init__(self, name):
        self.name = name

    def __repr__(self):
        return "<User {0} {1}>".format(self.id, self.name)

    def follow(self, user):
        if not self.is_following(user):
            self.followed.append(user)
            return self

    def unfollow(self, user):
        if self.is_following(user):
            self.followed.remove(user)
            return self

    def is_following(self, user):
        return self.followed.filter(user_to_user.c.followed_id == user.id).count() > 0

Base.metadata.create_all(db.engine)